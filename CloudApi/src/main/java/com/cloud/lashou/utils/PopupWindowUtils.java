package com.cloud.lashou.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by EXUTech on 16/9/23.
 */

public class PopupWindowUtils {
    private static PopupWindowUtils popupWindowUtils;

    public static PopupWindowUtils getInstance() {
        if (null == popupWindowUtils) popupWindowUtils = new PopupWindowUtils();
        return popupWindowUtils;
    }

    public PopupWindow getPopupwindow(final View popupView, final Activity context,boolean ifalphaBack) {
        PopupWindow mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        mPopupWindow.setFocusable(true);
        if (ifalphaBack) {
            backgroundAlpha(0.6f, context);
            popupView.setBackgroundColor(Color.parseColor("#99000000"));
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f, context);
                    popupView.setBackgroundColor(Color.parseColor("#00000000"));
                }
            });
        }
        return mPopupWindow;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    private void backgroundAlpha(float bgAlpha, Activity context) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
//        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
