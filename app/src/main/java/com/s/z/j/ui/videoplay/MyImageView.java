package com.s.z.j.ui.videoplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * 作者：王强
 * 日期： 2015/10/8
 * 版本： V1.0
 * 说明：图片播放控件
 */
public class MyImageView extends ImageView implements Player {
    private FrameLayout.LayoutParams tparams;
    private String dataSources;
    private final String TAG = "MyImageView";
    public MyImageView(Context context, FrameLayout.LayoutParams tparams, String dataSources) {
        super(context);
        this.tparams = tparams;

        this.setLayoutParams(tparams);
        this.dataSources = dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

    public MyImageView(Context context, String dataSources) {
        super(context);
        this.dataSources = dataSources;
    }

    public String getDataSources() {
        return this.dataSources;
    }

    @Override
    public void init() {

    }

    @Override
    public int startPlay() {
        Log.i(TAG, "+++++startPlay: 开始播放Image" +dataSources);

        this.setScaleType(ScaleType.FIT_XY);//改变图片的大小
        Bitmap bmp = BitmapFactory.decodeFile(dataSources);
        this.setDrawingCacheEnabled(true);
        this.setImageBitmap(bmp);
        return 0;
    }

    @Override
    public int pausePlay() {
        return 0;
    }

    @Override
    public int stopPlay() {
        return 0;
    }

    @Override
    public int resumePlay() {
        return 0;
    }

    @Override
    public void release() {
        try {
            Bitmap bitmap = this.getDrawingCache();
            if (null != bitmap) {
                if (!bitmap.isRecycled()) {
                    //Log.e(TAG, "++++release:!bitmap.isRecycled()进入资源回收" +dataSources);
                    bitmap.recycle();

                }
            } else {
               // Log.e(TAG, "++++release: 82027======== 图片资源回收"+dataSources );
             //   LogManager.writeLog(LogManager.ERROR, TAG, PlayErrorCode.RELEASE_ERROR);
            }
            this.setDrawingCacheEnabled(false);
            this.setImageBitmap(null);
        } catch (Exception e) {
          //  LogManager.writeLog(LogManager.ERROR, TAG, e, true);
        }
    }

    /**
     * 设置布局
     *
     * @param tparams
     */
    public void setViewLayout(FrameLayout.LayoutParams tparams) {
        this.tparams = tparams;
        this.setLayoutParams(tparams);
    }

    /**
     * 获得布局
     *
     * @return
     */
    public FrameLayout.LayoutParams getViewLayout() {
        return this.tparams;
    }
}
