package com.s.z.j.logs;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author
 * @version v1.0, 2015-10-05
 */
//public class LogManager {
//    // 锁，是否关闭Log日志输出
//    public static boolean LogOFF = false;
//    // 是否关闭VERBOSE输出
//    public static boolean LogOFF_VERBOSE = false;
//    // 是否关闭debug输出
//    public static boolean LogOFF_DEBUG = false;
//    private static Context context;
//
//    /**** 5中Log日志类型 *******/
//    /**
//     * 信息日志类型
//     */
//    public static final int INFO = 1;
//    /**
//     * 详细信息日志类型
//     */
//    public static final int VERBOSE = 2;
//    /**
//     * 调试日志类型
//     */
//    public static final int DEBUG = 3;
//    /**
//     * 错误日志类型
//     */
//    public static final int ERROR = 5;
//
//
//    public static int MAX_LOG = 1;
//    /**
//     * 警告调试日志类型
//     */
//    public static final int WARN = 4;
//
//    /**
//     * 设置上下文
//     *
//     * @param context
//     */
//    public static void setContext(Context context) {
//        LogManager.context = context;
//    }
//
//    /**
//     * 显示，打印日志
//     */
//    public static void LogShow(String tag, String message, int type) {
//        if (type < MAX_LOG) {
//            return;
//        }
//        if (!LogOFF) {
//            switch (type) {
//                case DEBUG:
//                    if (!LogOFF_DEBUG) {
//                        Log.d(tag, message);
//                    }
//                    break;
//                case ERROR:
//                    Log.e(tag, message);
//                    break;
//                case INFO:
//                    Log.i(tag, message);
//                    break;
//                case VERBOSE:
//                    if (!LogOFF_VERBOSE) {
//                        Log.v(tag, message);
//                    }
//                    break;
//                case WARN:
//                    Log.w(tag, message);
//                    break;
//            }
//        }
//    }
//
//    /**
//     * 向SD卡写入日志
//     *
//     * @param type    日志类型，INFO = 1，VERBOSE = 2，DEBUG = 3，ERROR = 4
//     * @param tag     消息源
//     * @param message 消息内容
//     */
//    public static void writeLog(int type, String tag, String message) {
//        if (type < MAX_LOG) {
//            return;
//        }
//        BufferedWriter writer = null;
//        try {
//            String str = MyTime.getFullTime() + "=[TYPE:]" + type + ",[TAG:]" + tag + ",[MSG:]" + message + "\n";
//            Uri uri = Uri.parse(Protocol.getDeflultPath() + "/facsimilemedia/Logs/" + "duoxun_" + MyTime.getNowTime() + ".txt");
//            File file = new File(uri.getPath());
//            if (!file.exists()) {
//                file.createNewFile();
//                collectDeviceInfo();
//            }
//            writer = new BufferedWriter(new FileWriter(file, true));
//            writer.write(str);
//            writer.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != writer) {
//                    writer.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    public static void writeXml(String message) {
//        BufferedWriter writer = null;
//        try {
//            String paths = Environment.getExternalStorageDirectory()+ "/facsimilemedia/Xml";
//            File file1 = new File(paths);
//            if(!file1.exists()){
//                file1.mkdir();
//            }
//            Uri uri = Uri.parse(paths + "/android_deviceId.xml");
//            File file = new File(uri.getPath());
//            if (!file.exists()) {
//                file.createNewFile();
//            }else{
//                file.delete();
//                file.createNewFile();
//            }
//            writer = new BufferedWriter(new FileWriter(file, true));
//            writer.write(message);
//            writer.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != writer) {
//                    writer.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    /**
//     * 收集设备参数信息
//     */
//    private static void collectDeviceInfo() {
//        StringBuffer sb = new StringBuffer();
//        sb.append("\n[===============VERSION============]\n");
//        String[] str = DeviceManager.getVersion();
//        sb.append("KernelVersion:" + str[0] + "\n");
//        sb.append("firmware version:" + str[1] + "\n");
//        sb.append("model:" + str[2] + "\n");
//        sb.append("system version:" + str[3] + "\n");
//
//        sb.append("[===============DeviceId============]\n");
//        DeviceManager.setContext(context);
//        String deviceId = DeviceManager.getDeviceId();
//        sb.append("DeviceId:" + deviceId + "\n");
//
//        sb.append("[===============SerialId============]\n");
//        String serialId = DeviceManager.getSerialId();
//        sb.append("SerialId:" + serialId + "\n");
//
//        sb.append("[===============CPU============]\n");
//        String[] strCpu = DeviceManager.getCpuInfo();
//        sb.append(strCpu[0] + "===" + strCpu[1] + "\n");
//
//        sb.append("[===============Memory============]\n");
//        String totalMemory = DeviceManager.getTotalMemory();
//        String avaiableMemory = DeviceManager.getAvailableMemory() + "";
//        sb.append("totalMemory:" + totalMemory + "===avaiableMemory:" + avaiableMemory + "\n");
//
//        sb.append("[===============SDCardMemory============]\n");
//        String[] sdMemory = DeviceManager.getSDCardMemory();
//        sb.append("totalMemory:" + sdMemory[0] + "===avaiableMemory:" + sdMemory[1] + "\n");
//
//        sb.append("[===============Other============]\n");
//        String other = DeviceManager.getOtherInfo();
//        sb.append(other);
//
//        sb.append("\n[===============StartUpTime============]\n");
//        String startUpTime = DeviceManager.getStartUpTime();
//        sb.append(startUpTime);
//        LogManager.writeLog(LogManager.INFO, "DEVICE", sb.toString());
//    }

public class LogManager {
    private static String TAG = "LogManager";
    // 锁，是否关闭Log日志输出
    public static boolean LogOFF = false;
    // 是否关闭VERBOSE输出
    public static boolean LogOFF_VERBOSE = true;
    // 是否关闭debug输出
    public static boolean LogOFF_DEBUG = true;
    private static Context context;
    private static final int MAX_SAVE_DAY = 30;//最多保存30天的日志
    private static String preDate = "";
    /**** 5中Log日志类型 *******/
    /**
     * 信息日志类型
     */
    public static final int INFO = 1;
    /**
     * 详细信息日志类型
     */
    public static final int VERBOSE = 2;
    /**
     * 调试日志类型
     */
    public static final int DEBUG = 3;
    /**
     * 错误日志类型
     */
    public static final int ERROR = 5;


    public static int MAX_LOG = 1;
    /**
     * 警告调试日志类型
     */
    public static final int WARN = 4;
    public static final int TRACE = 6;
    public static final int FATAL = 7;
    private static final String dirPath = Environment.getExternalStorageDirectory() + "/szj/logs/";
    private static final String path = "file:///" + dirPath;
    private static final String fileName = "myLog";
    private static final String external = ".txt";
    private static String token;
    private static Object showObj = new Object();
    private static Object writeObj = new Object();
    private static String badToken = "bad token";
    /**
     * 是否实时向服务器发日志,默认为false
     *
     * @param isSendToServer
     */

    /**
     * 设置当前用户通信token
     *
     * @param token
     */
    public static void setToken(String token) {
        LogManager.token = token;
    }

    /**
     * 设置设备ID
     *
     * @param devicesId
     */
    public static void setDevicesId(String devicesId) {
        LogManager.devicesId = devicesId;
    }

    private static String devicesId;
    private static boolean isSendToServer = false;

    /**
     * 设置上下文
     *
     * @param context
     */
    public static void setContext(Context context) {
        LogManager.context = context;
    }

    private static boolean isShowLogOFF = true;
    private static final String serverUrl = "https://api.facsimilemedia.com/logs";
    private final static int ITEM_COUNT = 100;//每次从数据库获取指定数量数据发送给SocketIO服务器

    /**
     * 是否在控制台显示日志信息，默认不显示
     *
     * @param isShowLogOFF
     */
    public static void setIsShowLogOFF(boolean isShowLogOFF) {
        LogManager.isShowLogOFF = isShowLogOFF;
    }

    /**
     * 显示，打印日志
     */
    public static void LogShow(String tag, String message, int type) {
        if (isShowLogOFF || type < MAX_LOG) {
            return;
        }
        synchronized (showObj) {
            if (!LogOFF) {
                switch (type) {
                    case DEBUG:
                        Log.d(tag, message);
                        break;
                    case ERROR:
                        Log.e(tag, message);
                        break;
                    case INFO:
                        Log.i(tag, message);
                        break;
                    case VERBOSE:
                        if (!LogOFF_VERBOSE) {
                            Log.v(tag, message);
                        }
                        break;
                    case WARN:
                        Log.w(tag, message);
                        break;
                }
            }
        }
    }

    /**
     * 将日志文件发送至OSS服务器
     */
    public static void sendLogFileToServer() {
    }

    /**
     * 向SD卡写入日志
     *
     * @param type 日志类型，INFO = 1，VERBOSE = 2，DEBUG = 3，ERROR = 4
     * @param tag  消息源
     * @param msg  消息详情
     */
    public static void writeLog(int type, String tag, String msg) {
        if (type < MAX_LOG) {
            return;
        }
        String str = MyTime.getFullTime() + "=[TYPE:]" + type + ",[TAG:]" + tag + ",[MSG:]" + msg + "\n";
        writeLog(type, str, isSendToServer);
    }

    /**
     * 向SD卡写入日志
     *
     * @param type   日志类型，INFO = 1，VERBOSE = 2，DEBUG = 3，ERROR = 4
     * @param tag    消息源
     * @param msg    消息详情
     * @param isSend 是否将消息发送给服务器
     */
    public static void writeLog(int type, String tag, String msg, boolean isSend) {
        if (type < MAX_LOG) {
            return;
        }
        String str = MyTime.getFullTime() + "=[TYPE:]" + type + ",[TAG:]" + tag + ",[MSG:]" + msg + "\n";
        writeLog(type, str, isSend);
    }

    /**
     * 向SD卡写入日志
     *
     * @param type 日志类型，INFO = 1，VERBOSE = 2，DEBUG = 3，ERROR = 4
     * @param tag  消息源
     * @param code 消息码
     */
    public static void writeLog(int type, String tag, int code) {
        if (type < MAX_LOG) {
            return;
        }
        String str = MyTime.getFullTime() + "=[TYPE:]" + type + ",[TAG:]" + tag + ",[code:]" + code + "\n";
        writeLog(type, str, isSendToServer);
    }

    /**
     * 向SD卡写入日志
     *
     * @param type   日志类型，INFO = 1，VERBOSE = 2，DEBUG = 3，ERROR = 4
     * @param tag    消息源
     * @param code   消息码
     * @param isSend 是否向服务器发出
     */
    public static void writeLog(int type, String tag, int code, boolean isSend) {
        if (type < MAX_LOG) {
            return;
        }
        String str = MyTime.getFullTime() + "=[TYPE:]" + type + ",[TAG:]" + tag + ",[code:]" + code + "\n";
        writeLog(type, str, isSend);
    }

    /**
     * 日志写入
     *
     * @param type   日志类型，CODE
     * @param msg    消息内容
     * @param isSend 是否向服务器发出
     */
    private static void writeLog(int type, String msg, boolean isSend) {
        synchronized (writeObj) {
            OutputStreamWriter writer = null;
            BufferedWriter out = null;
            try {
                String tempFile = path + fileName + "_" + MyTime.getTime() + external;
                Uri uri = Uri.parse(tempFile);//如果当天的日志文件不存在则创建
                FileManager.isDirExist(dirPath + "/", true);
                FileManager.isFileExist(tempFile, true);
                writer = new OutputStreamWriter(new FileOutputStream(uri.getPath(), true), Charset.forName("gbk"));//一定要使用gbk格式
                out = new BufferedWriter(writer);
                out.write(msg);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != out) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String cunDate = MyTime.getTime();
            if (!cunDate.equals(preDate)) {
                preDate = cunDate;
                deleteLogs();
            }
        }
    }

    /**
     * 根据日志等级获得日志说明
     *
     * @param type
     * @return
     */
    private static String getLevel(int type) {
        String result = "";
        switch (type) {
            case 1:
                result = "Info";
                break;
            case 2:
                result = "Verbose";
                break;
            case 3:
                result = "Gebug";
                break;
            case 4:
                result = "Warn";
                break;
            case 5:
                result = "Error";
                break;
            case 6:
                result = "Trace";
                break;
            case 7:
                result = "Fatal";
                break;
        }
        return result;
    }


    /**
     * 向SD卡写入日志
     *
     * @param type 日志k源
     * @param ex   消息详情
     */
    @TargetApi(19)
    public static void writeLog(int type, String tag, Exception ex) {
        if (type < MAX_LOG) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (null != ex) {
            sb.append("Cause:" + ex.getCause() + "\n");
            sb.append("LocalizedMessage:" + ex.getLocalizedMessage() + "\n");
            sb.append("StackTrace:" + ex.getStackTrace() + "\n");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sb.append("Suppressed:" + ex.getSuppressed() + "\n");
            }
            sb.append("Class:" + ex.getClass() + "\n");
        }
        String str = MyTime.getFullTime() + "=[TYPE:]" + type + ",[TAG:]" + tag + ",[MSG:]" + sb.toString() + "\n";
        writeLog(type, str, isSendToServer);
    }

    /**
     * 向SD卡写入日志
     *
     * @param type   日志k源
     * @param ex     消息详情
     * @param isSend 是否将消息发送给服务器
     */
    @TargetApi(19)
    public static void writeLog(int type, String tag, Exception ex, boolean isSend) {
        if (type < MAX_LOG) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        if (null != ex) {
            sb.append("Cause:" + ex.getCause() + "\n");
            sb.append("LocalizedMessage:" + ex.getLocalizedMessage() + "\n");
            sb.append("StackTrace:" + ex.getStackTrace() + "\n");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sb.append("Suppressed:" + ex.getSuppressed() + "\n");
            }
            sb.append("Class:" + ex.getClass() + "\n");
        }
        String str = MyTime.getFullTime() + "=[TYPE:]" + type + ",[TAG:]" + tag + ",[MSG:]" + sb.toString() + "\n";
        writeLog(type, str, isSend);
    }

    /**
     * 删除超期的日志信息
     */
    private static void deleteLogs() {
        File dirFile = new File(dirPath + "/");
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return;
        }
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        String cunDate = MyTime.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                String tempDate = files[i].getName().substring(files[i].getName().indexOf('_') + 1, files[i].getName().indexOf('.'));
                long m = 0;
                try {
                    m = sdf.parse(cunDate).getTime() - sdf.parse(tempDate).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (m / (1000 * 60 * 60 * 24) > MAX_SAVE_DAY) {
                    FileManager.deleteFile(files[i].getAbsolutePath());
                }
            }
        }
    }

    /**
     * 释放资源
     */
    public static void release() {
        isShowLogOFF = true;
        isSendToServer = false;
    }
}


