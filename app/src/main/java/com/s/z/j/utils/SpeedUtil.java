package com.s.z.j.utils;

import android.content.Context ;
import android.net.TrafficStats ;
import android.os.Handler ;
import android.os.Message ;

import java.util.Timer ;
import java.util.TimerTask ;

/**
 * 获取当前网速
 * Created by szj on 2016-10-17 0:25:34s
 */
public class SpeedUtil {
    private Context context;
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private Handler handler;
    private Timer timer;

    public SpeedUtil(Context context , Handler handler, Timer timer) {
        this .context = context ;
        this. handler = handler;
        this. timer = timer;
    }

    /**
     * 将结果转为KB
     * @return
     */
    private long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(context.getApplicationInfo(). uid)==TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024) ;
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            initGetSpeed();
        }
    };

    /**
     * 初始化获取网速
     */
    private void initGetSpeed() {
        long nowTotalRxBytes = getTotalRxBytes() ;
        long nowTimeStamp = System. currentTimeMillis() ;
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp)) ;//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        Message msg = handler.obtainMessage();
        msg. what = 100 ;
        msg. obj = String.valueOf (speed) + " kb/s" ;
        handler .sendMessage(msg);//更新界面
    }

    /**
     * 开始获取
     */
    public void start(){
        lastTotalRxBytes = getTotalRxBytes();
        lastTimeStamp = System.currentTimeMillis();
        timer .schedule(task, 1000, 2000); // 1s后启动任务，每2s执行一次
    }

    /**
     * 停止获取
     */
    public void stop(){
        if (timer != null){
            timer .cancel();
        }
        if (task != null){
            task .cancel();
        }
    }
}
