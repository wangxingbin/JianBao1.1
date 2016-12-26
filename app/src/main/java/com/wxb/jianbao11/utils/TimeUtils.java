package com.wxb.jianbao11.utils;

public class TimeUtils {
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 300) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}  
