package com.s.z.j.zidingyijindutiao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.s.z.j.R;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017-02-27.
 */
@ContentView(R.layout.activity_jindutiao)
public class JinDuTiaoActivity extends BaseActivity {

    @ViewInject(R.id.jindutiao_button)
    private Button btn_go = null;

    @ViewInject(R.id.jindutiao_progressbar)
    private MyProgress myProgress = null;

    @Override
    public void initialize(Bundle savedInstanceState) {
        addListener();
    }

    Handler mHandler =  new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            myProgress.setProgress(msg.what);
            return false;
        }
    });

    private void addListener(){
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        for(int i = 0;i <=50; i++){
                            mHandler.sendEmptyMessage(i * 2);
                            try {
                                Thread.sleep(80);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
}
