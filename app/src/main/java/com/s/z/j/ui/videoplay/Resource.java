package com.s.z.j.ui.videoplay;

import java.io.Serializable;

/**
 * 播放资源
 * Created by Administrator on 2016-12-21.
 */
public class Resource implements Serializable {
    private String path;//路径
    private String type;//类型
    private int time;//播放时间/秒

    public Resource() {
    }

    public Resource(String path, String type, int time) {
        this.path = path;
        this.type = type;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                '}';
    }
}
