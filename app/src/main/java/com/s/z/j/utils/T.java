package com.s.z.j.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐丝显示
 * Created by szj on 2015/7/28.
 */
public class T {
    public static void s (Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
