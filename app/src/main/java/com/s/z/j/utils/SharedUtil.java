package com.s.z.j.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 轻量级保存用户信息
 * Created by Administrator on 2016/8/1 0001.
 */
public class SharedUtil {

    public static SharedPreferences sharedPre;
    Context context;

    public SharedUtil(Context context) {
        this.context = context;
    }
    /**
     * 使用SharedPreferences保存用户登录信息
     */
    public  void save( String key, String value){
        //获取SharedPreferences对象
        //获取Editor对象
        sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);//保存的文件名，模式
        SharedPreferences.Editor editor = sharedPre.edit();
        //设置参数
        editor.putString(key, value);
        //提交
        editor.commit();

    }
    /**
     * 从SharedPreferences中获取保存的用户信息
     * @return
     */
    public String get(String key){
        sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);//保存的文件名，模式
        return sharedPre.getString(key, "");
    }
}
