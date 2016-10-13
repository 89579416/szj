package com.szj.library.utils.entity;

/**
 * 网络相关信息
 * Created by Fan on 2014-12-12.
 */
public class NetInf {

    private NetType type;   //网络类型

    /** constructor */
    public NetInf(){}

    /** constructor */
    public NetInf(NetType type){
        this.type   = type;
    }

    public NetType getType() {
        return type;
    }

    public void setType(NetType type) {
        this.type = type;
    }

    /**  */
    public static enum NetType {
        T_2G,       //2G网络
        T_3G,       //3G网络
        T_4G,       //4G网络
        T_WAP,      //Wap网络
        T_WIFI,     //wifi网络
        T_UNKNOWN,  //未知网络
        T_NO_CONN   //无网络连接
    }

}
