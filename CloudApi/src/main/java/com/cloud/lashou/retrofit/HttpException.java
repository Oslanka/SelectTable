package com.cloud.lashou.retrofit;

/**
 * Created by Administrator on 2017/8/1.
 */

public class HttpException extends Throwable {

    private String errorMsg;
    private int code;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HttpException(String errorMsg, int code) {
        this.errorMsg = errorMsg;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
