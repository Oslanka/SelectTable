package com.cloud.lashou.widget.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以添加多个透视图、尾视图的适配器
 * 只需重写简单方法即可
 * 需要 DmlViewHolder 配合
 */
public abstract class SlideRecycleViewAdapter<T> extends RecyclerView.Adapter<SlideViewHolder> {


    private ArrayList<View> mHeaderViews = new ArrayList<>(); //头视图
    private ArrayList<View> mFooterViews = new ArrayList<>();   //尾视图


    private ArrayList<Integer> mHeaderViewTypes = new ArrayList<>();
    private ArrayList<Integer> mFooterViewTypes = new ArrayList<>();


    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;


    /**
     * 构造器
     *
     * @param context
     * @param mDatas
     * @param itemLayoutId
     */
    protected SlideRecycleViewAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    /**
     * 可以添加多个头视图
     *
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        mHeaderViews.add(headerView);
    }

    public void clearHeaderView() {
        mHeaderViews.clear();
    }

    /**
     * 可以添加多个尾视图
     *
     * @param footerView 尾视图
     */
    public void addFooterView(View footerView) {
        mFooterViews.add(footerView);
    }

    public void clearFooterView() {
        mFooterViews.clear();
    }

    public void reMoveFooterView(View footerView) {
        mFooterViews.remove(footerView);
    }

    @Override
    public int getItemViewType(int position) {

        if (mHeaderViews.size() > 0 && position < mHeaderViews.size()) {
            //用position作为HeaderView 的   ViewType标记
            //记录每个ViewType标记
            mHeaderViewTypes.add(position * 100000);
            return position * 100000;
        }

        if (mFooterViews.size() > 0 && position > getAdvanceCount() - 1 + mHeaderViews.size()) {
            //用position作为FooterView 的   ViewType标记
            //记录每个ViewType标记
            mFooterViewTypes.add(position * 100000);
            return position * 100000;
        }

        if (mHeaderViews.size() > 0) {
            return getAdvanceViewType(position - mHeaderViews.size());
        }


        return getAdvanceViewType(position);
    }

    /**
     * Item页布局类型个数，默认为1
     * ！！！！不能返回0！！！！
     *
     * @param position
     * @return 不能放为0！！
     */
    public int getAdvanceViewType(int position) {
        return 1;
    }

    public int getAdvanceCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    private void onBindAdvanceViewHolder(SlideViewHolder holder, int i) {
        convert(holder, mDatas.get(i));
        convertWithPosition(holder,mDatas.get(i),i);
    }

    /**
     * 设置每个页面显示的内容
     *
     * @param holder itemHolder
     * @param item   每一Item显示的数据
     */
    public abstract void convert(SlideViewHolder holder, T item);
    public void convertWithPosition(SlideViewHolder holder, T item,int position){

    }

    /**
     * 创建ViewHolder
     *
     * @param parent   RecycleView对象
     * @param viewType viee类型
     * @return Holder对象
     */
    protected SlideViewHolder onCreateAdvanceViewHolder(ViewGroup parent, int viewType) {

        View v = mInflater.inflate(mItemLayoutId, parent, false);
        return new SlideViewHolder(v, this);
    }

    @Override
    public SlideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (mHeaderViewTypes.contains(viewType)) {
            return new HeaderHolder(mHeaderViews.get(viewType / 100000));
        }

        if (mFooterViewTypes.contains(viewType)) {
            int index = viewType / 100000 - getAdvanceCount() - mHeaderViews.size();
            return new FooterHolder(mFooterViews.get(index));
        }

        return onCreateAdvanceViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(SlideViewHolder holder, int position) {

        if (mFooterViews.size() > 0 && (position > getAdvanceCount() - 1 + mHeaderViews.size())) {
            return;
        }


        if (mHeaderViews.size() > 0) {
            if (position < mHeaderViews.size()) {
                return;
            }
            onBindAdvanceViewHolder(holder, position - mHeaderViews.size());
            return;
        }
        onBindAdvanceViewHolder(holder, position);
    }


    class HeaderHolder extends SlideViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterHolder extends SlideViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderViews.size() > 0 && mFooterViews.size() > 0) {
            return getAdvanceCount() + mHeaderViews.size() + mFooterViews.size();
        }
        if (mHeaderViews.size() > 0) {
            return getAdvanceCount() + mHeaderViews.size();
        }
        if (mFooterViews.size() > 0) {
            return getAdvanceCount() + mFooterViews.size();
        }

        return getAdvanceCount();
    }

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void addItem(T t, int position) {
        this.mDatas.add(position, t);
        notifyItemInserted(position);
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }


    protected OnItemClickListener l;


    public OnItemClickListener getItemClickListener() {
        return l;
    }

    /**
     * 设置点击事件监听器
     *
     * @param l 监听器对象
     */
    public void setOnItemClickListener(OnItemClickListener l) {
        this.l = l;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }


    public Context getmContext() {
        return mContext;
    }
}
