package com.cloud.lashou.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloud.lashou.R;

/**
 * Created by Administrator on 2017/8/27.
 */

public class CustomDialog extends ProgressDialog {

//    LottieAnimationView lottieAnimationView;  //先注销了
    boolean cancelable;
    private ImageView ivLoadingGif;

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme, boolean cancelable) {
        super(context, theme);
        this.cancelable = cancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext(), cancelable);
    }

    private void init(Context context, boolean cancelable) {
        //设置可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);

        setContentView(R.layout.loading_layout_circle);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.dimAmount = 0.2f;
        getWindow().setAttributes(params);

//        lottieAnimationView = (LottieAnimationView) findViewById(R.id.loading_animotionView);
//        lottieAnimationView.setAnimation("Logo/loading_icon.json", LottieAnimationView.CacheStrategy.Weak);
////        lottieAnimationView.setAlpha(0.2f);
//        lottieAnimationView.loop(true);
//        lottieAnimationView.playAnimation();

        ivLoadingGif = (ImageView) findViewById(R.id.iv_loading_gif);
        Glide.with(context).load(R.drawable.icon_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivLoadingGif);
    }

//    @Override
//    public void show() {
//        super.show();
//    }

    @Override
    public void dismiss() {
//        if (lottieAnimationView != null) {
//            lottieAnimationView.cancelAnimation();
//        }
        if(ivLoadingGif != null){
            ivLoadingGif.setImageBitmap(null);
        }
        super.dismiss();
    }
}
