package com.s.z.j.newUtils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/11
 *     desc  : SD����ع�����
 * </pre>
 */
public class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * ��ȡ�豸SD���Ƿ����
     *
     * @return true : ����<br>false : ������
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * ��ȡ�豸SD��·��
     * <p>һ����/storage/emulated/0/</p>
     *
     * @return SD��·��
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    public static String getSDCardCacheDir(Context context){
        return context.getExternalCacheDir().getPath();
    }

//    /**
//     * ����SD����ʣ��ռ�
//     *
//     * @return ����-1��˵��û�а�װsd��
//     */
//    public static long getFreeBytes(int unit) {
//        long freeSpace = 0;
//        if (isSDCardEnable()) {
//            try {
//                File path = Environment.getExternalStorageDirectory();
//                StatFs stat = new StatFs(path.getPath());
//                long blockSize = 0;
//                long availableBlocks = 0;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                    blockSize = stat.getBlockSizeLong();
//                    availableBlocks = stat.getAvailableBlocksLong();
//                }
//                freeSpace = (availableBlocks * blockSize) / unit;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            return -1;
//        }
//        return (freeSpace);
//    }


//    /**
//     * ��ȡSD����ʣ������ ��λbyte
//     *
//     * @return
//     */
//    public static long getSDCardAllSize() {
//        if (isSDCardEnable()) {
//            StatFs stat = new StatFs(getSDCardPath());
//            // ��ȡ���е����ݿ������
//            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
//            // ��ȡ�������ݿ�Ĵ�С��byte��
//            long freeBlocks = stat.getAvailableBlocks();
//            return freeBlocks * availableBlocks;
//        }
//        return 0;
//    }
//
//    /**
//     * ��ȡָ��·�����ڿռ��ʣ����������ֽ�������λbyte
//     *
//     * @param filePath
//     * @return �����ֽ� SDCard���ÿռ䣬�ڲ��洢���ÿռ�
//     */
//    public static long getFreeBytes(String filePath) {
//        // �����sd�����µ�·�������ȡsd����������
//        if (filePath.startsWith(getSDCardPath())) {
//            filePath = getSDCardPath();
//        } else {// ������ڲ��洢��·�������ȡ�ڴ�洢�Ŀ�������
//            filePath = Environment.getDataDirectory().getAbsolutePath();
//        }
//        StatFs stat = new StatFs(filePath);
//        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
//        return stat.getBlockSize() * availableBlocks;
//    }
//
//    /**
//     * ��ȡϵͳ�洢·��
//     *
//     * @return
//     */
//    public static String getRootDirectoryPath() {
//        return Environment.getRootDirectory().getAbsolutePath();
//    }
//
//    /**
//     * Check if the file is exists
//     *
//     * @param filePath
//     * @param fileName
//     * @return
//     */
//    public static boolean isFileExistsInSDCard(String filePath, String fileName) {
//        boolean flag = false;
//        if (isSDCardEnable()) {
//            File file = new File(filePath, fileName);
//            if (file.exists()) {
//                flag = true;
//            }
//        }
//        return flag;
//    }
//
//    /**
//     * Write file to SD card
//     *
//     * @param filePath
//     * @param filename
//     * @param content
//     * @return
//     * @throws Exception
//     */
//    public static boolean saveFileToSDCard(String filePath, String filename, String content)
//            throws Exception {
//        boolean flag = false;
//        if (isSDCardEnable()) {
//            File dir = new File(filePath);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File file = new File(filePath, filename);
//            FileOutputStream outStream = new FileOutputStream(file);
//            outStream.write(content.getBytes());
//            outStream.close();
//            flag = true;
//        }
//        return flag;
//    }
//
//    /**
//     * Read file as stream from SD card
//     *
//     * @param fileName String PATH =
//     *                 Environment.getExternalStorageDirectory().getAbsolutePath() +
//     *                 "/dirName";
//     * @return
//     */
//    public static byte[] readFileFromSDCard(String filePath, String fileName) {
//        byte[] buffer = null;
//        FileInputStream fin = null;
//        try {
//            if (isSDCardEnable()) {
//                String filePaht = filePath + "/" + fileName;
//                fin = new FileInputStream(filePaht);
//                int length = fin.available();
//                buffer = new byte[length];
//                fin.read(buffer);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fin != null) fin.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return buffer;
//    }
}