package com.szj.library.imagetovideo;//package com.szj.library.imagetovideo;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Handler;
//
//import com.googlecode.javacv.FFmpegFrameRecorder;
//import com.googlecode.javacv.cpp.opencv_core;
//import com.szj.library.utils.L;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//
//import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
///**
// * 图片转视频类
// */
//public class VideoCapture {
//    private static int switcher = 0;//录像键
//    private static boolean isPaused = false;//暂停键
//    private static Context context;
//
//    /**
//     * 图片转 mp4 视频
//     * 要用到
//     * @param context  上下文
//     * @param handler  用于返回是否成功的信息
//     * @param imagePathData  图片集合，包含路径和时间
//     * @param filepath  生成后文件存放路径
//     * @param fileName  生成后的文件名
//     * @param width     生成后的视频宽度
//     * @param height    生成后的视频高度
//     */
//    public static void start(Context context, final Handler handler, final ArrayList<Images> imagePathData, final String filepath, final String fileName, final int width, final int height) {
//        //init
//        VideoCapture.context = context;
//        switcher = 1;
//        new Thread() {
//            public void run() {
//                OutputStream os = null;
//                try {
//                    FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(filepath + File.separator+ fileName, width, height);
//                    recorder.setFormat("mp4");
//                    recorder.setFrameRate(2f);//录像帧率
//                    recorder.start();
//                    if (!isPaused) {
//                        L.i("文件个数：" + imagePathData.size());
//                        for (int s = 0;s<imagePathData.size();s++) {
//                            L.i("当前是第几张图：----"+s);
//                            opencv_core.IplImage image = cvLoadImage(imagePathData.get(s).getPath());
//                            for (int i = 0; i < imagePathData.get(s).getTime() * 2; i++) {
//                                L.i("第"+(0+i)+"次添加图片"+imagePathData.get(s));
//                                recorder.record(image);
//                            }
//                        }
//                        L.i("recorder.stop");
//                        recorder.stop();
//                        handler.sendEmptyMessage(0);
//                    }
//
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                    handler.sendEmptyMessage(-1);
//                }
//            }
//        }.start();
//    }
//
//    public static void stop() {
//        L.i("停止了");
//        switcher = 0;
//        isPaused = false;
//    }
//
//    public static void pause() {
//        L.i("暂停了");
//        if (switcher == 1) {
//            isPaused = true;
//        }
//    }
//
//    public static void restart() {
//        L.i("重新开始了");
//        if (switcher == 1) {
//            isPaused = false;
//        }
//    }
//
//    public static boolean isStarted() {
//        if (switcher == 1) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static boolean isPaused() {
//        return isPaused;
//    }
//
//    private static Bitmap getImageFromAssetsFile(String filename) {
//        Bitmap image = null;
//        AssetManager am = context.getResources().getAssets();
//        try {
//            InputStream is = am.open(filename);
//            image = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return image;
//    }
//}
