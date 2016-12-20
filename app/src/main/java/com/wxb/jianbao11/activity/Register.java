package com.wxb.jianbao11.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wxb.jianbao11.R;
import com.wxb.jianbao11.bean.RegisterBeen;
import com.wxb.jianbao11.contants.Contant;
import com.wxb.jianbao11.utils.PhotoPostUtils;
import com.wxb.jianbao11.utils.ShowToastUtils;
import com.wxb.jianbao11.utils.TakePhotoPopWin;

import java.io.File;
import java.util.HashMap;


/**
 * Created by 孙贝贝 on 2016/11/28.
 */
public class Register extends Activity implements View.OnClickListener {
    private static final String TAG = "Register";
    protected Button upload;
    private EditText username;
    private EditText password;
    private EditText name;
    private EditText code;
    private RadioGroup group;
    private String yonghu;
    private String mima;
    private String xingming;
    private String inivite_code;
    private RadioButton rb;
    private String gender;
    private Button register_btn;
    private String path;
    private File file;
    private HashMap<String, String> map;
    private String status;
    private TextView toolname;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);
        initview();
        getGenderData();


    }

    private void getGenderData() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rb = (RadioButton) findViewById(i);
                //gender 性别
                gender = rb.getText().toString().trim();

                ShowToastUtils.showToast(Register.this, gender);

            }
        });
    }

    private void initData() {
        yonghu = username.getText().toString().trim();
        mima = password.getText().toString().trim();
        xingming = name.getText().toString().trim();
        inivite_code = code.getText().toString().trim();
        SharedPreferences sharepath = getSharedPreferences("PATH", MODE_PRIVATE);
        path = sharepath.getString("path", "");
        Log.i(TAG,path+"_______________________________+++++++++++++++++++++++++");
        file = new File(path);

        if (file == null) {
          ShowToastUtils.showToast(Register.this,"请另选一张图片");
        }
        map = new HashMap<>();
        map.put("code", inivite_code);
        map.put("mobile", yonghu);
        map.put("name", xingming);
        map.put("password", mima);
        map.put("gender", gender);
        Log.i(TAG, path + "___________________________________________________________");
    }

    private void initview() {
        toolname = (TextView) findViewById(R.id.bar_tv_name);
        toolname.setText("注册");
        back = (ImageView) findViewById(R.id.bar_iv_back);
        back.setOnClickListener(this);
        username = (EditText) findViewById(R.id.username_edit);
        password = (EditText) findViewById(R.id.password_edit);
        name = (EditText) findViewById(R.id.name);
        code = (EditText) findViewById(R.id.invite_code_edit);
        group = (RadioGroup) findViewById(R.id.grop);
        register_btn = (Button) findViewById(R.id.register_button);
        register_btn.setOnClickListener(this);
        upload = (Button) findViewById(R.id.upload_photo);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_photo:
                startActivity(new Intent(Register.this, Camera.class));
                break;
            case R.id.register_button:

                initData();
                if (yonghu.equals("")||password.equals("")||name.equals("")||code.equals("")||gender.equals("")){
                    ShowToastUtils.showToast(Register.this,"请完善你的信息");
                   return;
                }else if(yonghu.equals("")&&password.equals("")&&name.equals("")&&code.equals("")&&gender.equals("")){
                    ShowToastUtils.showToast(Register.this,"请填写你的用户信息");
                    return;
                }
                upLoadPhoto();
                break;
            case R.id.bar_iv_back:
                finish();
                break;

        }

    }

    private void upLoadPhoto() {
        String path = Contant.RESGISTER;
        PhotoPostUtils.upLoad(file, this, path,"card", map, RegisterBeen.class);
        PhotoPostUtils.getData(new PhotoPostUtils.GetRegisterData() {
            @Override
            public void setRegisterData(Object o) {
                RegisterBeen data = (RegisterBeen) o;
                status = data.getStatus();

              if (status.equals("200")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowToastUtils.showToast(Register.this, "注册成功");
                            finish();

                        }
                    });
                }else if(status.equals("201")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowToastUtils.showToast(Register.this,"此号码已注册请换号");
                        }
                    });
                    return;
                }else if(status.equals("206")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowToastUtils.showToast(Register.this,"邀请码已使用");
                        }
                    });
                    return;
                }


            }
        });
    }


}
