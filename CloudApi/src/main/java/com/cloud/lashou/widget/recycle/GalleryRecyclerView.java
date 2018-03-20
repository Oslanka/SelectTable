package com.cloud.lashou.widget.recycle;


import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.cloud.lashou.utils.DensityUtil;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

public class GalleryRecyclerView extends RecyclerView {

    private final static int MINIMUM_SCROLL_EVENT_OFFSET_MS = 20;

    private boolean userScrolling = false;
    private boolean mScrolling = false;
    private int scrollState = SCROLL_STATE_IDLE;
    //最后滑动时间
    private long lastScrollTime = 0;
    //handler
    private Handler mHandler = new Handler();
    //是否支持缩放
    private boolean scaleViews = false;
    //是否支持透明度
    private boolean alphaViews = false;
    //方向  默认水平
    private Orientation orientation = Orientation.HORIZONTAL;

    private ChildViewMetrics childViewMetrics;
    //选中回调
    private OnViewSelectedListener listener;

    //点击回调
    private OnViewClickedListener clickedListener;
    // 选中的位置position
    private int selectedPosition;
    //recycleview   LinearLayoutManager
    private LinearLayoutManager mLinearLayoutManager;

    private TouchDownListem listem;
    //缩放基数
    private float baseScale = 0.7f;
    //缩放透明度
    private float baseAlpha = 0.7f;

    private Context mContext;
    private int childAdapterPosition;

    public GalleryRecyclerView(Context context) {
        this(context, null);
    }

    public GalleryRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        setHasFixedSize(true);
        setOrientation(orientation);
        enableSnapping();
    }

    private boolean scrolling;

    public enum Mode {

        ALINE_BOTTOM,

    }

    public Mode ALINE_MODE = Mode.ALINE_BOTTOM;

    public Mode getAlineMode() {
        return ALINE_MODE;
    }

    public void setAlineMode(Mode alineMode) {
        ALINE_MODE = alineMode;
    }

    /**
     * 获取当前位置position
     *
     * @return
     */
    public int getCurrentPosition() {
        return selectedPosition;
    }

    @Override
    public void onChildAttachedToWindow(View child) {
        super.onChildAttachedToWindow(child);

        if (!scrolling && scrollState == SCROLL_STATE_IDLE) {
            scrolling = true;
            scrollToView(getCenterView());
            if (getAlineMode() == Mode.ALINE_BOTTOM) {
                updateViewsBottom();
            }
        }
    }

    private int sCrollNum = 0;

    public int getsCrollNum() {
        return sCrollNum;
    }

    private void enableSnapping() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (null != listem) {
                    listem.onScrolling();
                }
                if (sCrollNum == 0) {
                    if (getAlineMode() == Mode.ALINE_BOTTOM) {
                        updateViewsBottom();
                    }
                } else {
                    if (getAlineMode() == Mode.ALINE_BOTTOM) {
                        updateViewsBottomScroll();
                    }
                }
                sCrollNum++;
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                /** if scroll is caused by a touch (scroll touch, not any touch) **/
                if (newState == SCROLL_STATE_TOUCH_SCROLL) {
                    /** if scroll was initiated already, it would probably be a tap **/
                    /** if scroll was not initiated before, this is probably a user scrolling **/
                    if (!mScrolling) {
                        userScrolling = true;
                    }
                } else if (newState == SCROLL_STATE_IDLE) {
                    /** if user is the one scrolling, snap to the view closest to center **/
                    if (userScrolling) {
                        scrollToView(getCenterView());
                    }

                    if (null != listem) {
                        listem.onScrollComepleted();
                    }

                    userScrolling = false;
                    mScrolling = false;

                    /** if idle, always check location and correct it if necessary, this is just an extra check **/
                    if (getCenterView() != null && getPercentageFromCenter(getCenterView()) > 0) {
                        scrollToView(getCenterView());
                    }

                } else if (newState == SCROLL_STATE_FLING) {
                    mScrolling = true;
                }

                scrollState = newState;
            }
        });
    }


    /**
     * 通知回调并设置当前选中位置
     */
    private void notifyListener(View view, int position) {

        /** if there is a listener and the index is not the same as the currently selected position, notify listener **/
        if (listener != null && position != selectedPosition) {
            listener.onSelected(view, position);
        }
        selectedPosition = position;
    }

    /**
     * 设置方向 水平 or 竖直
     *
     * @param orientation LinearLayoutManager.HORIZONTAL or LinearLayoutManager.VERTICAL
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        childViewMetrics = new ChildViewMetrics(orientation);
        mLinearLayoutManager = new LinearLayoutManager(getContext(), orientation.intValue(), false);

        setLayoutManager(mLinearLayoutManager);
    }

    /**
     * 设置选择position
     *
     * @param position
     */
    public void setSelectPosition(int position) {
        selectedPosition = position;
        mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
        scrollToView(getChildAt(position));
    }

    /**
     * 设置选中回调接口
     *
     * @param listener the OnViewSelectedListener
     */
    public void setOnViewSelectedListener(OnViewSelectedListener listener) {
        this.listener = listener;
    }

    public void setOnViewClickedListener(OnViewClickedListener clickedListener) {
        this.clickedListener = clickedListener;
    }

    /**
     * 设置两边是否可以缩放
     *
     * @param enabled
     */
    public void setCanScale(boolean enabled) {
        this.scaleViews = enabled;
    }

    /**
     * 设置两边的透明度是否支持
     *
     * @param enabled
     */
    public void setCanAlpha(boolean enabled) {
        this.alphaViews = enabled;
    }


    /**
     * 设置基数缩放值
     *
     * @param baseScale
     */
    public void setBaseScale(float baseScale) {
        this.baseScale = 1f - baseScale;
    }

    /**
     * 设置基数透明度
     *
     * @param baseAlpha
     */
    public void setBaseAlpha(float baseAlpha) {
        this.baseAlpha = 1f - baseAlpha;
    }

    private View selectedView;

    /**
     * 第一次初始化更新views（底部平行 中间view放大）
     */
    private void updateViewsBottom() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            setMarginsForChild(child);
            //设置缩放
            if (scaleViews) {
                if (sCrollNum == 0) {
                    if (selectedPosition != getChildAdapterPosition(child)) {//选中的View角标
                        child.setScaleX(1 - baseScale);
                        child.setScaleY(1 - baseScale);
//                        child.setTranslationY(DensityUtil.dip2px(mContext,
//                                (DensityUtil.px2dip(mContext, child.getHeight())
//                                        - DensityUtil.px2dip(mContext, child.getHeight()) * (1 - baseScale)) * 0.5f * 0.5f));

                    } else {
                        childAdapterPosition = getChildAdapterPosition(child);
                        child.setScaleX(1.0f);
                        child.setScaleY(1.0f);
//                        child.setTranslationY(-DensityUtil.dip2px(mContext,
//                                (DensityUtil.px2dip(mContext, child.getHeight())
//                                        - DensityUtil.px2dip(mContext, child.getHeight()) * (1 - baseScale)) * 0.5f * 0.5f));
                        if (child != getCenterView()) {
                            scrollToView(child);
                        }
                        listener.onSelected(child, childAdapterPosition);
                    }
                }
            }

        }
    }

    /**
     * 点击时更新views（底部平行 中间view放大）
     */
    private void updateClickViewsBottom(View view) {
        for (int i = 0; i < getChildCount(); i++) {
            notifyListener(view, getChildAdapterPosition(view));//当前选中的位置
            View child = getChildAt(i);
            setMarginsForChild(child);
            float percentage = getPercentageFromCenter(child);
            float alpha = 1f - (baseAlpha * percentage);
            //设置缩放
            if (scaleViews) {
                if (child != view) {
                    child.setScaleX(1 - baseScale);
                    child.setScaleY(1 - baseScale);
//                    child.setTranslationY(DensityUtil.dip2px(mContext,
//                            (DensityUtil.px2dip(mContext, child.getHeight())
//                                    - DensityUtil.px2dip(mContext, child.getHeight()) * (1 - baseScale)) * 0.5f * 0.5f));
                } else {
                    childAdapterPosition = getChildAdapterPosition(child);
                    child.setScaleX(1.0f);
                    child.setScaleY(1.0f);
//                    child.setTranslationY(-DensityUtil.dip2px(mContext,
//                            (DensityUtil.px2dip(mContext, child.getHeight())
//                                    - DensityUtil.px2dip(mContext, child.getHeight()) * (1 - baseScale)) * 0.5f * 0.5f));

                }
            }
            //设置透明度
            if (alphaViews) {
                child.setAlpha(alpha);
            }
        }
    }

    /**
     * 滑动时更新views（底部平行 中间view放大）
     */
    private void updateViewsBottomScroll() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            setMarginsForChild(child);

            //设置缩放
            if (scaleViews) {
                if (getChildAdapterPosition(child) != childAdapterPosition) {
                    child.setScaleX(1 - baseScale);
                    child.setScaleY(1 - baseScale);
//                    child.setTranslationY(DensityUtil.dip2px(mContext,
//                            (DensityUtil.px2dip(mContext, child.getHeight())
//                                    - DensityUtil.px2dip(mContext, child.getHeight()) * (1 - baseScale)) * 0.5f * 0.5f));
                } else {
                    child.setScaleX(1.0f);
                    child.setScaleY(1.0f);
//                    child.setTranslationY(-DensityUtil.dip2px(mContext,
//                            (DensityUtil.px2dip(mContext, child.getHeight())
//                                    - DensityUtil.px2dip(mContext, child.getHeight()) * (1 - baseScale)) * 0.5f * 0.5f));
                }
            }

        }
    }


    /**
     * Adds the margins to a childView so a view will still center even if it's only a single child
     *
     * @param child childView to set margins for
     */
    private void setMarginsForChild(View child) {
        int lastItemIndex = getLayoutManager().getItemCount() - 1;
        int childIndex = getChildAdapterPosition(child);

        int startMargin = 0;
        int endMargin = 0;
        int topMargin = 0;
        int bottomMargin = 0;

        if (orientation == Orientation.VERTICAL) {
            topMargin = childIndex == 0 ? getCenterLocation() : 0;
            bottomMargin = childIndex == lastItemIndex ? getCenterLocation() : 0;
        } else {
            startMargin = childIndex == 0 ? 10 : 0;
            endMargin = childIndex == lastItemIndex ? 10 : 0;
        }

        /** if sdk minimum level is 17, set RTL margins **/
        if (orientation == Orientation.HORIZONTAL && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).setMarginStart(startMargin);
            ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).setMarginEnd(endMargin);
        }

        /** If layout direction is RTL, swap the margins  **/
        if (ViewCompat.getLayoutDirection(child) == ViewCompat.LAYOUT_DIRECTION_RTL)
            ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).setMargins(startMargin, topMargin, endMargin, bottomMargin);
        else {
            ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).setMargins(startMargin, topMargin, endMargin, bottomMargin);
        }

        /** if sdk minimum level is 18, check if view isn't undergoing a layout pass (this improves the feel of the view by a lot) **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (!child.isInLayout())
                child.requestLayout();
        }
    }

    private boolean isClick;
    float startX, endX;

    public boolean isClick() {
        return isClick;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        long currentTime = System.currentTimeMillis();

        /** if touch events are being spammed, this is due to user scrolling right after a tap,
         * so set userScrolling to true **/
        if (mScrolling && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            if ((currentTime - lastScrollTime) < MINIMUM_SCROLL_EVENT_OFFSET_MS) {
                userScrolling = true;
            }
        }

        lastScrollTime = currentTime;

        int location = orientation == Orientation.VERTICAL ? (int) event.getY() : (int) event.getX();

        View targetView = getChildClosestToLocation(location);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getRawX();
                if (Math.abs(endX - startX) > 10.0f) {
                    isClick = false;
                } else {
                    isClick = true;
                }

                break;
        }

        if (isClick) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                updateClickViewsBottom(targetView);
                if (targetView != getCenterView()) {
                    scrollToView(targetView);
                    return true;
                }
            }

        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        int location = orientation == Orientation.VERTICAL ? (int) event.getY() : (int) event.getX();
        View targetView = getChildClosestToLocation(location);
        if (targetView != getCenterView()) {
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }


    public void setTouchDownlistem(TouchDownListem listem) {
        this.listem = listem;
    }

    public interface TouchDownListem {
        void onScrollComepleted();

        void onScrolling();
    }

    @Override
    public void scrollToPosition(int position) {
        childViewMetrics.size(getChildAt(0));
        smoothScrollBy(childViewMetrics.size(getChildAt(0)) * position);
    }

    private View getChildClosestToLocation(int location) {
        if (getChildCount() <= 0)
            return null;

        int closestPos = 9999;
        View closestChild = null;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            int childCenterLocation = (int) childViewMetrics.center(child);
            int distance = childCenterLocation - location;

            /** if child center is closer than previous closest, set it as closest child  **/
            if (Math.abs(distance) < Math.abs(closestPos)) {
                closestPos = distance;
                closestChild = child;
            }
        }

        return closestChild;
    }

    /**
     * Check if the view is correctly centered (allow for 10px offset)
     *
     * @param child the child view
     * @return true if correctly centered
     */
    private boolean isChildCorrectlyCentered(View child) {
        int childPosition = (int) childViewMetrics.center(child);
        return childPosition > (getCenterLocation() - 10) && childPosition < (getCenterLocation() + 10);
    }

    /**
     * 获取中间的view
     *
     * @return
     */
    public View getCenterView() {
        return getChildClosestToLocation(getCenterLocation());
    }

    /**
     * 滚动指定view
     *
     * @param child
     */
    private void scrollToView(View child) {
        if (child == null)
            return;

        stopScroll();

        int scrollDistance = getScrollDistance(child);

        if (scrollDistance != 0)
            smoothScrollBy(scrollDistance);
    }

    private int getScrollDistance(View child) {
        int childCenterLocation = (int) childViewMetrics.center(child);
        return childCenterLocation - getCenterLocation();
    }

    private float getPercentageFromCenter(View child) {
        float center = getCenterLocation();
        float childCenter = childViewMetrics.center(child);

        float offSet = Math.max(center, childCenter) - Math.min(center, childCenter);
        float maxOffset = (center + childViewMetrics.size(child));

        return (offSet / maxOffset);
    }

    private int getCenterLocation() {
        if (orientation == Orientation.VERTICAL)
            return getMeasuredHeight() / 2;

        return getMeasuredWidth() / 2;
    }

    public void smoothScrollBy(int distance) {
        if (orientation == Orientation.VERTICAL) {
            super.smoothScrollBy(0, distance);
            return;
        }

        super.smoothScrollBy(distance, 0);
    }

    public void scrollBy(int distance) {
        if (orientation == Orientation.VERTICAL) {
            super.scrollBy(0, distance);
            return;
        }

        super.scrollBy(distance, 0);
    }

    private void scrollTo(int position) {
        int currentScroll = getScrollOffset();
        scrollBy(position - currentScroll);
    }

    public int getScrollOffset() {
        if (orientation == Orientation.VERTICAL)
            return computeVerticalScrollOffset();

        return computeHorizontalScrollOffset();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 绘制一个中间view
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }


    private static class ChildViewMetrics {
        private Orientation orientation;

        public ChildViewMetrics(Orientation orientation) {
            this.orientation = orientation;
        }

        public int size(View view) {
            if (orientation == Orientation.VERTICAL)
                return view.getHeight();

            return view.getWidth();
        }

        public float location(View view) {
            if (orientation == Orientation.VERTICAL)
                return view.getY();

            return view.getX();
        }

        public float center(View view) {
            return location(view) + (size(view) / 2);
        }
    }

    public enum Orientation {
        HORIZONTAL(LinearLayout.HORIZONTAL),
        VERTICAL(LinearLayout.VERTICAL);

        int value;

        Orientation(int value) {
            this.value = value;
        }

        public int intValue() {
            return value;
        }
    }

    /**
     * 中间view选中接口
     */
    public interface OnViewSelectedListener {
        void onSelected(View view, int position);
    }

    /**
     * 点击某个item接口
     */
    public interface OnViewClickedListener {
        void onClicked(View view, int position);
    }
}
