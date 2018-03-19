/*

package runvr.oslanka.cnn.selecttable.http;

import android.text.TextUtils;

import com.cloud.lashou.retrofit.BasicCaching;
import com.cloud.lashou.retrofit.SmartCallFactory;
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

public class MSGHostHttp {

    private static HttpUrls mhttpUrl;

    protected static final Object monitor = new Object();
    private static String guid = "";

    public static HttpUrls getInstance() {
        synchronized (monitor) {
//            if (mhttpUrl == null) {
            guid = Session.get(LaShouApplication.getContext()).getGuid();
            if (TextUtils.isEmpty(guid)) {
                guid = "";
            }
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
                            .header("guid", guid)
                            .header("channelId", Session.get(LaShouApplication.getContext()).getChannelId())
                            .build();
                    return chain.proceed(compressedRequest);
                }
            };
            //设置OKHttpClient
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptorImpl);//创建OKHttpClient的Builder
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
//            }
            return mhttpUrl;
        }
    }


}
*/
