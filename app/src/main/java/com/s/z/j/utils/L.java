package com.s.z.j.utils;

import android.util.Log;

/**
 * 日志输出和日志写入
 * Created by Administrator on 2016/7/7 0007.
 */
public class L {
    public static String TAG = "SZJ";

    public static void i(String str) {
        Log.i(TAG, str);
    }
    public static void e(String str) {
        Log.e(TAG, str);
    }
}
