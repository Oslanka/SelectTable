package com.cloud.lashou.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ShowMessage {
    /**
     * Show Warning to user.
     */
    public static void showToast(Activity activity, String str) {
    	if(null != activity){
            Toast.makeText(activity.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    }
    public static void showToast(Context context, String str) {
        if(null != context) {
            Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    }
    public static void showToastLong(Context context, String str) {
        if(null != context) {
            Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_LONG).show();
        }
    }
}
