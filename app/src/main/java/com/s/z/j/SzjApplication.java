package com.s.z.j;

import android.app.Application;

import com.s.z.j.logs.CrashHandler;
import com.s.z.j.logs.DeviceManager;
import com.s.z.j.logs.LogManager;
import com.s.z.j.net.HttpClient;

import org.xutils.x;


/**
 * 初始化，程序只运行一次
 * Created by Administrator on 2016/4/14 0014.
 */
public class SzjApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

        HttpClient.getInstance().init(this);
        initLogManager();
    }

    /**
     * 初始化日志记录类
     */
    private  void initLogManager() {
        //异常信息日志记录配置
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this); //在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
        DeviceManager.setContext(getApplicationContext());
        LogManager.setContext(this);
    }
}
