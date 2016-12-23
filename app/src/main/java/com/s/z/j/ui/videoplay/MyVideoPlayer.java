package com.s.z.j.ui.videoplay;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;


import java.io.IOException;


/**
 * 作者：王强
 * 日期：2015/9/24.
 * 版本： V1.0
 * V1.1 20160307 增加配图显示
 * 说明：播放入口类
 */
public class MyVideoPlayer extends FrameLayout implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback,
        Player {
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private Context context;
    private String dataSources;
    private final String TAG = "MyVideoPlayer";

    /**
     * 设置当前播放路径
     *
     * @param dataSources
     */
    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

    private int playIndex = 0;
    private int playerIndex = 0;
    private boolean isPlayerSetHandle = false;
    //  private boolean isComplication = true;
    private int visibility = View.VISIBLE;
    private LayoutParams tparams;

    /**
     * 设置播放ID
     *
     * @param playerIndex
     */
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public String getDataSources() {
        return dataSources;
    }

    /**
     * 获得SurfaceView
     *
     * @return
     */
    public FrameLayout getView() {
        return this;
    }

    public static final int STATUE_PLAY = 1;
    public static final int STATUE_STOP = 2;
    private int statue;
    private boolean isPause;
    private boolean isRelease;
    private long temp;

    /**
     * 获得播放状态
     *
     * @return
     */
    public int getStatue() {
        return statue;
    }

    public MyVideoPlayer(Context context, LayoutParams tparams, String dataSources) {
        super(context);
        isRelease = false;
        this.context = context;
        this.dataSources = dataSources;
        this.tparams = tparams;
        this.setLayoutParams(tparams);
        //设置配图
        int thumbnailWidth = 0, thumbnailHeight = 0, thumbnailLeftMargin = 0, thumbnailTopMargin = 0;
        int videoWidth = tparams.width, videoHeight = tparams.height, videoLeftMargin = 0, videoTopMargin = 0;
        LayoutParams imgParams = null;

        imgParams = new LayoutParams(thumbnailWidth, thumbnailHeight);
        imgParams.leftMargin = thumbnailLeftMargin;
        imgParams.topMargin = thumbnailTopMargin;

        LayoutParams videoParams = new LayoutParams(videoWidth, videoHeight);
        videoParams.leftMargin = videoLeftMargin;
        videoParams.topMargin = videoTopMargin;


        surfaceView = new SurfaceView(context);
        surfaceView.setLayoutParams(videoParams);
        surfaceHolder = surfaceView.getHolder();
        // holder.setFixedSize(w, h)

        surfaceHolder.addCallback(this);
        mediaPlayer = new MediaPlayer();
        // mediaPlayer.setVideoScalingMode(MediaPlayer.);
        //为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型,SURFACE_TYPE_PUSH_BUFFERS
        //holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //下面开始实例化MediaPlayer对象
        mediaPlayer = new MediaPlayer();
        //mediaPlayer.setAudioSessionId(index);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnVideoSizeChangedListener(this);

        this.addView(surfaceView);
        Log.e(TAG, "+++++constract: " + dataSources + temp);
    }


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // 当Surface尺寸等参数改变时触发
        //Log.v("Surface Change:::", "surfaceChanged called");
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Toast.makeText(context, "surfaceCreated:ID" + playerIndex, Toast.LENGTH_SHORT).show();
        // 当SurfaceView中的Surface被创建的时候被调用
        //在这里我们指定MediaPlayer在当前的Surface中进行播放
        if (visibility == View.VISIBLE) {
            mediaPlayer.setDisplay(holder);
            isPlayerSetHandle = true;
            //   Toast.makeText(context, "开启：" + dataSources + ",isPause:" + isPause, Toast.LENGTH_SHORT).show();monitors   free   allocated memory
            if (!isPause) {
                setPerparePlay();
            }
        }
    }

    @Override
    public void setViewLayout(LayoutParams tparams) {
        this.tparams = tparams;
        surfaceView.refreshDrawableState();
        surfaceView.setLayoutParams(tparams);
    }

    @Override
    public LayoutParams getViewLayout() {
        return this.tparams;
    }

    /**
     * 准备播放
     */
    private void setPerparePlay() {
        //=======================开启MediaPlayer===================================
        mediaPlayer.reset();
        try {
            // mediaPlayer.seekTo(0);
            mediaPlayer.setDataSource(dataSources);
            mediaPlayer.prepareAsync();
            statue = STATUE_PLAY;
        } catch (IllegalArgumentException e) {
            //LogManager.writeLog(LogManager.ERROR, TAG, e);
        } catch (IllegalStateException e) {
            // LogManager.writeLog(LogManager.ERROR, TAG, e);
        } catch (IOException e) {
            //  LogManager.writeLog(LogManager.ERROR, TAG, e);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        //Log.v("Surface Destory:::", "surfaceDestroyed called");
        //Toast.makeText(context, "surfaceDestroyed:ID" + playerIndex, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
        // 当video大小改变时触发
        //这个方法在设置player的source后至少触发一次
        //Log.v("Video Size Change", "onVideoSizeChanged called");
        //Toast.makeText(context, "onVideoSizeChanged:ID" + playerIndex, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSeekComplete(MediaPlayer arg0) {
        // seek操作完成时触发
        //Log.v("Seek Completion", "onSeekComplete called");

    }

    @Override
    public void onPrepared(MediaPlayer player) {
        //Toast.makeText(context, "playerStart:ID" + playerIndex, Toast.LENGTH_SHORT).show();
        if (!isPause) {
            player.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.release();
                }
            });
        }
    }

    @Override
    public boolean onInfo(MediaPlayer player, int whatInfo, int extra) {
        // 当一些特定信息出现或者警告时触发
        switch (whatInfo) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                break;
        }
        return false;
    }

    @Override
    public boolean onError(MediaPlayer player, int whatError, int extra) {
        // Log.v("Play Error:::", "onError called");
        switch (whatError) {
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                //   Log.v("Play Error:::", "MEDIA_ERROR_SERVER_DIED");
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                //   Log.v("Play Error:::", "MEDIA_ERROR_UNKNOWN");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        // 当MediaPlayer播放完成后触发
        //Log.v("Play Over:::", "onComletion called");
        // 当SurfaceView中的Surface被创建的时候被调用
        //在指定了MediaPlayer播放的容器后，我们就可以使用prepare或者prepareAsync来准备播放了
        //Toast.makeText(context, "onCompletion:ID" + playerIndex, Toast.LENGTH_SHORT).show();
        //  isComplication = true;
       /* if (!isPause) {
            setPerparePlay();
        }*/
    }

    /**
     * 设置是否可视
     *
     * @param visibility
     */
    public void setVisibility(int visibility) {
        this.visibility = visibility;
        surfaceView.setVisibility(visibility);
    }


    @Override
    public void release() {
        //  LogManager.writeLog(5, TAG, "MyVideoPlayer============release" + temp);
        //Toast.makeText(context, "release:ID" + playerIndex, Toast.LENGTH_SHORT).show();
        // isComplication = false;
        //mediaPlayer.pause();
        Log.e(TAG, "+++++release: " + temp);
        if (isRelease) {
            return;
        }
        this.removeViewAt(0);
        try {
            mediaPlayer.setDisplay(null);
            if (null != mediaPlayer) {
                //  Log.e(TAG, "+++++release: "+dataSources );
                //    LogManager.writeLog(5, TAG, "视频释放资源" + dataSources);
                mediaPlayer.release();
            }
            mediaPlayer = null;
            surfaceView = null;
            surfaceHolder.removeCallback(this);
            surfaceHolder = null;
        } catch (Exception e) {
            //   LogManager.writeLog(LogManager.ERROR, TAG, e, true);
            e.printStackTrace();
        }
        if (this.getChildCount() > 1) {//有配图显示
            MyImageView myImageView = (MyImageView) this.getChildAt(0);
            this.removeViewAt(0);
            //Log.e(TAG, "release: "+TAG );
            myImageView.stopPlay();
            //  Log.e(TAG, "++++release:myImageView.release() " );
            //   LogManager.writeLog(5, TAG, "+++release:myImageView.release()回收资源配图");
            myImageView.release();
        }
        isRelease = true;
    }


    @Override
    public void init() {
        //      LogManager.writeLog(5, TAG, "MyVideoPlayer============init");
        isPause = false;
    }

    @Override
    public int startPlay() {
        Log.e(TAG, "+++startPlay:" + dataSources + "        "  +temp);
        //    LogManager.writeLog(5, TAG, "MyVideoPlayer============start" + dataSources + temp);
        if (isPause) {
            return -1;
        }
        setPerparePlay();
        return 0;
    }

    @Override
    public int pausePlay() {
        isPause = true;
        if (null != mediaPlayer) {
            mediaPlayer.pause();
        }
        // Toast.makeText(context, "暂停：" + dataSources, Toast.LENGTH_SHORT).show();
        return 0;
    }

    @Override
    public int resumePlay() {
        isPause = false;
        Log.e(TAG, "resumePlay: " + TAG);
        stopPlay();
      /*  mediaPlayer.seekTo(0);//暂停后恢复是从头开始放
        mediaPlayer.start();*/
        // setPerparePlay();
        //  Toast.makeText(context, "resume：" + dataSources, Toast.LENGTH_SHORT).show();
        return 0;
    }

    @Override
    public int stopPlay() {
        if (statue == STATUE_STOP) {
            return 0;
        }
        Log.e(TAG, "+++ stopPlay:" + dataSources + temp);
        //  LogManager.writeLog(5, TAG, "MyVideoPlayer============stop" + dataSources + temp + "mediaPlayer:" + mediaPlayer);
        try {
            if (null != mediaPlayer) {
                mediaPlayer.stop();
            }
        } catch (Exception e) {
            //   LogManager.writeLog(LogManager.ERROR, TAG, "video error");
            //  LogManager.writeLog(LogManager.ERROR, TAG, e);
        }
        //Toast.makeText(context, "Stop", Toast.LENGTH_SHORT).show();
        statue = STATUE_STOP;
        return 0;
    }
}
