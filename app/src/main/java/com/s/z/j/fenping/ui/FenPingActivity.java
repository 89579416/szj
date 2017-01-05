package com.s.z.j.fenping.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import com.s.z.j.R;
import com.s.z.j.utils.FileUtil;
import com.s.z.j.utils.L;
import com.s.z.j.widget.mediaplayer.MyVideoPlayer;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;

/**
 * 循环播放一个目录下的mp4视频
 * 播放SD卡根目录中的视频
 * Created by Administrator on 2016-10-21.
 */
@ContentView(R.layout.activity_fenping)
public class FenPingActivity extends BaseActivity {

    @ViewInject(R.id.media_start_btn)
    Button startBtn;//获取“播放”按钮

    @ViewInject(R.id.media_pause_btn)
    Button pauseBtn;//获取“暂停/继续”按钮

    @ViewInject(R.id.media_stop_btn)
    Button stopBtn;//获取“停止”按钮

    @ViewInject(R.id.media_player_framelayout)
    private FrameLayout lframerLayout;//显示播放视频的控件

    private boolean isPause = false;//是否暂停
    private ArrayList<String> data = new ArrayList<String>();//要播放的路径

    private MyVideoPlayer myVideoPlayer;

    @ViewInject(R.id.fenping_webview)
    private WebView webView;

    private File file;
    @Override
    public void initialize(Bundle savedInstanceState) {
        FileUtil.createFile("aabb");
        file = Environment.getExternalStorageDirectory();
        data = FileUtil.getFile(file);
//        data = FileUtil.myListFiles(file.getPath());
        initPlay();
        initWebView();
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVideoPlayer.setDataSources(data);
                myVideoPlayer.startPlay();
                lframerLayout.removeAllViewsInLayout();
                lframerLayout.addView(myVideoPlayer);
                pauseBtn.setEnabled(true);//“暂停/继续”按钮可用
                stopBtn.setEnabled(true);//"停止"按钮可用
                startBtn.setEnabled(false);//“播放”按钮不可用
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myVideoPlayer.isPlaying()) {
                    myVideoPlayer.pausePlay();//暂停播放
                    isPause = true;
                    ((Button) v).setText("继续");
                    startBtn.setEnabled(true);//“播放”按钮可用
                } else {
                    myVideoPlayer.startPlay();//继续播放
                    ((Button) v).setText("暂停");
                    isPause = false;
                    startBtn.setEnabled(false);//“播放”按钮不可用
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVideoPlayer.stopPlay();
                pauseBtn.setEnabled(false);//“暂停/继续”按钮不可用
                stopBtn.setEnabled(false);//“停止”按钮不可用
                startBtn.setEnabled(true);//“播放”按钮可用
            }
        });
    }

    public  void initPlay(){
        if(data.size()>0) {
            String path = data.get(0);
            Log.i("AAAA", "path=" + path);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            myVideoPlayer = new MyVideoPlayer(FenPingActivity.this, layoutParams, path);
        }else {
            L.i("没有获取到MP4文件");
        }
    }

    @Override
    protected void onDestroy() {
        myVideoPlayer.stopPlay();
        super.onDestroy();
    }
    /**
     * 初始化webView
     */
    private void initWebView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        String url1 = "http://wxgx.cpsino.com/gxlottery/my/gc.html";//无反应
        String url1 = "https://i.ys7.com/h5/qrcode/intro?qrId=aaa411ea8be842c5a68bc14c5ecb15ce";//无反应
        /**下面是方法1*/
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //支持缩放
        settings.setBlockNetworkImage(true);
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
//        webView.setWebViewClient(new MyWebViewClient());
        settings.setSupportZoom(true);//设定支持缩放
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        webView.getSettings().setDatabaseEnabled(true);
        // String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        //开启 Application Caches 功能
        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100) {
                    Log.i("AAAA", "已加载 " + newProgress + "%");
                }
            }
        });
        webView.addJavascriptInterface(new JsInteration(), "control");
        webView.loadUrl(url1);
    }

    /**
     * JS交互
     */
    public class JsInteration {
//        @JavascriptInterface
//        public void updateOrderList(String message) {
//        }
//
    }

    final class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("WebView", "onPageStarted");
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            Log.d("WebView", "onPageFinished ");
            super.onPageFinished(view, url);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(webView != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack();// 返回前一个页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
