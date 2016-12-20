package com.wxb.jianbao11.bean.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxb.jianbao11.R;

import java.util.HashMap;

import static java.security.AccessController.getContext;

/**
 * Created by 孙贝贝 on 2016/12/12.
 */

public class ListGoods extends android.support.v4.app.Fragment{


    private View view;
    private SwipeRefreshLayout swipe;
    private RecyclerView list;
    private static final String TAG = "ListGoods";
    private List<ListGoodBeen.DataBean.ListBean> lists;
    private String  statue;
    private RecyclerView.LayoutManager manager;
    private RecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listgoods_view,null);
        initview();

        return view;
    }

    private void initview() {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        list = (RecyclerView) view.findViewById(R.id.list_view);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(manager);
        initdata();
        adapter = new RecyclerViewAdapter(lists, getContext());
        list.setAdapter(adapter);
    }



    private void initdata() {
        String path= Contans.LISTGOODS;
        HashMap<String,String> map= new HashMap<>();
        map.put("curPage","1");
        OkhttpUtils.post(map,path,getContext(),ListGoodBeen.class);
        OkhttpUtils.setGetEntiydata(new OkhttpUtils.EntiyData() {
            @Override
            public void getEntiy(Object o) {
                ListGoodBeen listbeen= (ListGoodBeen) o;
                String status = listbeen.getStatus();
                ListGoodBeen.DataBean data = listbeen.getData();
                lists = data.getList();
                Log.i(TAG, lists +"");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });




            }
        });
    }
}
