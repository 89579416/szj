package com.s.z.j.entity;

import java.io.Serializable;

/**
 * 公共方法
 * 修改密码在用
 * Created by Administrator on 2016/6/20 0020.
 */
public class Resp implements Serializable{
    private int ret;
    private boolean is_valid;
    private String message;

    public Resp() {
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public boolean is_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Resp{" +
                "ret=" + ret +
                ", is_valid=" + is_valid +
                ", message='" + message + '\'' +
                '}';
    }
}
