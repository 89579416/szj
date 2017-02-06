package com.s.z.j.ui;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.s.z.j.R;
import com.s.z.j.abcde.navigationdrawer.ui.NavigationdrawerActivity;
import com.s.z.j.choose_images.imageloader.ChooseImageMainActivity;
import com.s.z.j.danmu.DanMuActivity;
import com.s.z.j.fenping.FenPingActivity;
import com.s.z.j.fragment.weixin.WeiXinFragmentActivity;
import com.s.z.j.html.HtmlActivity;
import com.s.z.j.newUtils.AppUtils;
import com.s.z.j.photo_wall_falls_demo.PhotoWallFallsActivity;
import com.s.z.j.service.TopWindowService;
import com.s.z.j.ui.apppackage.SystemAppPackageNameActivity;
import com.s.z.j.ui.device.DeviceInfoActivity;
import com.s.z.j.ui.dialog.DialogActivity;
import com.s.z.j.ui.mediaplayer.MediaPlayerActivity;
import com.s.z.j.ui.nav.NavigationActivity;
import com.s.z.j.ui.photo.PhotographActivity;
import com.s.z.j.ui.qrcode.QrCodeActivity;
import com.s.z.j.ui.sdcard.SdcardUrlActivity;
import com.s.z.j.ui.slidingmenu.SlidingMainActivity;
import com.s.z.j.ui.videoplay.PlayActivity;
import com.s.z.j.ui.wifihost.SetWifiHostActivity;
import com.s.z.j.utils.FileUtil;
import com.s.z.j.utils.HttpUtils;
import com.s.z.j.utils.L;
import com.s.z.j.utils.SpeedUtil;
import com.s.z.j.utils.VibratorUtil;
import com.s.z.j.xuanfuchuang_360.Xuanfu360MainActivity;
import com.s.z.j.xuanfuchuang_qq.XuanFuQqMainActivity;
import com.squareup.picasso.Picasso;
import com.szj.library.ui.BaseActivity;
import com.szj.library.utils.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

/**
 * 程序主入口
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.main_get_device_info_btn)
    private Button getDeviceInfoBtn;
    /**
     * 获取网络
     */
    @ViewInject(R.id.main_get_net_type_btn)
    private Button getNetBtn;

    /**
     * 获取IP地址
     */
    @ViewInject(R.id.main_get_ip_btn)
    private Button getIpBtn;

    /**
     * 获取公网IP
     */
    @ViewInject(R.id.main_get_net_ip_btn)
    private Button getNetIpBtn;

    /**
     * 获取MAC地址
     */
    @ViewInject(R.id.main_get_mac_btn)
    private Button getMacBtn;

    /**
     * 通过URL获取bitmap显示图片
     */
    @ViewInject(R.id.main_show_image_by_bitmap_btn)
    private Button bitmapBtn;

    /**
     * 直接显示网格图片
     */
    @ViewInject(R.id.main_show_image_by_net_btn)
    private Button netBitmapBtn;

    /**
     * 显示图片的imageView
     */
    @ViewInject(R.id.main_show_pic_imageview)
    private ImageView picImageView;

    /**
     * 下载文件
     */
    @ViewInject(R.id.main_load_file_btn)
    private Button loadFileBtn;

    /**
     * 文件下载进度条
     */
    @ViewInject(R.id.main_load_file_progressBar)
    private ProgressBar loadProgressBar;

    /**
     * 截屏
     */
    @ViewInject(R.id.main_save_current_btn)
    private Button saveCurrentBtn;

    /**
     * 获取当前网速
     */
    @ViewInject(R.id.main_get_net_speed_btn)
    private Button getNetSpeedBtn;

    /**
     * 显示网速
     */
    @ViewInject(R.id.main_show_net_speed_textview)
    private TextView netSpeedTxt;

    /**
     * 动态注册广播
     */
    @ViewInject(R.id.main_broad_cast_btn)
    private Button broadCastBtn;

    /**
     * 测滑菜单
     */
    @ViewInject(R.id.main_sliding_menu_btn)
    private Button slidingMenuBtn;

    /**
     * 引导页面
     */
    @ViewInject(R.id.main_navigation_btn)
    private Button navigationBtn;

    /**
     * mediaPlayer播放视频
     */
    @ViewInject(R.id.main_media_player_btn)
    private Button medaiPlayerBtn;

    /**
     * 二维码生成与扫描
     */
    @ViewInject(R.id.main_qr_code_btn)
    private Button qrCodeBtn;

    /**
     * 修改头像
     */
    @ViewInject(R.id.main_update_head_btn)
    private Button updateHeadBtn;

    /**
     * 浮动窗口
     */
    @ViewInject(R.id.main_floating_window_btn)
    private Button floatWindowBtn;

    /**
     * 仿360手机卫士悬浮窗效果
     */
    @ViewInject(R.id.main_floating_window_360_btn)
    private Button floatWindow360Btn;


    /**
     * QQ手机管家小火箭效果实现
     */
    @ViewInject(R.id.main_floating_window_qq_btn)
    private Button floatWindowqqBtn;

    /**
     * 设置wifi
     */
    @ViewInject(R.id.main_wifi_btn)
    private Button setWifiBtn;

    /**
     *设置wifi热点
     */
    @ViewInject(R.id.main_wifi_host_btn)
    private Button setWifiHotBtn;

    /**
     * 获取包名
     */
    @ViewInject(R.id.main_get_package_name_btn)
    private Button getPackageNameBtn;

    /**
     *各式Dialog
     */
    @ViewInject(R.id.to_dialog_btn)
    private Button dialogBtn;

    /**
     * 获取SD卡目录和U盘目录
     */
    @ViewInject(R.id.main_gget_sdcard_url_btn)
    private Button getSdcardUrlBtn;

    /**
     * 未完成的侧滑菜单
     */
    @ViewInject(R.id.main_navigationdrawer_btn)
    private Button navigationdrawerBtn;

    /**
     * 分屏显示视频播放和网页显示
     */
    @ViewInject(R.id.main_fenping_btn)
    private Button fenpingBtn;

    /**.
     * 仿微信布局
     */
    @ViewInject(R.id.main_weixin_fragment)
    private Button weixinBtn;

    /**播放assets里面的视频*/
    @ViewInject(R.id.main_text_share_btn)
    private Button textShareBtn;

    /**
     * 播放本地图片和视频
     */
    @ViewInject(R.id.main_play_img_and_video_btn)
    private Button playBtn;

    /**
     * 图片滑动效果
     */
    @ViewInject(R.id.main_huadong_tupian_btn)
    private Button huadongImgBtn;

    /**播放本地html并播放视频*/
    @ViewInject(R.id.main_html_btn)
    private Button htmlBtn;

    /**模仿微信选择相册*/
    @ViewInject(R.id.main_choose_image_btn)
    private Button chooseImageBtn;

    /** 发送弹幕并显示*/
    @ViewInject(R.id.main_danmu_btn)
    private Button danmuBtn;

    /** Android照片墙加强版，使用ViewPager实现画廊效果Demo */
    @ViewInject(R.id.main_photo_wall_falls_btn)
    private Button photoWallFallsBtn;

    private Bitmap picBitmap;//通过url获取的bitmap
    private String picUrl = "http://gb.cri.cn/mmsource/images/2010/09/27/eo100927986.jpg";//直接显示图片地址
    private String bitmapUrl = "http://cdn.duitang.com/uploads/item/201408/28/20140828160017_wBrME.jpeg";//获取bitmap地址
    private String defaultPath;//SD卡路径
    private SpeedUtil speedUtil; //网络速度监测
    private String net_ip = "";

    @Override
    public void initialize(Bundle savedInstanceState) {
        createFile();
        getNetBtn.setOnClickListener(this);
        getMacBtn.setOnClickListener(this);
        getIpBtn.setOnClickListener(this);
        bitmapBtn.setOnClickListener(this);
        netBitmapBtn.setOnClickListener(this);
        loadFileBtn.setOnClickListener(this);
        saveCurrentBtn.setOnClickListener(this);
        getNetSpeedBtn.setOnClickListener(this);
        broadCastBtn.setOnClickListener(this);
        slidingMenuBtn.setOnClickListener(this);
        navigationBtn.setOnClickListener(this);
        medaiPlayerBtn.setOnClickListener(this);
        qrCodeBtn.setOnClickListener(this);
        updateHeadBtn.setOnClickListener(this);
        floatWindowBtn.setOnClickListener(this);
        setWifiBtn.setOnClickListener(this);
        getPackageNameBtn.setOnClickListener(this);
        setWifiHotBtn.setOnClickListener(this);
        getSdcardUrlBtn.setOnClickListener(this);
        getDeviceInfoBtn.setOnClickListener(this);
        dialogBtn.setOnClickListener(this);
        navigationdrawerBtn.setOnClickListener(this);
        fenpingBtn.setOnClickListener(this);
        weixinBtn.setOnClickListener(this);
        textShareBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        floatWindow360Btn.setOnClickListener(this);
        floatWindowqqBtn.setOnClickListener(this);
        huadongImgBtn.setOnClickListener(this);
        htmlBtn.setOnClickListener(this);
        getNetIpBtn.setOnClickListener(this);
        chooseImageBtn.setOnClickListener(this);
        danmuBtn.setOnClickListener(this);
        photoWallFallsBtn.setOnClickListener(this);
        speedUtil = new SpeedUtil(this, speedHandler, new Timer());
    }

    /**
     * 创建文件夹
     */
    public void createFile() {
        defaultPath = Environment.getExternalStorageDirectory() + "";
        FileUtil.makeRootDirectory(defaultPath + "/my_screen");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_get_net_type_btn:
                T.s(context, "当前网络类型：" + HttpUtils.getNetType(context));
                break;
            case R.id.main_get_mac_btn:
                T.s(context, "MAC=" + HttpUtils.getMacByWifiManager(context)[0] + "\nMAC=" + HttpUtils.getMacByFile());
                break;
            case R.id.about_version_code:
                getBitmapByUrl();
                break;
            case R.id.main_get_ip_btn:
                T.s(context, HttpUtils.getLocalIpAddress(context));
                break;
            case R.id.main_get_net_ip_btn:
                VibratorUtil.Vibrate(MainActivity.this,100);//点击按钮后震动
                new Thread(){
                    @Override
                    public void run() {
                        net_ip = HttpUtils.GetNetIp();
                        L.i("ip=" + net_ip);
                        handler.sendEmptyMessage(5);
                    }
                }.start();

                break;
            case R.id.main_show_image_by_bitmap_btn:
                if (picImageView.getVisibility() == View.GONE) {
                    picImageView.setVisibility(View.VISIBLE);
                    bitmapBtn.setText(R.string.yincang);
                    netBitmapBtn.setText(R.string.yincang);
                    getBitmapByUrl();
                } else {
                    bitmapBtn.setText(R.string.show_image_by_net_bitmap);
                    netBitmapBtn.setText(R.string.show_image_by_net_url);
                    picImageView.setVisibility(View.GONE);
                }
                break;
            case R.id.main_show_image_by_net_btn:
                if (picImageView.getVisibility() == View.GONE) {
                    picImageView.setVisibility(View.VISIBLE);
                    netBitmapBtn.setText(R.string.yincang);
                    bitmapBtn.setText(R.string.yincang);
                    Picasso.with(context).load(picUrl).into(picImageView);
                } else {
                    netBitmapBtn.setText(R.string.show_image_by_net_url);
                    bitmapBtn.setText(R.string.show_image_by_net_bitmap);
                    picImageView.setVisibility(View.GONE);
                }
                break;
            case R.id.main_load_file_btn:
                loadFile();
                break;
            case R.id.main_save_current_btn:
                saveScreen();
                break;
            case R.id.main_get_net_speed_btn:
                if ("开始获取当前网速".equals(getNetSpeedBtn.getText())) {
                    speedUtil.start();
                    getNetSpeedBtn.setText("停止");
                } else {
                    speedUtil.stop();
                    getNetSpeedBtn.setText("开始获取当前网速");
                }
                break;
            case R.id.main_broad_cast_btn:
                startActivityForResult(new Intent(context, BroadCastActivity.class), 1);
                break;
            case R.id.main_sliding_menu_btn:
                startActivity(new Intent(this, SlidingMainActivity.class));
                break;
            case R.id.main_navigation_btn:
                startActivity(new Intent(context, NavigationActivity.class));
                break;
            case R.id.main_media_player_btn:
                startActivity(new Intent(context, MediaPlayerActivity.class));
                break;
            case R.id.main_qr_code_btn:
                startActivity(new Intent(context, QrCodeActivity.class));
                break;
            case R.id.main_update_head_btn:
                startActivity(new Intent(context, PhotographActivity.class));
                break;
            case R.id.main_floating_window_btn:
                Intent show = new Intent(this, TopWindowService.class);
                show.putExtra(TopWindowService.OPERATION, TopWindowService.OPERATION_SHOW);
                startService(show);
                finish();
//                /** 以下代码是悬浮窗的应用  需要添加浮动窗口权限    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />  */
//                WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
//                WindowManager mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
//                wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;//PHONE级别，保证在最前方！
//                wmParams.format = PixelFormat.RGBA_8888;
//                wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                wmParams.gravity = Gravity.LEFT | Gravity.TOP;//位置：左上角
//                wmParams.x = 0;
//                wmParams.y = 0;
//                wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽度自适应
//                wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//高度自适应
//                LayoutInflater inflater = LayoutInflater.from(getApplication());
//                LinearLayout view = (LinearLayout) inflater.inflate(R.layout.dialog_floating_window, null);
//                TextView backTxt = (TextView) view.findViewById(R.id.floating_window_one_back_btn);
//                backTxt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        /** 点击事件，点击后返回桌面 */
//                        Intent intent = new Intent(Intent.ACTION_MAIN);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                    }
//                });
//                mWindowManager.addView(view, wmParams);//在窗口管理器上添加一个View
                break;
            case R.id.main_floating_window_360_btn:
                startActivity(new Intent(context, Xuanfu360MainActivity.class));
                break;
            case R.id.main_floating_window_qq_btn:
                startActivity(new Intent(context, XuanFuQqMainActivity.class));
                break;
            case R.id.main_wifi_btn:
                setWifi();
                break;
            case R.id.main_get_package_name_btn:
                startActivity(new Intent(context, SystemAppPackageNameActivity.class));
                break;
            case R.id.main_wifi_host_btn:
                    startActivity(new Intent(context,SetWifiHostActivity.class));
                break;
            case R.id.main_gget_sdcard_url_btn:
                startActivity(new Intent(context,SdcardUrlActivity.class));
                break;
            case R.id.main_get_device_info_btn:
                startActivity(new Intent(context,DeviceInfoActivity.class));
                break;
            case R.id.to_dialog_btn:
                startActivity(new Intent(context,DialogActivity.class));
                break;
            case R.id.main_navigationdrawer_btn:
                startActivity(new Intent(context,NavigationdrawerActivity.class));
                break;
            case R.id.main_fenping_btn:
                startActivity(new Intent(context,FenPingActivity.class));
                break;
            case R.id.main_weixin_fragment:
                startActivity(new Intent(context,WeiXinFragmentActivity.class));
                break;
            case R.id.main_play_img_and_video_btn:
                startActivity(new Intent(context, PlayActivity.class));
                break;
            case R.id.main_huadong_tupian_btn:
                startActivity(new Intent(context, com.s.z.j.tupianhuadong.MainActivity.class));
                break;
            case R.id.main_text_share_btn:
                AppUtils.shareAppInfo(context,"这是分享测试内容");
                doStartApplicationWithPackageName("com.liantuo.cashierdesk");//跳转到另一个APP
                break;
            case R.id.main_html_btn:
                startActivity(new Intent(context, HtmlActivity.class));
                break;
            case R.id.main_choose_image_btn:
                startActivity(new Intent(context, ChooseImageMainActivity.class));
                break;
            case R.id.main_danmu_btn:
                startActivity(new Intent(context, DanMuActivity.class));
                break;
            case R.id.main_photo_wall_falls_btn:
                startActivity(new Intent(context, PhotoWallFallsActivity.class));
                break;
            default:
                break;
        }
    }
    /**
     * 获取外网的IP(要访问Url，要放到后台线程里处理)
     *
     * @Title: GetNetIp
     * @Description:
     * @param @return
     * @return String
     * @throws
     */
    String GetNetIp(String ipaddr){
        URL infoUrl = null;
        InputStream inStream = null;
        try {
            infoUrl = new URL(ipaddr);
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
            int responseCode = httpConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");
                inStream.close();
                return strber.toString();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 通过包名获取类名
     * 然后跳转到另一个APP
     * @param packagename
     */
    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);
            L.i("className"+className);
            intent.setComponent(cn);
            if (intent != null) {
                startActivityForResult(intent, 111);
            } else {
                T.s(context,"打开快收银失败，有可能是没有安装快收银");
            }
        }
    }

    /**
     * 跳转到设置wifi页面
     */
    public void setWifi() {
        Intent wifiIntent = new Intent();
        wifiIntent.setAction("android.net.wifi.PICK_WIFI_NETWORK");//跳转到wifi设置页面
        wifiIntent.putExtra("extra_prefs_show_button_bar", true);//是否显示button bar,传递值为true的话是显示
        wifiIntent.putExtra("wifi_enable_next_on_connect", true);//是否打开网络连接检测功能（如果连上wifi，则下一步按钮可被点击）
        wifiIntent.putExtra("extra_prefs_set_next_text", "完成");//自定义按钮的名字，不传递的话，默认为下一步
        wifiIntent.putExtra("extra_prefs_set_back_text", "返回");//自定义按钮的名字，不传递的话，默认为上一步
        startActivity(wifiIntent);
    }

    /**
     * 下载文件
     */
    public void loadFile() {
        new Thread() {
            public void run() {
                try {
                    //下载文件，参数：第一个URL，第二个存放路径
                    HttpUtils.down_file(loadHandler, "http://dl.facsimilemedia.com/pos/android/newbeemall_pos_1.0.2.apk", Environment.getExternalStorageDirectory() + File.separator + "/ceshi/");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 截屏
     * 这种方法状态栏是空白，显示不了状态栏的信息
     * 文件命名方式   img_screen_yyyy_MM_dd_HH_mm_ss
     */
    private void saveScreen() {
        //获取当前屏幕的大小//回家看看获取控件的宽高试试呢
        int width = getWindow().getDecorView().getRootView().getWidth();
        int height = getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //找到当前页面的跟布局
        View view = getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = view.getDrawingCache();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        String fname = sdf.format(new Date()) + ".png";
        //输出到sd卡
        File file = new File(defaultPath + "/my_screen/img_screen_" + fname);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream foStream = new FileOutputStream(file);
            temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.flush();
            foStream.close();
            T.s(context, "保存成功" + file.getPath());
        } catch (Exception e) {
            T.s(context, "保存失败" + e.toString());
            Log.e("SZJ", e.toString());
        }
    }

    /**
     * 根据URL得到图片bitmap
     */
    public void getBitmapByUrl() {
        new Thread() {
            @Override
            public void run() {
                try {
                    picBitmap = HttpUtils.getHttpBitmap(bitmapUrl);
                    if (picBitmap != null) {
                        handler.sendEmptyMessage(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    L.i("handler==1");
                    picImageView.setImageBitmap(picBitmap);
                    break;
                case 2:
                    Bundle bundle = null;
                    bundle = msg.getData();//接受Bundle数据
                    T.s(context, bundle.getString("String"));
                    break;
                case 3:
                    bundle = msg.getData();//接受Bundle数据
                    T.s(context, bundle.getString("String"));
                    break;
                case 4:
                    bundle = msg.getData();//接受Bundle数据
//                    textView1.setText(bundle.getString("String"));
                    break;
                case 5:
                    T.s(context,"公网IP："+net_ip);
                    break;
            }
        }
    };

    /**
     * 用来接收线程发送来的文件下载量，进行UI界面的更新
     * 下载文件时，更新界面的handler
     */
    private Handler loadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {//定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
                        loadProgressBar.setMax(msg.arg2);
                    case 1:
                        loadProgressBar.setProgress(msg.arg1);
                        break;
                    case 2:
                        T.s(context, "文件下载完成");
                        break;
                    case -1:
                        T.s(context, msg.getData().getString("error"));
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

    private Handler speedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Log.i("AAAA", "当前网速：" + msg.obj);//更新UI
                    netSpeedTxt.setText("当前网速：" + msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.i("onActivityResult-->requestCode="+requestCode+"\tresultCode="+resultCode);
    }

}
