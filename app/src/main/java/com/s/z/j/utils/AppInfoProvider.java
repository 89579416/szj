package com.s.z.j.utils;

/**
 * 手机里面应用程序信息的提供类
 * Created by Administrator on 2016-11-07.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

/**
 *  APK包名检测
 */
public class AppInfoProvider {
    public static final String LOTTERY_PACKAGENAME_PREFIX = "com.teamviewer.quicksupport.addon";//
    public static final String LOTTERY_PACKAGENAME_SUFFIX = "ppl";

    /**
     * 获取手机里面app包名
     * @param context  上下文
     * @return
     */
    public static String getLotteryAppPackageName(Context context) {
        PackageManager pm = context.getPackageManager();
        // 获取手机里面所有的apk包的信息，PackageInfo代表的就是每个应用程序的manifest.xml文件
        List<PackageInfo> packInfos = pm.getInstalledPackages(0);
        String lotteryPackageName = null;
        for (PackageInfo packInfo : packInfos) {
            String packName = packInfo.packageName;
            if (packName.startsWith(LOTTERY_PACKAGENAME_PREFIX) && packName.endsWith(LOTTERY_PACKAGENAME_SUFFIX)) {
                lotteryPackageName = packName;
                break;
            }
        }
        return lotteryPackageName;
    }
    /**
     * 返回系统安装的所有应用名和包名
     * @param context
     * @return
     */
    public static String getAllAppPackageName(Context context){
        int j=0;//计数，通过这个得到系统安装了多少个应用
        StringBuffer packageNames = new StringBuffer("");
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : list2) {
            //得到手机上已经安装的应用的名字,即在AndriodMainfest.xml中的app_name。
            String appName=packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            //得到应用所在包的名字,即在AndriodMainfest.xml中的package的值。
            String packageName=packageInfo.packageName;
            Log.i("AAAA", "appName:" + appName + "  packageName:" + packageName);
            packageNames.append(appName+"\n"+packageName+"\n\n");
            j++;
        }
        packageNames.append("总个数："+j);
        return packageNames.toString();
    }
}