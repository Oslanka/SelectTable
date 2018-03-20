package com.cloud.lashou.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ProgressUtil {

    private static ProgressDialog progressDialog;

    public static ProgressDialog createProgressDialog(String msg, Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setCancelable(false);
        progressDialog.setMessage(msg);
        return progressDialog;
    }

    public static void showProgressDialog(Context context) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = createProgressDialog("加载中...", context);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    public static void showProgressDialog(String msg, Context context) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        try {
            progressDialog = createProgressDialog(msg, context);
            progressDialog.setMessage(msg);
            progressDialog.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 隐藏正在加载的进度条
     */
    public static void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing() == true) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
