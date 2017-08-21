package com.s.z.j.smil;

/**
 * 文字 标签
 * 显示文字
 * Created by Administrator on 2017-08-21.
 */
public class SmilText {
    /** 未知 */
    private Tev tev;
    /** id */
    private String id;
    /** 为该标签指定显示到head中创建的region中 */
    private String region;
    /** 文字对齐方式，(left,right等) */
    private String textAlign;
    /** 表示重复播放该节目次数，也可以设置一直播放(repeatCount=” indefinite”) */
    private String repeatCount;
    /** 样式 */
    private String style;
    /** 显示的文字内容 */
    private String text;

    public Tev getTev() {
        return tev;
    }

    public void setTev(Tev tev) {
        this.tev = tev;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(String repeatCount) {
        this.repeatCount = repeatCount;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SmilText{" +
                "tev=" + tev +
                ", id='" + id + '\'' +
                ", region='" + region + '\'' +
                ", textAlign='" + textAlign + '\'' +
                ", repeatCount='" + repeatCount + '\'' +
                ", style='" + style + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
