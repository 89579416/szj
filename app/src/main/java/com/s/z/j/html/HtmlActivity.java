package com.s.z.j.html;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.s.z.j.R;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * webView显示SD卡里面的html网页
 * 网页里面显示一个视频和两张图片
 * html目录自己指定。
 * 新建了一个图片目录，一个视频目录
 * 测试结果--OK
 * Created by Administrator on 2017-01-17.
 */
@ContentView(R.layout.activity_html)
public class HtmlActivity extends BaseActivity {
    @ViewInject(R.id.html_webview)
    private WebView webView;

    private FrameLayout frameLayout = null;
    private WebChromeClient chromeClient = null;
    private View myView = null;
    private WebChromeClient.CustomViewCallback myCallBack = null;

    private String url = Environment.getExternalStorageDirectory() + "";
    @Override
    public void initialize(Bundle savedInstanceState) {
        frameLayout = (FrameLayout)findViewById(R.id.framelayout);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new MyWebviewCient());
        chromeClient = new MyChromeClient();
        webView.setWebChromeClient(chromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        final String USER_AGENT_STRING = webView.getSettings().getUserAgentString() + " Rong/2.0";
        webView.getSettings().setUserAgentString(USER_AGENT_STRING);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///" + url + "/html/index.html");// 测试播放本地html里面的视频
//        webView.loadUrl("http://dl.facsimilemedia.com/campaigns/kgyjnbZ8tlIQ6U08fTdDn7LHUPEyt70T.html");// 测试播放html里面的视频
//        webView.loadUrl("http://dl-facsimile.oss-cn-hangzhou.aliyuncs.com/campaigns/pU8QWagTLHBcrjcKexQaVoAAaCChcduB.html");
//        webView.loadUrl("http://dl.facsimilemedia.com/campaigns/SLH7DH3HIFw3bHpGbFickn98iG77BMnn.html");
//        webView.loadUrl("http://192.168.3.13:8080/itel/html/aa.html");
        if(savedInstanceState != null){

            webView.restoreState(savedInstanceState);
        }
        if(savedInstanceState != null){
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        if(webView == null){
            super.onBackPressed();
        }
        else{
            chromeClient.onHideCustomView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        webView.saveState(outState);
    }

    public void addJavaScriptMap(Object obj, String objName){
        webView.addJavascriptInterface(obj, objName);
    }

    public class MyWebviewCient extends WebViewClient{
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,
                                                          String url) {
            WebResourceResponse response = null;
            response = super.shouldInterceptRequest(view, url);
            return response;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            Log.d("dream", "***on page finished");
            webView.loadUrl("javascript:startPlay()");
        }

    }

    public class MyChromeClient extends WebChromeClient {

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if(webView != null){
                callback.onCustomViewHidden();
                return;
            }
            frameLayout.removeView(webView);
            frameLayout.addView(view);
            myView = view;
            myCallBack = callback;
        }

        @Override
        public void onHideCustomView() {
            if(webView == null){
                return;
            }
            frameLayout.removeView(webView);
            myView = null;
            frameLayout.addView(webView);
            myCallBack.onCustomViewHidden();
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            // TODO Auto-generated method stub
            return super.onConsoleMessage(consoleMessage);
        }
    }
}
