package com.s.z.j.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 简单访问网络工具类
 * get请求访问网络
 * post请求访问网络
 * 下载文件
 * 获取网络类型
 * 显示网络图片
 * 访问网络获取 XML文件流
 */
public class HttpUtils {

    /**
     * 访问网络获取 XML文件流
     * @param urlstr
     * @return
     */
    public static InputStream getInputStreamFromURL(String urlstr){
        HttpURLConnection connection;
        URL url;
        InputStream stream=null;
        try{
            url=new URL(urlstr);
            connection =(HttpURLConnection)url.openConnection();
            connection.connect();
            stream = connection.getInputStream();
        }catch(MalformedURLException e){
            e.printStackTrace();
            return null;
        }catch(IOException e1){
            e1.printStackTrace();
            return null;
        }
        return stream;
    }
    /**
     * 从服务器取图片
     *http://bbs.3gstdy.com
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 通过HttpUrlConnection发送GET请求
     * HttpUrlConnection是JDK里提供的联网API，
     * 我们知道Android SDK是基于Java的，
     * 所以当然优先考虑HttpUrlConnection这种最原始最基本的API，
     * 其实大多数开源的联网框架基本上也是基于JDK的HttpUrlConnection进行的封装罢了，
     * 掌握HttpUrlConnection需要以下几个步骤：
     * 1将访问的路径转换成URL。
     * 2，通过URL获取连接。
     * 3，设置请求方式。
     * 4，设置连接超时时间。
     * 5，设置请求头的信息。
     * 6，获取响应码
     * 7，针对不同的响应码，做不同的操作
     * 7.1，请求码200，表明请求成功，获取返回内容的输入流
     *
     * @return
     */
    public static String loginByGet(String path) {
//        String path ="http://192.168.0.107:8080/WebTest/LoginServerlet?username=" + username + "&password=" + password;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");//5，设置请求头的信息。
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream(); // 字节流转换成字符串
                return streamToString(is);
            } else {
                return "网络访问失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "网络访问失败";
        }
    }


    /**
     * 通过HttpUrlConnection发送POST请求
     * 目前演示的是查询快递接口
     *
     * @param type
     * @param postid
     * @return
     */
    public static String loginByPost(String path, String type, String postid) {
//        String path = "http://192.168.0.107:8080/WebTest/LoginServerlet";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String data = "type=" + type + "&postid=" + postid;//组合参数
            conn.setRequestProperty("Content-Length", data.length() + "");
            // POST方式，其实就是浏览器把数据写给服务器
            conn.setDoOutput(true); // 设置可输出流
            OutputStream os = conn.getOutputStream(); // 获取输出流
            os.write(data.getBytes()); // 将数据写给服务器
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                return streamToString(is);
            } else {
                return "网络访问失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "网络访问失败";
        }
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e("SZJ", e.toString());
            return "";
        }
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static String getNetType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {//wifi网络
                return "wifi网络";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {// 非wifi网络
                return "移动网络";
            }
        }
        return "无网络";
    }

    /**
     * 文件下载
     *
     * @param handler         用于传递消息
     * @param url：文件的下载地址
     * @param path：文件保存到本地的地址
     * @throws IOException
     */
    public static void down_file(Handler handler, String url, String path) throws IOException {
        int downLoadFileSize;
        int fileSize;
        String filename;
        //下载函数
        filename = url.substring(url.lastIndexOf("/") + 1);
        //获取文件名
        URL myURL = new URL(url);
        URLConnection conn = myURL.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        fileSize = conn.getContentLength();//根据响应获取文件大小
        if (fileSize <= 0) throw new RuntimeException("无法获知文件大小 ");
        if (is == null) throw new RuntimeException("stream is null");
        File file1 = new File(path);
        File file2 = new File(path + filename);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        if (!file2.exists()) {
            file2.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(path + filename);
        //把数据存入路径+文件名
        byte buf[] = new byte[1024];
        downLoadFileSize = 0;
        Message mes = handler.obtainMessage();
        mes.arg2 = fileSize;
        mes.what = 0;
        handler.sendMessage(mes);//下载前
        do {
            //循环读取
            int numread = is.read(buf);
            if (numread == -1) {
                break;
            }
            fos.write(buf, 0, numread);
            downLoadFileSize += numread;
            Message message = handler.obtainMessage();
            message.arg1 = downLoadFileSize;
            message.arg2 = downLoadFileSize * 100 / fileSize;
            message.what = 1;
            handler.sendMessage(message);//下载中，发送下载进度，页面更新
        } while (true);
        handler.sendEmptyMessage(2);//下载完成，页面提示
        try {
            is.close();
        } catch (Exception ex) {
            Log.e("tag", "error: " + ex.getMessage(), ex);
        }
    }

    /**
     * 获取MAC地址方法1
     * 通过访问 WifiManager
     * @param context
     * @return
     */
    public static String[] getMacByWifiManager(Context context) {
        String[] other = {"null", ""};
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        try {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo.getMacAddress() != null) {
                other[0] = wifiInfo.getMacAddress();
                other[1] = intToIp(wifiInfo.getIpAddress());
            } else {
                other[0] = "Fail";
                other[1] = "0";
            }
        } catch (Exception e) {
            other[0] = "Fail";
            other[1] = "0";
        }
        return other;
    }

    /**
     * 获取MAC地址方法2
     * 通过读取文件获取
     *
     * @return
     */
    public static String getMacByFile() {
        String macSerial = " ";
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }


    /**
     * 获取IP地址
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    /**
     * ipAddress转换为ip地址
     * @param i
     * @return
     */
    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    /**
     * 获取公网IP
     * @return
     */
    public static String GetNetIp() {
        String IP = "";
        try {
            String address = "http://ipecho.net/plain";
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url .openConnection();
            connection.setUseCaches(false);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                // 将流转化为字符串
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String tmpString = "";
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString);
                }
                IP = retJSON.toString();
            } else {
                IP = "";
                Log.e("提示", "网络连接异常，无法获取IP地址！");
            }
        } catch (Exception e) {
            IP = "";
            Log.e("提示", "获取IP地址时出现异常，异常信息是：" + e.toString());
        }
        return IP;
    }

}
