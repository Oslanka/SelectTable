package com.cloud.lashou.widget.swiplelist.swipemenu.interfaces;


import com.cloud.lashou.widget.swiplelist.swipemenu.bean.SwipeMenu;
import com.cloud.lashou.widget.swiplelist.swipemenu.view.SwipeMenuView;

public interface OnSwipeItemClickListener {
    void onItemClick(SwipeMenuView view, SwipeMenu menu, int index);
}