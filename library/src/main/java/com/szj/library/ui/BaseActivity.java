package com.szj.library.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.szj.library.R;

import org.xutils.x;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public abstract class BaseActivity extends Activity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        context = this;
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
}
