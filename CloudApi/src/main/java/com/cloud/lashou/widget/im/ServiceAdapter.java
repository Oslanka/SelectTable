package com.cloud.lashou.widget.im;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cloud.lashou.R;

/**
 * Created by Administrator on 2017/8/25.
 */

public class ServiceAdapter extends BaseAdapter {
    private String[] arr;
    private Context mContext;
    public ServiceAdapter(Context mContext, String[] arr) {
        this.arr = arr;
        this.mContext  = mContext;
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int position) {
        return arr[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = View.inflate(mContext, R.layout.layout_service_item, null);
            holder = new ViewHolder();
            holder.service_icon = (ImageView) convertView.findViewById(R.id.service_icon);
            holder.service_content = (TextView) convertView.findViewById(R.id.service_content);
            holder.lock_iv = (ImageView) convertView.findViewById(R.id.lock_iv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        if(arr[position].equals("笑话")){
            Glide.with(mContext).load(R.mipmap.joke_icon).into(holder.service_icon);
            holder.lock_iv.setVisibility(View.GONE);
        }else if(arr[position].equals("天气")){
            Glide.with(mContext).load(R.mipmap.weather_icon).into(holder.service_icon);
            holder.lock_iv.setVisibility(View.GONE);
        }else if(arr[position].equals("购物")){
            Glide.with(mContext).load(R.mipmap.shoping_icon).into(holder.service_icon);
            holder.lock_iv.setVisibility(View.GONE);
        }else if(arr[position].equals("附近")){
            Glide.with(mContext).load(R.mipmap.nearby_icon_gary).into(holder.service_icon);
            holder.lock_iv.setVisibility(View.VISIBLE);
        }else if(arr[position].equals("酒店")){
            Glide.with(mContext).load(R.mipmap.hotel_icon_gary).into(holder.service_icon);
            holder.lock_iv.setVisibility(View.VISIBLE);
        }else if(arr[position].equals("电影票")){
            Glide.with(mContext).load(R.mipmap.cinema_ticket_icon_gary).into(holder.service_icon);
            holder.lock_iv.setVisibility(View.VISIBLE);
        }
        holder.service_content.setText(arr[position]);
//        PictureUtils.showImageViewGone(mContext,holder.brand_iv,brands.get(position).getImg());
//        Glide.with(mContext).load(brands.get(position).getImg()).into(holder.brand_iv);
        return convertView;
    }

    class ViewHolder{
        ImageView lock_iv;
        TextView service_content;
        ImageView service_icon;
    }
}
