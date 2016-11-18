package com.s.z.j.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

import com.s.z.j.entity.Info;
import com.s.z.j.model.CpuManager;
import com.s.z.j.model.InfoModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 获取设备信息，初始化的activity
 * Created by szj on 2015/7/20.
 */
public class GetInfoUtil implements SensorEventListener {
    private static String TAG = "AAAA";
    public Activity activity;
    public static Info info;
    private static CpuManager cpuManager;
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public GetInfoUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * 获取CPU信息
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Info GetInfo() {
        try {
            info = new InfoModel().getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            getCpu_temp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String deviceId = null;//获取设备唯一ID
        try {
            deviceId = getsID(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cpuManager = new CpuManager(activity);
        int MaxCpuFreq = 0;//获取CPU最大频率
        int MinCpuFreq = 0;//获取CPU最小频率
        int CurCpuFreq = 0;//实时获取CPU当前频率
        try {
            MaxCpuFreq = getMaxCpuFreq() * GetInfoUtil.getNumCores();
            MinCpuFreq = getMinCpuFreq() * GetInfoUtil.getNumCores();
            CurCpuFreq = getCurCpuFreq();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String CpuName = null;//获取CPU名字
        String CpuUsage = null;//CPU使用率
        String Version[] = new String[0];//系统的版本信息
        String OtherInfo[] = new String[0];//mac地址和开机时间
        try {
            CpuName = getCpuName();
            CpuUsage = getCpuUsage();
            Version = cpuManager.getVersion();
            OtherInfo = cpuManager.getOtherInfo(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int currentVolume = 0;//当前音量
        try {
            /**获取音量信息*/
            AudioManager mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
            int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int widthPx = 0;
        int heightPx = 0;
        float density = 0;

        try {
            /**获取屏幕信息*/
            Point point = new Point();
            activity.getWindowManager().getDefaultDisplay().getRealSize(point);
            // 屏幕宽度(px)
            widthPx = point.x;
            // 屏幕高度(px)
            heightPx = point.y;
            // 屏幕密度:指每平方英寸中的像素数,在DisplayMetrics类中，该密度值为dpi/160
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            density = displayMetrics.density;
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**屏幕亮度*/
        int brightness = 0;
        try {
            ContentResolver resolver = activity.getContentResolver();
            brightness = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**获取摄相头信息*/
        try {
            int num = Camera.getNumberOfCameras();
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(num - 1, cameraInfo);
            boolean sound;
            try {
//                sound = cameraInfo.canDisableShutterSound;
                sound = false;
            } catch (Exception e) {
                sound = false;
            }
            info.setCanDisableShutterSound(sound);
            info.setFacing(cameraInfo.facing);
            info.setOrientation(cameraInfo.orientation);
        } catch (Exception e) {
            info.setCanDisableShutterSound(false);
            info.setFacing(-1);
            info.setOrientation(-1);
        }


        try {
            /**获取手机系统信息*/
            info.setBoard(Build.BOARD);
            info.setBrand(Build.BRAND);
            info.setCup_abi(Build.CPU_ABI);
            info.setDevice(Build.DEVICE);
            info.setHost(Build.HOST);
            info.setManufacturer(Build.MANUFACTURER);
            info.setTags(Build.TAGS);
            info.setModel(Build.MODEL);
            info.setFingerprint(Build.FINGERPRINT);
            info.setDisplay(Build.DISPLAY);
            info.setProduct(Build.PRODUCT);
            info.setTime(Build.TIME);
            info.setType(Build.TYPE);
            info.setUser(Build.USER);
            info.setBootloader(Build.BOOTLOADER);
            info.setHardware(Build.HARDWARE);
            info.setRadio(Build.RADIO);
            info.setVersion_release(Build.VERSION.RELEASE);
            info.setVersion_codename(Build.VERSION.CODENAME);
            info.setVersion_incremental(Build.VERSION.INCREMENTAL);
            info.setVersion_sdk(Build.VERSION.SDK);
            info.setVersion_sdk_int(Build.VERSION.SDK_INT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String android_id = null;
        try {
            android_id = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean netavailable = false;//有没有网络
        boolean gpsIsOpen = false;//GPS是否打开
        boolean wifiavailable = false;//WIFI是否连上
        boolean sdCardIsExist = false;//有没有SD卡
        try {
            netavailable = NetAvailable(activity);
            gpsIsOpen = ExistGPS(activity);
            wifiavailable = NetAvailable(activity);
            sdCardIsExist = ExistSDCard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            info.setCurrentVolume(currentVolume);
            info.setMaxCpuFreq(MaxCpuFreq);
            info.setMinCpuFreq(MinCpuFreq);
            info.setCurCpuFreq(CurCpuFreq);
            info.setCpuName(CpuName);
            info.setCpuUsage(CpuUsage);
            info.setVersion(Version);
            info.setOtherInfo(OtherInfo);
            info.setWidth(widthPx);
            info.setHeight(heightPx);
            info.setDensity(density);
            info.setBrightness(brightness);
            info.setGpsIsOpen(gpsIsOpen);
            info.setNetavailable(netavailable);
            info.setWifiavailable(wifiavailable);
            info.setSdCardIsExist(sdCardIsExist);
            if (!TextUtils.isEmpty(android_id)) {
                info.setFacsimileId(android_id);
            }
            boolean camerIsUse = checkCameraDevice(activity);
            info.setCameraIsUse(camerIsUse);
            info.setDeviceId(deviceId);
            String ram_total = getTotalMemory(activity);
            String ram_free = getAvailMemory(activity);
            String disk_total = getSDCardMemory()[0] + "M";
            String disk_free = getSDCardMemory()[1] + "M";
            info.setRam_total(ram_total);
            info.setRam_free(ram_free);
            info.setDisk_total(disk_total);
            info.setDisk_free(disk_free);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 获取当前使用的内存设备空间大小和剩余大小
     * 一般硬盘、U盘都属于ROM类型，内存条是RAM
     *
     * @return
     */
    public static long[] getSDCardMemory() {
        long[] sdCardInfo = new long[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[0] = (bSize * bCount) / 1024 / 1024;//总大小 单位M
            sdCardInfo[1] = (bSize * availBlocks) / 1024 / 1024;//可用大小 单位M
        }
        return sdCardInfo;
    }

    /**
     * 获取总RAM大小
     * 一般硬盘、U盘都属于ROM类型，内存条是RAM
     *
     * @param activity
     * @return
     */
    public static String getTotalMemory(Activity activity) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return Formatter.formatFileSize(activity.getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 一般硬盘、U盘都属于ROM类型，内存条是RAM
     * // 获取剩余RAM内存条大小
     *
     * @param activity
     * @return
     */
    public static String getAvailMemory(Activity activity) {
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(activity.getBaseContext(), mi.availMem);// 将获取的内存大小规格化
    }


    /**
     * 判断SD卡是否存在
     */
    public static boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 系统的版本信息
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
     * 判断wifi是否可用 turn 是 false 否
     * 代码改为判断网络是否可用，因为有些设备是使用的网线，
     */
    public static boolean WifiAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断网络是否可用,包括2G,3G,4G
     */
    public static boolean NetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断GPS是否打开
     */
    public static boolean ExistGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 检测Android设备是否支持摄像机
     */
    public static boolean checkCameraDevice(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取设备唯一ID
     *
     * @param context
     * @return
     */
    public synchronized static String getsID(Context context) {
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

    /**
     * 获取CPU信息
     *
     * @return
     */
    public static CPUInfo getCPUInfo() {
        String strInfo = null;
        try {
            byte[] bs = new byte[1024];
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            strInfo = "";
            ex.printStackTrace();
        }

        CPUInfo info = parseCPUInfo(strInfo);
        info.mCPUMaxFreq = getMaxCpuFreq();

        return info;
    }

    private SensorManager mSensorManager;
    private Sensor mPressure;

    /**
     * 获取CPU温度
     */
    public void getCpu_temp() {
        mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float millibars_of_pressure = event.values[0];
        info.setCpu_temp("" + millibars_of_pressure);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    static class CPUInfo {
        public CPUInfo() {
        }

        public static final int CPU_TYPE_UNKNOWN = 0x00000000;
        public static final int CPU_TYPE_ARMV5TE = 0x00000001;
        public static final int CPU_TYPE_ARMV6 = 0x00000010;
        public static final int CPU_TYPE_ARMV7 = 0x00000100;

        public static final int CPU_FEATURE_UNKNOWS = 0x00000000;
        public static final int CPU_FEATURE_VFP = 0x00000001;
        public static final int CPU_FEATURE_VFPV3 = 0x00000010;
        public static final int CPU_FEATURE_NEON = 0x00000100;

        public int mCPUType;
        public int mCPUCount;
        public int mCPUFeature;
        public double mBogoMips;
        public int mCPUMaxFreq;

        @Override
        public String toString() {
            return "CPUInfo{" +
                    "mCPUType=" + mCPUType +
                    ", mCPUCount=" + mCPUCount +
                    ", mCPUFeature=" + mCPUFeature +
                    ", mBogoMips=" + mBogoMips +
                    ", mCPUMaxFreq=" + mCPUMaxFreq +
                    '}';
        }
    }

    private static CPUInfo parseCPUInfo(String cpuInfo) {
        if (cpuInfo == null || "".equals(cpuInfo)) {
            return null;
        }

        CPUInfo ci = new CPUInfo();
        ci.mCPUType = CPUInfo.CPU_TYPE_UNKNOWN;
        ci.mCPUFeature = CPUInfo.CPU_FEATURE_UNKNOWS;
        ci.mCPUCount = 1;
        ci.mBogoMips = 0;

        if (cpuInfo.contains("ARMv5")) {
            ci.mCPUType = CPUInfo.CPU_TYPE_ARMV5TE;
        } else if (cpuInfo.contains("ARMv6")) {
            ci.mCPUType = CPUInfo.CPU_TYPE_ARMV6;
        } else if (cpuInfo.contains("ARMv7")) {
            ci.mCPUType = CPUInfo.CPU_TYPE_ARMV7;
        }

        if (cpuInfo.contains("neon")) {
            ci.mCPUFeature |= CPUInfo.CPU_FEATURE_NEON;
        }

        if (cpuInfo.contains("vfpv3")) {
            ci.mCPUFeature |= CPUInfo.CPU_FEATURE_VFPV3;
        }

        if (cpuInfo.contains(" vfp")) {
            ci.mCPUFeature |= CPUInfo.CPU_FEATURE_VFP;
        }

        String[] items = cpuInfo.split("n");

        for (String item : items) {
            if (item.contains("CPU variant")) {
                int index = item.indexOf(": ");
                if (index >= 0) {
                    String value = item.substring(index + 2);
                    try {
                        ci.mCPUCount = Integer.decode(value);
                        ci.mCPUCount = ci.mCPUCount == 0 ? 1 : ci.mCPUCount;
                    } catch (NumberFormatException e) {
                        ci.mCPUCount = 1;
                    }
                }
            } else if (item.contains("BogoMIPS")) {
                int index = item.indexOf(": ");
                if (index >= 0) {
                    String value = item.substring(index + 2);
                }
            }
        }

        return ci;
    }

    /**
     * CPU最大频率
     *
     * @return
     */
    public static int getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        String str = result.trim();
        return Integer.valueOf(str) / 1000;
    }

    // 实时获取CPU当前频率（单位KHZ）
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

    // 获取CPU最小频率（单位KHZ）
    public static int getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        String str2 = result.trim();
        return Integer.valueOf(str2) / 1000;
    }


    // 获取CPU名字
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
     * 获取CPU核心数
     *
     * @return
     */
    public static int getNumCores() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            // Default to return 1 core
            return 1;
        }
    }

    /**
     * @param facing
     * @return
     */
    private static boolean checkCameraFacing(final int facing) {
        if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
            return false;
        }
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    /**
     * 有后置摄像头？
     *
     * @return
     */
    public static boolean hasBackFacingCamera() {
        final int CAMERA_FACING_BACK = 0;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    /**
     * 有前置摄像头？
     *
     * @return
     */
    public static boolean hasFrontFacingCamera() {
        final int CAMERA_FACING_BACK = 1;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    /**
     * 获取SDK版本
     *
     * @return
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 2  * 获取当前程序的版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public static String getMyVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
