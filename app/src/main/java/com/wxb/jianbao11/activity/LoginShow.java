package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button button;
    private TextView tv_timer;
    private int num = 4;
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginShow.this, MainActivity.class);
                startActivity(intent);
                intent.clone();
            }
        });
    }
    private void initView() {
        button = (Button) findViewById(R.id.button);
        tv_timer = (TextView) findViewById(R.id.tv_timer);
    }

    private void initIntent() {
        String tag = (String) SharedPreferencesUtil.get(LoginShow.this, "tag", "");
        startActivity(new Intent(LoginShow.this, MainActivity.class));
    }

    public void onClilk() {
        flag = false;
        initIntent();
    }
}
