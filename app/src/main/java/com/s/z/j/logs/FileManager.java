package com.s.z.j.logs;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * 作者：王强
 * 日期： 2015/10/23
 * 版本： V1.0
 * 说明：文件管理类
 */
public class FileManager {
    private static final String TAG = "FileManager";
    private static boolean flag = false;

    /**
     * 判断路径是否存在
     *
     * @param path     路径
     * @param isCreate 如果路径不存在是否创建
     * @return 路径存在或路径创建结果
     */
    public static boolean isDirExist(String path, boolean isCreate) {
        File destDir = new File(path);
        boolean result = true;
        if (!destDir.exists()) {
            result = false;
        }
        if (!result && isCreate) {
            result = destDir.mkdirs();//创建多级目录
        }
        return result;
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName 文件（含文件名）
     * @param isCreate 如果文件不存在是否创建
     * @return 文件存在或文件创建结果
     */
    public static boolean isFileExist(String fileName, boolean isCreate) {
        Uri uri = Uri.parse(fileName);
        File file = new File(uri.getPath());
        boolean result = true;
        if (!file.exists()) {
            result = false;
        }
        if (!result && isCreate) {
            try {
                result = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 删除指定名称的文件
     *
     * @param fileName 文件（含文件名）
     * @return 删除成功或失败
     */
    public static boolean deleteFile(String fileName) {
        boolean result = false;
        if (isFileExist(fileName, false)) {
            Uri uri = Uri.parse(fileName);
            File file = new File(uri.getPath());
            result = file.delete();
        }
        return result;
    }

    /**
     * 按行向指定文件追加写入文件，如果文件不存在则先创建再写入
     *
     * @param fileName 文件名
     * @param content  写入的内容
     */
    public static void appendWrite(String fileName, String content) {
        boolean result = isFileExist(fileName, true);
        if (result) {
            OutputStreamWriter writer = null;
            BufferedWriter out = null;
            try {
                Uri uri = Uri.parse(fileName);//如果当天的日志文件不存在则创建
                writer = new OutputStreamWriter(new FileOutputStream(uri.getPath(), true), Charset.forName("utf-8"));
                out = new BufferedWriter(writer);
                out.write(content + "\n");
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
        }
    }

    /**
     * 按行读取文件
     *
     * @param fileName
     * @return
     */
    public static ArrayList<String> readFile(String fileName) {
        ArrayList<String> content = new ArrayList<>();
        if (!isFileExist(fileName, false)) {
            return content;
        }
        try {
            InputStream instream = new FileInputStream(fileName);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream, "utf-8");
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    content.add(line);
                }
                instream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 复制文件到指定文件
     *
     * @param fromFile 源文件
     * @param toFile   目标文件
     */
    public static void copyFile(String fromFile, String toFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            if (isFileExist(fromFile, false)) { //文件存在时
                InputStream inStream = new FileInputStream(fromFile); //读入原文件
                isFileExist(toFile, true);
                FileOutputStream fs = new FileOutputStream(toFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            LogManager.writeLog(LogManager.ERROR, TAG, e.toString());
            e.printStackTrace();

        }
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存图片至指定路径
     *
     * @param b
     * @param fileName
     */
    public static void savePic(Bitmap b, String fileName) {
        isFileExist(fileName, true);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
        }
        if (null != fos) {
            try {
                b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                LogManager.writeLog(LogManager.ERROR, TAG, e.toString());
                e.printStackTrace();
            } finally {
            }
        }
    }

    /**
     * 将文件写到本地区
     * @param ins  、、输入流
     * @param file  写入文件
     */

    public static void inputstreamtofile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
