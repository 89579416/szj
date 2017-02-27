package com.s.z.j.utils;

/**
 * 手机里面应用程序信息的提供类
 * 通过包名获取类名
 * 然后跳转到另一个APP
 * Created by Administrator on 2016-11-07.
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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

    /**
     * 通过包名获取类名
     * 然后跳转到另一个APP
     * @param packagename
     */
    private void doStartApplicationWithPackageName(Context context,String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
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
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
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
                context.startActivity(intent);
            } else {
                com.szj.library.utils.T.s(context, "打开快收银失败，有可能是没有安装快收银");
            }
        }
    }
}