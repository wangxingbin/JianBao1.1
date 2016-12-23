package com.wxb.jianbao11.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by 诺古 on 2016/12/23.
 */

public class GuideAdapter extends PagerAdapter {
    private static final String TAG = "GuideAdapter";
    private Context mContext;
    private ArrayList mList;

    public GuideAdapter(Context context, ArrayList list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        Log.e(TAG, "getCount: " + mList.size());
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = (View) mList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //什么都不写'
        View view = (View) mList.get(position);
        if (view.getParent() != null) {
            ((ViewPager) container).removeView(view);
        }
    }
}
