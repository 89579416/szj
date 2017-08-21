package com.s.z.j.toast;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.s.z.j.R;

/**
 * Created by Administrator on 2017-08-16.
 */
public class MyToastActivity extends Activity{

    Button checkBtn,check1Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytoast);
        checkBtn = (Button) findViewById(R.id.mytoast_btn);
        check1Btn = (Button) findViewById(R.id.mytoast_1_btn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast();
            }
        });
        check1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 弹出自定义布局吐丝
                 */
                View view = LayoutInflater.from(MyToastActivity.this).inflate(R.layout.toast_iamge,null);
                new ToastUtil(MyToastActivity.this,view, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showToast(){
        ToastUtil toastUtil=new ToastUtil();
        toastUtil.Short(MyToastActivity.this,"自定义message字体、背景色").setToastColor(Color.WHITE, getResources().getColor(R.color.colorAccent)).show();
//        toastUtil.Short(MyToastActivity.this,"自定义message字体颜色和背景").setToastBackground(Color.WHITE,R.drawable.bg_toast).show();
    }
}
