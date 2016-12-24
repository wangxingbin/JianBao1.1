package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxb.jianbao11.R;
import com.wxb.jianbao11.bean.GeRenXinxi;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.MyCallBack;
import com.wxb.jianbao11.utils.MyOkhttp;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/20.
 */

public class MessageActivity extends Activity {
    @InjectView(R.id.bar_iv_back)
    ImageView barIvBack;
    @InjectView(R.id.bar_tv_name)
    TextView barTvName;
    @InjectView(R.id.message_ll_name)
    TextView messageLlName;
    @InjectView(R.id.message_tv_sex)
    TextView messageTvSex;
    @InjectView(R.id.message_tv_phoneNum)
    TextView messageTvPhoneNum;
    @InjectView(R.id.message_tv_qq)
    TextView messageTvQq;
    @InjectView(R.id.message_tv_weixin)
    TextView messageTvWeixin;
    @InjectView(R.id.message_tv_email)
    TextView messageTvEmail;
    @InjectView(R.id.message_tv_time)
    TextView messageTvTime;
    private Map map;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.inject(this);
        barTvName.setText("个人信息");
        SharedPreferences sp = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        map = new HashMap<>();
        map.put("token", token);
        initData();
    }

    private void initData() {
        final String path = Contant.GeRenXinXi;
        MyOkhttp.getInstance().doRequest(MessageActivity.this,path, MyOkhttp.RequestType.POST, map, new MyCallBack() {

            private String email;
            private String wechat;
            private String qq;
            private GeRenXinxi geRenXinxi;

            @Override
            public void loading() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(Object o) {
                if (o == null || !(o instanceof GeRenXinxi)) {
                    return;
                }
                geRenXinxi = (GeRenXinxi) o;
                qq = geRenXinxi.getData().getQq();
                wechat = geRenXinxi.getData().getWechat();
                email = geRenXinxi.getData().getEmail();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageLlName.setText(geRenXinxi.getData().getName());
                        messageTvSex.setText(geRenXinxi.getData().getGender());
                        messageTvPhoneNum.setText(geRenXinxi.getData().getMobile());
                        messageTvTime.setText(geRenXinxi.getData().getLast_time());
                        setMessage(messageTvQq, qq);
                        setMessage(messageTvWeixin, wechat);
                        setMessage(messageTvEmail, email);
                    }
                });
            }

            @Override
            public void onError() {

            }
        }, GeRenXinxi.class);

    }

    public void setMessage(TextView tv, String str) {
        if (str!=null &&!("".equals(str))) {
            tv.setText(str);
        }
        else
        {
            tv.setText("暂无信息");
        }
    }

    @OnClick(R.id.bar_iv_back)
    public void onClick() {
        finish();
    }
}
