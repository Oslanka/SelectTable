/*
package com.cloud.lashou.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.cloud.lashou.R;
import com.cloud.lashou.utils.DensityUtil;
import com.cloud.lashou.widget.scale.TouchScalView;

import java.lang.ref.WeakReference;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;


*/
/**
 * 网络图片的工具类
 * <p>
 * (1)ImageView设置图片（错误图片）
 * （2）ImageView设置图片---BitMap(不设置默认图)
 * （3）设置RelativeLayout
 * （4）设置LinearLayout
 * （5）设置FrameLayout
 * （6）高斯模糊------ RelativeLayout
 * （7）高斯模糊------ LinearLayout
 * （8）圆角显示图片  ImageView
 * 使用工具类
 * （9）多种样式（模糊+圆角）
 *
 * @author huangshuyuan
 *//*

public class PictureUtils {

    */
/**
     * (1)
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     *//*

    public static void showImageView(Context context, int errorimg, String url,
                                     ImageView imgeview) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imgeview);
        ImageView target = imageViewWeakReference.get();
        try {
            Glide.with(context).load(url)
                    .asBitmap()// 加载图片
                    .error(errorimg)// 设置错误图片
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(errorimg)// 设置占位图
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                    .into(target);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    public static void showImageView(Context context, int errorimg, @DrawableRes Integer resourceId,
                                     ImageView imgeview) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imgeview);
        ImageView target = imageViewWeakReference.get();
        try {
            Glide.with(context).load(resourceId)
                    .asBitmap()// 加载图片
                    .error(errorimg)// 设置错误图片
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(errorimg)// 设置占位图
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                    .into(target);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void showImageView(Context context, String url,
                                     ImageView imgeview) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imgeview);
        ImageView target = imageViewWeakReference.get();
        try {
            Glide.with(context).load(url)
                    .asBitmap()// 加载图片
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                    .into(target);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void showImageViewNoCache(Context context, int errorimg, String url,
                                            ImageView imgeview) {
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imgeview);
        ImageView target = imageViewWeakReference.get();
        try {

            Glide.with(context).load(url)
                    .asBitmap()// 加载图片
                    .error(errorimg)// 设置错误图片
                    .fitCenter()
                    .dontAnimate()
                    .skipMemoryCache(true)
                    .placeholder(errorimg)// 设置占位图
                    .into(target);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    */
/**
     * （2）
     * 获取到Bitmap---不设置错误图片，错误图片不显示
     *
     * @param context
     * @param imageView
     * @param url
     *//*


    public static void showImageViewGone(Context context,
                                         final ImageView imageView, String url) {
        Glide.with(context).load(url).asBitmap().centerCrop()

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {

                        imageView.setVisibility(View.VISIBLE);
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);
                        imageView.setImageDrawable(bd);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);
                        imageView.setVisibility(View.GONE);
                    }

                });

    }


    public static void setBitmap(final Context context, String url, final TouchScalView view, final int width, final int height) {

        Glide.with(context).load(url).asBitmap().centerCrop()

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(new SimpleTarget<Bitmap>() {


                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        view.setBitmap(loadedImage, width, height);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);
                        view.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.all_service), 30, 30);
                    }

                });
    }

    public static void setBitmap(final Context context, String url, final TouchScalView view) {

        Glide.with(context).load(url).asBitmap().centerCrop()

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(new SimpleTarget<Bitmap>() {


                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        view.setBitmap(loadedImage);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);
                        view.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.all_service), 30, 30);
                    }

                });
    }

    */
/**
     * （3）
     * 设置RelativeLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     *//*


    public static void showImageView(Context context, int errorimg, String url,
                                     final RelativeLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(errorimg)// 设置错误图片
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(errorimg)// 设置占位图
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    */
/**
     * （4）
     * 设置LinearLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     *//*


    public static void showImageView(Context context, int errorimg, String url,
                                     final LinearLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(errorimg)// 设置错误图片
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(errorimg)// 设置占位图
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    */
/**
     * （5）
     * 设置FrameLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param errorimg
     * @param url
     * @param frameBg
     *//*


    public static void showImageView(Context context, int errorimg, String url,
                                     final FrameLayout frameBg) {
        Glide.with(context).load(url).asBitmap().error(errorimg)// 设置错误图片
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(errorimg)// 设置占位图
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        frameBg.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        frameBg.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    */
/**
     * （6）
     * 获取到Bitmap 高斯模糊         RelativeLayout
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     *//*


    public static void showImageViewBlur(Context context, int errorimg,
                                         String url, final RelativeLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(errorimg)
                // 设置错误图片
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                // 缓存修改过的图片
                .placeholder(errorimg)
                .transform(new GlideBlurTransformation(context))// 高斯模糊处理
                // 设置占位图

                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    */
/**
     * （7）
     * 获取到Bitmap 高斯模糊 LinearLayout
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     *//*


    public static void showLinearBlur(Context context, int errorimg,
                                      String url, final LinearLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(errorimg)
                // 设置错误图片
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                // 缓存修改过的图片
                .placeholder(errorimg)
                .transform(new GlideBlurTransformation(context))// 高斯模糊处理
                // 设置占位图

                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    */
/**
     * （8）
     * 显示图片 圆角显示  ImageView
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     *//*

    public static void showImageViewToCircle(Application context, int errorimg,
                                             String url, ImageView imgeview) {
        Glide.with(context).load(url).centerCrop()
                // 加载图片
                .error(errorimg)
                // 设置错误图片
                .crossFade()
                // 设置淡入淡出效果，默认300ms，可以传参
                .placeholder(errorimg)
                // 设置占位图
                .transform(new GlideCircleTransform(context))//圆角
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imgeview);
    }

    public static void showImageViewToRound(Context context, int round, int errorimg, String url, ImageView imageView) {
        RoundCornersTransformation transformation = new RoundCornersTransformation(context, DensityUtil.dip2px(context, round),
                RoundCornersTransformation.CornerType.ALL);
        Glide.with(context).load(url).centerCrop()
                // 加载图片
                .error(errorimg)
                // 设置错误图片
                .crossFade()
                // 设置淡入淡出效果，默认300ms，可以传参
                .placeholder(errorimg)
                // 设置占位图
                .bitmapTransform(transformation)//圆角
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imageView);
    }

    */
/**
     * （9）
     * 多种样式（模糊+圆角） ImageView
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     *//*

    public static void showImageViewToCircleAndBlur(Application context, int errorimg,
                                                    String url, ImageView imgeview) {
        Glide.with(context).load(url).centerCrop()
                .error(errorimg)// 设置错误图片
                .bitmapTransform(new BlurTransformation(context, 15), new CropCircleTransformation(context))// 设置高斯模糊，圆角
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imgeview);


    }

    */
/**
     * （10）
     * 矩形圆角 ImageView
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     *//*

    public static void showImageViewToRoundedCorners(Context context, int errorimg,
                                                     String url, ImageView imgeview, int radiusCorner) {
        Glide.with(context).load(url)
                .dontAnimate()
                .error(errorimg)// 设置错误图片
                .placeholder(errorimg)
                .bitmapTransform(new CenterCrop(context),new RoundedCornersTransformation(context, radiusCorner, 0,
                        RoundedCornersTransformation.CornerType.ALL))// 设置矩形圆角
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imgeview);


    }
    public static void showImageViewToRoundedCorners(Context context,String url, ImageView imgeview, int radiusCorner) {
        showImageViewToRoundedCorners(context,R.drawable.default_rounded_img,url,imgeview,radiusCorner);
    }

    public static void showImageViewToRoundedCorners(Context context, int errorimg,
                                                     int img, ImageView imgeview, int radiusCorner) {
        Glide.with(context).load(img)
                .dontAnimate()
                .error(errorimg)// 设置错误图片
                .placeholder(errorimg)
                .bitmapTransform(new CenterCrop(context),new RoundedCornersTransformation(context, radiusCorner, 0,
                        RoundedCornersTransformation.CornerType.ALL))// 设置矩形圆角
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imgeview);


    }

    */
/**
     * 遮罩图层 ImageView
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     *//*

    public static void showImageViewToMask(Context context, int errorimg,
                                           String url, ImageView imgeview) {
        Glide.with(context).load(url).centerCrop()
                .error(errorimg)// 设置错误图片
                .bitmapTransform(
                        new VignetteFilterTransformation(context, new PointF(0.5f, 0.5f),
                                new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imgeview);


    }

    */
/**
     * （7）
     * 获取到Bitmap 高斯模糊 LinearLayout
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     *//*


    public static void showImageViewBlur(Context context, int errorimg,
                                         String url, final ImageView bgLayout) {
        Glide.with(context).load(url).asBitmap().error(errorimg)
                // 设置错误图片
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                // 缓存修改过的图片
                .placeholder(errorimg)
                .transform(new GlideBlurTransformation(context))// 高斯模糊处理
                // 设置占位图

                .into(bgLayout);

    }

    public static  void getBitmapFromUrl(Activity activity, String url, final CallBackBitmap callBackBitmap) {
        Glide.with(activity).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
               */
/* int width = resource.getIntrinsicWidth();// 取drawable的长宽
                int height = resource.getIntrinsicHeight();
                Bitmap.Config config = resource.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;// 取drawable的颜色格式
                Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap*//*

                callBackBitmap.finish(resource);
                return false;
            }
        }).preload();
    }

    public interface CallBackBitmap {
        void finish(Drawable drawable);
    }

}
*/
