package com.wxb.jianbao11.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ti on 2016/12/1.
 */

public class MyOkhttp {

    private volatile static MyOkhttp myOkhttp;
    private static MultipartBody.Builder requestBody;
    private Gson gson;
    private OkHttpClient okHttpClient;

    private MyOkhttp() {
        super();
        gson = new Gson();
    }

    public static MyOkhttp getInstance() {
        if (myOkhttp == null) {
            synchronized (MyOkhttp.class) {
                if (myOkhttp == null) {
                    myOkhttp = new MyOkhttp();
                }
            }
        }
        return myOkhttp;
    }


    public void doRequest(Context context,String path, RequestType requestType, Map<String, String> map,
                          final MyCallBack mycallback, Type type) {

        Request.Builder builder = new Request.Builder();
        builder.url(path);
        if (requestType == RequestType.GET) {
            builder.get();
        } else if (requestType == RequestType.POST) {
            FormBody.Builder fBuilder = new FormBody.Builder();
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> e : entries) {
                fBuilder.add(e.getKey(), e.getValue());
            }
            FormBody fb = fBuilder.build();
            builder.post(fb);
        }
        Request request = builder.build();

        executeCall(context,request, mycallback, type);


    }


    private void executeCall(final Context context, Request request, final MyCallBack mycallback, final Type type) {
        okHttpClient = new OkHttpClient();
        Call call =  okHttpClient.newCall(request);
        mycallback.loading();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mycallback.onFailure();
            }

            //200-300
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    if (type == null) {
                        mycallback.checkIsLogin(context,string);
                        mycallback.onSuccess(string);
                    } else {
                        mycallback.checkIsLogin(context,string);
                        Object o = gson.fromJson(string, type);
                        mycallback.onSuccess(o);
                    }
                    //success
                } else {
                    //error
                    mycallback.onError();
                    Log.e("错误的code", "" + response.code());

                }

            }
        });

    }

    // 接口回掉
    private static final String TAG = "OkhttpUtils";
    private static Gson gson2 = new Gson();//创建gson对象
    private static String string;
    private static OkHttpClient client = new OkHttpClient();//okhttp对象

    public interface EntiyData {//创建接口

        void getEntiy(Object o);
    }
    private static EntiyData data;
    public static void setGetEntiydata(EntiyData data1) {
        data = data1;
    }

    public static void postMuti(Map<String, String> map, String path, final Context context, final Type cla, ArrayList<String> imgs) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        Set<Map.Entry<String, String>> entries = map.entrySet();//用entryset的方法遍历map集合
        for (Map.Entry<String, String> entry : entries) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        if (imgs!=null&&!imgs.isEmpty()){
            for (String str:imgs) {
                String newStr = str.replace("file://", "");
                File file = new File(newStr);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"),file);
                builder.addFormDataPart("photo",file.getName(), fileBody);
            }
        }
        MultipartBody requestBody = builder.setType(MultipartBody.FORM).build();
        Request request = new Request.Builder()
                .url(path)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        //用这种方法不用创建子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("联网失败，检查网络");
                //Toast.makeText(context, "下载数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    string = response.body().string();
                    Log.i(TAG, "获取的数据：" + string);
                    Object o = gson2.fromJson(string, cla);
                    //回调结果
                    data.getEntiy(o);
                } else {
                    Log.e(TAG, "失败");
                }
            }

        });
    }





    public enum RequestType {
        GET,
        POST,
        UPLOADFILE
    }

}
