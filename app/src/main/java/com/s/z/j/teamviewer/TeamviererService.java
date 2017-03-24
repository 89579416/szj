package com.s.z.j.teamviewer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2017-03-24.
 */
public class TeamviererService extends Service {

    public static final String TAG = "MyService";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.w(TAG, "in onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(TAG, "in onStartCommand");
        Log.w(TAG, "MyService:" + this);
        String name = intent.getStringExtra("name");
        Log.w(TAG, "name:" + name);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "in onDestroy");
    }
}