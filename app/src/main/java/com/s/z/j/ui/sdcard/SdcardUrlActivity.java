package com.s.z.j.ui.sdcard;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.s.z.j.R;
import com.s.z.j.utils.FileUtil;
import com.s.z.j.utils.L;
import com.s.z.j.utils.T;
import com.s.z.j.widget.mediaplayer.MyVideoPlayer;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * 安卓设备下
 * 获取内置SD卡路径
 * 获取外置U盘路径
 * Created by Administrator on 2016-11-18.
 */

@ContentView(R.layout.activity_get_sdcard_path)
public class SdcardUrlActivity extends BaseActivity {

    private Context context;

    @ViewInject(R.id.media_sd_start_btn)
    Button startSdcardBtn;//获取“播放”按钮

    @ViewInject(R.id.media_usb_start_btn)
    Button startUsbBtn;//获取“播放”按钮

    @ViewInject(R.id.media_assets_btn)
    Button assetsBtn;//

    @ViewInject(R.id.media_stop_btn)
    Button stopBtn;//获取“停止”按钮

    @ViewInject(R.id.media_player_hint)
    private TextView hint;//声明显示提示信息的文本框

    @ViewInject(R.id.media_player_framelayout)
    private FrameLayout lframerLayout;//显示播放视频的控件

    private boolean isPause = false;//是否暂停
    private String defaultPath;//SD卡路径
    private String usbPath;

    private ArrayList<String> sdcardData = new ArrayList<String>();//要播放的路径
    private ArrayList<String> usbData = new ArrayList<String>();//要播放的路径
    private ArrayList<String> assetsData = new ArrayList<String>();//要播放的路径

    private MyVideoPlayer myVideoPlayer;

    private TextView sdCardTxt;//内置SD卡地址显示

    private TextView usbPathTxt;//usb插口地址显示

    String sd_path = "";//内存卡地址
    String usb_path = "";//usb插口地址
    File file;

    @Override
    public void initialize(Bundle savedInstanceState) {
        context = this;
        FileUtil.createFile("aabb");
        defaultPath = Environment.getExternalStorageDirectory() + "/aabb";

        sdCardTxt = (TextView) findViewById(R.id.sdcard_path);
        usbPathTxt = (TextView) findViewById(R.id.usb_path);
        getPath2();
        sdCardTxt.setText(sd_path);
        usbPathTxt.setText(usb_path);
        /**
         * 获取assets中的视频转为流
         * 流转为文件保存在SD卡
         */
        InputStream is = null;
        try {
            is = context.getAssets().open("a1.mp4");
            file = new File(defaultPath + "/a2.mp4");
            inputstreamtofile(is, file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        startSdcardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultPath = Environment.getExternalStorageDirectory() + "/aabb";
                sdcardData = FileUtil.getPlayFile(defaultPath);
                initPlay();
                myVideoPlayer.setDataSources(sdcardData);
                myVideoPlayer.startPlay();
                lframerLayout.removeAllViewsInLayout();
                lframerLayout.addView(myVideoPlayer);
                assetsBtn.setEnabled(true);//“暂停/继续”按钮可用
                stopBtn.setEnabled(true);//"停止"按钮可用
                startSdcardBtn.setEnabled(false);//“播放”按钮不可用
            }
        });
        startUsbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usbPath = getPath2();
                usbData = FileUtil.getPlayFile(usbPath);
                if (usbData == null || usbData.size() < 0) {
                    T.s(context, "没有发现USB设备");
                } else {
                    initUsbPlay();
                    myVideoPlayer.setDataSources(usbData);
                    myVideoPlayer.startPlay();
                    lframerLayout.removeAllViewsInLayout();
                    lframerLayout.addView(myVideoPlayer);
                    assetsBtn.setEnabled(true);//“暂停/继续”按钮可用
                    stopBtn.setEnabled(true);//"停止"按钮可用
                    startUsbBtn.setEnabled(false);//“播放”按钮不可用
                }
            }
        });
        assetsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetsData.add(file.getPath());
                initAssetsPlay();
                myVideoPlayer.setDataSources(assetsData);
                myVideoPlayer.startPlay();
                lframerLayout.removeAllViewsInLayout();
                lframerLayout.addView(myVideoPlayer);
                stopBtn.setEnabled(true);//"停止"按钮可用
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVideoPlayer.stopPlay();
                hint.setText("停止播放...");
                assetsBtn.setEnabled(false);//“暂停/继续”按钮不可用
                stopBtn.setEnabled(false);//“停止”按钮不可用
                startSdcardBtn.setEnabled(true);//“播放”按钮可用
                startUsbBtn.setEnabled(true);//“播放”按钮可用
            }
        });

    }

    /**
     * 输入流转文件
     * @param ins
     * @param file
     */
    public static void inputstreamtofile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SdCardPath")
    public String getPath2() {
        sd_path = Environment.getExternalStorageDirectory().getAbsolutePath();
        L.i("sd_path=" + sd_path);
        if (sd_path.endsWith("/")) {
            sd_path = sd_path.substring(0, sd_path.length() - 1);
        }
        // 得到路径
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_path.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        usb_path = columns[1];
                    }
                } else if (line.contains("fuse") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_path.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        usb_path = columns[1];
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        L.i("usb_path=" + usb_path);
        return usb_path;
    }

    public void initPlay() {
        String path = sdcardData.get(0);
        Log.i("AAAA", "path=" + path);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        myVideoPlayer = new MyVideoPlayer(context, layoutParams, path);
    }

    public void initUsbPlay() {
        String path = usbData.get(0);
        Log.i("AAAA", "path=" + path);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        myVideoPlayer = new MyVideoPlayer(context, layoutParams, path);
    }

    public void initAssetsPlay() {
        String path = assetsData.get(0);
        Log.i("AAAA", "path=" + path);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        myVideoPlayer = new MyVideoPlayer(context, layoutParams, path);
    }

    @Override
    protected void onDestroy() {
        myVideoPlayer.stopPlay();
        super.onDestroy();

    }
}