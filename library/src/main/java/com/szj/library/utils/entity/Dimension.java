package com.szj.library.utils.entity;

/**
 * Created by master on 15-8-20.
 */
public class Dimension {

    private int height;
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Dimension(int width, int height) {

        this.height = height;
        this.width = width;
    }
}
