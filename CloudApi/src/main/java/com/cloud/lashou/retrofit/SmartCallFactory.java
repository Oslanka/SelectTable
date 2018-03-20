package com.cloud.lashou.retrofit;

import android.util.Log;

import com.cloud.lashou.utils.AppUtils;
import com.cloud.lashou.utils.LogUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executor;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SmartCallFactory extends CallAdapter.Factory {
    private final CachingSystem cachingSystem;
    private final Executor asyncExecutor;

    public SmartCallFactory(CachingSystem cachingSystem) {
        this.cachingSystem = cachingSystem;
        this.asyncExecutor = new AndroidExecutor();
    }

    public SmartCallFactory(CachingSystem cachingSystem, Executor executor) {
        this.cachingSystem = cachingSystem;
        this.asyncExecutor = executor;
    }

    @Override
    public CallAdapter<SmartCall<?>> get(final Type returnType, final Annotation[] annotations,
                                         final Retrofit retrofit) {

        TypeToken<?> token = TypeToken.of(returnType);
        if (token.getRawType() != SmartCall.class) {
            return null;
        }

        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    "SmartCall must have generic type (e.g., SmartCall<ResponseBody>)");
        }

        final Type responseType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
        final Executor callbackExecutor = asyncExecutor;

        return new CallAdapter<SmartCall<?>>() {
            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public <R> SmartCall<R> adapt(Call<R> call) {
                return new SmartCallImpl<>(callbackExecutor, call, responseType(), annotations,
                        retrofit, cachingSystem);
            }
        };
    }

    static class SmartCallImpl<T> implements SmartCall<T> {
        private final Executor callbackExecutor;
        private final Call<T> baseCall;
        private final Type responseType;
        private final Annotation[] annotations;
        private final Retrofit retrofit;
        private final CachingSystem cachingSystem;
        private final Request request;

        public SmartCallImpl(Executor callbackExecutor, Call<T> baseCall, Type responseType,
                             Annotation[] annotations, Retrofit retrofit, CachingSystem cachingSystem) {
            this.callbackExecutor = callbackExecutor;
            this.baseCall = baseCall;
            this.responseType = responseType;
            this.annotations = annotations;
            this.retrofit = retrofit;
            this.cachingSystem = cachingSystem;

            // This one is a hack but should create a valid Response (which can later be cloned)
            this.request = buildRequestFromCall();
        }

        /***
         * Inspects an OkHttp-powered Call<T> and builds a Request
         * * @return A valid Request (that contains query parameters, right method and endpoint)
         */
        private Request buildRequestFromCall() {
            try {
                Field argsField = baseCall.getClass().getDeclaredField("args");
                argsField.setAccessible(true);
                Object[] args = (Object[]) argsField.get(baseCall);
                //retrofit2.0更改了字段(1.0+)requestFactory-->(2.0+)serviceMethod
                Field serviceMethodField = baseCall.getClass().getDeclaredField("serviceMethod");
                serviceMethodField.setAccessible(true);
                Object requestFactory = serviceMethodField.get(baseCall);
                //retrofit2.0更改了方法(1.0+)create-->(2.0+)toRequest
                Method createMethod = requestFactory.getClass().getDeclaredMethod("toRequest", Object[].class);
                createMethod.setAccessible(true);
                return (Request) createMethod.invoke(requestFactory, new Object[]{args});
            } catch (Exception exc) {
                Log.e("buildRequestFromCall", exc.toString());
                return null;
            }
        }

        public void enqueueWithCache(final Callback<T> callback) {
            Runnable enqueueRunnable = new Runnable() {
                @Override
                public void run() {
                    /* Read cache */
                    if (!AppUtils.isNetworkAvailable(BasicCaching.mContext)) {
                        byte[] data = cachingSystem.getFromCache(buildRequest());
                        if (data != null) {
                            final T convertedData = SmartUtils.bytesToResponse(retrofit, responseType, annotations,
                                    data);
                            Runnable cacheCallbackRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    callback.onResponse(baseCall, Response.success(convertedData));
                                }
                            };
                            callbackExecutor.execute(cacheCallbackRunnable);
                        }
                    }

                    /* Enqueue actual network call */
                    baseCall.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(final Call<T> call, final Response<T> response) {
                            Runnable responseRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (response.isSuccessful()) {
                                        byte[] rawData = SmartUtils.responseToBytes(retrofit, response.body(),
                                                responseType(), annotations);
                                        cachingSystem.addInCache(response, rawData);
                                    }
                                    callback.onResponse(call, response);
                                }
                            };
                            // Run it on the proper thread
                            callbackExecutor.execute(responseRunnable);
                        }

                        @Override
                        public void onFailure(final Call<T> call, final Throwable t) {
                            Runnable failureRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailure(call, t);
                                }
                            };
                            callbackExecutor.execute(failureRunnable);
                        }

                    });

                }
            };
            Thread enqueueThread = new Thread(enqueueRunnable);
            enqueueThread.start();
        }

        /**
         * 默认不缓存
         *
         * @param callback
         */
        @Override
        public void enqueue(Callback<T> callback) {
            enqueue(callback, false);
        }

        @Override
        public void enqueue(final Callback<T> callback, boolean isCache) {
            if (isCache) {
                enqueueWithCache(callback);
            } else {
                baseCall.enqueue(new Callback<T>() {
                    @Override
                    public void onResponse(final Call<T> call, final Response<T> response) {
                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if (response.isSuccessful() && response.code() == 200) {
                                    callback.onResponse(call, response);
                                } else transError2Body(response, call);
                            }
                        });
                    }

                    private void transError2Body(Response<T> response, Call<T> call) {
                        ResponseBody responseBody = response.errorBody();
                        ResponseError responseError = null;
                        if (responseBody != null) {
                            try {
                                responseError = new Gson().fromJson(responseBody.string(), ResponseError.class);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                responseError = HttpErrorUtil.createResponseError(HttpErrorUtil.error_JsonSyntaxException, e.getMessage());
                            } catch (IOException e) {
                                e.printStackTrace();
                                responseError = HttpErrorUtil.createResponseError(HttpErrorUtil.error_IOException, e.getMessage());
                            }
                        }
                        if (responseError == null) {
                            responseError = HttpErrorUtil.createResponseError(response.code(), response.message());
                        }

                        HttpException ex = new HttpException(responseError.getMessage(), responseError.getErrorCode());
                        onFailure(call, ex);
//                        String url = call.request().url().url().toString();
//                        LogUtils.d(url + "\nerrorCode:" + response.code() + "\nerrorMessage:" + responseError.getMessage());
                    }

                    @Override
                    public void onFailure(final Call<T> call, final Throwable t) {
                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if (call.isCanceled()) {
                                    LogUtils.d("request is canceled");
                                } else {
                                    ResponseError responseError = null;
                                    if (t instanceof SocketTimeoutException) {
                                        responseError = HttpErrorUtil.createResponseError(HttpErrorUtil.error_SocketTimeoutException);
                                    } else if (t instanceof ConnectException) {
                                        responseError = HttpErrorUtil.createResponseError(HttpErrorUtil.error_ConnectException);
                                    } else if (t instanceof JsonSyntaxException) {
                                        responseError = HttpErrorUtil.createResponseError(HttpErrorUtil.error_JsonSyntaxException);
                                    } else if (t instanceof RuntimeException) {
                                        responseError = HttpErrorUtil.createResponseError(HttpErrorUtil.error_RuntimeException);
                                    } else if (t instanceof HttpException) {
                                        responseError = new ResponseError(((HttpException) t).getCode(), t.getMessage());
                                    } else {
                                        responseError = HttpErrorUtil.createResponseError(HttpErrorUtil.error_code_common);
                                    }
                                    HttpException ex = new HttpException(responseError.getMessage(), responseError.getErrorCode());
                                    LogUtils.e("=================>" + t.getMessage());
                                    callback.onFailure(call, ex);
                                }
                            }
                        });
                    }
                });
            }
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public Request buildRequest() {
            return request.newBuilder().build();
        }

        @Override
        public SmartCall<T> clone() {
            return new SmartCallImpl<>(callbackExecutor, baseCall.clone(), responseType(),
                    annotations, retrofit, cachingSystem);
        }

        @Override
        public Response<T> execute() throws IOException {
            return baseCall.execute();
        }

        @Override
        public void cancel() {
            baseCall.cancel();
        }
    }
}