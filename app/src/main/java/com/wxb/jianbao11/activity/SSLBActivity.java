package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.reflect.TypeToken;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.adapter.SPZSAdapter;
import com.wxb.jianbao11.bean.LBZSbean;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.MyCallBack;
import com.wxb.jianbao11.utils.MyOkhttp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by ti on 2016/12/6.
 */
public class SSLBActivity extends Activity {

    private RecyclerView sslb_rv;
    private ImageView sslb_back;
    private MaterialRefreshLayout refresh;

    private ArrayList<LBZSbean.DataBean.ListBean> arrayList = null;

    private Handler mHandler = new Handler();

    private int status = 1;
    private int curPage = 1;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private String str;
    private SPZSAdapter myAdapter;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sslb_activity);

        Intent intent = getIntent();
        str = intent.getStringExtra("etstr");

        initData();
        initView();
        initEvent();

    }

    private void initData() {

        MyOkhttp myOkhttp = MyOkhttp.getInstance();
        Type type = new TypeToken<LBZSbean>() {
        }.getType();

        Log.e("sdhkfjsnl;kfdn", Contant.CHAXUN + "?curPage=" + curPage + "&title=" + str);
        //封装的okhttp工具类
        myOkhttp.doRequest(Contant.CHAXUN + "?curPage=" + curPage + "&title=" + str,
                MyOkhttp.RequestType.GET,
                null,
                new MyCallBack3(),
                type);


    }

    public void updateData() {
        switch (status) {
            case REFRESH:
                curPage = 1;
                initData();
                break;
            case LOADMORE:
                curPage = curPage + 1;
                initData();
                break;
            default:
                break;
        }
    }

    private void initEvent() {
        sslb_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //refreshing...
                Toast.makeText(SSLBActivity.this, "刷新...", Toast.LENGTH_SHORT).show();
                status = REFRESH;
                updateData();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //通知我已经刷新完了
                        materialRefreshLayout.finishRefresh();

                    }
                }, 1000);


            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                status = LOADMORE;
                updateData();
                //load more refreshing...
                Toast.makeText(SSLBActivity.this, "加载更多 ...", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //通知我已经刷新完了
                        materialRefreshLayout.finishRefreshLoadMore();

                    }
                }, 1000);

            }
        });

    }

    private void initView() {
        sslb_rv = (RecyclerView) findViewById(R.id.sslb_rv);
//        sslb_rv.setLayoutManager(new GridLayoutManager(SSLBActivity.this,2));

        ll = (LinearLayout) findViewById(R.id.ll);

        sslb_rv.setLayoutManager(new LinearLayoutManager(SSLBActivity.this));
        //用了一个开源的devider,在github上搜了itemdecoration
        HorizontalDividerItemDecoration horizontal = new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.YELLOW)//完成颜色的设置
                .build();
        sslb_rv.addItemDecoration(horizontal);

        sslb_back = (ImageView) findViewById(R.id.sslb_back);
        refresh = (MaterialRefreshLayout) findViewById(R.id.refresh);

    }

    class MyCallBack3 extends MyCallBack {

        @Override
        public void loading() {
        }

        @Override
        public void onFailure() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SSLBActivity.this, "网络错误", Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public void onSuccess(Object o) {

            displayData((LBZSbean) o);

        }

        @Override
        public void onError() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SSLBActivity.this, "接口异常", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    //抽取的展示数据的方法
    private void displayData(LBZSbean o) {
        final LBZSbean lb = o;

        if (arrayList == null) {

            arrayList = (ArrayList<LBZSbean.DataBean.ListBean>) lb.getData().getList();

            System.out.println("集合的大小1" + arrayList.size() + "");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(arrayList.size()>0){

                        myAdapter = new SPZSAdapter(SSLBActivity.this, arrayList);
                        sslb_rv.setAdapter(myAdapter);//rv的点击事件
                        myAdapter.setOnClickListener(new SPZSAdapter.OnItemClickListener() {
                            @Override
                            public void ItemClickListener(View view, int postion) {
                                Toast.makeText(SSLBActivity.this, postion + "", Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(SSLBActivity.this, SPXQActivity.class);
//                                System.out.println("jhgbkjklml;k,';l" + arrayList.get(postion).getId());
//                                intent.putExtra("id", "" + arrayList.get(postion).getId());
//                                startActivity(intent);

                            }
                        });


                    }else {

                        ll.setBackgroundResource(R.mipmap.ic_launcher);

                    }

                }
            });

        } else {
            if (status == REFRESH) {
                arrayList.clear();
                arrayList = (ArrayList<LBZSbean.DataBean.ListBean>) lb.getData().getList();
                System.out.println("集合的大小1" + arrayList.size() + "");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(arrayList.size()>0){
                            myAdapter = new SPZSAdapter(SSLBActivity.this,arrayList);
                            sslb_rv.setAdapter(myAdapter);//rv的点击事件
                            myAdapter.setOnClickListener(new SPZSAdapter.OnItemClickListener() {
                                @Override
                                public void ItemClickListener(View view, int postion) {
                                    Toast.makeText(SSLBActivity.this, postion + "", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(SSLBActivity.this, SPXQActivity.class);
//                                    System.out.println(arrayList.get(postion).getId());
//                                    intent.putExtra("id", "" + arrayList.get(postion).getId());
//                                    startActivity(intent);

                                }
                            });

                        }else {

                            ll.setBackgroundResource(R.mipmap.ic_launcher);
                        }

                    }
                });
            } else if (status == LOADMORE) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int size = arrayList.size();
                        ArrayList<LBZSbean.DataBean.ListBean> list1 = (ArrayList<LBZSbean.DataBean.ListBean>) lb.getData().getList();
                        arrayList.addAll(list1);
                        System.out.println("集合的大小1" + arrayList.size() + "");
                        if(arrayList.size()>0){

                            myAdapter = new SPZSAdapter(SSLBActivity.this, arrayList);
                            sslb_rv.setAdapter(myAdapter);
                            //rv的点击事件
                            sslb_rv.scrollToPosition(size - 1);
                            myAdapter.setOnClickListener(new SPZSAdapter.OnItemClickListener() {
                                @Override
                                public void ItemClickListener(View view, int postion) {
                                    Toast.makeText(SSLBActivity.this, postion + "", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(SSLBActivity.this, SPXQActivity.class);
//                                    intent.putExtra("id", "" + arrayList.get(postion).getId());
//                                    System.out.println(arrayList.get(postion).getId());
//                                    startActivity(intent);

                                }
                            });

                        }else {

                            ll.setBackgroundResource(R.mipmap.ic_launcher);

                        }

                    }
                });
            }
        }


    }
}
