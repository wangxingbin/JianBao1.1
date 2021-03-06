package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wxb.jianbao11.R;
import com.wxb.jianbao11.adapter.MyRecyclerAdapter;
import com.wxb.jianbao11.bean.CheckPublished;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.CustomProgress;
import com.wxb.jianbao11.utils.MyCallBack;
import com.wxb.jianbao11.utils.MyOkhttp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/19.
 */

public class PublishedActivity extends Activity {
    private TextView barName;
    private ImageView backImage;
    private RecyclerView recyclerview;
    private ImageView iv;
    private ArrayList<CheckPublished.DataBean.ListBean> list = new ArrayList<CheckPublished.DataBean.ListBean
            >();
    private Handler mHandler = new Handler();
    private String token;
    private MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_all_base);
        SharedPreferences sp = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        CustomProgress.getPrgressDolilog(PublishedActivity.this,"正在缓冲","请稍后");
        initView();
        // initData();
        initBack();
    }

    private void initBack() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        String curPage = "1";
        String PATH = Contant.CHECKPUBLISHED;
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("curPage", curPage);
        MyOkhttp.getInstance().doRequest(PublishedActivity.this, PATH, MyOkhttp.RequestType.POST, map, new MyCallBack() {
            @Override
            public void loading() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CustomProgress.dissPrgress();

                    }
                });
                if (o == null) {
                    Toast.makeText(PublishedActivity.this, "网络异常，请检查您的网络", Toast.LENGTH_SHORT).show();
                }
                if (o != null && o instanceof CheckPublished) {
                    //iv.setVisibility(View.GONE);
                    CheckPublished published = (CheckPublished) o;
                    list.clear();
                    list.addAll((ArrayList) published.getData().getList());
                    if (list != null && list.isEmpty()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageResource(R.mipmap.shoucang);
                                iv.setVisibility(View.VISIBLE);
                                recyclerview.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                initEvent();
                                iv.setVisibility(View.GONE);
                                recyclerview.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            }

            @Override
            public void onError() {

            }
        }, CheckPublished.class);

    }

    private void initEvent() {
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        if (adapter == null) {
            adapter = new MyRecyclerAdapter(PublishedActivity.this, list);
            adapter.setOnClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                @Override
                public void ItemClickListener(View view, int position) {
                    //Toast.makeText(PublishedActivity.this, "你点击了" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PublishedActivity.this, SPXQActivity.class);
                    intent.putExtra("id", list.get(position).getId() + "");
                    startActivity(intent);
                }
            });

            recyclerview.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // list.clear();
        initData();

    }

    private void initView() {
        barName = (TextView) findViewById(R.id.bar_tv_name);
        backImage = (ImageView) findViewById(R.id.bar_iv_back);
        barName.setText("我发布的");
        recyclerview = (RecyclerView) findViewById(R.id.fragment_fabu_recyclerview);
        iv = (ImageView) findViewById(R.id.fragment_fabu_iv);
    }
}
