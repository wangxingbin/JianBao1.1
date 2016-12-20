package com.wxb.jianbao11.bean.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import listview.mifeng.us.jianbao.R;

/**
 * Created by 孙贝贝 on 2016/12/12.
 */

public class PeopleCenter extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_people,null);
        return view;
    }
}
