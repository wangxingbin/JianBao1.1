package com.wxb.jianbao11.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.reflect.TypeToken;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.activity.Login;
import com.wxb.jianbao11.activity.SPXQActivity;
import com.wxb.jianbao11.activity.SousuoActivity;
import com.wxb.jianbao11.adapter.SPZSAdapter;
import com.wxb.jianbao11.bean.LBZSbean;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.MyCallBack;
import com.wxb.jianbao11.utils.MyOkhttp;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 诺古 on 2016/12/19.
 */
public class GoodsFragment extends android.support.v4.app.Fragment {
    private Handler mHandler = new Handler();
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private int curPage = 1;
    private RecyclerView rv;
    private ArrayList<LBZSbean.DataBean.ListBean> arrayList = null;
    private MaterialRefreshLayout refresh;
    private SPZSAdapter myAdapter;
    private EditText et_sousuo;
    private ImageView iv_sousuo;
    private String token;
    private SharedPreferences sp;
    private RefashRerver receiver=new RefashRerver();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods,container, false);

        sp = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE);
        token = sp.getString("token", "");

        //注册广播-----：动态广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("cn.bgs.refash");
        getActivity().registerReceiver(receiver, filter);


        initData();
        initView(view);
        initEvent();
        return view;
    }

    //广播接收器
    private class RefashRerver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //执行刷新的操作
            Log.e("", "这个广播 " );
            arrayList.clear();
            curPage = 1;
           initData();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }



    private void initEvent() {

        et_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SousuoActivity.class);
                getActivity().startActivity(intent);

            }
        });

        //搜索的点击事件
        iv_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SousuoActivity.class);
                getActivity().startActivity(intent);

            }
        });


        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //refreshing...
                Toast.makeText(getActivity(), "正在刷新...", Toast.LENGTH_SHORT).show();
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
                super.onRefreshLoadMore(materialRefreshLayout);

                status = LOADMORE;
                updateData();
//                Toast.makeText(MainActivity.this, "正在加载，请耐心等待...", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //通知我已经刷新完了
                        materialRefreshLayout.finishRefreshLoadMore();

                    }
                }, 200);

            }


        });


    }

    private void updateData() {

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

    private void initView(View v) {
        et_sousuo = (EditText) v.findViewById(R.id.et_sousuo);
        iv_sousuo = (ImageView) v.findViewById(R.id.iv_sousuo);
        refresh = (MaterialRefreshLayout) v.findViewById(R.id.id_refresh);
        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        HorizontalDividerItemDecoration horizontal = new HorizontalDividerItemDecoration.Builder(getActivity())
//                .color(Color.YELLOW)//完成颜色的设置
//                .build();
//        rv.addItemDecoration(horizontal);

    }

    private void initData() {

        MyOkhttp myOkhttp = MyOkhttp.getInstance();
        Type type = new TypeToken<LBZSbean>() {
        }.getType();

        Log.e("sdhkfjsnl;kfdn", Contant.CHAXUN + "?curPage=" + curPage);
        //封装的okhttp工具类
        myOkhttp.doRequest(getActivity(),
                Contant.CHAXUN + "?curPage=" + curPage,
                MyOkhttp.RequestType.GET,
                null,
                new MyCallBack2(),
                type);

    }

        private ProgressDialog progressDialog;

    //不是通过接口回调的方式写的回调方法,而是通过抽象类的方式写的.
    public class MyCallBack2 extends MyCallBack {
        @Override
        public void loading() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   progressDialog=new ProgressDialog(getActivity());
                    if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }

                }
            });
    }

        @Override
        public void onFailure() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            });
        }

        @Override
        public void onSuccess(Object o) {

            displayData((LBZSbean) o);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });

        }

        @Override
        public void onError() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "接口异常", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

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
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myAdapter = new SPZSAdapter(getActivity(), arrayList);
                    rv.setAdapter(myAdapter);//rv的点击事件
                    myAdapter.setOnClickListener(new SPZSAdapter.OnItemClickListener() {
                        @Override
                        public void ItemClickListener(View view, int postion) {
                            if (token.equals("")) {
                                 Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().startActivity(intent);
                            }else {

                                Toast.makeText(getActivity(), postion + "", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), SPXQActivity.class);
                                System.out.println("jhgbkjklml;k,';l" + arrayList.get(postion).getId());
                                intent.putExtra("id", "" + arrayList.get(postion).getId());
                                getActivity().startActivity(intent);

                            }
                        }
                    });

                }
            });

        } else {
            if (status == REFRESH) {
                arrayList.clear();
                arrayList = (ArrayList<LBZSbean.DataBean.ListBean>) lb.getData().getList();
                System.out.println("集合的大小1" + arrayList.size() + "");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter = new SPZSAdapter(getActivity(), arrayList);
                        rv.setAdapter(myAdapter);//rv的点击事件
                        myAdapter.setOnClickListener(new SPZSAdapter.OnItemClickListener() {
                            @Override
                            public void ItemClickListener(View view, int postion) {
                                if (token.equals("")) {
                                    Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), Login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getActivity().startActivity(intent);
                                }else {

                                    Toast.makeText(getActivity(), postion + "", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), SPXQActivity.class);
                                    System.out.println(arrayList.get(postion).getId());
                                    intent.putExtra("id", "" + arrayList.get(postion).getId());
                                    getActivity().startActivity(intent);

                                }


                            }
                        });

                    }
                });
            } else if (status == LOADMORE) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int size = arrayList.size();
                        ArrayList<LBZSbean.DataBean.ListBean> list1 = (ArrayList<LBZSbean.DataBean.ListBean>) lb.getData().getList();
                        arrayList.addAll(list1);
                        System.out.println("集合的大小1" + arrayList.size() + "");
                        myAdapter = new SPZSAdapter(getActivity(), arrayList);
                        rv.setAdapter(myAdapter);//rv的点击事件
                        rv.scrollToPosition(size - 1);
                        myAdapter.setOnClickListener(new SPZSAdapter.OnItemClickListener() {
                            @Override
                            public void ItemClickListener(View view, int postion) {
                                if (token.equals("")) {

                                    Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), Login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getActivity().startActivity(intent);
                                }else {

                                    Toast.makeText(getActivity(), postion + "", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), SPXQActivity.class);
                                    intent.putExtra("id", "" + arrayList.get(postion).getId());
                                    System.out.println(arrayList.get(postion).getId());
                                    getActivity().startActivity(intent);

                                }

                            }
                        });
                    }
                });


            }

        }

    }
}

