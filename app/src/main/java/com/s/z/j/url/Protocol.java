package com.s.z.j.url;

import android.os.Environment;

/**
 * Created by szj on 2016/1/27.
 */
public class Protocol {
    public static String updateUrl = "http://192.168.1.118:8080/itel/updates.xml";//测试服务器
    private static String defalutUrl;//获取默认内存地址

    /**
     * 获取默认内存地址
     * @return
     */
    public static String getDefaultUrl(){
        try {
            if(defalutUrl == null || "".equals(defalutUrl)) {
                defalutUrl = Environment.getExternalStorageDirectory() + "";
            }
            return defalutUrl;
        }catch (Exception e){
            return Environment.getExternalStorageDirectory() + "";
        }
    }
}
