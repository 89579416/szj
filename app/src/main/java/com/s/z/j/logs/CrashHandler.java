package com.s.z.j.logs;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 说明：全局未处理异常捕获
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler instance;//单例引用
    private Context context;
    private static final String TAG = "CrashHandler";


    private CrashHandler() {
    }

    public synchronized static CrashHandler getInstance() {//同步方法，避免多线程环境下出现异常
        if (null == instance) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 设置该对象为默认的UncaughtException处理器
     *
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        StringBuffer sb = new StringBuffer();
        sb.append("ThreadName:" + thread.getName());
        sb.append("\nThreadId:" + thread.getId());
        sb.append("\nStackTrace:" + getErrorInfo(throwable));
        LogManager.writeLog(LogManager.ERROR, TAG, sb.toString());
        showException();
    }

    /**
     * 显示异常信息
     */
    private void showException() {
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "出现异常！", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }

    /**
     * 获取错误的信息
     *
     * @param arg1
     * @return
     */
    private String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }
}
