package com.s.z.j.baiban;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.s.z.j.R;

/**
 * Created by Administrator on 2017-08-23.
 */
public class BaiBanActivity extends Activity {
    DrawBoardView drawBoardView;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baiban);
        drawBoardView = new DrawBoardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        drawBoardView.setLayoutParams(layoutParams);
        drawBoardView.setPaintColor(Color.RED);
        drawBoardView.setPaintStrokeWidth(20);
        drawBoardView.setPenMode(1);

        this.addContentView(drawBoardView,layoutParams);
    }
}
