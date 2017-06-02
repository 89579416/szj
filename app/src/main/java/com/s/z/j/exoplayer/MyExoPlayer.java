
package com.s.z.j.exoplayer;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.s.z.j.utils.L;

/**
 * Created by Administrator on 2017-03-14.
 */
public class MyExoPlayer {
    public Context context;
    SimpleExoPlayer player;
    LoadControl loadControl;
    TrackSelector trackSelector;
    SimpleExoPlayerView simpleExoPlayerView;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    FrameLayout.LayoutParams params;
    private boolean playerNeedsSource = true;
    LoopingMediaSource loopingMediaSource;
    com.google.android.exoplayer2.ExoPlayer.EventListener eventListener;
    PlaybackControlView.VisibilityListener listener;
    public MyExoPlayer(Context context, SimpleExoPlayerView simpleExoPlayerView, LoopingMediaSource loopingMediaSource, FrameLayout.LayoutParams params, ExoPlayer.EventListener eventListener, PlaybackControlView.VisibilityListener listener) {
        L.i("初始化数据");
        this.context = context;
        this.simpleExoPlayerView = simpleExoPlayerView;
        this.loopingMediaSource = loopingMediaSource;
        this.params = params;
        this.eventListener = eventListener;
        this.listener = listener;
        L.i("初始化数据完成");
    }
    /**
     * 初始化播放器
     */
    public void init() {
        if (player == null) {
            L.i("初始化播放器");
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            player.addListener(eventListener);
            if (playerNeedsSource) {
                player.prepare(loopingMediaSource, false, false);
                playerNeedsSource = false;
            }
            simpleExoPlayerView.setLayoutParams(params);
            simpleExoPlayerView.setControllerVisibilityListener(listener);
            simpleExoPlayerView.requestFocus();
            simpleExoPlayerView.setPlayer(player);
            L.i("初始化播放器结束");
        }else {
            L.i("player != null  什么也不做啦");
            if (playerNeedsSource) {
                player.prepare(loopingMediaSource, false, false);
                playerNeedsSource = false;
            }
        }
        start();
    }
    /**
     * 初始化播放器
     */
    public void initializePlayer() {
        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            player.addListener(eventListener);
            if (playerNeedsSource) {
                player.prepare(loopingMediaSource, false, false);
                playerNeedsSource = false;
            }
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            simpleExoPlayerView.setLayoutParams(params);
            simpleExoPlayerView.setControllerVisibilityListener(listener);
            simpleExoPlayerView.requestFocus();
            simpleExoPlayerView.setPlayer(player);
        }
        playerNeedsSource = true;
        player.setPlayWhenReady(true);
        simpleExoPlayerView.hideController();
        L.i("隐藏了控制器");
    }
    public void start(){
        playerNeedsSource = true;
        player.setPlayWhenReady(true);
        simpleExoPlayerView.hideController();
        L.i("开始播放了,隐藏控制按钮");

    }
    /**
     * 释放播放器
     */
    public void stop() {
        if (player != null) {
            L.i("释放播放器");
            player.stop();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    public void pause(){
        if(player != null){
            player.setPlayWhenReady(false);
            simpleExoPlayerView.showController();
        }
    }
}




