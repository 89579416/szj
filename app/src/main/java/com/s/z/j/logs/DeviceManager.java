package com.s.z.j.logs;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * 版本： V1.0
 * 说明：获得设备信息
 */
public class DeviceManager {
    private static final String TAG = "DeviceManager";
    private static Context context;
    private static int maxVolume = 0;//最大音量值
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";
    /**
     * 设置上下文
     * @param context
     */
    public static void setContext(Context context) {
        DeviceManager.context = context;
    }

    /**
     * 获得内存详细信息
     *
     * @return
     */
    public static String getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2 = "";
        StringBuffer sb = new StringBuffer();
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                sb.append(str2 + "\n");
            }
        } catch (IOException e) {
        }
        return sb.toString();
    }

    /**
     * 获得可用内存信息
     *
     * @return
     */
    public static String getAvailableMemory() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return formatSize(mi.availMem);
    }

    /**
     * 获得ROM信息
     *
     * @return
     */
    @TargetApi(18)
    public static String[] getRomMemroy() {
        String[] romInfo = new String[2];
        //Total rom memory
        romInfo[0] = formatSize(getTotalInternalMemorySize());

        //Available rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0, availableBlocks = 0;
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        romInfo[1] = formatSize(blockSize * availableBlocks);
        getVersion();
        return romInfo;
    }

    /**
     * 获得总的ROM
     *
     * @return
     */
    @TargetApi(18)
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0, totalBlocks = 0;
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        }
        return totalBlocks * blockSize;
    }

    /**
     * 获得SD卡总大小
     *
     * @return 总大小及可用大小
     */
    @TargetApi(18)
    public static String[] getSDCardMemory() {
        String[] sdCardInfo = new String[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long bSize = 0, bCount = 0, availBlocks = 0;
            if (Build.VERSION.SDK_INT >= 18) {
                bSize = sf.getBlockSizeLong();
                bCount = sf.getBlockCountLong();
                availBlocks = sf.getAvailableBlocksLong();
            } else {
                bSize = sf.getBlockSize();
                bCount = sf.getBlockCount();
                availBlocks = sf.getAvailableBlocks();
            }
            sdCardInfo[0] = formatSize(bSize * bCount);//总大小
            sdCardInfo[1] = formatSize(bSize * availBlocks);//可用大小
        }
        return sdCardInfo;
    }

    /**
     * 获得CPU信息
     *
     * @return
     */
    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpuInfo;
    }

    /**
     * 获得系统版本信息
     *
     * @return
     */
    public static String[] getVersion() {
        String[] version = {"null", "null", "null", "null"};
        String str1 = "/proc/version";
        String str2;
        String[] arrayOfString;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            version[0] = arrayOfString[2];//KernelVersion
            localBufferedReader.close();
        } catch (IOException e) {
        }
        version[1] = Build.VERSION.RELEASE;// firmware version
        version[2] = Build.MODEL;//model
        version[3] = Build.DISPLAY;//system version
        return version;
    }

    /**
     * 获得MAC地址等
     *
     * @return
     */
    public static String getOtherInfo() {
        String mac = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getMacAddress() != null) {
            mac = wifiInfo.getMacAddress();
        } else {
            mac = "Fail";
        }
        return mac;
    }

    /**
     * 获得本机IP
     *
     * @return
     */
    public static String getLocalHostIp() {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int i = wifiInfo.getIpAddress();
        return int2ip(i);
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    private static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获得DeviceID
     *
     * @return
     */
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        // tmSerial = "" + tm.getSimSerialNumber();
        // androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        //  UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        //String uniqueId = deviceUuid.toString();
        return tmDevice;
    }

    /**
     * 获得SerialID
     *
     * @return
     */
    public static String getSerialId() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        //tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        // androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        //  UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        //String uniqueId = deviceUuid.toString();
        return tmSerial;
    }

    /**
     * 获得开机时间
     *
     * @return
     */
    public static String getStartUpTime() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        if (ut == 0) {
            ut = 1;
        }
        int m = (int) ((ut / 60) % 60);
        int h = (int) ((ut / 3600));
        return h + "HH " + m + "mm";
    }

    public static String formatSize(long size) {
        String suffix = null;
        float fSize = 0;

        if (size >= 1024) {
            suffix = "KB";
            fSize = size / 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            fSize = size;
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        if (suffix != null)
            resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    /**
     * 获取剩余RAM内存大小
     * /
     *
     * @return
     */
    public static String getAvailMemory() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }
    /**
     * 获得屏幕信息
     *
     * @return
     */
    public static Point getWindowInfo() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point;
    }

    /**
     * 获取获取设备序列号
     */
    public static String getDeviceSerialNumber() {
        return Build.SERIAL;
    }

    /**
     * 获取屏幕是几寸
     */
    //   @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static double getScreenSizeOfDevice() {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = getWindowInfo().x;
        int height = getWindowInfo().y;
        double x1 = Math.pow(width, 2);
        double y1 = Math.pow(height, 2);
        double diagonal = Math.sqrt(x1 + y1);
        int densityDpi = dm.densityDpi;
        double screenInches1 = diagonal / (double) densityDpi;
        int i = (int) Math.round(screenInches1 * 1);     //小数点后 a 位前移，并四舍五入
        double f2 = (i / (double) 1);        //还原小数点后 a 位
        return f2;
    }

    /**
     * 获得设备当前音量
     *
     * @param context
     * @return
     */
    public static int getCurrentVolume(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int volume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (maxVolume == 0) {
            getMaxVolume(context);
        }
        int result = (volume * 100) / maxVolume;
        return result;
    }
    /**
     * 获得当前设备最大的音量值
     *
     * @param context
     */
    private static void getMaxVolume(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }
    /**
     * 设置音乐音量
     *
     * @param context
     * @param volume
     */
    @TargetApi(23)
    public static void setVolume(Context context, int volume) {
        if (volume < 0) {//静音或静音恢复
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (volume == -1) {//静音
                if (Build.VERSION.SDK_INT >= 23) {
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
                } else {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                }
            } else if (volume == -2) {//静音恢复
                if (Build.VERSION.SDK_INT >= 23) {
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
                } else {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                }
            }
        } else {
            if (maxVolume == 0) {
                getMaxVolume(context);
            }
            int tempVolume = (maxVolume * volume) / 100;
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, tempVolume, 0);
        }
    }
    /**
     * 实时获取CPU当前频率（单位KHZ）
     *
     * @return
     */
    public static int getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str1 = result;
        return Integer.valueOf(str1) / 1000;
    }

    /**
     * CPU使用率
     *
     * @return
     */
    public static String getCpuUsage() {
        String Result;
        StringBuffer tv = new StringBuffer();//返回一堆
        StringBuffer cpu_1 = new StringBuffer();//只返回使用率
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("top -n 1");
            BufferedReader br = new BufferedReader(new InputStreamReader
                    (p.getInputStream()));
            while ((Result = br.readLine()) != null) {
                if (Result.trim().length() < 1) {
                    continue;
                } else {
                    String[] CPUusr = Result.split("%");
                    tv.append("USER:" + CPUusr[0] + "\n");
                    String[] CPUusage = CPUusr[0].split("User");
                    String[] SYSusage = CPUusr[1].split("System");
                    tv.append("CPU:" + CPUusage[1].trim() + " length:" + CPUusage[1].trim().length() + "\n");
                    tv.append("SYS:" + SYSusage[1].trim() + " length:" + SYSusage[1].trim().length() + "\n");
                    tv.append(Result + "\n");
                    cpu_1.append(CPUusage[1].trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "-100";
        }
        return cpu_1.toString();

    }

    /**
     * 判断网络连接是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected() {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 获得设备devices_id
     *
     * @param context
     * @return
     */
    public synchronized static String getDevicesId(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}
