package com.szj.library.imagetovideo;

import com.szj.library.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作类
 * 解析XML文件
 * 创建文件夹
 * 获取并删除7天前的文件
 * Created by szj on 2015/10/13.
 */
public class FileUtil {
    private static String TAG = "FileUtil";
    private static String defaultUrl = "";
    /**
     * 创建文件夹
     * @param filePath
     */
    public static boolean makeRootDirectory(String filePath) {
        File file = null;
        boolean success = true;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                success = file.mkdir();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (success) {
            return true;
        } else {
            return false;
        }
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
     * 检查扩展名，得到.txt格式的文件
     *
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
        L.i("开始删除" + root.getPath() + "下的文件");
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
     * @param root  文件夹路径
     * @param file1  不删除的文件1
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
     * 获取一个路径下的所有文件 放入一个列表
     * @param path
     * @return
     */
    public static ArrayList<Images> getImagePaths(String path) {
        ArrayList<Images> paths = new ArrayList<Images>();
        File file = new File(path);
        File[] fs = null;
        if (file.exists() && file != null && file.isDirectory()) {
            fs = file.listFiles();
        }
        if (fs != null && fs.length > 0) {
            for (int i = 0; i < fs.length; i++) {
                if(fs[i].getPath().endsWith(".png") || fs[i].getPath().endsWith("jpg")) {
                    paths.add(new Images(fs[i].getPath(), 20));
                }
            }
        }
        return paths;
    }
}
