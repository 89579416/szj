package com.s.z.j.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.s.z.j.entity.ApkItem;
import com.s.z.j.entity.Person;
import com.s.z.j.url.Protocol;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 文件操作类
 * 解析XML文件
 * 创建文件夹
 * 获取并删除7天前的文件
 * 序列化和反序列化
 * Created by szj on 2015/10/13.
 */
public class FileUtil {
    private static String TAG = "FileUtil";
    private static String defaultUrl = "";
    long startTime = 0l;
    long endTime = 0l;
    /**
     * 精简版：利用dom4j-1.6.1.jar 解析
     * 示例XML文件在项目根目录  updates.xml
     * @param stream
     * @return
     */
    public static List<Person> getXml(InputStream stream) {
        List<Person> personList = new ArrayList<Person>();
        try {
            SAXReader reader = new SAXReader();
            Document document = null;
            document = reader.read(stream);
            Element root = document.getRootElement();
            //
            Element terminal = root.element("terminal");
            Element android = terminal.element("android");
            String main_appid;
            String main_codebase;
            String main_version;
            //android-->main
            //通用版本在main下面
            Element branches;
            List<Element> mainElements;
            /** 获取宿主更新信息 */
            branches = android.element("branches");//这个下面有两个
            mainElements = branches.elements();// 这里怎么得到数组形式
            for (Element appElement : mainElements) {
                Element branch = appElement.element("branch");
                main_appid = appElement.attribute("appid").getValue();
                main_codebase = appElement.element("updatecheck").attribute("codebase").getValue();
                main_version = appElement.element("updatecheck").attribute("version").getValue();
                Person person1 = new Person();
                person1.setAppid(main_appid);
                person1.setCodebase(main_codebase);
                person1.setVersion(main_version);
                person1.setType("main");
                personList.add(person1);
            }
            /** 获取插件更新信息  */
            Element plugins = android.element("plugins");//这个下面有两个
            List<Element> childElements = plugins.elements();// 这里怎么得到数组形式
            for (Element appElement : childElements) {
                Element app = appElement.element("app");
                String plugins_appid = appElement.attribute("appid").getValue();
                String plugins_codebase = appElement.element("updatecheck").attribute("codebase").getValue();
                String plugins_version = appElement.element("updatecheck").attribute("version").getValue();
                Person person1 = new Person();
                person1.setAppid(plugins_appid);
                person1.setCodebase(plugins_codebase);
                person1.setVersion(plugins_version);
                person1.setType("plugins");
                personList.add(person1);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return personList;
    }

    /**
     * 获取APK文件
     */
    public static ArrayList<ApkItem> startLoad(Context context) {
        ArrayList<ApkItem> data = new ArrayList<ApkItem>();
        File file = new File(Protocol.getDefaultUrl() + "/facsimilemedia/Plugin");
        List<File> apks = new ArrayList<File>(10);
        for (File apk : file.listFiles()) {
            if (apk.exists() && apk.getPath().toLowerCase().endsWith(".apk")) {
                apks.add(apk);
            }
        }
        PackageManager pm = context.getPackageManager();
        for (final File apk : apks) {
            try {
                if (apk.exists() && apk.getPath().toLowerCase().endsWith(".apk")) {
                    final PackageInfo info = pm.getPackageArchiveInfo(apk.getPath(), 0);
                    if (info != null) {
                        try {
                            data.add(new ApkItem(context, info, apk.getPath()));
                        } catch (Exception e) {
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        return data;
    }

    /**
     * 从内存卡指定目录下获取指定格式的文件
     *
     * @return
     */
    public static List<String> getTxtPathFromSD() {
        // 图片列表
        List<String> picList = new ArrayList<String>();
        // 得到sd卡内路径
        String imagePath = defaultUrl + "/facsimilemedia/Logs";
        // 得到该路径文件夹下所有的文件
        File mfile = new File(imagePath);
        if (!mfile.exists()) {
            return null;
        }
        File[] files = mfile.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                picList.add(file.getPath());
            }

        }
        // 返回得到的图片列表
        return picList;
    }

    /**
     * 删除7天以前的文件
     */
    public static void deleteFile() {
        List<String> txtFileList = new ArrayList<String>();
        txtFileList = getTxtPathFromSD();
        if (txtFileList != null && txtFileList.size() > 0)
            for (int i = 0; i < txtFileList.size(); i++) {
                Log.i(TAG, txtFileList.get(i));
                File f = new File(txtFileList.get(i));
                Date d1 = new Date(f.lastModified()); //这个是最后修改时间
                Date d2 = new Date(System.currentTimeMillis());// 获得当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                LogManager.writeLog(1, TAG, txtFileList.get(i) + "最后修改时间：" + sdf.format(d1));
//                LogManager.writeLog(1, TAG, "当前时间：" + sdf.format(d2));
                try {
                    if (Math.abs(((d2.getTime() - d1.getTime()) / (24 * 3600 * 1000))) >= 7) {
//                        LogManager.writeLog(1, TAG, txtFileList.get(i) + "时间大于7天，即将删除");
                        try {
                            f.delete();
//                            LogManager.writeLog(1, TAG, txtFileList.get(i) + "己删除");
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    /**
     * 检查扩展名，得到.txt格式的文件
     * @param fName
     * @return
     */
    public static boolean checkIsImageFile(String fName) {
        boolean isTxtFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("txt")) {
            isTxtFile = true;
        } else {
            isTxtFile = false;
        }
        return isTxtFile;
    }

    /**
     * 删除一个目录下的文件
     *
     * @param root
     */
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 删除一个目录下的文件
     *
     * @param root  文件夹路径
     * @param file1 不删除的文件1
     * @param file2 不删除的文件2
     */
    public static void deleteAllFiles(File root, String file1, String file2) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f, file1, file2);
                    try {
                        if (!f.getName().equals(file1) && !f.getName().equals(file2)) {
                            f.delete();
                        }
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f, file1, file2);
                        try {
                            if (!f.getName().equals(file1) && !f.getName().equals(file2)) {
                                f.delete();
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 删除一个目录下的文件
     *
     * @param root 路径
     * @param file 要保留的文件
     */
    public static void deleteAllFiles(File root, String file) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f, file);
                    try {
                        if (!f.getName().equals(file)) {
                            f.delete();
                        }
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
//                        deleteAllFiles(f, file);
                        try {
                            L.i(f.getName() + "   " + file);
                            if (!f.getName().equals(file)) {
                                boolean dele = f.delete();
                                if (dele) {
                                } else {
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 从内存卡指定目录下获取指定格式的文件
     *
     * @return
     */
    public static ArrayList<String> getPlayFile(String path) {
        // 图片列表
        ArrayList<String> picList = new ArrayList<String>();
        // 得到该路径文件夹下所有的文件
        File mfile = new File(path);
        if (!mfile.exists()) {
            return null;
        }
        File[] files = mfile.listFiles();
        if(files != null){
            // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (checkIsPlayFile(file.getPath())) {
                    picList.add(file.getPath());
                }
            }
        }
        // 返回得到的图片列表
        return picList;
    }
    /**
     * 检查扩展名，得到.mp4格式的文件
     * @param fName
     * @return
     */
    public static boolean checkIsPlayFile(String fName) {
        boolean isTxtFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if ("mp4".equals(FileEnd) || "mov".equals(FileEnd)){
            isTxtFile = true;
        } else {
            isTxtFile = false;
        }
        return isTxtFile;
    }

    /**
     * 加载本地图片
     * http://bbs.3gstdy.com
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * create file
     */
    public static void  createFile() {
        defaultUrl = Environment.getExternalStorageDirectory() + "";
        /**创建我们软件的专用文件夹*/
        makeRootDirectory(defaultUrl + "/abcde");
        /**创建存放日志的文件夹*/
        makeRootDirectory(defaultUrl + "/abcde/Logs");

        makeRootDirectory(defaultUrl + "/html");
    }
    /**
     * create file
     */
    public static void  createFile(String name) {
        defaultUrl = Environment.getExternalStorageDirectory() + "";
        makeRootDirectory(defaultUrl + "/"+name);
    }
    /**
     * 创建文件夹
     * @param filePath
     */
    public static boolean makeRootDirectory(String filePath) {
        java.io.File file = null;
        boolean success = true;
        try {
            file = new java.io.File(filePath);
            if (!file.exists()) {
                success = file.mkdir();
                L.i("success="+success);
            } else {
                L.i("success="+success);
            }
        } catch (Exception e) {
            e.printStackTrace();
            L.i("创建失败："+e.toString());
        }
        if (success) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向SD卡写入日志
     *
     * @param message 消息内容
     */
    public static void writeLog(String message) {
        BufferedWriter writer = null;
        try {
            String str = getFullTime()  +"===="+ message + "\n";
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory()  + "/xiaofei/Logs/" + "xiaofei_" + getNowTime() + ".txt");
            File file = new File(uri.getPath());
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(str);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得当前日期，yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getFullTime() {
        java.sql.Date cunDate = new java.sql.Date(System.currentTimeMillis());// 获得当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(cunDate);
        return date;
    }


    /**
     * 获得当前日期，yyyyMMdd
     *
     * @return
     */
    public static String getNowTime() {
        java.sql.Date cunDate = new java.sql.Date(System.currentTimeMillis());// 获得当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cunDate);
        return date;
    }

    public static ArrayList  getAllMp4File(){
        File path = Environment.getExternalStorageDirectory();// 获得SD卡路径
        // File path = new File("/mnt/sdcard/");
        File[] files = path.listFiles();// 读取
        return getFileName(files);
    }

    public static ArrayList name =  new ArrayList();
    public static ArrayList getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    Log.i("BBBB", "若是文件目录。继续读1" + file.getName().toString() + file.getPath().toString());
                    getFileName(file.listFiles());
                    Log.i("BBBB", "若是文件目录。继续读2" + file.getName().toString() + file.getPath().toString());
                } else {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt")) {
                        HashMap map = new HashMap();
                        String s = fileName.substring(0,
                                fileName.lastIndexOf(".")).toString();
                        Log.i("zeng", "文件名txt：：   " + s);
                        map.put("Name", fileName.substring(0,
                                fileName.lastIndexOf(".")));
                        name.add(map);
                    }
                }
            }
        }
        L.i("当前有"+name.size()+"个视频");
        return name;
    }

    /**
     * 获取一个目录下的某种类型的文件
     * @param dir
     * @return
     */
    public static  ArrayList <String>  myListFiles(String dir) {
        ArrayList <String> data = new ArrayList<String>();
        File directory = new File(dir);
        if (!directory.isDirectory()) {
            System.out.println("No directory provided");
            L.i("没有目录");
            return data;
        }else{
            L.i("有目录");
        }
        File[] files = directory.listFiles(filefilter);
        if(files != null) {
            for (File f : files) {
                data.add(f.getPath());
                System.out.println(f.getName());
                L.i("myListFiles：" + f.getName());
            }
        }
        L.i("data.size="+data.size());
        return data;
    }

    /**
     * 文件名过滤
     */
    public static FileFilter filefilter = new FileFilter() {
        public boolean accept(File file) {
            if (file.getName().endsWith(".mp4")) {
                return true;
            }
            return false;
        }
    };
    /**
     * 保存查找到的指定格式文件路径
     */
    public  static ArrayList <String> data;

    /**
     * 获取一个路径下的所有MP4文件，包括子文件夹里面的
     * @param root
     * @return
     */
    public  static ArrayList <String> getFile(File root){
        data = new  ArrayList <String>();
        getAllFiles(root);
        return data;
    }

    /**
     * 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
     * @param root
     */
    public static  void   getAllFiles(File root){
        File files[] = root.listFiles();
        if(files != null){
            for (File f : files){
                if(f.isDirectory()){
                    getAllFiles(f);
                }else{
                    if(f.getName().endsWith(".mp4")){
                        L.i("检测到mp4文件：" + f.getPath());
                        data.add(f.getPath());
                    }
                    if(f.getName().endsWith(".mov")){
                        L.i("检测到mov文件：" + f.getPath());
                        data.add(f.getPath());
                    }
                }
            }
        }
    }

    /**
     * 序列化对象
     * @param person
     * @return
     * @throws IOException
     */
    private String serialize(Person person) throws IOException {
        startTime = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(person);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        endTime = System.currentTimeMillis();
        Log.d("serial", "序列化耗时为:" + (endTime - startTime));
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Person deSerialization(String str) throws IOException,
            ClassNotFoundException {
        startTime = System.currentTimeMillis();
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Person person = (Person) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        endTime = System.currentTimeMillis();
        Log.d("serial", "反序列化耗时为:" + (endTime - startTime));
        return person;
    }

    /**
     * 获取一个路径下的所有文件 放入一个列表
     * @param path
     * @return
     */
    public static List<String> getPaths(String path) {
        List<String> paths = new ArrayList<>();
        File file = new File(path);
        File[] fs = null;
        if (file.exists() && file != null && file.isDirectory()) {
            fs = file.listFiles();
        }
        if (fs != null && fs.length > 0) {
            for (int i = 0; i < fs.length; i++) {
                paths.add(fs[i].getAbsoluteFile().toString());
            }
        }
        return paths;
    }

}
