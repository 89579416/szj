package com.s.z.j.exoplayer;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ClippingMediaSource;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.s.z.j.R;
import com.s.z.j.utils.FileUtil;
import com.s.z.j.utils.L;

import java.io.File;
import java.util.ArrayList;

/**
 * ExoPlayer 类封装后测试播放视频
 * Created by Administrator on 2017-04-10.
 */
public class MyExoPlayerActivity extends Activity {

    SimpleExoPlayerView simpleExoPlayerView;
    private ArrayList<String> data = new ArrayList<>();
    MyExoPlayer myExoPlayer;
    Context context;
    FrameLayout.LayoutParams params;
    DataSource.Factory dataSourceFactory;
    BandwidthMeter bandwidthMeter;
    MediaSource[] mediaSources;
    ExtractorsFactory extractorsFactory;
    LoopingMediaSource loopingMediaSource;
    ConcatenatingMediaSource concatenatedSource;
    FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        context = this;
        setContentView(R.layout.activity_exoplayer);
        data =  FileUtil.getFile(new File(Environment.getExternalStorageDirectory() + "/fd"));
        fl = (FrameLayout) findViewById(R.id.activity_exoplayer_fl);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.activity_exoplayer_simpleexoplayerview);

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        data = FileUtil.getFile(new File(Environment.getExternalStorageDirectory() + "/fd"));
        bandwidthMeter = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "MyAppLication"), (TransferListener<? super DataSource>) bandwidthMeter);
        extractorsFactory = new DefaultExtractorsFactory();
        mediaSources = new MediaSource[data.size()];
        for (int i = 0; i < data.size(); i++) {
            mediaSources[i] = new ExtractorMediaSource(Uri.parse(data.get(i)), dataSourceFactory, extractorsFactory, null, null);
            ClippingMediaSource clippingMediaSource = new ClippingMediaSource(mediaSources[i],0,4);
        }
        L.i("mediaSources.length=" + mediaSources.length);
        concatenatedSource = new ConcatenatingMediaSource(mediaSources);
        loopingMediaSource = new LoopingMediaSource(concatenatedSource);

        if (data != null && data.size() > 0) {
            myExoPlayer = new MyExoPlayer(context,simpleExoPlayerView,loopingMediaSource,params,eventListener,listener);
            myExoPlayer.init();
//            handler.postDelayed(runnable, 5000);
//            handler.postDelayed(runnable1,10000);
//            myExoPlayer.start();
        }



    }
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            myExoPlayer.pause();
        }
    };

    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            myExoPlayer.start();
        }
    };

    /**
     * 全屏显示
     */
    public void initWindow() {
        // 设置为全屏并隐藏有些设备下面的控制按钮
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    ExoPlayer.EventListener eventListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            L.i("onTimelineChanged--" + timeline + "  " + manifest.toString());
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            L.i("onTracksChanged");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            L.i("onLoadingChanged--"+isLoading);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            L.i("onPlayerStateChanged--"+playWhenReady+"  "+playbackState);
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            L.i("onPlayerError--"+error);
        }

        @Override
        public void onPositionDiscontinuity() {
            L.i("onPositionDiscontinuity");
        }
    };


    PlaybackControlView.VisibilityListener listener = new PlaybackControlView.VisibilityListener(){
        @Override
        public void onVisibilityChange(int visibility) {
            L.i("onVisibilityChange--" + visibility);
        }
    };
}
