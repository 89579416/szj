package com.s.z.j.smil;

import java.util.List;

/**
 * 顺序显示其内部资源
 * Created by Administrator on 2017-08-21.
 */
public class Seq {
    /**
     * 1，	dur属性
     表示播放时长，如dur=”10s”表示播放10秒，也可以写成dur=”10”
     */
    private int dur;
    /**
     * 表示重复播放该节目次数(repeatCount=”2”)，也可以设置一直播放(repeatCount=” indefinite”)
     */
    private String repeatCount;
    /** 同时显示里面的资源 */
    private Par par;
    /** 需要顺序播放的图片集合 */
    private List<Img> imgList;
    /** 需要顺序播放的视频集合 */
    private List<Video> videoList;
    /** 需要顺序播放的文字集合 */
    private List<SmilText> smilTextList;

    public int getDur() {
        return dur;
    }

    public void setDur(int dur) {
        this.dur = dur;
    }

    public String getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(String repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Par getPar() {
        return par;
    }

    public void setPar(Par par) {
        this.par = par;
    }

    public List<Img> getImgList() {
        return imgList;
    }

    public void setImgList(List<Img> imgList) {
        this.imgList = imgList;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public List<SmilText> getSmilTextList() {
        return smilTextList;
    }

    public void setSmilTextList(List<SmilText> smilTextList) {
        this.smilTextList = smilTextList;
    }

    @Override
    public String toString() {
        return "Seq{" +
                "dur=" + dur +
                ", repeatCount='" + repeatCount + '\'' +
                ", par=" + par +
                ", imgList=" + imgList +
                ", videoList=" + videoList +
                ", smilTextList=" + smilTextList +
                '}';
    }
}
