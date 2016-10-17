package com.s.z.j.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.s.z.j.utils.L;

/**
 * 广播接收
 * Created by Administrator on 2016-10-17.
 */
public class ReceiveBroadCast  extends BroadcastReceiver {
    private Handler handler;

    public ReceiveBroadCast(Handler handler){
        this.handler = handler;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        //得到广播中得到的数据
        String str = intent.getStringExtra("data");
        L.i("广播中收到的数据="+str);
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("data", str);
        message.setData(bundle);//bundle传值，耗时，效率低
        message.what=1;//标志是哪个线程传数据
        handler.sendMessage(message);//发送message信息
    }

}
