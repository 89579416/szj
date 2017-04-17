package com.s.z.j.connectwifi;

/**
 * Created by Administrator on 2017-04-13.
 */
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WifiService extends IntentService {

    private static final String DEBUG = "debug";
    private static List<ScanResult>resultList = new ArrayList<ScanResult>();
    private List<WifiConfiguration>wificonfigList = new ArrayList<WifiConfiguration>();
    private NetworkInfo mnetWorkInfo;
    private WifiInfo mWifiInfo;
    private WifiManager mwifiManager;//管理wifi

    public  List<Map<String,String>>mlist = new ArrayList<Map<String,String>>();

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        //获得系统wifi服务
        mwifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int status = intent.getExtras().getInt("status");
        switch (status) {
            case 0://open wifi && search wifi
                Log.d(DEBUG, "打开service wifi");
                if (!mwifiManager.isWifiEnabled()) {
                    mwifiManager.setWifiEnabled(true);
                    resultList.clear();
                    mwifiManager.startScan();//开始搜索，当搜索到可用的wifi时，将发送WifiManager.SCAN_RESULTS_AVAILABLE_ACTION的广播

                    while (mlist.size() == 0) {//此处休眠0.1秒是因为搜索wifi是一个耗时的操作，如果不休眠，则扫描结果可能为空
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    Intent broadCastIntent = new Intent();
//				Bundle bundle = new Bundle();
                    broadCastIntent.putExtra("result",(Serializable) mlist);

                    broadCastIntent.setAction("com.androidwifi.opensuccess");
                    sendBroadcast(broadCastIntent);

                }
                break;
            case 1://close wifi
                Log.d(DEBUG, "关闭service wifi");
                if (mwifiManager.isWifiEnabled()) {
                    mwifiManager.setWifiEnabled(false);
                }
                break;
            case 2:
                int result = -1;//表示链接失败
                Intent resultIntent = new Intent();
                resultIntent.setAction("com.androidwifi.result");

                String  ssid= intent.getStringExtra("ssid");
                String pass = intent.getStringExtra("pass");

                resultIntent.putExtra("ssid",ssid);
                int netWorkId = AddWifiConfig(resultList, ssid, pass);//添加该网络的配置
                if (netWorkId !=-1) {
                    getConfigurations();
                    boolean isConnect = ConnectWifi(netWorkId);
                    if (isConnect) {
                        result = 1;
                    }
                }
                resultIntent.putExtra("result",result);
                sendBroadcast(resultIntent);
                break;

            default:
                break;
        }

    }

    /**
     * 当搜索到可用wifi时，将结果封装到mlist中
     */
    private final BroadcastReceiver wifiReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                resultList = mwifiManager.getScanResults();
                sortByLevel(resultList);
                for (ScanResult scanResult : resultList) {
                    Map<String,String>map = new HashMap<String, String>();
                    map.put("wifi_name",scanResult.SSID);
                    map.put("wifi_bssid",scanResult.BSSID);
                    mlist.add(map);
                }
            }

        }
    };

    /**
     * 得到配置信息
     */
    public void getConfigurations() {
        wificonfigList = mwifiManager.getConfiguredNetworks();
    }

    /**
     * 该链接是否已经配置过
     * @param SSID
     * @return
     */
    public int isConfigured(String SSID) {
        for (int i = 0; i < wificonfigList.size(); i++) {
            if (wificonfigList.get(i).SSID.equals(SSID)) {
                return wificonfigList.get(i).networkId;
            }
        }
        return -1;
    }
    /**
     * 链接到制定wifi
     * @param wifiId
     * @return
     */
    public boolean ConnectWifi(int wifiId){
        boolean isConnect = false;
        int id= 0;
        for(int i = 0; i < wificonfigList.size(); i++){
            WifiConfiguration wifi = wificonfigList.get(i);
            id = wifi.networkId;
            if(id == wifiId){
                while(!(mwifiManager.enableNetwork(wifiId, true))){
                    Log.i("ConnectWifi",String.valueOf(wificonfigList.get(wifiId).status));
                }

                if ( mwifiManager.enableNetwork(wifiId, true)) {
                    isConnect = true;
                }
            }
        }
        return isConnect;
    }

    /**
     * 添加wifi配置
     * @param wifiList
     * @param ssid
     * @param pwd
     * @return
     */
    public int AddWifiConfig(List<ScanResult> wifiList,String ssid,String pwd){
        int wifiId = -1;
        for(int i = 0;i < wifiList.size(); i++){
            ScanResult wifi = wifiList.get(i);
            if(wifi.SSID.equals(ssid)){
                Log.i("AddWifiConfig","equals");
                WifiConfiguration wifiCong = new WifiConfiguration();
                wifiCong.SSID = "\""+wifi.SSID+"\"";
                wifiCong.preSharedKey = "\""+pwd+"\"";
                wifiCong.hiddenSSID = false;
                wifiCong.status = WifiConfiguration.Status.ENABLED;
                wifiId = mwifiManager.addNetwork(wifiCong);
                if(wifiId != -1){
                    return wifiId;
                }
            }
        }
        return wifiId;
    }

    //将搜索到的wifi根据信号强度从强到弱进行排序
    private void sortByLevel(List<ScanResult> resultList) {
        for(int i=0;i<resultList.size();i++)
            for(int j=1;j<resultList.size();j++)
            {
                if(resultList.get(i).level<resultList.get(j).level)    //level属性即为强度
                {
                    ScanResult temp = null;
                    temp = resultList.get(i);
                    resultList.set(i, resultList.get(j));
                    resultList.set(j, temp);
                }
            }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        unregisterReceiver(wifiReceiver);
        super.onDestroy();
    }

    public WifiService() {
        super("");
        // TODO Auto-generated constructor stub
    }

}

