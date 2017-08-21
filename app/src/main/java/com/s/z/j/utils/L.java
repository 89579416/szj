package com.s.z.j.utils;

import android.util.Log;

import com.s.z.j.logs.LogManager;


/**
 * 日志输出和日志写入
 * Created by Administrator on 2016/7/7 0007.
 */
public class L {
    public static String TAG = "AAAA";

    public static void i(String str) {
        Log.i(TAG, str);
        LogManager.writeLog(1,TAG,str);
    }
    public static void e(String str) {
        Log.e(TAG, str);
    }
}
