package com.s.z.j.smil;

import java.util.List;

/**
 * 同时显示其包含的资源
 * Created by Administrator on 2017-08-21.
 */
public class Par {
    /** 播放时长 */
    private int dur;
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
        return "Par{" +
                "smilTextList=" + smilTextList +
                ", videoList=" + videoList +
                ", imgList=" + imgList +
                ", dur=" + dur +
                '}';
    }
}
