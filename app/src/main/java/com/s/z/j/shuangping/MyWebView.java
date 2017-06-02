package com.s.z.j.shuangping;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.s.z.j.ui.videoplay.Player;


/**
 * Created by Administrator on 2016/10/8.
 * 作者：朱山川
 * 版本：1.1.3
 * 说明：网页播放控件
 */
public class MyWebView extends WebView implements Player {
    private FrameLayout.LayoutParams tparams;
    private int refreshTime;//页面刷新时间
    private final String TAG = "MyWebView";
    private String url=null;
    public MyWebView(Context context, FrameLayout.LayoutParams tparams , String url){
        super(context);
        this.tparams=tparams;
        this.url=url;
        initMyWebView(this);
    }



    @Override
    public void init() {
        //Log.e(TAG,"WebView=======startPlay():");
    }

    @Override
    public void release() {
        this.loadUrl("");
        //  Log.e(TAG,"WebView=======release()");
    }

    @Override
    public int startPlay() {
        //设置刷新
        if (url!=null){
            this.loadUrl(url);
        }
        //   Log.e(TAG,"WebView=======startPlay()"+url);
        return 0;
    }

    @Override
    public int pausePlay() {
        return 0;
    }

    @Override
    public int resumePlay() {
        return 0;
    }

    @Override
    public int stopPlay() {
        //Log.e(TAG,"WebView=======stopPlay()");
        return 0;
    }

    @Override
    public void setViewLayout(FrameLayout.LayoutParams tparams) {
        this.tparams=tparams;
        this.setLayoutParams(tparams);
    }

    @Override
    public FrameLayout.LayoutParams getViewLayout() {
        return this.tparams;
    }
    private void initMyWebView(WebView webView) {
        /**下面是方法1*/
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //h5页面的缓存
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //支持缩放
        settings.setBlockNetworkImage(true);
        settings.setJavaScriptEnabled(true);//启用js
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setBlockNetworkImage(false);//解决图片不显示
        settings.setLoadsImagesAutomatically(true);//加载图片
        settings.setSupportZoom(true);//设定支持缩放
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //开启 database storage API 功能
        webView.getSettings().setDatabaseEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setDefaultTextEncodingName("UTF-8");
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //Log.i("AAAA", "已加载 " + newProgress + "%");
            }
        });

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
            //  view.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
            view.loadUrl("javascript:startPlay()");
        }
    }

}
