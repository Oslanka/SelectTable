package com.cloud.lashou.widget.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.cloud.lashou.widget.pulltorefresh.PullToRefreshBase;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;


/**
 * 可以刷新的RecycleView
 */
public class SlideRecycleView extends PullToRefreshBase<RecyclerView> {

    public SlideRecycleView(Context context) {
        super(context);
    }

    public SlideRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideRecycleView(Context context, Mode mode) {
        super(context, mode);
    }

    public SlideRecycleView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    //重写4个方法
    //1 滑动方向
    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    //重写4个方法
    //2  滑动的View
    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView view = new RecyclerView(context, attrs);
        return view;
    }

    public void setImagePauseLazyLoadListener() {
        this.getRefreshableView().setOnScrollListener(new ImageAutoLoadScrollListener());
    }

    //监听滚动来对图片加载进行判断处理
    public class ImageAutoLoadScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                    //当屏幕停止滚动，加载图片
                    try {
                        if (getContext() != null) Glide.with(getContext()).resumeRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
                    //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                    try {
                        if (getContext() != null) Glide.with(getContext()).pauseRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under outside control.
                    //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                    try {
                        if (getContext() != null) Glide.with(getContext()).pauseRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    //重写4个方法
    //3 是否滑动到底部
    @Override
    protected boolean isReadyForPullEnd() {
        int lastVisiblePosition = getRefreshableView().getChildAdapterPosition(getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1));
        if (getRefreshableView().getAdapter() != null && lastVisiblePosition >= getRefreshableView().getAdapter().getItemCount() - 1 && getRefreshableView().getAdapter().getItemCount() > 0) {
            return getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1).getBottom() <= getRefreshableView().getBottom();
        }
        return false;
    }

    //重写4个方法
    //4 是否滑动到顶部
    @Override
    protected boolean isReadyForPullStart() {
        if (getRefreshableView().getChildCount() <= 0)
            return true;
        int firstVisiblePosition = getRefreshableView().getChildAdapterPosition(getRefreshableView().getChildAt(0));
        if (firstVisiblePosition == 0)
            return getRefreshableView().getChildAt(0).getTop() == getRefreshableView().getPaddingTop();
        else
            return false;
    }

    //是否可以继续加载 true 仍可继续加载 false 不可继续加载
    public void onLoadComplete(boolean isMore) {
        setLoadAvailable(isMore);
    }
}
