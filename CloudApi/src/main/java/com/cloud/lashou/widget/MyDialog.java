package com.cloud.lashou.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cloud.lashou.R;

/**
 * Created by Administrator on 2017/3/27.
 */

public class MyDialog extends Dialog {

    Context context;
    private View layout = null;
    private TextView content_tv;
    private String content;
    private TextView right_tv;
    private TextView left_tv;
    private View.OnClickListener rightListener;
    private String title;

    public MyDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;

    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;

    }
    public MyDialog(Context context, int theme, View layout) {
        super(context, theme);
        this.context = context;
        this.layout = layout;
    }
    public void setContent(String content){
        this.content = content;
        }

    public void setTitle(String title){
        this.title = title;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (layout == null) {
            this.setContentView(R.layout.lashou_my_dialog);
        } else {
            this.setContentView(layout);
        }
        content_tv = (TextView) findViewById(R.id.content_tv);
        if (!TextUtils.isEmpty(content)){
            content_tv.setText(content);
        }
        TextView text = (TextView) findViewById(R.id.text);
        if (!TextUtils.isEmpty(title)){
            text.setText(title);
        }

    }
}
