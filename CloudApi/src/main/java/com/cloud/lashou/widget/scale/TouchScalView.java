package com.cloud.lashou.widget.scale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.cloud.lashou.R;
import com.cloud.lashou.utils.BitmapUtil;
import com.cloud.lashou.utils.DensityUtil;


public class TouchScalView extends ImageView {

    private float Radius;

    private Bitmap mBitmap;


    private Matrix mMatrix;

    private float viewWidth, viewHeight;

    private Paint mPaint;

    private int alpha = 255;

    private FinishListener mFinishListener;

    private PorterDuffColorFilter colorFilter;

    public void setColorFilter(PorterDuffColorFilter colorFilter) {
        this.colorFilter = colorFilter;
    }

    private boolean isFinished;

    Thread inVisibleThread, visibleThread;

    private Context mContext;

    public TouchScalView(Context context) {
        this(context, null);
    }

    public TouchScalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap, int width, int height) {
        this.mBitmap = BitmapUtil.createScaleBitmap(bitmap, DensityUtil.dip2px(mContext, width), DensityUtil.dip2px(mContext, height));
        viewWidth = mBitmap.getWidth();
        viewHeight = mBitmap.getHeight();
        requestLayout();
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        viewWidth = mBitmap.getWidth();
        viewHeight = mBitmap.getHeight();
        requestLayout();
    }

    public TouchScalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        mMatrix = new Matrix();
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) (viewWidth), (int) (viewHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAlpha(alpha);
        if (null != colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }
        mMatrix.setScale((viewWidth + Radius) * 1.0f / viewWidth,
                (viewHeight + Radius) * 1.0f / viewHeight, viewWidth / 2, viewHeight / 2);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);

        super.onDraw(canvas);
    }

    public interface FinishListener {
        void finished(boolean isFinished);
    }

    public void setFinishListener(FinishListener listener) {
        this.mFinishListener = listener;
    }

    public void setRadius(float dis) {
        isFinished = false;
        this.Radius += dis / 2;
        if (Radius >= 0) {
            Radius = 0;
            isFinished = true;
        }
        if (Radius < -viewWidth) {
            Radius = -viewWidth;
            isFinished = true;
        }
        if (mFinishListener != null) {
            mFinishListener.finished(isFinished);
        }
        invalidate();
        Log.i("AfterRadius", "" + Radius);
    }

}
