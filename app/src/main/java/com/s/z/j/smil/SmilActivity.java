package com.s.z.j.smil;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.s.z.j.R;
import com.s.z.j.utils.HttpUtils;
import com.s.z.j.utils.L;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * smil解析
 * 添加依赖包  compile 'org.jsoup:jsoup:1.10.3'//解析 HTML,SMIL
 * <p>
 * Created by Administrator on 2017-08-21.
 */
public class SmilActivity extends Activity {
    Button startBtn;
    String url = "http://192.168.88.243:8080/itel/smilTest/sample1_16x9_m1.smil";
    Smil smil;
    Layout myLayout;
    Seq mySeq;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smil);
        frameLayout = (FrameLayout) findViewById(R.id.smil_framelayout);
        smil = new Smil();
        startBtn = (Button) findViewById(R.id.smil_start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        getXmlUrl();
                    }
                }.start();
            }
        });
    }

    public void getXmlUrl() {
        InputStream inputStream = HttpUtils.getInputStreamFromURL(url);
        if(inputStream != null) {
            smil = SmilUtils.getXml((inputStream));
            L.i("smil=="+smil.toString());
            if(smil != null){
                handler.sendEmptyMessage(1);
            }
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ( msg.what){
                case 1:
                   getData();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                default:break;
            }
        }
    };


    public void getData(){
        myLayout = smil.getLayout();
        /** 先解析出来整个画面的大小，然后设置到一个总的view上 */
        RootLayout rootLayout = myLayout.getRootLayout();
        L.i("width:"+rootLayout.getWidth());
        L.i("height:"+rootLayout.getHeight());
        View view = new View(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(rootLayout.getWidth(),rootLayout.getHeight(), Gravity.CENTER);
        view.setBackgroundColor(Color.RED);
        view.setLayoutParams(layoutParams);

        /*** 解析所有的布局文件  */
        List<Region> regionList = new ArrayList<>();
        regionList = myLayout.getRegionList();
        if(regionList.size()>0){
            for (int i=0;i<regionList.size();i++){
                L.i("打印一下布局文件数据 "+regionList.get(i).toString());
            }
        }
        /** 解析SEQ  时面的东西要全部同时播放的哟  */
        mySeq = smil.getSeq();
        mySeq.getDur();//整个文件播放时间
        mySeq.getRepeatCount();//文件布局方式
        frameLayout.addView(view);
    }

}
