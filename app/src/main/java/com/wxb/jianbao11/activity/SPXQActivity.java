package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.adapter.DGSPTPAdapter;
import com.wxb.jianbao11.bean.GuanZhu;
import com.wxb.jianbao11.bean.SPXQ;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.MyCallBack;
import com.wxb.jianbao11.utils.MyOkhttp;
import com.wxb.jianbao11.view.FullyLinearLayoutManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ti on 2016/12/21.
 */

public class SPXQActivity extends Activity {
    private ImageView iv;
    private ScrollView scrollView;
    private int vpheight;
    private ImageView wpgzFollow;
    private String id;
    private TextView wpfbTime,wpPrice,wpTitle,wpDescription,wpMobile,wpQQ,wpWechat,wpEmail,tv_follownumber,tv_person;
    private SimpleDraweeView draweeView;
    private ArrayList<ImageView> dian;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private boolean aBoolean;
    private int act;
    private RecyclerView dgsptp_rv;
    private MyOkhttp myOkhttp;
    private HashMap<String, String> map = new HashMap<String, String>();
    private HashMap<String, String> map1 = new HashMap<String, String>();
    private boolean followed;
    private int follow;
    private SharedPreferences sp;
    private String token;
    private SimpleDraweeView iv_head;
    private LinearLayout ll_gz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spxiangqing);
        initView();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Log.e("ididiid", id);

        sp = getSharedPreferences("TOKEN", MODE_PRIVATE);
        token = sp.getString("token", "");

        initData();

        initEvent();


    }


    private void initEvent() {

        //返回按钮
        findViewById(R.id.bar_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //收藏按钮
        ll_gz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!followed) {
                    act = 0;
                    wpgzFollow.setBackgroundResource(R.mipmap.scd);
                    followed = true;

                } else {
                    act = 1;
                    wpgzFollow.setBackgroundResource(R.mipmap.scm);
                    followed = false;
                }

                map.put("id", id);
                map.put("act", "" + act);
                map.put("token", token);
                soucang(act);


            }
        });

    }

    private void soucang(final int aa) {

        //  Log.e("商品详情网址", Constant.GUANZHU + "?id=" + id + "&act=" + aa + "&token=" + TOKEN);
        Type type = new TypeToken<GuanZhu>() {
        }.getType();
        String path = Contant.GUANZHU;
        myOkhttp.doRequest(path,
                MyOkhttp.RequestType.POST,
                map,
                new MyCallBack() {
                    @Override
                    public void loading() {

                    }

                    @Override
                    public void onFailure() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(SPXQActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onSuccess(Object o) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (aa == 0) {
//                                    Toast.makeText(SPXQActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                    Toast.makeText(SPXQActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                                    follow = follow + 1;
                                    tv_follownumber.setText("关注人数："+follow);

                                } else if (aa == 1) {
                                    Toast.makeText(SPXQActivity.this, "取消关注", Toast.LENGTH_SHORT).show();
                                    follow = follow - 1;
                                    tv_follownumber.setText("关注人数："+follow);

                                }


                            }
                        });


                    }

                    @Override
                    public void onError() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SPXQActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                },
                type);

    }

    private void initData() {

        map1.put("id", id);
        map1.put("token", token);


        myOkhttp = MyOkhttp.getInstance();
        Log.e("商品详情网址", Contant.XIANGQING + "?id=" + id + "&token=" + token);
        Type type = new TypeToken<SPXQ>() {
        }.getType();
        myOkhttp.doRequest(Contant.XIANGQING,
                MyOkhttp.RequestType.POST,
                map1,
                new MyCallBack11(),
                type);


    }


    private void initView() {

        scrollView = (ScrollView) findViewById(R.id.scrollview);
        wpfbTime = (TextView) findViewById(R.id.wuping_time);
        wpPrice = (TextView) findViewById(R.id.wuping_jiaqian);
        TextView bar_tv_name= (TextView) findViewById(R.id.bar_tv_name);
        bar_tv_name.setText("商品详情");

        tv_follownumber = (TextView) findViewById(R.id.tv_guanzhushuliang);
        wpgzFollow = (ImageView) findViewById(R.
                id.wuping_shoucang);
      /*  wpTitle = (TextView) findViewById(R.id.wuping_title);*/
        wpDescription = (TextView) findViewById(R.id.wuping_xiangqing);
        tv_person = (TextView) findViewById(R.id.tv_person);
        wpMobile = (TextView) findViewById(R.id.wuping_phone);
        iv_head = (SimpleDraweeView) findViewById(R.id.my_image_view);
        wpQQ = (TextView) findViewById(R.id.wuping_qq);
        wpWechat = (TextView) findViewById(R.id.wuping_weixin);
        wpEmail = (TextView) findViewById(R.id.wuping_email);

        ll_gz = (LinearLayout) findViewById(R.id.ll_gz);

        dgsptp_rv = (RecyclerView) findViewById(R.id.dgsptp_rv);
//        FullyGridLayoutManager manager=new FullyGridLayoutManager(SPXQActivity.this,3);
//        manager.setOrientation(GridLayoutManager.VERTICAL);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(SPXQActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        dgsptp_rv.setLayoutManager(manager);


    }

    public class MyCallBack11 extends MyCallBack {


        public void loading() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }

        @Override
        public void onFailure() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SPXQActivity.this, "network failure", Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public void onSuccess(Object o) {

            displayData((SPXQ) o);

        }

        @Override
        public void onError() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SPXQActivity.this, "request params error", Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

    private void displayData(SPXQ o) {
        final SPXQ sp = o;
        Log.e("", sp.toString());
        Log.e("", sp.getData().toString());

        final String title = sp.getData().getTitle();
        final String time = sp.getData().getIssue_time();
        final String description = sp.getData().getDescription();
        final String price = sp.getData().getPrice();
        final String mobile = sp.getData().getMobile();
        final String qq = sp.getData().getQq();
        final String email = sp.getData().getEmail();
        final String wecat = sp.getData().getWechat();
        final String head=sp.getData().getHead();
        follow = sp.getData().getFollow();
        followed = sp.getData().isFollowed();
        final String Contact = sp.getData().getContact();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                wpfbTime.setText("" + time);
//                wpTitle.setText(title);
                wpDescription.setText(description);
                wpPrice.setText("¥"+price);
                tv_follownumber.setText("关注人数:"+follow );
                tv_person.setText(Contact);

                Uri uri = Uri.parse("http://192.168.4.188/Goods/uploads/"+ head);
                iv_head.setImageURI(uri);

                if (TextUtils.isEmpty(mobile)) {
                    wpMobile.setText("");
                    wpMobile.setVisibility(View.GONE);
                } else {
                    wpMobile.setVisibility(View.VISIBLE);
                    wpMobile.setText("联系电话：" + sp.getData().getMobile());
                }

                if (TextUtils.isEmpty(qq)) {
                    wpQQ.setText("");
                    wpQQ.setVisibility(View.GONE);
                } else {
                    wpQQ.setVisibility(View.VISIBLE);
                    wpQQ.setText("Q Q：" + sp.getData().getMobile());
                }
                if (TextUtils.isEmpty(email)) {
                    wpEmail.setText("");
                    wpEmail.setVisibility(View.GONE);
                } else {
                    wpEmail.setVisibility(View.GONE);
                    wpEmail.setText("Email：" + sp.getData().getMobile());
                }
                if (TextUtils.isEmpty(wecat)) {
                    wpWechat.setText("");
                    wpWechat.setVisibility(View.GONE);
                } else {
                    wpWechat.setVisibility(View.VISIBLE);
                    wpWechat.setText("微 信：" + sp.getData().getMobile());
                }
                if (followed) {
                   wpgzFollow.setBackgroundResource(R.mipmap.scd);
                } else {
                    wpgzFollow.setBackgroundResource(R.mipmap.scm);
                }

                tianjiatupian(sp);

            }
        });


    }

    private void tianjiatupian(SPXQ sp) {

        final ArrayList<String> alist = (ArrayList<String>) sp.getData().getPhotos();

        if (alist.size() > 0) {

            DGSPTPAdapter dpAdapter = new DGSPTPAdapter(alist, SPXQActivity.this);
            dgsptp_rv.setAdapter(dpAdapter);
            dpAdapter.setOnClickListener(new DGSPTPAdapter.OnItemClickListener() {
                @Override
                public void ItemClickListener(View view, int postion) {

                    Intent intent = new Intent(SPXQActivity.this, PhotoViewD.class);
                    intent.putExtra("photo", alist.get(postion));
                    startActivity(intent);


                }
            });

        } else {
            dgsptp_rv.setVisibility(View.GONE);

        }

    }



}

