package com.szj.library.widget.probutton;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class MTFProcessButton extends ProcessButton {

    public MTFProcessButton(Context context) {
        super(context);
    }

    public MTFProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MTFProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void drawProgress(Canvas canvas) {
        float scale = (float) getProgress() / (float) getMaxProgress();
        float indicatorWidth = (float) getMeasuredWidth() * scale;

        getProgressDrawable().setBounds(0, 0, (int) indicatorWidth, getMeasuredHeight());
        getProgressDrawable().draw(canvas);
    }

}
