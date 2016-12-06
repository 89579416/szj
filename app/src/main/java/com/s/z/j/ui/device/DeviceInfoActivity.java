package com.s.z.j.ui.device;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.s.z.j.R;
import com.s.z.j.entity.Info;
import com.s.z.j.utils.GetInfoUtil;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

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
    boolean a = false;
    @Override
    public void initialize(Bundle savedInstanceState) {
        info = new Info();
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
     * Handler
     */
    final Handler handler_new = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    messageTxt.setText("deviceId:"+info.getDeviceId()+
                                    "\nSDK版本::"+info.getVersion_sdk()+
                                    "\n最大CPU的频率:"+info.getMaxCpuFreq()+
                                    "\n最小CPU的频率:"+info.getMinCpuFreq()+
                                    "\n当前CPU的频率:"+info.getCurCpuFreq()+
                                    "\ncpu名字:"+info.getCpuName()+
                                    "\n内存条总大小:"+info.getRam_total()+
                                    "\n内存条剩余大小:"+info.getRam_free()+
                                    "\n内存卡总大小:"+info.getDisk_total()+
                                    "\n内存卡剩余大小:"+info.getDisk_free()+
                                    "\n音量:"+info.getCurrentVolume()+
                                    "\n屏幕宽度:"+info.getWidth()+
                                    "\n屏幕高度:"+info.getHeight()+
                                    "\n密度:"+info.getDensity()+
                                    "\n是否禁用快门声音:"+info.isCanDisableShutterSound()+
                                    "\n网络是否连接:"+info.isNetavailable()+
                                    "\nGPS是否打开:" +info.isGpsIsOpen()+
                                    "\nWIFI是否可用:"+info.isWifiavailable()+
                                    "\nSD卡是否存在:"+info.isSdCardIsExist()+
                                    "\n摄相头能否使用:"+ info.isCameraIsUse()

                    );
                    break;
            }
        }

    };


}
