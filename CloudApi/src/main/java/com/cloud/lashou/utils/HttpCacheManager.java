package com.cloud.lashou.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.LruCache;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class HttpCacheManager {

	private CacheType type;

	private Context mContext;

	private static HttpCacheManager mInstance;
	
	private LruCache<String, String> mResponseCache = null;
    private int memoryCacheSize = 1024 * 1024 * 4; // 4MB

	public static HttpCacheManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new HttpCacheManager(context.getApplicationContext());
		}
		return mInstance;
	}

	public HttpCacheManager(Context context) {
		type = getDefault();
		this.mContext = context.getApplicationContext();
 		mResponseCache=new LruCache<String, String>(memoryCacheSize){
 			@Override
 			protected int sizeOf(String key, String value) {
 				if (value == null) return 0;
 				return value.length();
 				
 			}
 		};
	}

	public synchronized Object loadCacheData(String key) {
		if (TextUtils.isEmpty(key))
            return null;

		Object result = null;
        if (mResponseCache != null) {
        	result = mResponseCache.get(key);
        	if (result != null)
        		return result;
        }
        
		switch (type) {
		case FILE:
			/** 查找本地md5_key */
			try {
				String keypath = AppUtils.getPath(mContext, AppUtils.StorageFile.file);
				File md5_keyFile = new File(keypath, key);
				if (md5_keyFile.exists()) {
					result = FileUtils.readFileToString(md5_keyFile);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		return result;
	}
	 public static void saveToCache(final Context context, final String md5, final String webtime, final String value, final String key){
	    	
	        Thread saveTask = new Thread() {
	            @Override
	            public void run() {
	            	/**判断缓存文件是否过大，自动清除*/
	            	//AppUtils.get
	            	if (md5 != null && value != null && webtime!=null) {
	    				String keypath = AppUtils.getPath(context, AppUtils.StorageFile.file);
	    				File md5_keyFile = new File(keypath, key + "_key");
	    				File md5_valueFile = new File(keypath, key + "_value");
	    				try {
	    					if (!md5_keyFile.exists()) {
	    						md5_keyFile.createNewFile();
	    					}
	    					if (!md5_valueFile.exists()) {
	    						md5_valueFile.createNewFile();
	    					}
	    					FileUtils.writeStringToFile(md5_keyFile, md5+":"+webtime);
	    					FileUtils.writeStringToFile(md5_valueFile, value);
	    				} catch (IOException e) {
	    					e.printStackTrace();
	    				}
	    			}
	            }
	        };
	        saveTask.setPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
	        saveTask.start();
	    }
	public synchronized int saveCacheData(String key, String md5, String webtime, String value) {
		if (mResponseCache == null) {
			return -1;
        }
		long now= System.currentTimeMillis();
		mResponseCache.put(key + "_key", md5+":"+webtime);
		mResponseCache.put(key + "_value", value);
		switch(type) {
		case FILE:
			saveToCache(mContext, md5, webtime, value, key);
			break;
		default:
			break;
		}
		return 0;
	}

	public void setCacheType(CacheType type) {
		this.type = type;
	}

	CacheType getDefault() {
		return CacheType.FILE;
	}

	public enum CacheType {
		FILE,
	}
}
