package com.s.z.j.smil;

/**
 * 一个Smil文件
 * 包含一个layout布局文件
 * 包含一个seq资源文件
 * Created by Administrator on 2017-08-21.
 */
public class Smil {
    private Seq seq;
    private Layout layout;

    public Seq getSeq() {
        return seq;
    }

    public void setSeq(Seq seq) {
        this.seq = seq;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "Smil{" +
                "layout=" + layout.toString() +
                ", seq=" + seq.toString() +
                '}';
    }
}
