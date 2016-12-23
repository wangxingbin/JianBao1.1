package com.wxb.jianbao11.utils;

import android.view.View;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ViewUtils {

   public static  int  getViewWidth(View v){
       int w = View.MeasureSpec.makeMeasureSpec(0,
               View.MeasureSpec.UNSPECIFIED);
       int h = View.MeasureSpec.makeMeasureSpec(0,
               View.MeasureSpec.UNSPECIFIED);
       v.measure(w, h);
       int width = v.getMeasuredWidth();
       return width;
   }
    public static  int  getViewHeight(View v){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        int height = v.getMeasuredHeight();
        return height;
    }
}
