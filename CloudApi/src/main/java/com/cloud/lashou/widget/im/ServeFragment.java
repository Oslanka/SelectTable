package com.cloud.lashou.widget.im;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cloud.lashou.R;
import com.cloud.lashou.widget.MyViewPager;

/**
 * Created by Administrator on 2017/8/25.
 */

public class ServeFragment extends Fragment {

    private LinearLayout ll_dot;
    private ServeCallBack callBack;

    public static ServeFragment newInstance(int index) {

        ServeFragment myFragment = new ServeFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        myFragment.setArguments(args);
        return myFragment;
    }
    public void setCallBack(ServeCallBack callBack){
        this.callBack = callBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_service_pager, container, false);
        MyViewPager pager = (MyViewPager) view.findViewById(R.id.service_pager);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        ll_dot.removeAllViews();
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.selector_bg_guide_dot);
        imageView.setSelected(true);
        ll_dot.addView(imageView);

        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                String [] arr = {"笑话","天气","购物","附近","酒店","电影票"};
                final String [] content = {"讲个笑话","今天天气怎么样？","推荐一些热销商品","推荐附近人气美食","推荐附近优惠酒店","推荐最新影片"};
                View view  = View.inflate(getContext(),R.layout.layou_service_content,null);
                GridView service_gv = (GridView) view.findViewById(R.id.service_gv);
                service_gv.setAdapter(new ServiceAdapter(getContext(),arr));
                service_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        callBack.contentCallBack(content[position]);
                    }
                });
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ll_dot.removeAllViews();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                    imageView.setImageResource(R.drawable.selector_bg_guide_dot);
                    imageView.setSelected(0 == position);
                    ll_dot.addView(imageView);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    interface ServeCallBack{
        void contentCallBack(String content);
    }
}
