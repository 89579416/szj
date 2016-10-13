package com.szj.library.getimg.util;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.szj.library.R;


/******************************************************************
 * 文件名称 :  DialogLoadingUtil.java
 * 作   者 : zhougc
 * 创建时间 : 2014-7-1 下午5:03:28
 * 文件描述 : dialog 加载提示框
 * 版权声明 : Copyright (C) 2014 深圳艾泰尔科技有限公司
 ******************************************************************/
public class DialogLoadingUtil extends Dialog
{


    private final String TAG = "DialogLoadingUtil";

    /**
     * 提示框 文字
     */
    private String load_content;

    private RequestBack requestBack;

    public DialogLoadingUtil(Context context, boolean cancelable,
                             OnCancelListener cancelListener)
    {
        super(context, cancelable, cancelListener);
    }

    public DialogLoadingUtil(Context context, int theme, String load_content)
    {

        super(context, theme);
        this.load_content = load_content;
    }

    public DialogLoadingUtil(Context context, int theme, String load_content, RequestBack requestBack)
    {

        super(context, theme);
        this.load_content = load_content;
        this.requestBack = requestBack;
    }

    public DialogLoadingUtil(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loaddata);
        this.setCancelable(false);
        if(!TextUtils.isEmpty(load_content)){
            ((TextView) findViewById(R.id.load_tv)).setText(load_content);
        }
        if(requestBack !=null){
            this.setCancelable(true);
            this.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                    if (keyCode==KeyEvent.KEYCODE_BACK&&keyEvent.getRepeatCount()==0)
                    {

                        if(requestBack !=null){
                            requestBack.back();
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            });
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dismiss();
            return true;
        } else
            return super.onKeyDown(keyCode , event);
    }

}
