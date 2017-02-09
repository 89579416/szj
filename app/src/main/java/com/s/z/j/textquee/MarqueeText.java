package com.s.z.j.textquee;

/**
 * 实现了跑马灯效果
 * Created by Administrator on 2017-02-09.
 */

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class MarqueeText extends TextView implements Runnable {
    private int contentWidth;
    private int currentscrollToX;
    private boolean isStop = false;
    private List<String> mList;
    private int currentNews = 0;

    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setClickable(true);
        setSingleLine(true);
        setEllipsize(TruncateAt.MARQUEE);
        setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
    }

    public void setData(List<String> mList) {
        if (mList == null || mList.size() == 0) {
            return;
        }
        this.mList = mList;
        nextNews();

    }
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        MeasureTextWidth();
    }
    private void nextNews() {

        currentNews = currentNews % mList.size();
        String s = mList.get(currentNews);
        setText(s);
        setTag(s);
        currentNews++;
        startScroll();

    }


    private void MeasureTextWidth() {
        // 获取文字长度
        Paint paint = this.getPaint();
        String content = this.getText().toString();
        contentWidth = (int) paint.measureText(content);

    }

    public void run() {

        if (currentscrollToX >= contentWidth) {
            // 重新开始
            currentscrollToX = -getWidth();
            nextNews();
        }

        currentscrollToX = currentscrollToX + 5;
        scrollTo(currentscrollToX, 0);
        postDelayed(this, 150);
    }

    // 点击开始,执行线程
    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }

    public void stopScoll() {
        isStop = true;
    }

}
