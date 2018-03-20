package com.cloud.lashou.widget.im;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloud.lashou.R;
import com.cloud.lashou.widget.MyViewPager;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class ServeAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public ServeAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
