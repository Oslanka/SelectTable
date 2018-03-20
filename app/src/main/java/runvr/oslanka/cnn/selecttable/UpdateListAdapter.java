package runvr.oslanka.cnn.selecttable;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.lashou.widget.recycle.SlideRecycleViewAdapter;
import com.cloud.lashou.widget.recycle.SlideViewHolder;

import java.util.List;

import runvr.oslanka.cnn.selecttable.bean.SelectBean;

public class UpdateListAdapter extends SlideRecycleViewAdapter<SelectBean.DataBean> {


    /**
     * 构造器
     *
     * @param context
     * @param mDatas
     * @param itemLayoutId
     */
    protected UpdateListAdapter(Context context, List<SelectBean.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, R.layout.activity_iamge_upload_layout);
    }

    @Override
    public void convert(SlideViewHolder holder, final SelectBean.DataBean dataBean) {

        ((TextView) holder.getView(R.id.ydh)).setText("运单号 : " + dataBean.getYdxh());
        ((TextView) holder.getView(R.id.time)).
                setText("托运日期 : " + dataBean.getTyrq());
        ((TextView) holder.getView(R.id.from)).setText(dataBean.getJbren());
        ((TextView) holder.getView(R.id.name)).setText(dataBean.getTyr());
        ((TextView) holder.getView(R.id.to)).setText(dataBean.getYhzh());
        ((TextView) holder.getView(R.id.who)).setText(dataBean.getShdw());
        ImageView ivIsCopy = holder.getView(R.id.iv_is_copy);
        if (dataBean.isSelect()) {
            ivIsCopy.setImageResource(R.mipmap.scene_copy_select);
            ivIsCopy.setVisibility(View.VISIBLE);

        } else if (mDatas.size() == 1) {
            ivIsCopy.setImageResource(R.mipmap.scene_copy_select);
            ivIsCopy.setVisibility(View.VISIBLE);
        } else {
            ivIsCopy.setImageResource(R.mipmap.scene_copy_back);
            ivIsCopy.setVisibility(View.GONE);
        }
//        layoutCenter.addView(getTextView("运单号 : ", dataBean.getYdxh(), from));
//        layoutCenter.addView(getTextView("托运日期 : ", dataBean.getTyrq(), from));
//        layoutCenter.addView(getTextView("始发站 : ", dataBean.getJbren(), from));
//        layoutCenter.addView(getTextView("目的区域 : ", dataBean.getYhzh(), from));
//        layoutCenter.addView(getTextView("收货人 : ", dataBean.getShdw(), from));
//        layoutCenter.addView(getTextView("拍照上传", dataBean.getShdw(), from));

    }
}