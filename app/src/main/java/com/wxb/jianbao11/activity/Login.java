package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wxb.jianbao11.MainActivity;
import com.wxb.jianbao11.R;
import com.wxb.jianbao11.bean.LandBeen;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.CustomProgress;
import com.wxb.jianbao11.utils.MyCallBack;
import com.wxb.jianbao11.utils.MyOkhttp;
import com.wxb.jianbao11.utils.ShowToastUtils;

import java.util.HashMap;


/**
 * Created by 孙贝贝 on 2016/12/2.
 */

public class Login extends Activity implements View.OnClickListener{



    private TextView register;
    private Button land;
    private EditText yonhuming;
    private EditText mima;
    private String status;
    private CheckBox cb1;
    private String password1;
    private String name;
    private String username;
    private String password;
    private String token;
    private TextView toolname;
    private ImageView back;
    private TextView suiyi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initview();
        remberpsna();
        checklister();
    }

    private void checklister() {
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                SharedPreferences settings = getSharedPreferences("SETTING_Infos", 0);
                if (arg1 == true) {//勾选时，存入EditText中的用户名密码
                    settings.edit().putString("judgeText", "yes")
                            .putString("userNameText", yonhuming.getText().toString())
                            .putString("passwordText", mima.getText().toString())
                            .commit();
                    Toast.makeText(Login.this, "记住用户名和密码", Toast.LENGTH_SHORT)
                            .show();

                } else {//不勾选，存入空String对象
                    settings.edit().putString("judgeText", "no")
                            .putString("UserNameText", "")
                            .putString("passwordText", "")
                            .commit();
                    Toast.makeText(Login.this, "不记住用户名和密码", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private void remberpsna() {
        //从配置文件中取用户名密码的键值对
        //若第一运行，则取出的键值对为所设置的默认值
        SharedPreferences settings = getSharedPreferences("SETTING_Infos", 0);
        String strJudge = settings.getString("judgeText", "no");// 选中状态
        String strUserName = settings.getString("userNameText", "");// 用户名
        String strPassword = settings.getString("passwordText", "");// 密码
        if (strJudge.equals("yes")) {
            cb1.setChecked(true);
            yonhuming.setText(strUserName);
            mima.setText(strPassword);
        } else {
            cb1.setChecked(false);
            yonhuming.setText("");
            mima.setText("");
        }

    }

    private void initdata() {

        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        String path = Contant.LAND;
        MyOkhttp.getInstance().doRequest(path, MyOkhttp.RequestType.POST, map, new MyCallBack() {
            @Override
            public void loading() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(Object o) {
                LandBeen landBeen = (LandBeen) o;
                if (landBeen == null) {
                    return;
                }
                status = landBeen.getStatus();
                if (status.equals("200")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomProgress.dissPrgress();
                            ShowToastUtils.showToast(Login.this,"登陆成功");
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        }
                    });
                }else if (status.equals("302")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomProgress.dissPrgress();
                            ShowToastUtils.showToast(Login.this,"异地登录");
                        }
                    });
                    return;
                }else if(status.equals("303")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomProgress.dissPrgress();
                            ShowToastUtils.showToast(Login.this,"用户不存在");
                        }
                    });
                    return;
                }else if(status.equals("304")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomProgress.dissPrgress();
                            ShowToastUtils.showToast(Login.this,"用户名或者密码不正确");
                        }
                    });
                    return;
                }
                LandBeen.DataBean data = landBeen.getData();

                int state = data.getState();
                token = landBeen.getToken();
                SharedPreferences share= getSharedPreferences("TOKEN",MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit();
                edit.putString("token", token);
                edit.commit();

            }

            @Override
            public void onError() {

            }
        },LandBeen.class);


    }
    private void initview() {
        suiyi = (TextView) findViewById(R.id.suiyi);
        suiyi.setOnClickListener(this);
        toolname = (TextView) findViewById(R.id.bar_tv_name);
        toolname.setText("登陆");
        back = (ImageView) findViewById(R.id.bar_iv_back);
        back.setOnClickListener(this);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
        land = (Button) findViewById(R.id.signin_button);
        land.setOnClickListener(this);
        yonhuming = (EditText) findViewById(R.id.username_edit);
        mima = (EditText) findViewById(R.id.password_edit);
        cb1 = (CheckBox) findViewById(R.id.check);
    }


    private void jumpregister() {
        startActivity(new Intent(this, Register.class));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_button:
                username = yonhuming.getText().toString().trim();
                password = mima.getText().toString().trim();
                if (username.equals("") && password.equals("")) {
                    ShowToastUtils.showToast(this,"请输入手机号跟密码");
                    return;
                } else if (username.length()>11||username.length()<11) {
                    ShowToastUtils.showToast(this,"请输入正确手机号");
                    return;
                } else {
                    CustomProgress.getPrgressDolilog(Login.this,"正在登陆","请稍等....");
                    initdata();
                }
                break;
            case R.id.register:
                jumpregister();
                break;
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.suiyi:
               startActivity(new Intent(Login.this, MainActivity.class));
                break;
        }
    }
}


