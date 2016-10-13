package com.szj.library.ui;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.szj.library.R;
import com.szj.library.ui.annotation.MTFActivityFeature;

import org.xutils.x;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public abstract class BaseCompatActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        context = this;


        MTFActivityFeature activityFeature = BaseCompatActivity.this.getClass().getAnnotation(MTFActivityFeature.class);
        if(activityFeature == null) throw new IllegalArgumentException(">>> not set layout resources!");
        if(activityFeature.status_bar_color() > 0 ) setStatusBarColor(activityFeature.status_bar_color());
        setContentView(activityFeature.layout());
        ButterKnife.bind(this);
        initialize(savedInstanceState);
    }

    public abstract void initialize(Bundle savedInstanceState);

    /**
     * anim launch Activity
     * @param clazz class
     */
    public void animStart(Class clazz){
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
        overridePendingTransition(R.anim.roll_up, R.anim.roll);
    }

    /**
     * anim launch Activity
     * @param intent intent
     */
    public void animStart(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.roll_up, R.anim.roll);
    }


    /**
     * anim finish Activity
     */
    public void animFinish(){
        finish();
        overridePendingTransition(0, R.anim.roll_down);
    }
    /**
     * set status bar color
     */
    public void setStatusBarColor(int statusBarColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, statusBarColor));
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(statusBarColor);
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
