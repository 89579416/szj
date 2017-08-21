package com.s.z.j.ui.test;

import android.app.Activity;
import android.os.Bundle;

import com.s.z.j.R;
import com.s.z.j.utils.L;

/**
 * 播放视频
 * 测试类
 */
public class MediaPlayerTestActivity extends Activity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("onCreate--Activity创建时调用");
        setContentView(R.layout.test_recyclerview_header);
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.i("onStart--这是onCreate函数调用之后被调用，此时Activity还不可见");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i("onResume Activity变为可见时被调用");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.i("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.i("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.i("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.i("onRestart");
    }


}