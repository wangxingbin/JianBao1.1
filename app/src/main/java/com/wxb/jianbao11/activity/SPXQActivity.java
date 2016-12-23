package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.adapter.DGSPTPAdapter;
import com.wxb.jianbao11.bean.GuanZhu;
import com.wxb.jianbao11.bean.SPXQ;
import com.wxb.jianbao11.bean.XiaJia;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.ImageTools;
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
    private TextView wpfbTime,wpPrice,wpTitle,wpDescription,wpMobile,wpQQ,
            wpWechat,wpEmail,tv_follownumber,tv_person,wuping_pel;
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
    private HashMap<String, String> map2 = new HashMap<String, String>();
    private boolean followed;
    private int follow;
    private SharedPreferences sp;
    private String token;
    private SimpleDraweeView iv_head;
    private LinearLayout ll_gz;
    private Button bt_xiajia;
    private Button bt_xiangyao;
    private TextView bar_tv_name;
    private String wecat;
    private String email;
    private String qq;
    private String mobile;
    private String contact;
    private PopupWindow popupWindow;
    private String head;
    private ArrayList<String> alist;


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
        //想要的按钮
        bt_xiangyao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow();

            }
        });

    }



    private void showPopupWindow() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SPXQActivity.this);
        // 一个自定义的布局，作为显示的内容
        View v = LayoutInflater.from(SPXQActivity.this).inflate(
                R.layout.pop, null);

        SimpleDraweeView  sdv = (SimpleDraweeView) v.findViewById(R.id.sdv);
        wuping_pel = (TextView) v.findViewById(R.id.wuping_pel);
        wpMobile = (TextView) v.findViewById(R.id.wuping_phone);
        wpQQ = (TextView) v.findViewById(R.id.wuping_qq);
        TextView wpQQ1 = (TextView) v.findViewById(R.id.wuping_qq1);
        wpWechat = (TextView) v.findViewById(R.id.wuping_weixin);
        TextView wpWechat1 = (TextView) v.findViewById(R.id.wuping_weixin1);
        wpEmail = (TextView) v.findViewById(R.id.wuping_email);
        TextView wpEmail1 = (TextView) v.findViewById(R.id.wuping_email1);


        Uri uri = Uri.parse("http://192.168.4.188/Goods/uploads/"+ head);
        ImageTools.load(uri,sdv,30,30);

        wuping_pel.setText(contact);
        if (TextUtils.isEmpty(mobile)) {
            wpMobile.setText("");
            wpMobile.setVisibility(View.GONE);
        } else {
            wpMobile.setVisibility(View.VISIBLE);
            wpMobile.setText(mobile);
        }
        wpMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+ mobile));
                startActivity(intent);


            }
        });

        if (TextUtils.isEmpty(qq)) {
            wpQQ.setText("");
            wpQQ.setVisibility(View.GONE);
            wpQQ1.setVisibility(View.GONE);
        } else {
            wpQQ.setVisibility(View.VISIBLE);
            wpQQ1.setVisibility(View.VISIBLE);
            wpQQ.setText(qq);
        }
        if (TextUtils.isEmpty(email)) {
            wpEmail.setText("");
            wpEmail1.setVisibility(View.GONE);
            wpEmail.setVisibility(View.GONE);
            }
        else {
            wpEmail.setVisibility(View.VISIBLE);
            wpEmail1.setVisibility(View.VISIBLE);
            wpEmail.setText(email);
        }
        if (TextUtils.isEmpty(wecat)) {
            wpWechat.setText("");
            wpWechat.setVisibility(View.GONE);
            wpWechat1.setVisibility(View.GONE);
        } else {
            wpWechat.setVisibility(View.VISIBLE);
            wpWechat1.setVisibility(View.VISIBLE);
            wpWechat.setText(wecat);
        }



        builder.setView(v);//添加自定义View
        builder.create();
        builder.show();

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
        bar_tv_name = (TextView) findViewById(R.id.bar_tv_name);

        tv_follownumber = (TextView) findViewById(R.id.tv_guanzhushuliang);
        wpgzFollow = (ImageView) findViewById(R.
                id.wuping_shoucang);
        wpDescription = (TextView) findViewById(R.id.wuping_xiangqing);
        tv_person = (TextView) findViewById(R.id.tv_person);
        iv_head = (SimpleDraweeView) findViewById(R.id.my_image_view);
        ll_gz = (LinearLayout) findViewById(R.id.ll_gz);
        dgsptp_rv = (RecyclerView) findViewById(R.id.dgsptp_rv);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(SPXQActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        dgsptp_rv.setLayoutManager(manager);
        bt_xiajia = (Button) findViewById(R.id.bt_xiajia);
        bt_xiangyao = (Button) findViewById(R.id.bt_xiangyao);



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
        mobile = sp.getData().getMobile();
        qq = sp.getData().getQq();
        email = sp.getData().getEmail();
        wecat = sp.getData().getWechat();
        head = sp.getData().getHead();
        final int state=sp.getData().getState();
        final boolean owned=sp.getData().isOwned();
        follow = sp.getData().getFollow();
        followed = sp.getData().isFollowed();
        contact = sp.getData().getContact();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                wpfbTime.setText("" + time);
                bar_tv_name.setText(title);
                wpDescription.setText(description);
                wpPrice.setText("¥"+price);
                tv_follownumber.setText("关注人数:"+follow );
                tv_person.setText(contact);

                Uri uri = Uri.parse("http://192.168.4.188/Goods/uploads/"+ head);
                ImageTools.load(uri,iv_head,50,50);

                if (followed) {
                   wpgzFollow.setBackgroundResource(R.mipmap.scd);
                } else {
                    wpgzFollow.setBackgroundResource(R.mipmap.scm);
                }
                if(owned){
                    if(state==1){
                        bt_xiajia.setVisibility(View.VISIBLE);
                        bt_xiajia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                xiajia();
                            }
                        });


                    }else if(state==9||state==0||state==3){
                        bt_xiajia.setVisibility(View.GONE);
                    }

                }else {
                    bt_xiajia.setVisibility(View.GONE);
                }



                tianjiatupian(sp);

            }
        });


    }

    private void xiajia() {

        map2.put("id", id);
        map2.put("token", token);
        map2.put("state",""+9);

        Type type = new TypeToken<XiaJia>() {
        }.getType();

        myOkhttp.doRequest(Contant.BIANGENG,
                MyOkhttp.RequestType.POST,
                map2,
                new MyCallBack() {
                    @Override
                    public void loading() {

                    }

                    @Override
                    public void onFailure() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SPXQActivity.this, "联网失败", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public void onSuccess(Object o) {
                        final XiaJia xj= (XiaJia) o;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(xj.getStatus().equals("200")){
                                    Toast.makeText(SPXQActivity.this, "下架成功", Toast.LENGTH_SHORT).show();
                                    //发送广播
                                    Intent intent = new Intent();
                                    intent.setAction("cn.bgs.refash");
                                    SPXQActivity.this.sendBroadcast(intent);
//                                    bt_xiajia.setText("我要发布");
//                                    Intent intent1=new Intent();
//                                    Bundle mBundle = new Bundle();
//                                    mBundle.putStringArrayList("list",alist);


                                }
                                else {

                                    Toast.makeText(SPXQActivity.this, "下架失败", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                    }

                    @Override
                    public void onError() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SPXQActivity.this, "接口异常", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                },type);







    }

    private void tianjiatupian(SPXQ sp) {

        alist = (ArrayList<String>) sp.getData().getPhotos();

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

