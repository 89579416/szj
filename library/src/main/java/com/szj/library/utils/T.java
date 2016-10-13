package com.szj.library.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 13510 on 2016/1/15.
 */
public class T {

    public static void s(Context context, String msg){//short
        Toast.makeText(context  , msg, Toast.LENGTH_SHORT).show();
    }

    public static void l(Context context, String msg){//long
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }


}
