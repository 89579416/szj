package com.s.z.j.connectwifi;

/**
 * Created by Administrator on 2017-04-13.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.s.z.j.R;
import com.s.z.j.utils.L;

import java.util.List;

public class ConnWifiMainActivity extends Activity {


    private Button getWifiBtn;
    private CustomPopWindow mListPopWindow;
    private WifiAdmin mwifiAdmin;
    private WifiManager wifiManager;
    List<ScanResult> list;
    private String ssid;
    public ConnectivityManager connectManager;
    public int level;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_main);
        context = this;
        getWifiBtn = (Button) findViewById(R.id.conn_wifi_button1);
        getWifiBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("点击按钮");
                showwifiList();
            }
        });

    }
    /**
     * wifiList
     */
    private void showwifiList() {
        L.i("准备显示列表");
        View contentView = LayoutInflater.from(context).inflate(R.layout.item_wifi_list, null);
        L.i("获取到VIEW");
        //处理popWindow 显示内容
        WifihandleListView(contentView);
        //创建并显示popWindow
//        mListPopWindow = new CustomPopWindow.PopupWindowBuilder(context).setView(contentView) .create().showAsDropDown(getWifiBtn, 0, 20);

        mListPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(600, 400) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .create()//创建PopupWindow
                .showAsDropDown(getWifiBtn, 0, 10);//显示PopupWindow


    }

    /**
     * WIFIListpop
     */
    private void WifihandleListView(View contentView) {
        mwifiAdmin = new WifiAdmin(ConnWifiMainActivity.this);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        list = wifiManager.getScanResults();
        ListView listView = (ListView) contentView.findViewById(R.id.ListView);
        connectManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);
        if (list == null) {
            Toast.makeText(this, "wifi未打开！", Toast.LENGTH_LONG).show();
        } else {
            listView.setAdapter(new MyWifiAdapter(ConnWifiMainActivity.this, list));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ConnWifiMainActivity.this);
                ssid = list.get(position).SSID;
                alert.setTitle(ssid);
                alert.setMessage("输入密码");
                final EditText et_password = new EditText(ConnWifiMainActivity.this);
                final SharedPreferences preferences = getSharedPreferences("wifi_password", Context.MODE_PRIVATE);
                et_password.setText(preferences.getString(ssid, ""));
                alert.setView(et_password);
                alert.setPositiveButton("连接", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pw = et_password.getText().toString();
                        if (null == pw || pw.length() < 8) {
                            Toast.makeText(ConnWifiMainActivity.this, "密码至少8位", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(ssid, pw);
                        editor.commit();
                        mwifiAdmin.addNetwork(mwifiAdmin.CreateWifiInfo(ssid, et_password.getText().toString(), 3));
                    }
                });
                alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.create();
                alert.show();
            }
        });

    }

    /**
     * WIFIListAdapter
     */
    public class MyWifiAdapter extends BaseAdapter {
        LayoutInflater inflater;
        List<ScanResult> list;

        public MyWifiAdapter(Context context, List<ScanResult> list) {
            // TODO Auto-generated constructor stub
            this.inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * scanResult  扫描结果
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            view = inflater.inflate(R.layout.item_wifi_info, null);
            ScanResult scanResult = list.get(position);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(scanResult.SSID);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            level = WifiManager.calculateSignalLevel(scanResult.level, 5);
            if (scanResult.capabilities.contains("WEP") || scanResult.capabilities.contains("PSK") ||
                    scanResult.capabilities.contains("EAP")) {
                imageView.setImageResource(R.drawable.wifi_signal_lock);
            } else {
                imageView.setImageResource(R.drawable.wifi_signal_open);
            }
            imageView.setImageLevel(level);
            return view;
        }
    }

    //监听wifi状态变化
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo.isConnected()) {
                WifiManager wifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                String wifiSSID = wifiManager.getConnectionInfo()
                        .getSSID();
                Toast.makeText(context, wifiSSID + "连接成功", Toast.LENGTH_SHORT).show();
            }
        }
    };




}

