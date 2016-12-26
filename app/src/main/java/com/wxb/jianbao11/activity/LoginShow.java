package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.wxb.jianbao11.MainActivity;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.utils.SharedPreferencesUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/11/28.
 */

public class LoginShow extends Activity {
    private TextView tv_timer;
    private int num = 3;
    private Timer timer = new Timer();
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_show);
        initView();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (flag) {
                    //更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            num--;
                            tv_timer.setText("跳转" + num + "秒");
                            if (num <= 0) {
                                timer.cancel();
                                initIntent();
                            }
                        }
                    });
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    private void initView() {
        tv_timer = (TextView) findViewById(R.id.tv_timer);
    }

    private void initIntent() {

        String tag = (String) SharedPreferencesUtil.get(LoginShow.this, "tag", "");

        //定义一个setting记录APP是几次启动！！！
        SharedPreferences setting = getSharedPreferences("count", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {// 第一次则跳转到欢迎页面
            setting.edit().putBoolean("FIRST", false).commit();
            startActivity(new Intent(LoginShow.this, GuideActivity.class));
            finish();
        } else {//如果是第二次启动则直接跳转到主页面
            startActivity(new Intent(LoginShow.this, MainActivity.class));
            LoginShow.this.finish();
        }
    }


    public void onClilk() {
        flag = false;
        initIntent();
    }

}
