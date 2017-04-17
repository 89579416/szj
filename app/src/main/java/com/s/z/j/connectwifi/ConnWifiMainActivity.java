package com.s.z.j.connectwifi;

/**
 * Created by Administrator on 2017-04-13.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.s.z.j.R;

import java.util.List;
import java.util.Map;

public class ConnWifiMainActivity extends Activity {

    private Button msearchBtn;
    private Button mopenBtn;
    private Button mcloseBtn;
    private static  ListView msearchList;
    private static ProgressDialog dialog;
    private static LinearLayout openView;
    static List<Map<String,String>>mlist;

    private Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_main);
        msearchList = (ListView) findViewById(R.id.wifi_main_listview);
        button1 = (Button) findViewById(R.id.conn_wifi_button1);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openWifiIntent = new Intent(ConnWifiMainActivity.this,WifiService.class);
                Bundle openBundle = new Bundle();
                openBundle.putInt("status",0);
                openWifiIntent.putExtras(openBundle);
                startService(openWifiIntent);

                dialog = ProgressDialog.show(ConnWifiMainActivity.this,"正在打开wifi","正在搜索wifi...");
            }
        });
        button2 = (Button) findViewById(R.id.conn_wifi_button2);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent closeWifiIntent = new Intent(ConnWifiMainActivity.this,WifiService.class);
                Bundle closeBundle = new Bundle();
                closeBundle.putInt("status",1);
                closeWifiIntent.putExtras(closeBundle);
                startService(closeWifiIntent);
            }
        });
        msearchList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                WifiService service = new WifiService();

                Map<String, String> map = mlist.get(position);//得到对应的list中的map
                final String ssid = map.get("wifi_name");//得到点击的热点的ssid，即wifi名称
                int wifiItemId = service.isConfigured("\"" + ssid + "\"");//判断是否已经存储该热点的信息,返回bssid
                if (service.ConnectWifi(wifiItemId)) {
                    view.setBackgroundResource(R.color.green);
                } else {//弹出对话框，输入密码

                    View inflater = LayoutInflater.from(ConnWifiMainActivity.this).inflate(R.layout.item_conn_wifi, null);
                    final AlertDialog alertDialog = new AlertDialog.Builder(ConnWifiMainActivity.this).setTitle("请输入密码").setView(inflater).create();
                    alertDialog.show();
                    final EditText passEdit = (EditText) inflater.findViewById(R.id.conn_wifi_edittext);
                    Button connBtn = (Button) inflater.findViewById(R.id.conn_wifi_button);
                    connBtn.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            String pass = passEdit.getText().toString();
                            if (null == pass || "".equals(pass)) {
                                Toast.makeText(ConnWifiMainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent connectIntent = new Intent(ConnWifiMainActivity.this, WifiService.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("status", 2);
                            connectIntent.putExtras(bundle);
                            connectIntent.putExtra("ssid", ssid);
                            connectIntent.putExtra("pass", pass);
                            startService(connectIntent);
                            alertDialog.dismiss();
                            dialog = ProgressDialog.show(ConnWifiMainActivity.this, "", "正在链接" + ssid);

                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.androidwifi.opensuccess")) {
                @SuppressWarnings("unchecked")
                List<Map<String, String>>list = (List<Map<String, String>>) intent.getSerializableExtra("result");
                mlist = list;
                dialog.dismiss();
                //				ArrayAdapter<String>adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,scanResult);
                SimpleAdapter adapter = new SimpleAdapter(context, mlist, R.layout.searchlist_item,new String[]{"wifi_name","wifi_bssid"},new int[]{R.id.wifi_name,R.id.wifi_bssid});

                msearchList.setAdapter(adapter);

            }

        }

    }

    public static class WifiResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            // TODO Auto-generated method stub
            if (intent.getAction().equals("com.androidwifi.result")) {
                int result = intent.getIntExtra("result",0);
                String ssid = intent.getStringExtra("ssid");
                if (result == -1) {
                    Toast.makeText(context,"链接"+ssid+"失败",Toast.LENGTH_SHORT).show();
                }
                if (result == 1) {
                    Toast.makeText(context,"已链接到"+ssid,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}

