package com.s.z.j.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.s.z.j.R;
import com.s.z.j.utils.FileUtil;
import com.s.z.j.utils.HttpUtils;
import com.s.z.j.utils.L;
import com.squareup.picasso.Picasso;
import com.szj.library.ui.BaseActivity;
import com.szj.library.utils.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 程序主入口
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.main_get_net_type_btn)
    private Button getNetBtn;/**获取网络*/

    @ViewInject(R.id.main_get_ip_btn)
    private Button getIpBtn;/**获取IP地址*/

    @ViewInject(R.id.main_get_mac_btn)
    private Button getMacBtn;/**获取MAC地址*/

    @ViewInject(R.id.main_show_image_by_bitmap_btn)
    private Button bitmapBtn;/**通过URL获取bitmap显示图片*/

    @ViewInject(R.id.main_show_image_by_net_btn)
    private Button netBitmapBtn;/**直接显示网格图片*/

    @ViewInject(R.id.main_show_pic_imageview)
    private ImageView picImageView;/**显示图片的imageView*/

    @ViewInject(R.id.main_load_file_btn)
    private Button loadFileBtn;

    @ViewInject(R.id.main_load_file_progressBar)
    private ProgressBar loadProgressBar;

    @ViewInject(R.id.main_save_current_btn)
    private Button saveCurrentBtn;

    private Bitmap picBitmap;/**通过url获取的bitmap*/
    private String picUrl = "http://gb.cri.cn/mmsource/images/2010/09/27/eo100927986.jpg";//直接显示图片地址
    private String bitmapUrl = "http://cdn.duitang.com/uploads/item/201408/28/20140828160017_wBrME.jpeg";//获取bitmap地址
    private String defaultPath;

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
    }

    /**
     * 创建文件夹
     */
    public void createFile(){
        defaultPath = Environment.getExternalStorageDirectory() + "";
        FileUtil.makeRootDirectory(defaultPath + "/my_screen");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_get_net_type_btn:
                T.s(context, "当前网络类型："+HttpUtils.getNetType(context));
                break;
            case R.id.main_get_mac_btn:
                T.s(context,"MAC="+HttpUtils.getMacByWifiManager(context)+"\nMAC="+HttpUtils.getMacByFile());
                break;
            case R.id.about_version_code:
                getBitmapByUrl();
                break;
            case R.id.main_get_ip_btn:
                T.s(context,HttpUtils.getIpAddress());
                break;
            case R.id.main_show_image_by_bitmap_btn:
                if(picImageView.getVisibility()==View.GONE){
                    picImageView.setVisibility(View.VISIBLE);
                    bitmapBtn.setText(R.string.yincang);
                    netBitmapBtn.setText(R.string.yincang);
                    getBitmapByUrl();
                }else {
                    bitmapBtn.setText(R.string.show_image_by_net_bitmap);
                    netBitmapBtn.setText(R.string.show_image_by_net_url);
                    picImageView.setVisibility(View.GONE);
                }
                break;
            case R.id.main_show_image_by_net_btn:
                if(picImageView.getVisibility()==View.GONE){
                    picImageView.setVisibility(View.VISIBLE);
                    netBitmapBtn.setText(R.string.yincang);
                    bitmapBtn.setText(R.string.yincang);
                    Picasso.with(context).load(picUrl).into(picImageView);
                }else {
                    netBitmapBtn.setText(R.string.show_image_by_net_url);
                    bitmapBtn.setText(R.string.show_image_by_net_bitmap);
                    picImageView.setVisibility(View.GONE);
                }
                break;
            case R.id.main_load_file_btn:
                loadFile();
                break;
            case R.id.main_save_current_btn:
                saveCurrentImage();
                break;
            default:break;
        }
    }

    /**
     * 下载文件
     */
    public void loadFile(){
        new Thread() {
            public void run() {
                try {
                    //下载文件，参数：第一个URL，第二个存放路径
                    HttpUtils.down_file(load_handler, "http://dl.facsimilemedia.com/pos/android/newbeemall_pos_1.0.2.apk", Environment.getExternalStorageDirectory() + File.separator + "/ceshi/");
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
     */
    private void saveCurrentImage() {
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
        //输出到sd卡
        File file = new File(defaultPath+"/my_screen/screen02.jpg");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream foStream = new FileOutputStream(file);
            temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.flush();
            foStream.close();
            T.s(context,"保存成功"+file.getPath());
        } catch (Exception e) {
            T.s(context, "保存失败" + e.toString());
            Log.e("SZJ", e.toString());
        }
    }
    /**
     * 根据URL得到图片bitmap
     */
    public void getBitmapByUrl(){
        new Thread() {
            @Override
            public void run() {
                try {
                    picBitmap = HttpUtils.getusericon(new URL(bitmapUrl));
                    if (picBitmap != null) {
                        handler.sendEmptyMessage(1);
                    }
                } catch (MalformedURLException e) {
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
//                    textView1.setText("己下载：" + msg.obj + "%");
                    break;
            }
        }
    };

    /**
     * 用来接收线程发送来的文件下载量，进行UI界面的更新
     * 下载文件时，更新界面的handler
     */
    private Handler load_handler = new Handler() {
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
                        T.s(context,"文件下载完成");
                        break;
                    case -1:
                        T.s(context, msg.getData().getString("error"));
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };
}
