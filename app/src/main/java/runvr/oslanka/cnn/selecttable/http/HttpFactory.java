
package runvr.oslanka.cnn.selecttable.http;

import com.cloud.lashou.retrofit.BasicCaching;
import com.cloud.lashou.retrofit.SmartCallFactory;
import com.cloud.lashou.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import runvr.oslanka.cnn.selecttable.LaShouApplication;

public class HttpFactory {


    private static HttpUrls mhttpUrl;

    protected static final Object monitor = new Object();


    public static HttpUrls getInstance() {
        synchronized (monitor) {
            if (mhttpUrl == null) {
                createHttp();
            }
            return mhttpUrl;
        }
    }

    public static void createHttp() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        SmartCallFactory smartFactory = new SmartCallFactory(BasicCaching.fromCtx(LaShouApplication.getContext()));
        //实现拦截器，设置请求头
        Interceptor interceptorImpl = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request compressedRequest = request.newBuilder()
                        .header("X-Requested-With", "XMLHttpRequest")
                        .build();
                return chain.proceed(compressedRequest);

            }
        };
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.d("=========>   " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置OKHttpClient
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptorImpl);//创建OKHttpClient的Builder
        httpClientBuilder.addInterceptor(loggingInterceptor);
        //build OKHttpClient
        OkHttpClient okHttpClient = httpClientBuilder.build();
        Retrofit client = new Retrofit.Builder()
                .baseUrl(Configs.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(smartFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mhttpUrl = client.create(HttpUrls.class);
    }

}
