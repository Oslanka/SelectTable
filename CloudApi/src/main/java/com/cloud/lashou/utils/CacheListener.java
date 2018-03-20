package com.cloud.lashou.utils;

/**
 * Created by Administrator on 2017/8/7.
 */

public interface CacheListener {
    void onSuccess(String msg);
    void onFail();
}
