package com.s.z.j.widget.mediaplayer;

import android.widget.FrameLayout;

/**
 * 日期： 2015/10/19
 * 版本： V1.0
 * 说明：播放控件的接口
 */
public interface Player {
    /**
     * 初始化
     */
    public void init();

    /**
     * 释放资源
     */
    public void release();

    /**
     * 开启播放
     *
     * @return 结果码
     */
    public int startPlay();

    /**
     * 暂停播放
     *
     * @return 结果码
     */
    public int pausePlay();

    /**
     * 暂停恢复
     *
     * @return 结果码
     */
    public int resumePlay();

    /**
     * 停止播放
     *
     * @return 结果码
     */
    public int stopPlay();

    /**
     * 设置布局
     *
     * @param tparams
     */
    public void setViewLayout(FrameLayout.LayoutParams tparams);

    /**
     * 获得布局
     *
     * @return
     */
    public FrameLayout.LayoutParams getViewLayout();
}
