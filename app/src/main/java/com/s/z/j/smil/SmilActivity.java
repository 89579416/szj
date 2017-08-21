package com.s.z.j.smil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.s.z.j.R;
import com.s.z.j.utils.HttpUtils;
import com.s.z.j.utils.L;

import java.io.InputStream;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smil);
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
        }
    }


}
