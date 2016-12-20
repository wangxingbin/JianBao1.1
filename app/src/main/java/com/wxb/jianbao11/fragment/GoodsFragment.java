package com.wxb.jianbao11.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.adapter.SPZSAdapter;
import com.wxb.jianbao11.bean.LBZSbean;

import java.util.ArrayList;


/**
 * Created by 诺古 on 2016/12/19.
 */
public class GoodsFragment extends android.support.v4.app.Fragment {

    private EditText et_sousuo;
    private ImageView iv_sousuo;
    private MaterialRefreshLayout refresh;
    private SwipeMenuListView listView;
    private ArrayList<LBZSbean.DataBean.ListBean> arrayList=null;

    private Handler mHandler = new Handler();
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private int curPage = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods,null);

        initView(view);
        initData();//kjckmb

        SPZSAdapter adapter=new SPZSAdapter(getActivity(),arrayList);
        listView.setAdapter(adapter);


        initEvent();
        return view;
    }



    private void initEvent() {

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
                },1000);


            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);

                status = LOADMORE;
                updateData();
                //load more refreshing...
//                Toast.makeText(MainActivity.this, "正在加载，请耐心等待...", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //通知我已经刷新完了
                        materialRefreshLayout.finishRefreshLoadMore();

                    }
                },200);

            }


        });

//        //搜索的点击事件
//        et_sousuo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),SousuoActivity.class);
//                getActivity().startActivity(intent);
//
//            }
//        });
//        //搜索的点击事件
//        iv_sousuo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),SousuoActivity.class);
//                getActivity().startActivity(intent);
//
//            }
//        });

        SwipeMenuCreator creator=new SwipeMenuCreator(){
            @Override
            public void create(SwipeMenu menu) {

                //创建一个菜单条
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // 设置菜单的背景
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // 宽度  菜单的宽度是一定要有的，否则不显示菜单，笔者就吃了这样的亏
                openItem.setWidth(dp2px(90));
                // 菜单标题
                openItem.setTitle("Open");
                // 标题大小
                openItem.setTitleSize(18);
                // 标题的颜色
                openItem.setTitleColor(Color.WHITE);
                // 添加到menu
                menu.addMenuItem(openItem);

                SwipeMenuItem item1 = new SwipeMenuItem(
                        getActivity());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0,
                        0x3F)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.mipmap.ic_launcher);
                menu.addMenuItem(item1);


            }
        };
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open

//                        mAppList.remove(position);
//                        mAdapter.notifyDataSetChanged();

                        break;
                    case 1:
                        // delete
//					delete(item);
//                        mAppList.remove(position);
//                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

    }

    private void initData() {






    }


    private void updateData() {

        switch (status) {
            case REFRESH:
                curPage = 1;
                initData();
                break;
            case LOADMORE:
                curPage = curPage + 1 ;
                initData();
                break;
            default:
                break;
        }
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void initView(View v) {

        et_sousuo = (EditText) v.findViewById(R.id.et_sousuo);
        iv_sousuo = (ImageView) v.findViewById(R.id.iv_sousuo);
        et_sousuo.clearFocus();

        refresh = (MaterialRefreshLayout) v.findViewById(R.id.id_refresh);

        listView = (SwipeMenuListView) v.findViewById(R.id.listView);

    }

}
