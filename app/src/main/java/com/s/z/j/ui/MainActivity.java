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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.s.z.j.entity.Menu;
import com.s.z.j.fenping.FenPingActivity;
import com.s.z.j.fragment.weixin.WeiXinFragmentActivity;
import com.s.z.j.html.HtmlActivity;
import com.s.z.j.newUtils.AppUtils;
import com.s.z.j.photo_wall_falls_demo.PhotoWallFallsActivity;
import com.s.z.j.service.TopWindowService;
import com.s.z.j.test.TestMyEdittextActivity;
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
import com.szj.library.widget.recyclerview.MTFEndlessRecyclerOnScrollListener;
import com.szj.library.widget.recyclerview.MTFLoadingFooter;
import com.szj.library.widget.recyclerview.MTFRecyclerViewAdapterWrapper;
import com.szj.library.widget.recyclerview.MTFRecyclerViewStateUtils;
import com.szj.library.widget.recyclerview.MTFRecyclerViewUtils;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 程序主入口
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @ViewInject(R.id.fragment_home_recyclerView_refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @ViewInject(R.id.fragment_home_recyclerView)
    RecyclerView recyclerView;

    private ArrayList<Menu> commentdata = new ArrayList<>();//菜单
    private final int PAGE_COUNT = 33;//总大小
    private final int PAGE_SIZE  = 10;//每页大小
    private MTFRecyclerViewAdapterWrapper adapterWrapper;
    private MyAdapter adapter;
    private LayoutInflater mLayoutInflater;
    private GridLayoutManager gridLayoutManager;

    /**
     * 显示图片的imageView
     */
    @ViewInject(R.id.main_show_pic_imageview)
    private ImageView picImageView;

    /**
     * 文件下载进度条
     */
    @ViewInject(R.id.main_load_file_progressBar)
    private ProgressBar loadProgressBar;

    /**
     * 显示网速
     */
    @ViewInject(R.id.main_show_net_speed_textview)
    private TextView netSpeedTxt;


    private Bitmap picBitmap;//通过url获取的bitmap
    private String picUrl = "http://gb.cri.cn/mmsource/images/2010/09/27/eo100927986.jpg";//直接显示图片地址
    private String bitmapUrl = "http://cdn.duitang.com/uploads/item/201408/28/20140828160017_wBrME.jpeg";//获取bitmap地址
    private String defaultPath;//SD卡路径
    private SpeedUtil speedUtil; //网络速度监测
    private String localIp = "";//本地IP
    private String net_ip = "";//公网IP
    private boolean isSpeed;//是否在获取网速

    @Override
    public void initialize(Bundle savedInstanceState) {
        createFile();
        speedUtil = new SpeedUtil(this, speedHandler, new Timer());
        commentdata.add(new Menu(1, "获取设备信息"));
        commentdata.add(new Menu(2,"获取网络类型"));
        commentdata.add(new Menu(3,"获取本地ip地址和公网IP"));
        commentdata.add(new Menu(4,"获取MAC"));
        commentdata.add(new Menu(5,"通过URL获取bitmap图像"));
        commentdata.add(new Menu(6,"通过第三方工具显示网络图片"));
        commentdata.add(new Menu(7,"下载文件"));
        commentdata.add(new Menu(8,"截屏"));
        commentdata.add(new Menu(9,"开始获取当前网速"));
        commentdata.add(new Menu(10,"动态注册广播"));
        commentdata.add(new Menu(11,"侧划菜单演示"));
        commentdata.add(new Menu(12,"引导页面"));
        commentdata.add(new Menu(13,"MediaPlayer播放视频"));
        commentdata.add(new Menu(14,"二维码生成与扫描"));
        commentdata.add(new Menu(15,"1.浮动窗口"));
        commentdata.add(new Menu(16,"2.仿360手机卫士悬浮窗效果"));
        commentdata.add(new Menu(17,"3.QQ手机管家小火箭效果实现"));
        commentdata.add(new Menu(18,"连接wifi"));
        commentdata.add(new Menu(19,"设置wifi热点"));
        commentdata.add(new Menu(20,"获取系统里所有程序包名"));
        commentdata.add(new Menu(21,"获取内存目录和SD卡目录"));
        commentdata.add(new Menu(22,"跳转到Dialog页面"));
        commentdata.add(new Menu(23,"Android导航抽屉-Navigation Drawer"));
        commentdata.add(new Menu(24,"分屏显示广告和网页"));
        commentdata.add(new Menu(25,"仿微信布局"));
        commentdata.add(new Menu(26,"文字分享"));
        commentdata.add(new Menu(27,"播放一个目录下的视频和图片"));
        commentdata.add(new Menu(28,"左右滑动图片+圆点效果"));
        commentdata.add(new Menu(29,"打开本地html并播放本地视频"));
        commentdata.add(new Menu(30,"仿微信从相册选择相片"));
        commentdata.add(new Menu(31,"播放视频时发送弹幕"));
        commentdata.add(new Menu(32,"图片瀑布流查看"));
        commentdata.add(new Menu(33,"修改头像"));
        commentdata.add(new Menu(34,"自定义Edittext测试"));

        mLayoutInflater = LayoutInflater.from(context);
        adapter = new MyAdapter();
        adapterWrapper = new MTFRecyclerViewAdapterWrapper(adapter);
        recyclerView.setAdapter(adapterWrapper);
        /** 1.设置线性布局 */
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        /** 2.设置GridLayout布局 */
//        gridLayoutManager = new GridLayoutManager(context, 3);//几列或几行
//        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);//横向还是纵向
//        recyclerView.setLayoutManager(gridLayoutManager);

        /**添加下拉刷新监听*/
        refreshLayout.setOnRefreshListener(this);
        /**这2句是添加headView*/
//        View headerView = LayoutInflater.from(context).inflate(R.layout.test_recyclerview_header, null, false);
//        MTFRecyclerViewUtils.setHeaderView(recyclerView, headerView);
        /**添加上拉加载监听*/
        recyclerView.addOnScrollListener(mOnScrollListener);
    }

    /**
     * 上拉加载处理
     */
    private MTFEndlessRecyclerOnScrollListener mOnScrollListener = new MTFEndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            MTFLoadingFooter.State state = MTFRecyclerViewStateUtils.getFooterViewState(recyclerView);
            if(state == MTFLoadingFooter.State.Loading) {
                return;
            }

            if (recyclerView.getAdapter().getItemCount() < PAGE_COUNT) {
                // 当前数据小于总大小时，显示加载中并且去加载，加载完成后消失
                MTFRecyclerViewStateUtils.setFooterViewState(activity, recyclerView, PAGE_SIZE, MTFLoadingFooter.State.Loading, null);
                requestData();
            } else {
                // 当前数据大于等于总大小时，显示已经到底了
                MTFRecyclerViewStateUtils.setFooterViewState(activity, recyclerView, PAGE_SIZE, MTFLoadingFooter.State.TheEnd, null);
            }
        }
    };

    private void requestData() {
        handler.sendEmptyMessageDelayed(0, 1500);
    }
    /**
     * 创建文件夹
     */
    public void createFile() {
        defaultPath = Environment.getExternalStorageDirectory() + "";
        FileUtil.makeRootDirectory(defaultPath + "/my_screen");
    }

    /**
     * 处理点击事件
     * @param position
     */
    public void onMyClick(int position) {
        switch (position) {
            case 1:
                startActivity(new Intent(context, DeviceInfoActivity.class));
                break;
            case 2:
                T.s(context, "当前网络类型：" + HttpUtils.getNetType(context));
                break;
            case 3:
                localIp = HttpUtils.getLocalIpAddress(context);//本地IP
                VibratorUtil.Vibrate(MainActivity.this,100);//点击按钮后震动
                new Thread(){
                    @Override
                    public void run() {
                        net_ip = HttpUtils.GetNetIp();//公网IP
                        L.i("ip=" + net_ip);
                        handler.sendEmptyMessage(5);
                    }
                }.start();

                break;
            case 4:
                T.s(context, "MAC=" + HttpUtils.getMacByWifiManager(context)[0] + "\nMAC=" + HttpUtils.getMacByFile());
                break;
            case R.id.about_version_code:
                getBitmapByUrl(1);
                break;
            case 5:
                getBitmapByUrl(1);
                break;
            case 6:
                getBitmapByUrl(2);
                break;
            case 7:
                loadFile();
                break;
            case 8:
                saveScreen();
                break;
            case 9:
                if(netSpeedTxt.getVisibility()==View.GONE) {
                    netSpeedTxt.setVisibility(View.VISIBLE);
                    if(isSpeed == false) {
                        speedUtil.start();
                        isSpeed = true;
                    }
                }else {
                    netSpeedTxt.setVisibility(View.GONE);
                }
                break;
            case 10:
                startActivityForResult(new Intent(context, BroadCastActivity.class), 1);
                break;
            case 11:
                startActivity(new Intent(this, SlidingMainActivity.class));
                break;
            case 12:
                startActivity(new Intent(context, NavigationActivity.class));
                break;
            case 13:
                startActivity(new Intent(context, MediaPlayerActivity.class));
                break;
            case 14:
                startActivity(new Intent(context, QrCodeActivity.class));
                break;
            case 15:
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
            case 16:
                startActivity(new Intent(context, Xuanfu360MainActivity.class));
                break;
            case 17:
                startActivity(new Intent(context, XuanFuQqMainActivity.class));
                break;
            case 18:
                setWifi();
                break;
            case 19:
                startActivity(new Intent(context, SetWifiHostActivity.class));
                break;
            case 20:
                startActivity(new Intent(context, SystemAppPackageNameActivity.class));
                break;
            case 21:
                startActivity(new Intent(context,SdcardUrlActivity.class));
                break;
            case 22:
                startActivity(new Intent(context,DialogActivity.class));
                break;
            case 23:
                startActivity(new Intent(context,NavigationdrawerActivity.class));
                break;
            case 24:
                startActivity(new Intent(context,FenPingActivity.class));
                break;
            case 25:
                startActivity(new Intent(context,WeiXinFragmentActivity.class));
                break;
            case 26:
                AppUtils.shareAppInfo(context, "这是分享测试内容");
//                doStartApplicationWithPackageName("com.liantuo.cashierdesk");//跳转到另一个APP
                break;
            case 27:
                startActivity(new Intent(context, PlayActivity.class));
                break;
            case 28:
                startActivity(new Intent(context, com.s.z.j.tupianhuadong.MainActivity.class));
                break;
            case 29:
                startActivity(new Intent(context, HtmlActivity.class));
                break;
            case 30:
                startActivity(new Intent(context, ChooseImageMainActivity.class));
                break;
            case 31:
                startActivity(new Intent(context, DanMuActivity.class));
                break;
            case 32:
                startActivity(new Intent(context, PhotoWallFallsActivity.class));
                break;
            case 33:
                startActivity(new Intent(context, PhotographActivity.class));
                break;
            case 34:
                startActivity(new Intent(context, TestMyEdittextActivity.class));
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
    public void getBitmapByUrl(final int type) {
        if(picImageView.getVisibility()==View.GONE){
            picImageView.setVisibility(View.VISIBLE);
            if(type == 1) {
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
            }else {
                Picasso.with(context).load(picUrl).into(picImageView);
            }
        }else {
            picImageView.setVisibility(View.GONE);
        }
    }
    /**
     * 根据URL得到图片bitmap
     */
    public void getBitmapByUrl() {
        if(picImageView.getVisibility()==View.GONE){
            picImageView.setVisibility(View.VISIBLE);
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
        }else {
            picImageView.setVisibility(View.GONE);
        }
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    for (int i = 11; i < 21; i++) {
                        commentdata.add(new Menu(i,"西游"+i+"记"));
                    }
                    adapter.notifyDataSetChanged();
                    MTFRecyclerViewStateUtils.setFooterViewState(recyclerView, MTFLoadingFooter.State.Normal);
                    break;
                case 1:
                    picImageView.setImageBitmap(picBitmap);
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
                    T.s(context,"本地Ip"+localIp+"\n公网IP："+net_ip);
                    break;
                case 6:
                    refreshLayout.setRefreshing(false);
                    MTFRecyclerViewStateUtils.setFooterViewState(recyclerView, MTFLoadingFooter.State.Normal);
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
                        loadProgressBar.setVisibility(View.VISIBLE);
                        loadProgressBar.setProgress(msg.arg1);
                        break;
                    case 2:
                        T.s(context, "文件下载完成");
                        loadProgressBar.setVisibility(View.GONE);
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

    @Override
    public void onRefresh() {
        T.s(context,"刷新了");
        handler.sendEmptyMessageDelayed(6,1000);
//        refreshLayout.setRefreshing(false);
//        MTFRecyclerViewStateUtils.setFooterViewState(recyclerView, MTFLoadingFooter.State.Normal);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MTFViewHolder> {

        @Override
        public MTFViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MTFViewHolder(mLayoutInflater.inflate(R.layout.item_main, parent, false));
        }

        @Override
        public void onBindViewHolder(MTFViewHolder holder, final int position) {
            Menu menu = commentdata.get(position);
            holder.nameTextView.setText(menu.getName());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMyClick(1+position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return commentdata.size();
        }

        public class MTFViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.item_main_name_textivew)
            TextView nameTextView;

            @Bind(R.id.item_main_layout)
            LinearLayout layout;

            public MTFViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
