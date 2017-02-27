package com.s.z.j.shuangping;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.s.z.j.R;
import com.s.z.j.newUtils.FileUtils;
import com.s.z.j.utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// 目前想在一个surfaceView里面实现图片和视频操作起来比较困难。
// 要分别用两个控件来，一个显示视频，一个显示图片，然后适时隐藏。
// 视频用surfaceView. 图片还是用viewPager来.

public class SPMainActivity extends Activity {

    // 主屏播放视频的路径。
    public final static String LVDS_PATH = Environment.getExternalStorageDirectory()+"/facsimilemedia/resources/play";

    private List<String> lvdsVideoPath = new ArrayList<>();
    private int nowLvdsPosition;
    private List<String> hdmiVideoPath = new ArrayList<>();
    private int nowHdmiPosition;

    // 两个player
    private MediaPlayer screen1Player;
    private MediaPlayer screen2Player;

    private DisplayManager mDisplayManager;

    private SurfaceView mainSurface;
    private SurfaceView hdmiSurface;

    private MyPresentation myPresentation;

    private PopupWindow videoControlPop;

    private boolean isVideoControlShowing = false;
    private SeekBar videoProgress;

    private final static int HIDE_POP = 0x111;
    private final static int HIDE_POP_TIME = 10 * 1000;

    private boolean isAirplay = false;
    private boolean isPause = false;

    private ImageView airPlay, playPre, playNext, playOrPause;

    private int currentVideoTime;

    private Timer timer;

    private final static String SERVICE_ACTION = "com.ryx.twovideos.actions";

    // List of all currently visible presentations indexed by display id.
    private final SparseArray<MyPresentation> mActivePresentations = new SparseArray<MyPresentation>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HIDE_POP:
                    if (isVideoControlShowing) {
                        videoControlPop.dismiss();
                        videoControlPop = null;
                        // 停止刷新seekbar计时。
                        timer.cancel();
                    }
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangping_main);
        mDisplayManager = (DisplayManager) getSystemService(
                Context.DISPLAY_SERVICE);
        mDisplayManager.registerDisplayListener(mDisplayListener, null);
        initView();
    }

    void initView() {
        lvdsVideoPath = FileUtil.getPlayFile(LVDS_PATH);
        mainSurface = (SurfaceView) findViewById(R.id.main_surface);
        mainSurface.setOnTouchListener(new MySurfaceViewTouchListener());
        mainSurface.getHolder().addCallback(new MySurfaceCallBack());
        screen1Player = new MediaPlayer();
        playVideo(screen1Player, nowLvdsPosition);
    }

    // surface 的回调接口。
    class MySurfaceCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                screen1Player.setDisplay(holder);
            }catch (Exception e){}
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    // 播放结束监听。
    class MyVideoFinishListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mp == screen1Player) {
                nowLvdsPosition++;
                if (nowLvdsPosition >= lvdsVideoPath.size()) {
                    nowLvdsPosition = 0;
                }
                playVideo(mp, nowLvdsPosition);
            }

            if (mp == screen2Player) {
                nowHdmiPosition++;
                if (nowHdmiPosition >= hdmiVideoPath.size()) {
                    nowHdmiPosition = 0;
                }
                screen2Player.reset();
                try {
                    screen2Player.setDataSource(hdmiVideoPath.get(nowHdmiPosition));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // surface 触摸监听。
    class MySurfaceViewTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (videoControlPop == null) {
                    showBottomPop();
                }
            }
            return false;
        }
    }

    // 显示Pop
    private void showBottomPop() {
        mHandler.sendEmptyMessageDelayed(HIDE_POP, HIDE_POP_TIME);
        isVideoControlShowing = true;
        mainSurface.setZOrderOnTop(false);
        LayoutInflater inflater = getLayoutInflater();
        View bottomPopView = inflater.inflate(R.layout.video_control_layout, null);
        videoProgress = (SeekBar) bottomPopView.findViewById(R.id.video_progress);
        videoProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                screen1Player.seekTo(seekBar.getProgress() * screen1Player.getDuration() / 100);
            }
        });

        airPlay = (ImageView) bottomPopView.findViewById(R.id.air_play);
        airPlay.setOnClickListener(new MyViewOnClickListener());

        playPre = (ImageView) bottomPopView.findViewById(R.id.play_pre);
        playPre.setOnClickListener(new MyViewOnClickListener());

        playNext = (ImageView) bottomPopView.findViewById(R.id.play_next);
        playNext.setOnClickListener(new MyViewOnClickListener());

        playOrPause = (ImageView) bottomPopView.findViewById(R.id.play_or_pause);
        playOrPause.setOnClickListener(new MyViewOnClickListener());

        videoControlPop = new PopupWindow(bottomPopView, WindowManager.LayoutParams.MATCH_PARENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        //  popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        videoControlPop.showAtLocation(bottomPopView, Gravity.BOTTOM, 0, 0);
        videoControlPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isVideoControlShowing = false;
            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    videoProgress.setProgress(screen1Player.getCurrentPosition() * 100 / screen1Player.getDuration());
                    currentVideoTime = screen1Player.getCurrentPosition();
                }catch (Exception e){}
            }
        }, 0, 1000);
    }

    /**
     * Shows a {@link } on the specified display.
     */
    /*private void showPresentation(Display display) {
        final int displayId = display.getDisplayId();
        if (mActivePresentations.get(displayId) != null) return;
        myPresentation = new MyPresentation(this, display);
        //myPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        myPresentation.show();
        //myPresentation.setOnDismissListener(mOnDismissListener);
        mActivePresentations.put(displayId, myPresentation);
        hdmiSurface = myPresentation.getSurface();
        hdmiSurface.getHolder().addCallback(new MySurfaceCallBack());
    }
*/

    /*public void updateContents() {
        Display[] displays = mDisplayManager.getDisplays();

        if (displays[1] != null) {
         // displays[1] = null;
        }
       // showPresentation(displays[1]);
       // playVideo(screen1Player, nowLvdsPosition);
       // screen1Player.seekTo(currentVideoTime);
      //  mainSurface.destroyDrawingCache();
    }
*/
    // 点击监听。
    class MyViewOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (v == airPlay) {
                if (!isAirplay) {
                    Intent i = new Intent(SPMainActivity.this, ShuangPingService.class);
                    startService(i);
                    isAirplay = true;
                } else {
                    Intent i = new Intent(SERVICE_ACTION);
                    i.putExtra("receiver_key", 0);
                    sendBroadcast(i);
                    isAirplay = false;
                }

            }

            if (v == playPre) {
                nowLvdsPosition--;
                if (nowLvdsPosition < 0) {
                    nowLvdsPosition = lvdsVideoPath.size() - 1;
                }
                playVideo(screen1Player, nowLvdsPosition);
            }

            if (v == playNext) {
                nowLvdsPosition++;
                if (nowLvdsPosition >= lvdsVideoPath.size()) {
                    nowLvdsPosition = 0;
                }
                playVideo(screen1Player, nowLvdsPosition);
            }

            if (v == playOrPause) {
                if (!isPause) {
                    screen1Player.pause();
                    isPause = true;
                } else {
                    screen1Player.start();
                    isPause = false;
                }
            }
        }
    }

    // 统一的播放界面。
    private void playVideo(MediaPlayer mediaPlayer, int pathIndex) {
        mediaPlayer.reset();
        mediaPlayer.setOnCompletionListener(new MyVideoFinishListener());
        try {
            mediaPlayer.setDataSource(lvdsVideoPath.get(pathIndex));
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

    /**
     * Listens for displays to be added, changed or removed. We use it to update
     * the list and show a new {@link } when a display is connected.
     * <p/>
     * Note that we don't bother dismissing the {@link } when a
     * display is removed, although we could. The presentation API takes care of
     * doing that automatically for us.
     *
     * 显示设备接入监听。
     */
    private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() {
        public void onDisplayAdded(int displayId) {
            // mHasTwoDispalys = true;
            // updateContents();
        }

        public void onDisplayChanged(int displayId) {
            //updateContents();
        }

        public void onDisplayRemoved(int displayId) {
            // mHasTwoDispalys = true;
            Log.d("3333333", "0000000000222");
            // updateContents();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        screen1Player.release();
    }
}