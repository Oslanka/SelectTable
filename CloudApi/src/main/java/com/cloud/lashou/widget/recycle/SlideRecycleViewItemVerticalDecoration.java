package com.cloud.lashou.widget.recycle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cloud.lashou.utils.DensityUtil;


/**
 * 分割线
 */
public class SlideRecycleViewItemVerticalDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    private int leftMargin = 0;
    private int rightMargit = 0;


    private boolean isShowFirstItemDecoration = true;
    private boolean isShowSecondItemDecoration = true;
    private boolean isShowLastItemDecoration = true;

    public void setShowLastItemDecoration(boolean showLastItemDecoration) {
        this.isShowLastItemDecoration = showLastItemDecoration;
    }


    public boolean isShowFirstItemDecoration() {
        return isShowFirstItemDecoration;
    }

    /**
     * 设置是否显示第一行分割线
     *
     * @param isShowFirstItemDecoration false 不显示
     */
    public void setIsShowFirstItemDecoration(boolean isShowFirstItemDecoration) {
        this.isShowFirstItemDecoration = isShowFirstItemDecoration;
    }

    public boolean isShowSecondItemDecoration() {
        return isShowSecondItemDecoration;
    }

    /**
     * 设置是否显示第二行分割线
     *
     * @param isShowSecondItemDecoration false 不显示
     */
    public void setIsShowSecondItemDecoration(boolean isShowSecondItemDecoration) {
        this.isShowSecondItemDecoration = isShowSecondItemDecoration;
    }

    public SlideRecycleViewItemVerticalDecoration(Context context, int orientation, Drawable divider) {
        mDivider = divider;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    /**
     * 设置  分割线左边边距
     *
     * @param dp
     * @return
     */
    public SlideRecycleViewItemVerticalDecoration setMarginLeftDP(int dp) {
        leftMargin = dp;
        return this;
    }

    /**
     * 设置  分割线右边边距
     *
     * @param dp
     * @return
     */
    public SlideRecycleViewItemVerticalDecoration setMarginRightDP(int dp) {
        this.rightMargit = dp;
        return this;
    }

    private LinearLayoutManager getLinearLayoutManger(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return (LinearLayoutManager) layoutManager;
        }
        return null;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {


        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + DensityUtil.dip2px(parent.getContext(), leftMargin);
        final int right = parent.getWidth() - parent.getPaddingRight() - DensityUtil.dip2px(parent.getContext(), rightMargit);

        final int childCount = parent.getChildCount();
        LinearLayoutManager layoutManager = getLinearLayoutManger(parent);
        if (layoutManager == null) {
            return;
        }
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView v = new RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            if (i == 0 && firstVisiblePosition == 0 && !isShowFirstItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }

            if (i == 1 && firstVisiblePosition == 0 && !isShowSecondItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }

            if (i == 0 && firstVisiblePosition == 1 && !isShowSecondItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }
            if (i == childCount - 1 && !this.isShowLastItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }

            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        LinearLayoutManager layoutManager = getLinearLayoutManger(parent);
        if (layoutManager == null) {
            return;
        }
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            if (i == 0 && firstVisiblePosition == 0 && !isShowFirstItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }
            if (i == 1 && firstVisiblePosition == 0 && !isShowSecondItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }
            if (i == 0 && firstVisiblePosition == 1 && !isShowSecondItemDecoration) {
                mDivider.setBounds(0, 0, 0, 0);
            }
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        int childPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        if (manager != null) {
            if (manager instanceof LinearLayoutManager) {
                setLinearOffset(((LinearLayoutManager) manager).getOrientation(), outRect, childPosition, itemCount);
            }
        }

    }

    private void setLinearOffset(int orientation, Rect outRect, int childPosition, int itemCount) {
        if (mOrientation == HORIZONTAL_LIST) {
            if (childPosition == 0 && !isShowFirstItemDecoration) {
                // 第一个要设置PaddingLeft
                outRect.set(0, 0, 0, 0);
            } else if (childPosition == itemCount - 1 && !isShowFirstItemDecoration) {
                // 最后一个设置PaddingRight
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        } else {
            if (childPosition == 0 && !isShowFirstItemDecoration) {
                // 第一个要设置PaddingTop
                outRect.set(0, 0, 0, 0);
            } else if (childPosition == itemCount - 1 && !isShowLastItemDecoration) {
                // 最后一个要设置PaddingBottom
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
        }
    }

}
