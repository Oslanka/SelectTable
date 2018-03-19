package runvr.oslanka.cnn.selecttable.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import runvr.oslanka.cnn.selecttable.LaShouApplication;

/**
 * sharedPreference工具类
 */
public class SharedPreferencesUtil {

	private static SharedPreferences mSharedPreferences;

	private static synchronized SharedPreferences getPreferneces() {
		if (mSharedPreferences == null) {
			mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(LaShouApplication.getContext());
		}
		return mSharedPreferences;
	}

	public static void putString(String key, String value) {
		getPreferneces().edit().putString(key, value).commit();
	}

	public static String getString(String key, String defaultValue) {
		return getPreferneces().getString(key, defaultValue);
	}

	public static void putInt(String key, int value) {
		getPreferneces().edit().putInt(key, value).commit();
	}

	public static int getInt(String key, int defaultValue) {
		return getPreferneces().getInt(key, defaultValue);
	}

	public static void putBoolean(String key, Boolean value) {
		getPreferneces().edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		return getPreferneces().getBoolean(key, defaultValue);
	}

	public static void putFloat(String key, long value) {
		getPreferneces().edit().putFloat(key, value).commit();
	}

	public static float getFloat(String key, float defaultValue) {
		return getPreferneces().getFloat(key, defaultValue);
	}

	public static void putLong(String key, long value) {
		getPreferneces().edit().putLong(key, value).commit();
	}

	public static long getLong(String key, long defaultValue) {
		return getPreferneces().getLong(key, defaultValue);
	}

	/**
	 * 描述 将key对应的json转换为一个对象
	 * @param key 存储数据的key
	 * @param cls 对象
	 * @return		转化失败返回null
	 */
	public static <T> T getBean(String key, Class<T> cls) {
		String json = getString(key,null);
		if(TextUtils.isEmpty(json)){
			return null;
		}
		T t = null;
		Gson gson = new Gson();
		try {
			t = gson.fromJson(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 保存一个对象
	 * @param key	存储数据的key
	 * @param t   保存的对象
	 * @param <T>
     */
	public static <T> void putBean(String key, T t) {
		if(t != null){
			Gson gson = new Gson();
			getPreferneces().edit().putString(key, gson.toJson(t)).commit();
		}else{
			removeKey(key);
		}
	}
	/**
	 * 移除字段
	 * 
	 * @return
	 */
	public static void removeKey(String key) {
		getPreferneces().edit().remove(key).commit();
	}

	/**
	 * 打印所有
	 */
	public static void printAll() {
		System.out.println(getPreferneces().getAll());
	}

	/**
	 * 清空保存在默认SharePreference下的所有数据
	 */
	public static void clear() {
		getPreferneces().edit().clear().commit();
	}

}
