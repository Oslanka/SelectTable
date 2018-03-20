package com.cloud.lashou.utils;

import android.content.Context;
import android.util.LruCache;

import com.cloud.lashou.retrofit.BasicCaching;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;

/**
 * Created by Administrator on 2017/5/12.
 */

public class CacheUtil {
    public static void clearCache(Context context) throws IOException {
        DiskLruCache diskCache = BasicCaching.fromCtx(context).getDiskCache();
        LruCache<String, Object> memoryCache = BasicCaching.fromCtx(context).getMemoryCache();
        diskCache.delete();
        memoryCache.evictAll();
    }
}
