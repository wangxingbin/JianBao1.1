package com.wxb.jianbao11.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.wxb.jianbao11.activity.Login;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ti on 2016/12/1.
 */

public abstract class MyCallBack {
    private static final String TAG = "MyCallBack";
    private Activity act;

    public abstract void loading();
    public abstract void onFailure();
    public abstract void onSuccess(Object o);
    public abstract void onError();
    public void  checkIsLogin(final Context context, String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String status = (String) jsonObject.get("status");
            if ("301".equals(status)){
                Log.e(TAG, "checkIsLogin: 未登录");
                act = (Activity) context;
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sp = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("isLogin",false);
                        edit.commit();
                        Toast.makeText(act, "请登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(act, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        act.startActivity(intent);
                    }
                });

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
