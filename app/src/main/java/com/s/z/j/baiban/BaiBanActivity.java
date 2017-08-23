package com.s.z.j.baiban;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        this.addContentView(drawBoardView, layoutParams);
        Button btn1 = new Button(this);
        Button btn2 = new Button(this);
        btn1.setText("改变颜色 ");
        btn2.setText("擦除");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBoardView.setPaintColor(Color.BLUE);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBoardView.setPenMode(0);

            }
        });
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(300, 300);
        layoutParams2.setMargins(0,100,0,0);
        this.addContentView(btn1, layoutParams1);
        this.addContentView(btn2,layoutParams2);
    }
}
