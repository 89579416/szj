package com.s.z.j.smil;

/**
 * 定义该smil文件的显示区域大小
 * Created by Administrator on 2017-08-21.
 */
public class RootLayout {
    /** 宽度 */
    private int width;  //宽度
    /** 高度 */
    private int height;//高度

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "RootLayout{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }
}
