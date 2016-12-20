package com.wxb.jianbao11.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by 孙贝贝 on 2016/12/12.
 */

public class CustomProgress {

    private static ProgressDialog progressDialog;

    public static void getPrgressDolilog(Context context, String title, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.show();
    }
    public static  void  dissPrgress(){
        progressDialog.dismiss();

    }


}
