package com.s.z.j.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.s.z.j.R;
import com.s.z.j.textquee.MarqueeText;
import com.s.z.j.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-02-09.
 */
public class TestMyEdittextActivity extends Activity {
    MyEdittext edittext;
    Button button;
    EditText editText2;
    private MarqueeText mMarqueeText;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_myedittext);
        edittext = (MyEdittext) findViewById(R.id.test_myedittext_edittext);
        editText2 = (EditText) findViewById(R.id.test_myedittext_2_edittext);
        tv = (TextView) findViewById(R.id.test_myedittext_tv);
        button = (Button) findViewById(R.id.test_myedittext_button);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 30, 0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i(edittext.gettext());
                editText2.setText("" + edittext.gettext());
                tv.setText("" + edittext.gettext());
                //当字数小于10时，靠右并离右边30dp，否则居中
                if(tv.getText().length()<10){
                    tv.setGravity(Gravity.RIGHT);
                    tv.setLayoutParams(lp);
                }else {
                    tv.setGravity(Gravity.CENTER);
                }
            }
        });

        /** 跑马灯效果 */
        List<String> news = new ArrayList<String>();
        news.add("第一行开始运行啦！！！");
        news.add("第二行跑起来了，，，，，，，，，");
        news.add("zzzzzzz呜呜呜呜zzzzzz呜呜zzz");
        mMarqueeText = (MarqueeText)findViewById(R.id.marqueeText);
        mMarqueeText.setData(news);


        /** 测试 */


    }
}
