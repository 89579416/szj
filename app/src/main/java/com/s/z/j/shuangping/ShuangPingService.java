package com.s.z.j.shuangping;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.s.z.j.R;
import com.s.z.j.utils.FileUtil;
import com.s.z.j.utils.L;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RYX on 2016/6/24.
 * 用于后台播放视频。
 */
public class ShuangPingService extends Service {
    // 外接HDMI显示的视频路径。
    public final static String HDMI_PATH = Environment.getExternalStorageDirectory()+"/facsimilemedia/Resources/Play";
    private List<String> hdmiVideoPath = new ArrayList<>();
    private MediaPlayer mBackgroundPlayer;

    private SurfaceView presentSurface;
    private MyPresentation myPresentation;

    private DisplayManager mDisplayManager;
    private SharedPreferences sharedPreferences;

    private int nowHdmiPosition;
    private final static String SERVICE_ACTION = "com.ryx.twovideos.actions";


    MsgReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("play_time", 0);
        L.i("HDMI_PATH=" + HDMI_PATH);
        hdmiVideoPath = FileUtil.getPlayFile(HDMI_PATH);
        L.i("hdmiVideoPath.size="+hdmiVideoPath.size());
        updateContents();
        registMyReceiver();
        initViewSurface();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initViewSurface() {
        mBackgroundPlayer = new MediaPlayer();
        mBackgroundPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
        L.i("HDMI_PATH=" + HDMI_PATH);
        hdmiVideoPath = FileUtil.getPlayFile(HDMI_PATH);
        L.i("hdmiVideoPath.size="+hdmiVideoPath.size());
        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 获取LayoutParams对象
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        // 宽度和高度为0, 可以避免主屏退出后出现的主屏触摸和点击问题。
        wmParams.width = 0;
        wmParams.height = 0;
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout presentationLayout = (LinearLayout) inflater.inflate(R.layout.presentation_layout, null);
        presentationLayout.setFocusable(false);
        mWindowManager.addView(presentationLayout, wmParams);
        playVideo(mBackgroundPlayer, nowHdmiPosition, true);
    }

    private void registMyReceiver() {
        receiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SERVICE_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    //  接收activity 发送过来的广播，来作相应的播放处理。目前就做了一个切换功能。可以加上下一曲和暂停等一些功能。
    class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getIntExtra("receiver_key", -1) == 0) {
                    myPresentation.dismiss();
                    unregisterReceiver(receiver);
                    stopSelf();
                }
            }
        }
    }

    // 获取显示设备。
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void updateContents() {
        mDisplayManager = (DisplayManager) getSystemService(
                Context.DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();
        Log.d("00000", "4343453nums===" + displays.length);
        showPresentation(displays[1]);
    }

    /**
     * 将内容显示到display上面。
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showPresentation(Display display) {
        myPresentation = new MyPresentation(this, display);
        myPresentation.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // 监听消失，保存当前播放位置。
                sharedPreferences.edit().putInt("index", nowHdmiPosition).commit();
                sharedPreferences.edit().putInt("position", mBackgroundPlayer.getCurrentPosition()).commit();
            }
        });
        myPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        myPresentation.show();
        presentSurface = myPresentation.getSurface();
        presentSurface.getHolder().addCallback(new MySurfaceCallback());
    }

    // 统一的播放界面。
    private void playVideo(MediaPlayer mediaPlayer, int pathIndex, boolean isFirstPlay) {

        if (isFirstPlay) {
            mediaPlayer.setOnCompletionListener(new MyVideoFinishListener());
            try {
                nowHdmiPosition = sharedPreferences.getInt("index", 0);
//                mediaPlayer.setDataSource(hdmiVideoPath.get(sharedPreferences.getInt("index", 0)));
                mediaPlayer.setDataSource(hdmiVideoPath.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                //  拔hdmi会出现异常。
            }
            mediaPlayer.start();
            mediaPlayer.seekTo(sharedPreferences.getInt("position", 0));

        } else {
            mediaPlayer.reset();
            mediaPlayer.setOnCompletionListener(new MyVideoFinishListener());
            try {
                mediaPlayer.setDataSource(hdmiVideoPath.get(pathIndex));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                //  拔hdmi会出现异常。
            }
            mediaPlayer.start();
        }
    }

    // 播放结束监听。
    class MyVideoFinishListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            nowHdmiPosition++;
            if (nowHdmiPosition >= hdmiVideoPath.size()) {
                nowHdmiPosition = 0;
            }
            playVideo(mp, nowHdmiPosition, false);
        }
    }

    class MySurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // SurfaceHolder是SurfaceView的控制接口
            // 对mediaplayer设定显示的surfaceView.
            mBackgroundPlayer.setDisplay(presentSurface.getHolder());
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBackgroundPlayer.release();
    }
}