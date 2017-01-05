package com.s.z.j.ui.mediaplayer;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.s.z.j.R;
import com.s.z.j.utils.FileUtil;
import com.s.z.j.widget.mediaplayer.MyVideoPlayer;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 循环播放一个目录下的mp4视频
 * Created by Administrator on 2016-10-21.
 */
@ContentView(R.layout.activity_media_player)
public class MediaPlayerActivity extends BaseActivity {
    @Override
    public void initialize(Bundle savedInstanceState) {

    }
//
//    @ViewInject(R.id.media_start_btn)
//    Button startBtn;//获取“播放”按钮
//
//    @ViewInject(R.id.media_pause_btn)
//    Button pauseBtn;//获取“暂停/继续”按钮
//
//    @ViewInject(R.id.media_stop_btn)
//    Button stopBtn;//获取“停止”按钮
//
//    @ViewInject(R.id.media_player_hint)
//    private TextView hint;//声明显示提示信息的文本框
//
//    @ViewInject(R.id.media_player_framelayout)
//    private FrameLayout lframerLayout;//显示播放视频的控件
//
//    private boolean isPause = false;//是否暂停
//    private String defaultPath;//SD卡路径
//    private ArrayList<String> data = new ArrayList<String>();//要播放的路径
//
//    private MyVideoPlayer myVideoPlayer;
//    @Override
//    public void initialize(Bundle savedInstanceState) {
//        FileUtil.createFile("aabb");
//        defaultPath = Environment.getExternalStorageDirectory() + "/aabb";
//        data = FileUtil.getPlayFile(defaultPath);
//        initPlay();
//        startBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myVideoPlayer.setDataSources(data);
//                myVideoPlayer.startPlay();
//                lframerLayout.removeAllViewsInLayout();
//                lframerLayout.addView(myVideoPlayer);
//                pauseBtn.setEnabled(true);//“暂停/继续”按钮可用
//                stopBtn.setEnabled(true);//"停止"按钮可用
//                startBtn.setEnabled(false);//“播放”按钮不可用
//            }
//        });
//
//        pauseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (myVideoPlayer.isPlaying()) {
//                    myVideoPlayer.pausePlay();//暂停播放
//                    isPause = true;
//                    ((Button) v).setText("继续");
//                    hint.setText("暂停播放...");
//                    startBtn.setEnabled(true);//“播放”按钮可用
//                } else {
//                    myVideoPlayer.startPlay();//继续播放
//                    ((Button) v).setText("暂停");
//                    hint.setText("正在播放...");
//                    isPause = false;
//                    startBtn.setEnabled(false);//“播放”按钮不可用
//                }
//            }
//        });
//
//        stopBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               myVideoPlayer.stopPlay();
//                hint.setText("停止播放...");
//                pauseBtn.setEnabled(false);//“暂停/继续”按钮不可用
//                stopBtn.setEnabled(false);//“停止”按钮不可用
//                startBtn.setEnabled(true);//“播放”按钮可用
//            }
//        });
//    }
//
//    public  void initPlay(){
//        String path = data.get(0);
//        Log.i("AAAA", "path=" + path);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        myVideoPlayer = new MyVideoPlayer(MediaPlayerActivity.this, layoutParams, path);
//    }
//
//    @Override
//    protected void onDestroy() {
//        myVideoPlayer.stopPlay();
//        super.onDestroy();
//
//    }
}
