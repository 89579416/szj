package com.s.z.j.ui.device;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.widget.TextView;

import com.s.z.j.R;
import com.s.z.j.entity.Info;
import com.s.z.j.utils.FileSizeUtil;
import com.s.z.j.utils.FileUtil;
import com.s.z.j.utils.GetInfoUtil;
import com.s.z.j.utils.L;
import com.szj.library.ui.BaseActivity;
import com.szj.library.utils.DialogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * 设备信息
 * Created by Administrator on 2016-11-18.
 */
@ContentView(R.layout.activity_device_info)
public class DeviceInfoActivity extends BaseActivity {

    public Info info;
    public GetInfoUtil getInfoUtil;

    @ViewInject(R.id.device_info_message_tv)
    private TextView messageTxt;

    @ViewInject(R.id.device_cache_size_tv)
    private TextView sizeTxt;

    boolean a = false;
    private Activity activity;

    @Override
    public void initialize(Bundle savedInstanceState) {
        activity = this;
        DialogUtils.show(activity, "正在处理...");
        info = new Info();
        testGetPath();
        getSize();
        getInfoUtil = new GetInfoUtil(this);
        new Thread() {
            @Override
            public void run() {
                info = getInfoUtil.GetInfo();
                while (a == false) {
                    if (info == null) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        a = true;
                        handler_new.sendEmptyMessage(1);
                        break;
                    }
                }
            }
        }.start();
    }

    /**
     * 获取种内存大小
     */
    public void getSize(){
        StringBuilder str = new StringBuilder();
        str.append("SD卡是否存在："+FileSizeUtil.externalMemoryAvailable());
        str.append("\n内部存储总空间："+FileSizeUtil.formatFileSize(FileSizeUtil.getTotalInternalMemorySize(), false));
        str.append("\n内部存储剩余空间："+FileSizeUtil.formatFileSize(FileSizeUtil.getAvailableInternalMemorySize(), false));
        str.append("\nSDCARD总存储空间："+FileSizeUtil.formatFileSize(FileSizeUtil.getTotalExternalMemorySize(), false));
        str.append("\nSDCARD剩余存储空间：" + FileSizeUtil.formatFileSize(FileSizeUtil.getAvailableExternalMemorySize(), false));
        str.append("\n系统总内存：" + FileSizeUtil.formatFileSize(FileSizeUtil.getTotalMemorySize(getApplication()), false));
        str.append("\n当前可用内存：" + FileSizeUtil.formatFileSize(FileSizeUtil.getAvailableMemory(getApplication()), false));
        if(ExistSDCard()){
            str.append("\nSD总内存:"+getSDFreeSize()+"\nSD卡剩余空间:"+getSDAllSize());
        }else {

        }
        StringBuffer sb = new StringBuffer();
        sb.append("主板：" + Build.BOARD);
        sb.append("\n系统启动程序版本号：" + Build.BOOTLOADER);
        sb.append("\n系统定制商：" + Build.BRAND);
        sb.append("\ncpu指令集：" + Build.CPU_ABI);
        sb.append("\ncpu指令集2" + Build.CPU_ABI2);
        sb.append("\n设置参数：" + Build.DEVICE);
        sb.append("\n显示屏参数：" + Build.DISPLAY);
        sb.append("\n无线电固件版本：" + Build.getRadioVersion());
        sb.append("\n硬件识别码：" + Build.FINGERPRINT);
        sb.append("\n硬件名称：" + Build.HARDWARE);
        sb.append("\nHOST:" + Build.HOST);
        sb.append("\n修订版本列表：" + Build.ID);
        sb.append("\n硬件制造商：" + Build.MANUFACTURER);
        sb.append("\n版本：" + Build.MODEL);
        sb.append("\n硬件序列号：" + Build.SERIAL);
        sb.append("\n手机制造商：" + Build.PRODUCT);
        sb.append("\n描述Build的标签：" + Build.TAGS);
        sb.append("\nTIME:" + Build.TIME);
        sb.append("\nbuilder类型：" + Build.TYPE);
        sb.append("\nUSER:" + Build.USER);

        sizeTxt.setText(sb.toString());

        //
    }

    /**
     * SD卡是否存在
     * @return
     */
    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * SD卡剩余空间
     * @return
     */
    public long getSDAllSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();
        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize)/1024/1024; //单位MB
    }

    /**
     * SD卡总容量
     * @return
     */
    public long getSDFreeSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize)/1024 /1024; //单位MB
    }
    /**
     * Handler
     */
    final Handler handler_new = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    DialogUtils.dismiss();
                    messageTxt.setText("deviceId:" + info.getDeviceId() +
                                    "\nSDK版本::" + info.getVersion_sdk() +
                                    "\n最大CPU的频率:" + info.getMaxCpuFreq() +
                                    "\n最小CPU的频率:" + info.getMinCpuFreq() +
                                    "\n当前CPU的频率:" + info.getCurCpuFreq() +
                                    "\ncpu名字:" + info.getCpuName() +
                                    "\n内存条总大小:" + info.getRam_total() +
                                    "\n内存条剩余大小:" + info.getRam_free() +
                                    "\n内存卡总大小:" + info.getDisk_total() +
                                    "\n内存卡剩余大小:" + info.getDisk_free() +
                                    "\n音量:" + info.getCurrentVolume() +
                                    "\n屏幕宽度:" + info.getWidth() +
                                    "\n屏幕高度:" + info.getHeight() +
                                    "\n密度:" + info.getDensity() +
                                    "\n是否禁用快门声音:" + info.isCanDisableShutterSound() +
                                    "\n网络是否连接:" + info.isNetavailable() +
                                    "\nGPS是否打开:" + info.isGpsIsOpen() +
                                    "\nWIFI是否可用:" + info.isWifiavailable() +
                                    "\nSD卡是否存在:" + info.isSdCardIsExist() +
                                    "\n摄相头能否使用:" + info.isCameraIsUse()

                    );
                    break;
            }
        }

    };

    /**
     * 测试获取种路径
     */
    public void testGetPath() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        L.i("mExternalStorageAvailable:" + mExternalStorageAvailable + "\nmExternalStorageWriteable:" + mExternalStorageWriteable + "\nstate:" + state);
        Log.i("AAAA", "getFilesDir = " + getFilesDir());
        Log.i("AAAA", "getExternalFilesDir = " + getExternalFilesDir("exter_test").getAbsolutePath());
        Log.i("AAAA", "getDownloadCacheDirectory = " + Environment.getDownloadCacheDirectory().getAbsolutePath());
        Log.i("AAAA", "getDataDirectory = " + Environment.getDataDirectory().getAbsolutePath());
        Log.i("AAAA", "getExternalStorageDirectory = " + Environment.getExternalStorageDirectory().getAbsolutePath());//外部存储根目录
        Log.i("AAAA", "getExternalStoragePublicDirectory = " + Environment.getExternalStoragePublicDirectory("pub_test"));
        FileUtil.createFile();
        //联想测试结果：
//        getFilesDir =/data / data / com.s.z.j / files
//        getExternalFilesDir =/storage / sdcard0 / Android / data / com.s.z.j / files / exter_test
//        getDownloadCacheDirectory =/cache
//        getDataDirectory =/data
//        getExternalStorageDirectory =/storage / sdcard0
//        getExternalStoragePublicDirectory =/storage / sdcard0 / pub_test
        //三星手机测试结果
//        getFilesDir =/data / data / com.s.z.j / files
//        getExternalFilesDir =/storage / emulated / 0 / Android / data / com.s.z.j / files / exter_test
//        getDownloadCacheDirectory =/cache
//        getDataDirectory =/data
//        getExternalStorageDirectory =/storage / emulated / 0
//        getExternalStoragePublicDirectory =/storage / emulated / 0 / pub_test

    }
}
