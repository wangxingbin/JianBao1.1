package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.adapter.GuideAdapter;
import com.wxb.jianbao11.app.MInterface;
import com.wxb.jianbao11.bean.Navi;
import com.wxb.jianbao11.utils.MyOkhttp;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GuideActivity extends Activity {
    private static final String TAG = "GuideActivity";
    private ArrayList<String> imageList = new ArrayList<>();
    private ArrayList<View> mGuideViewList = new ArrayList<View>();
    private ImageView dots[] = new ImageView[imageList.size()];
    private Button btn;
    boolean isFirstIn = false;
    @InjectView(R.id.vp)
    ViewPager vp;
    @InjectView(R.id.layout)
    LinearLayout layout;
    private String url;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                initView();
                initViewPager();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
        initRequest();

    }

    private void initRequest() {
        MyOkhttp.setGetEntityCallBack(new MyOkhttp.GetEntityCallBack(){
            @Override
            public void getEntity(Object obj) {
                Navi navi = (Navi)obj;
                List<Navi.DataBean.ListBean> listBeen = navi.getData().getList();
                Log.e(TAG, "getEntity: " + listBeen.size());
                for (int i = 0; i < listBeen.size(); i++) {
                    url = listBeen.get(i).getUrl();
                    Log.e(TAG, "getEntity: " + url);
//                    imageUris[i] = image;
                    imageList.add(url);
                }
                handler.sendEmptyMessage(0);
            }
        });
      MyOkhttp.getData(MInterface.zhuji + MInterface.NAVI, Navi.class);
    }

    private void initView() {
        for (int i = 0; i < imageList.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.guide_item, null);
            SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.iv);
//            ImageView iv = (ImageView) view.findViewById(iv);
            if (i == imageList.size() - 1) {
                btn = (Button) view.findViewById(R.id.bt_tiaozhuan);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(listener);
            }
            String s = "http://192.168.4.188/Goods/uploads/" + imageList.get(i);
            draweeView.setImageURI(Uri.parse(s));
            mGuideViewList.add(view);
            Log.e(TAG, "initView: " + mGuideViewList.size());
        }
    }

    private void initViewPager() {
        GuideAdapter guideAdapter = new GuideAdapter(GuideActivity.this, mGuideViewList);
        vp.setAdapter(guideAdapter);
        vp.setCurrentItem(0);//展示第一张
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(GuideActivity.this,LoginShow.class));
        }
    };
}
