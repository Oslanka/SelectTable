package com.cloud.lashou.retrofit;

import android.text.TextUtils;


/**
 * 网络请求异常集中处理
 */
public class HttpErrorUtil {
    public static final int error_code_common    =  0;  //未知异常
    public static final int error_code_auth      = 900; //身份验证失败
    public static final int error_code_400      = 400; //参数格式不正确
    public static final int error_code_401      = 401; //OAuth签名不正确
    public static final int error_code_403      = 403; //对资源的访问被拒绝。通常这意味着当前请求被限制。也可能是你没有访问此资源的权限
    public static final int error_code_404      = 404; //资源未发现(例如：请求一个不存在的用户或URL)
    public static final int error_code_500      = 500; //在服务端有应用错误
    public static final int error_JsonSyntaxException      = 1000; //解析异常
    public static final int error_SocketTimeoutException   = 1001; //请求超时
    public static final int error_ConnectException          = 1002; //网络连接异常
    public static final int error_IOException                = 1003; //读写异常
    public static final int error_RuntimeException          = 1004; //运行时异常

    public static ResponseError createResponseError(int errorCode){
        return createResponseError(errorCode,null);
    }
    public static ResponseError createResponseError(int errorCode,String message) {
        if (TextUtils.isEmpty(message)) {
            switch (errorCode) {
                case HttpErrorUtil.error_code_auth:
                    message = "身份验证失败";
                    break;
                case HttpErrorUtil.error_code_400:
                    message = "参数格式不正确";
                    break;
                case HttpErrorUtil.error_code_401:
                    message = "OAuth签名不正确";
                    break;
                case HttpErrorUtil.error_code_403:
                    message = "资源的访问被拒绝";
                    break;
                case HttpErrorUtil.error_code_404:
                    message = "资源未发现";
                    break;
                case HttpErrorUtil.error_code_500:
                    message = "服务端错误";
                    break;
                case HttpErrorUtil.error_JsonSyntaxException:
                    message = "解析异常";
                    break;
                case HttpErrorUtil.error_SocketTimeoutException:
                    message = "请求超时";
                    break;
                case HttpErrorUtil.error_ConnectException:
                    message = "网络连接异常";
                    break;
                case HttpErrorUtil.error_IOException:
                    message = "读写异常";
                    break;
                case HttpErrorUtil.error_RuntimeException:
                    message = "运行时异常";
                    break;
                default:
                    message = "未知异常";
                    break;
            }
        }

        return new ResponseError(errorCode, message);
    }
}
