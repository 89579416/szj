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
                    messageTxt.setText(info.toString());
                    break;
            }
        }

    };


}
