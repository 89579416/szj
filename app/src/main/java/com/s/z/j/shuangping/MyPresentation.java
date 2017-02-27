package com.s.z.j.shuangping;

import android.annotation.TargetApi;
import android.app.Presentation;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;

import com.s.z.j.R;

/**
 * 对于双屏异显(lcd 和 hdmi 的双屏异显)，android框架已经支持，但是底层接口功能还是要自己去实现，且需要底层驱动支持。
 * 使用presentation 去画第二个display就好了。
 * Created by RYX on 2016/6/23.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class MyPresentation extends Presentation {
    private SurfaceView presentSurface;

    public MyPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_layout);
        presentSurface = (SurfaceView) findViewById(R.id.present_surface);
    }

    public SurfaceView getSurface() {
        return presentSurface;
    }
}
