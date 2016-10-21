package com.s.z.j.ui.test;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.s.z.j.R;
import com.s.z.j.utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 播放视频
 * 资源来源
 * http://www.cnblogs.com/devinzhang/archive/2012/02/03/2337576.html
 */
public class MediaPlayerTestActivity extends Activity implements SurfaceHolder.Callback,MediaPlayer.OnCompletionListener {
    /** Called when the activity is first created. */
    MediaPlayer player;
    SurfaceView surface;
    SurfaceHolder surfaceHolder;
    Button play,pause,stop;
    private int index;
    private String defaultPath;//SD卡路径
    private ArrayList<String> data = new ArrayList<String>();//要播放的路径

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        defaultPath = Environment.getExternalStorageDirectory() + "/aabb";
        data = FileUtil.getPlayFile(defaultPath);
        play=(Button)findViewById(R.id.button1);
        pause=(Button)findViewById(R.id.button2);
        stop=(Button)findViewById(R.id.button3);
        surface =(SurfaceView)findViewById(R.id.surface);

        surfaceHolder = surface.getHolder();//SurfaceHolder是SurfaceView的控制接口
        surfaceHolder.addCallback(this); //因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
        surfaceHolder.setFixedSize(320, 220);//显示的分辨率,不设置为视频默认
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//Surface类型

        play.setOnClickListener(new View.OnClickListener(){
             @Override
            public void onClick(View v) {
                player.start();
            }});
        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                player.pause();
            }});
        stop.setOnClickListener(new View.OnClickListener(){
             @Override
            public void onClick(View v) {
                player.stop();
            }});
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
//必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDisplay(surfaceHolder);
        //设置显示视频显示在SurfaceView上
            try {
                player.setDataSource(data.get(index));
                player.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(player.isPlaying()){
        player.stop();
        }
        player.release();
        //Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i("AAAA", "播放结束");
        player.stop();
        index++;
        setPerparePlay();
    }

    /**
     * 准备播放
     */
    public void setPerparePlay() {
        //=======================开启MediaPlayer===================================
        player.reset();
        try {
            // mediaPlayer.seekTo(0);
            if (index >= data.size()) {
                index = 0;
            }
            player.setDataSource(data.get(index));
            player.prepareAsync();//预加载音频
            player.start();
        } catch (IllegalArgumentException e) {
            // LogManager.writeLog(LogManager.ERROR, TAG, e);
        } catch (IllegalStateException e) {
            // LogManager.writeLog(LogManager.ERROR, TAG, e);
        } catch (IOException e) {
            //    LogManager.writeLog(LogManager.ERROR, TAG, e);
        }
    }
}