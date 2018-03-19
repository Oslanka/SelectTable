package runvr.oslanka.cnn.selecttable.http;


import com.cloud.lashou.retrofit.SmartCall;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import runvr.oslanka.cnn.selecttable.bean.InsertContentBean;
import runvr.oslanka.cnn.selecttable.bean.SelectBean;
import runvr.oslanka.cnn.selecttable.bean.SelectPicBean;
import runvr.oslanka.cnn.selecttable.bean.UpLoadImageResponse;
import runvr.oslanka.cnn.selecttable.bean.UserBean;

/**
 * 访问地址存放
 */
public interface HttpUrls {

    @GET("login")
    SmartCall<UserBean> login(@Query("user") String user, @Query("password") String password);

    @GET("getSelect")
    SmartCall<SelectBean> getSelect(@Query("ydxh") String ydxh, @Query("yhzh") String yhzh, @Query("tyr") String tyr);

    @GET("getSelectPic")
    SmartCall<SelectPicBean> getSelectPic(@Query("start") String start, @Query("end") String end,
                                          @Query("ydh") String ydh, @Query("user") String user);

    //上传图片
    @Multipart
    @POST("upload")
    SmartCall<UpLoadImageResponse> postImage(@Part MultipartBody.Part file);

    @GET("uploadContent")
    SmartCall<InsertContentBean> uploadContent(@Query("ydh") String ydh, @Query("user") String user);

    @GET("change")
    SmartCall<UserBean> changePassword(@Query("nickName") String nickName
            , @Query("user") String user
            , @Query("oldPass") String oldPass
            , @Query("newPass") String newPass

    );
}