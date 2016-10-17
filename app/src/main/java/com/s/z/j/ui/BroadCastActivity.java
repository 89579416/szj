package com.s.z.j.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.s.z.j.R;
import com.s.z.j.broadcast.ReceiveBroadCast;
import com.szj.library.ui.BaseActivity;
import com.szj.library.utils.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-10-17.
 */
@ContentView(R.layout.activity_broad_cast)
public class BroadCastActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.broad_register_btn)
    private Button registerBtn;
    /**
     * 注册广播
     */

    @ViewInject(R.id.broad_send_btn)
    private Button sendBtn;
    /**
     * 发送广播
     */

    @ViewInject(R.id.broad_cancel_btn)
    private Button cancelBtn;
    /**
     * 取消注册
     */

    private ReceiveBroadCast receiveBroadCast;  //广播实例
    private String flag = "com.s.z.j.ui.BroadCastActivity";

    @Override
    public void initialize(Bundle savedInstanceState) {
        registerBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.broad_register_btn:
                // 注册广播接收
                receiveBroadCast = new ReceiveBroadCast(broadHandler);
                IntentFilter filter = new IntentFilter();
                filter.addAction(flag);    //只有持有相同的action的接受者才能接收此广播
                registerReceiver(receiveBroadCast, filter);
                break;
            case R.id.broad_send_btn:
                /** 发送数据为当前时间，从Calendar日历类中获取，当秒为0-9时，前面加0 */
                Intent intent = new Intent();
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                String second  = ""+calendar.get(Calendar.SECOND);
                if(second.length()<2){
                    second = "0"+second;
                }
                intent.putExtra("data", day+":"+minute+":"+second);
                intent.setAction(flag);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
                sendBroadcast(intent);   //发送广播
                break;
            case R.id.broad_cancel_btn:
                unregisterReceiver(receiveBroadCast);
                break;
            default:
                break;
        }
    }

    /**
     * 用于更新UI的广播handler
     */
    private Handler broadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    T.s(context,"收到广播发送的时间："+msg.getData().getString("data"));
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //这里写你要做的事
            return true;
        } else
            return super.onKeyDown(keyCode , event);
    }

}
