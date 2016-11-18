package com.s.z.j.model;


import com.s.z.j.entity.Info;

/**
 * 获取一个Info
 * Created by szj on 2015/7/21.
 */
public class InfoModel {
    public static Info info;
    public static Info getInfo(){
        if(info == null){
            synchronized(InfoModel.class) {
                info = new Info();
            }
        }
        return info;
    }
}
