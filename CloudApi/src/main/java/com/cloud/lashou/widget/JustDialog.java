package com.cloud.lashou.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cloud.lashou.R;

/**
 * Created by Administrator on 2017/3/27.
 */

public class JustDialog extends Dialog {

    Context context;
    private View layout = null;

    public JustDialog(Context context, int theme, View layout) {
        super(context, theme);
        this.context = context;
        this.layout = layout;
    }

    public JustDialog(Context context, int theme, int res) {
        super(context, theme);
        this.context = context;
        this.layout = LayoutInflater.from(context).inflate(res, null);
    }

    public void setBottom() {
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        dialogWindow.setAttributes(lp);
    }

    public View getView() {
        return layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (layout == null) {
            this.setContentView(R.layout.lashou_my_dialog);
        } else {
            this.setContentView(layout);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (overRideOnBackPressed!=null){
            overRideOnBackPressed.onBackPressed();
        }
    }

    private OverRideOnBackPressed overRideOnBackPressed;

    public void setOverRideOnBackPressed(OverRideOnBackPressed overRideOnBackPressed) {
        this.overRideOnBackPressed = overRideOnBackPressed;
    }

    public interface OverRideOnBackPressed {
        void onBackPressed();
    }
}
