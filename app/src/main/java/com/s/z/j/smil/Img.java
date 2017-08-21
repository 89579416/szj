package com.s.z.j.smil;

/**
 * 显示图片
 * Created by Administrator on 2017-08-21.
 */
public class Img {
    /** 图片路径 */
    private String src;
    /** 为该标签指定显示到head中创建的region中 */
    private String region;
    /** 表示播放时长 */
    private String dur;

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

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur = dur;
    }

    @Override
    public String toString() {
        return "Img{" +
                "src='" + src + '\'' +
                ", region='" + region + '\'' +
                ", dur='" + dur + '\'' +
                '}';
    }
}
