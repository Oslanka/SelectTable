package runvr.oslanka.cnn.selecttable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cloud.lashou.widget.recycle.SlideRecycleViewAdapter;
import com.cloud.lashou.widget.recycle.SlideViewHolder;

import java.util.List;

import runvr.oslanka.cnn.selecttable.bean.SelectPicBean;

import static runvr.oslanka.cnn.selecttable.http.Configs.baseUrl;

public class ScenceListAdapter extends SlideRecycleViewAdapter<SelectPicBean.DataBean> {


    /**
     * 构造器
     *
     * @param context
     * @param mDatas
     * @param itemLayoutId
     */
    protected ScenceListAdapter(Context context, List<SelectPicBean.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, R.layout.select_item);
    }

    @Override
    public void convert(SlideViewHolder holder, final SelectPicBean.DataBean item) {
        final TextView imageView = holder.getView(R.id.imageView);
        TextView ydh = holder.getView(R.id.ydh);
        TextView ydxh = holder.getView(R.id.ydxh);
        TextView time = holder.getView(R.id.time);
        TextView who = holder.getView(R.id.who);
        who.setText("上传者    ： " + item.getWho());
        ydh.setText("运单号    ： " + item.getYdxh());
        ydxh.setText("运单序号    ： " + item.getYdh());
        time.setText("上传时间：  \n" + item.getSctime());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) getmContext();
                activity.startActivity(new Intent(activity, BigPicActivity.class).putExtra("url", baseUrl + "/images?id=" + item.getId()));

            }
        });
//        Glide.with(getmContext()).load(baseUrl + "/images?id=" + item.getId()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

    }
}