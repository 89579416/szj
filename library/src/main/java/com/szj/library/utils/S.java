package com.szj.library.utils;

import com.szj.library.config.Conf;

/**
 * Created by 13510 on 2016/1/15.
 */
public class S {
    public static void o(String msg){
        if(Conf.DEBUG) System.out.println("::inf:>" + msg);
    }

    public static void e(String msg){
        if(Conf.DEBUG) System.out.println("=err=>" + msg);
    }
}
