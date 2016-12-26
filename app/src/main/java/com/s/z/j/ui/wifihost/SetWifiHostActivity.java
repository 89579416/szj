package com.s.z.j.ui.wifihost;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.s.z.j.R;
import com.s.z.j.utils.L;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 设置wifi热点
 * Created by Administrator on 2016-11-09.
 */
@ContentView(R.layout.activity_set_wifihost)
public class SetWifiHostActivity extends BaseActivity {
    @ViewInject(R.id.set_wifi_name_edittext)
    private EditText nameEdit;

    @ViewInject(R.id.set_wifi_pwd_edittext)
    private EditText pwdEdit;

    @ViewInject(R.id.set_wifihot_button)
    private Button setBtn;

    private String wifiName;
    private String password;//接收从EditText中取出的密码
    private SharedPreferences mySharedPreferences;
    private String oldName;//从SharedPreferences里面取出的名称
    private String oldPwd;//从SharedPreferences里面取出的密码
    private boolean isWifiHostOpen = false;//wifi热点是否打开
    private boolean isAutomatic;

    @ViewInject(R.id.set_wifi_is_automatic_checkbox)
    private CheckBox isAutomaticBox;//选择是否自动开启热点

    private WifiHotUtil wifiHotUtil;

    @Override
    public void initialize(Bundle savedInstanceState) {
        initView();
        getData();
    }

    public void getData() {
        try {
            oldName = getName();
            oldPwd = getPwd();
            isAutomatic = getAutomatic();
            if(!TextUtils.isEmpty(oldName)){
                nameEdit.setText(oldName);
            }
            if(!TextUtils.isEmpty(oldPwd)){
                pwdEdit.setText(oldPwd);
            }
            if(isAutomatic){
                isAutomaticBox.setChecked(isAutomatic);
                openWifiHot(oldName, oldPwd);
            }
        } catch (Exception e) {

        }
    }

    public boolean getInfo() {
        wifiName = nameEdit.getText().toString().trim();
        if (TextUtils.isEmpty(wifiName)) {
            messageBox("热点名称不能为空");
            return false;
        }
        password = pwdEdit.getText().toString().trim();
        if(!TextUtils.isEmpty(password)){
            if(password.length()<8 || password.length()>16){
                messageBox("请输入8-16位密码");
                return false;
            }
        }
        return true;
    }

    private void initView() {
        mySharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
        isAutomatic = isAutomaticBox.isChecked();
        L.i("当前状态：" + isAutomatic);
        isAutomaticBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAutomatic = isChecked;
                L.i("修改后的状态："+isAutomatic);
            }
        });
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("点击了按钮，保存当前状态为："+isAutomatic);
                saveAutomatic(isAutomatic);
                if (!isWifiHostOpen) {
                    if (getInfo()) {
                        openWifiHot(wifiName, password);
                    }
                } else {
                    closeWifiHot();
                }
            }
        });
        WiFiAPService.startService(this);
        //init WifiHotUtil
        wifiHotUtil = new WifiHotUtil(this);
        WiFiAPService.addWiFiAPListener(new WiFiAPListener() {

            @Override
            public void stateChanged(int state) {
                switch (state) {
                    case WiFiAPListener.WIFI_AP_OPENING:
                        setBtn.setText(getResources().getString(R.string.opening_wifi_hot));
                        break;
                    case WiFiAPListener.WIFI_AP_OPEN_SUCCESS:
                        isWifiHostOpen = true;
                        messageBox("WiFi热点已开启");
                        setBtn.setText(getResources().getString(R.string.close_wifi_hot));
                        finish();
                        System.exit(0);
                        break;
                    case WiFiAPListener.WIFI_AP_CLOSEING:
                        setBtn.setText(getResources().getString(R.string.closeing_wifi_hot));
                        break;
                    case WiFiAPListener.WIFI_AP_CLOSE_SUCCESS:
                        isWifiHostOpen = false;
                        messageBox("WiFi热点已关闭");
                        setBtn.setText(getResources().getString(R.string.open_wifi_hot));
                        break;
                    default:
                        break;
                }
            }
        });
    }


    /**
     * 屏幕弹出提示消息
     *
     * @param str
     */
    public void messageBox(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取保存的密码
     *
     * @return
     */
    public String getPwd() {
        return mySharedPreferences.getString("pass", "");
    }

    /**
     * 获取保存的名称
     *
     * @return
     */
    public String getName() {
        return mySharedPreferences.getString("name", "");
    }

    /**
     * 保存名称和密码
     *
     * @param pwd
     */
    public void saveInfo(String name, String pwd) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("pass", pwd);
        editor.commit();
    }

    /**
     * 保存是否自动开启热点
     * @param isAutomatic
     */
    public void saveAutomatic(boolean isAutomatic){
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean("isAutomatic", isAutomatic);
        editor.commit();
    }
    /**
     * open the wifi hot with the setted hotName and password
     */
    private void openWifiHot(String name, String pwd) {
        wifiHotUtil.startWifiAp(name, pwd);
        saveInfo(name, pwd);
    }

    /**
     * close wifi hot
     */
    private void closeWifiHot() {
        wifiHotUtil.closeWifiAp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            System.exit(0);
            return true;
        } else
            return super.onKeyDown(keyCode, event);

    }

    /**
     * 从配置文件里面取出信息
     * @return
     */
    public boolean getAutomatic() {
        return mySharedPreferences.getBoolean("isAutomatic", false);
    }
}
