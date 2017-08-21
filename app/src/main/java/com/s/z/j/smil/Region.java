package com.s.z.j.smil;

/**
 * 定义一个显示区域 如一个View
 * 有宽高，距离上，左多少
 * Created by Administrator on 2017-08-21.
 */
public class Region {
    /** id */
    private String  id;
    /** 指定区域大小 */
    private String top;
    /** 指定区域大小 */
    private String left;
    /** 指定区域大小 */
    private String width;
    /** 指定区域大小 */
    private String height;
    /** 填充属性 */
    private String fit;
    /** 显示层级，数值越小显示得越下面 */
    private int z_index;
    /** 文字显示模式：1，	crawl，横向滚动模式  2，	scroll，纵向滚动模式      3，	jump， */
    private String textMode;
    /** 文字滚动速率 */
    private String textRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public int getZ_index() {
        return z_index;
    }

    public void setZ_index(int z_index) {
        this.z_index = z_index;
    }

    public String getTextMode() {
        return textMode;
    }

    public void setTextMode(String textMode) {
        this.textMode = textMode;
    }

    public String getTextRate() {
        return textRate;
    }

    public void setTextRate(String textRate) {
        this.textRate = textRate;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", top='" + top + '\'' +
                ", left='" + left + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", fit='" + fit + '\'' +
                ", z_index=" + z_index +
                ", textMode='" + textMode + '\'' +
                ", textRate='" + textRate + '\'' +
                '}';
    }
}
