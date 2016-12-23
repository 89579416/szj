package com.s.z.j.ui.videoplay;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.app.Activity;

import com.s.z.j.R;
import com.s.z.j.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 播放一个路径下的图片和视频
 */
public class PlayActivity extends Activity {
    private List<MyImageView> imgList;
    private List<MyVideoPlayer> videoList;
    private List<MyTextView> textList;
    private MyHandler handler = new MyHandler();
    private int counter = 0;
    private String defaultPath = "";
    private ArrayList<Resource> dataPath;
    private int playNum = 0;

    FrameLayout fl;
    MyVideoPlayer videoView1;
    MyVideoPlayer videoView2;

    MyImageView imageView1;
    MyImageView imageView2;

    int type = 1;//1为第一组，2为第二组
    int count = 1;//第几次循环播放了

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        L.i("----------------onCreate----------------");
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_play_main);
        defaultPath = Environment.getExternalStorageDirectory() + "/facsimilemedia/Resources/Play";
        dataPath = new ArrayList<Resource>();
        dataPath = FileUtil.GetVideoFileName(defaultPath);
        imgList = new ArrayList<>();
        videoList = new ArrayList<>();
        textList = new ArrayList<>();


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        L.i("----------------onWindowFocusChanged----------------");
//           initImgPlayer();
//         initVideoPlayer();
//        initTextPlayer();
        if (dataPath != null && dataPath.size() > 0) {
            L.i("获取到的文件个数：" + dataPath.size());
            for (int i = 0; i < dataPath.size(); i++) {
                L.i("文件名:" + dataPath.get(i).getPath());
            }
            initPlayer();//第二次测试：比较正常
//            initVideoPlayer();
        } else {
            L.i("没有获取到资源");
        }
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * 第二次测试
     */
    public void initPlayer() {
        fl = (FrameLayout) findViewById(R.id.fl);
        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(fl.getWidth(), fl.getHeight());
        if("video".equals(dataPath.get(playNum).getType())) {
            videoView1 = new MyVideoPlayer(this, imgParams, dataPath.get(playNum).getPath());
            fl.addView(videoView1);
            videoView1.startPlay();
            playNum++;
            handler.postDelayed(runnable4, 1 * 500);
            handler.postDelayed(runnable5, dataPath.get(playNum).getTime() * 1000);
        }else if("image".equals(dataPath.get(playNum).getType())){
            imageView1 = new MyImageView(this,imgParams,dataPath.get(playNum).getPath());
            fl.addView(imageView1);
            imageView1.startPlay();
            handler.postDelayed(runnable4, 1 * 500);
            handler.postDelayed(runnable5, dataPath.get(playNum).getTime() * 1000);
            playNum++;
        }
//        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
//        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(fl.getWidth(), fl.getHeight());
//        for (int i = 0; i < dataPath.size(); i++) {
//            MyVideoPlayer imageView = new MyVideoPlayer(this, imgParams, dataPath.get(i).getPath());
//            videoList.add(imageView);
//        }
//        L.i("videoList.size="+videoList.size());
//        fl.addView(videoList.get(playNum));
//        videoList.get(playNum).startPlay();
//        playNum++;
//        handler.postDelayed(runnable1, 5 * 1000);
    }

    public Runnable runnable4 = new Runnable() {
        @Override
        public void run() {

            fl = (FrameLayout) findViewById(R.id.fl);
            FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(fl.getWidth(), fl.getHeight());
            if("image".equals(dataPath.get(playNum).getType())){
                if(type==1) {
                    imageView2 = new MyImageView(PlayActivity.this, imgParams, dataPath.get(playNum).getPath());
                    L.i("第二个图片OK");
                }else {
                    imageView1 = new MyImageView(PlayActivity.this, imgParams, dataPath.get(playNum).getPath());
                    L.i("第一个图片OK");
                }
            }else if("video".equals(dataPath.get(playNum).getType())){
                if(type==1) {
                    videoView2 = new MyVideoPlayer(PlayActivity.this, imgParams, dataPath.get(playNum).getPath());
                    L.i("第二个视频OK");
                }else {
                    videoView1 = new MyVideoPlayer(PlayActivity.this, imgParams, dataPath.get(playNum).getPath());
                }
            }

        }
    };

    public Runnable runnable5 = new Runnable() {
        @Override
        public void run() {
            if("image".equals(dataPath.get(playNum).getType())){
                if(type==1) {
                    try {
                        videoView1.stopPlay();
                    }catch (Exception e){

                    }
                    fl.removeAllViews();
                    fl.addView(imageView2);
                    imageView2.startPlay();
                    type = 2;
                    L.i("开始播放2-第"+playNum+"个  "+dataPath.get(playNum).getPath());
                }else {
                    try {
                        imageView2.stopPlay();
                    }catch (Exception e){

                    }
                    fl.removeAllViews();
                    fl.addView(imageView1);
                    imageView1.startPlay();
                    type = 1;
                    L.i("开始播放1-第"+playNum+"个  "+dataPath.get(playNum).getPath());
                }
            }else if("video".equals(dataPath.get(playNum).getType())){
                if(type==1) {
                    try {
                        videoView1.stopPlay();
                    }catch (Exception e){

                    }
                    fl.removeAllViews();
                    fl.addView(videoView2);
                    L.i("当前要播放的内容："+videoView2.getDataSources());
                    videoView2.startPlay();
                    type = 2;
                    L.i("开始播放2-第"+playNum+"个  "+dataPath.get(playNum).getPath());
                }else {
                    try {
                        videoView2.stopPlay();
                    }catch (Exception e){

                    }
                    fl.removeAllViews();
                    fl.addView(videoView1);
                    L.i("当前要播放的内容："+videoView1.getDataSources());
                    videoView1.startPlay();
                    type = 1;
                    L.i("开始播放1-第"+playNum+"个  "+dataPath.get(playNum).getPath());
                }
            }
            L.i("");
            playNum++;
            if(playNum==dataPath.size()){
                playNum=0;
                count++;
                L.i("第"+count+"次播放了");
            }
            handler.postDelayed(runnable4, 1 * 1000);
            handler.postDelayed(runnable5, dataPath.get(playNum).getTime() * 1000);
        }
    };

    private void initImgPlayer() {
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(fl.getWidth(), fl.getHeight());
        for (int i = 0; i < 60; i++) {
            MyImageView imageView = new MyImageView(this, imgParams, "/storage/sdcard0/facsimilemedia/Resources/Play/b1024x768.jpg");
            imgList.add(imageView);
        }
        fl.addView(imgList.get(imgList.size() - 1));
        imgList.get(imgList.size() - 1).startPlay();
        handler.postDelayed(runnable, 5 * 1000);
    }

    private void initVideoPlayer() {
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(fl.getWidth(), fl.getHeight());
        //  zhijiage.mp4    ok
        //  player001.mp4   no
        //  zuowei.mp4      no
        //  zuowei2.mov     ok
        //  zhuimengren.mp4     ok
        //  31          ok
        //  32          ok
        //  33          ok
        //  m1080.m4v   ok
        /// shenzhenlonghua1080p.mp4    ok
        //  shoujishipin720p.mp4        ok
        File file = new File(defaultPath + "/longhua.mp4");
        Log.i("AAAA", "视频文件:" + file.getPath() + " 是否存在:" + file.exists());
        for (int i = 0; i < 60; i++) {
            MyVideoPlayer imageView = new MyVideoPlayer(this, imgParams, defaultPath + "/moshou.mp4");
            videoList.add(imageView);
        }
        fl.addView(videoList.get(videoList.size() - 1));
        videoList.get(videoList.size() - 1).startPlay();
        handler.postDelayed(runnable1, 3 * 1000);
    }

    private void initTextPlayer() {
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(fl.getWidth(), fl.getHeight());
        for (int i = 0; i < 60; i++) {
            MyTextView textView = new MyTextView(this, imgParams);
            textView.setBackgroundColor(Color.RED);
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine();
            textView.setText("谷歌是Google公司开发的互联网搜索引擎。主要提供网页搜索，图片搜索，地图搜索，新闻搜索，博客搜索，论坛搜索，学术");
            textView.setTextColor(Color.BLUE);
            textView.setTextSize(25);
            textView.setSpeed(3);
            textList.add(textView);
        }
        fl.addView(textList.get(textList.size() - 1));
        textList.get(textList.size() - 1).startPlay();
        handler.postDelayed(runnable2, 5 * 1000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
            fl.removeAllViews();
            fl.addView(imgList.get(counter));
            imgList.get(counter).startPlay();
            counter++;
            Log.e("a", "change....." + counter);
            handler.postDelayed(runnable, 5 * 1000);
        }
    };
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
            fl.removeAllViews();

            fl.addView(videoList.get(counter));
            videoList.get(counter).startPlay();
            counter++;
            Log.e("a", "change....." + counter);
            handler.postDelayed(runnable1,3 * 1000);
        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
            fl.removeAllViews();
            fl.addView(textList.get(counter));
            textList.get(counter).startPlay();
            counter++;
            Log.e("a", "change....." + counter);
            handler.postDelayed(runnable2, 5 * 1000);
        }
    };

    public class MyHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            fl.removeAllViews();
            try {
                imageView1.stopPlay();
                imageView2.stopPlay();
                videoView1.stopPlay();
                videoView2.stopPlay();
            }catch (Exception e){

            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

