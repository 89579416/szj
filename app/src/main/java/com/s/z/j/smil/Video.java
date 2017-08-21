package com.s.z.j.smil;

/**
 * 显示视频
 * Created by Administrator on 2017-08-21.
 */
public class Video {
    /** 文件路径 */
    private String src;
    /** 为该标签指定显示到head中创建的region中 */
    private String region;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Video{" +
                "src='" + src + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
