package com.szj.library.imagetovideo;

import java.io.Serializable;

/**
 * 图片数据类
 * 包括路径和要生成的视频时间
 * Created by Administrator on 2017-04-11.
 */
public class Images implements Serializable {
    private String path;
    private int time;

    public Images(String path, int time) {
        this.path = path;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Images{" +
                "path='" + path + '\'' +
                ", time=" + time +
                '}';
    }
}
