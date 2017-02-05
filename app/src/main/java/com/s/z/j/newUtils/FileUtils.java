package com.s.z.j.newUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.s.z.j.newUtils.ConstUtils.*;
import static com.s.z.j.newUtils.ConstUtils.BYTE;
import static com.s.z.j.newUtils.ConstUtils.GB;
import static com.s.z.j.newUtils.ConstUtils.KB;
import static com.s.z.j.newUtils.ConstUtils.MB;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/11
 *     desc  : �ļ���ع�����
 * </pre>
 */
public class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * �����ļ�·����ȡ�ļ�
     *
     * @param filePath �ļ�·��
     * @return �ļ�
     */
    public static File getFileByPath(String filePath) {
        return StringUtils.isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * �ж��ļ��Ƿ����
     *
     * @param filePath �ļ�·��
     * @return {@code true}: ����<br>{@code false}: ������
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * �ж��ļ��Ƿ����
     *
     * @param file �ļ�
     * @return {@code true}: ����<br>{@code false}: ������
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * �ж��Ƿ���Ŀ¼
     *
     * @param dirPath Ŀ¼·��
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isDir(String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * �ж��Ƿ���Ŀ¼
     *
     * @param file �ļ�
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isDir(File file) {
        return isFileExists(file) && file.isDirectory();
    }

    /**
     * �ж��Ƿ����ļ�
     *
     * @param filePath �ļ�·��
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isFile(String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * �ж��Ƿ����ļ�
     *
     * @param file �ļ�
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isFile(File file) {
        return isFileExists(file) && file.isFile();
    }

    /**
     * �ж�Ŀ¼�Ƿ���ڣ����������ж��Ƿ񴴽��ɹ�
     *
     * @param dirPath �ļ�·��
     * @return {@code true}: ���ڻ򴴽��ɹ�<br>{@code false}: �����ڻ򴴽�ʧ��
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * �ж�Ŀ¼�Ƿ���ڣ����������ж��Ƿ񴴽��ɹ�
     *
     * @param file �ļ�
     * @return {@code true}: ���ڻ򴴽��ɹ�<br>{@code false}: �����ڻ򴴽�ʧ��
     */
    public static boolean createOrExistsDir(File file) {
        // ������ڣ���Ŀ¼�򷵻�true�����ļ��򷵻�false���������򷵻��Ƿ񴴽��ɹ�
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * �ж��ļ��Ƿ���ڣ����������ж��Ƿ񴴽��ɹ�
     *
     * @param filePath �ļ�·��
     * @return {@code true}: ���ڻ򴴽��ɹ�<br>{@code false}: �����ڻ򴴽�ʧ��
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * �ж��ļ��Ƿ���ڣ����������ж��Ƿ񴴽��ɹ�
     *
     * @param file �ļ�
     * @return {@code true}: ���ڻ򴴽��ɹ�<br>{@code false}: �����ڻ򴴽�ʧ��
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // ������ڣ����ļ��򷵻�true����Ŀ¼�򷵻�false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * �ж��ļ��Ƿ���ڣ��������ڴ���֮ǰɾ��
     *
     * @param filePath �ļ�·��
     * @return {@code true}: �����ɹ�<br>{@code false}: ����ʧ��
     */
    public static boolean createFileByDeleteOldFile(String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * �ж��ļ��Ƿ���ڣ��������ڴ���֮ǰɾ��
     *
     * @param file �ļ�
     * @return {@code true}: �����ɹ�<br>{@code false}: ����ʧ��
     */
    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) return false;
        // �ļ����ڲ���ɾ��ʧ�ܷ���false
        if (file.exists() && file.isFile() && !file.delete()) return false;
        // ����Ŀ¼ʧ�ܷ���false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ���ƻ��ƶ�Ŀ¼
     *
     * @param srcDirPath  ԴĿ¼·��
     * @param destDirPath Ŀ��Ŀ¼·��
     * @param isMove      �Ƿ��ƶ�
     * @return {@code true}: ���ƻ��ƶ��ɹ�<br>{@code false}: ���ƻ��ƶ�ʧ��
     */
    private static boolean copyOrMoveDir(String srcDirPath, String destDirPath, boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), isMove);
    }

    /**
     * ���ƻ��ƶ�Ŀ¼
     *
     * @param srcDir  ԴĿ¼
     * @param destDir Ŀ��Ŀ¼
     * @param isMove  �Ƿ��ƶ�
     * @return {@code true}: ���ƻ��ƶ��ɹ�<br>{@code false}: ���ƻ��ƶ�ʧ��
     */
    private static boolean copyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        if (srcDir == null || destDir == null) return false;
        // ���Ŀ��Ŀ¼��ԴĿ¼���򷵻�false���������Ļ��ú�����ݹ���ô����
        // srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res
        // destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1
        // Ϊ��ֹ��������������ֳ������У���ֱ��ں���Ӹ�·���ָ���
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        // Դ�ļ������ڻ��߲���Ŀ¼�򷵻�false
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        // Ŀ��Ŀ¼�����ڷ���false
        if (!createOrExistsDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                // �������ʧ�ܷ���false
                if (!copyOrMoveFile(file, oneDestFile, isMove)) return false;
            } else if (file.isDirectory()) {
                // �������ʧ�ܷ���false
                if (!copyOrMoveDir(file, oneDestFile, isMove)) return false;
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * ���ƻ��ƶ��ļ�
     *
     * @param srcFilePath  Դ�ļ�·��
     * @param destFilePath Ŀ���ļ�·��
     * @param isMove       �Ƿ��ƶ�
     * @return {@code true}: ���ƻ��ƶ��ɹ�<br>{@code false}: ���ƻ��ƶ�ʧ��
     */
    private static boolean copyOrMoveFile(String srcFilePath, String destFilePath, boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), isMove);
    }

    /**
     * ���ƻ��ƶ��ļ�
     *
     * @param srcFile  Դ�ļ�
     * @param destFile Ŀ���ļ�
     * @param isMove   �Ƿ��ƶ�
     * @return {@code true}: ���ƻ��ƶ��ɹ�<br>{@code false}: ���ƻ��ƶ�ʧ��
     */
    private static boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        // Դ�ļ������ڻ��߲����ļ��򷵻�false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        // Ŀ���ļ����������ļ��򷵻�false
        if (destFile.exists() && destFile.isFile()) return false;
        // Ŀ��Ŀ¼�����ڷ���false
        if (!createOrExistsDir(destFile.getParentFile())) return false;
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ����Ŀ¼
     *
     * @param srcDirPath  ԴĿ¼·��
     * @param destDirPath Ŀ��Ŀ¼·��
     * @return {@code true}: ���Ƴɹ�<br>{@code false}: ����ʧ��
     */
    public static boolean copyDir(String srcDirPath, String destDirPath) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * ����Ŀ¼
     *
     * @param srcDir  ԴĿ¼
     * @param destDir Ŀ��Ŀ¼
     * @return {@code true}: ���Ƴɹ�<br>{@code false}: ����ʧ��
     */
    public static boolean copyDir(File srcDir, File destDir) {
        return copyOrMoveDir(srcDir, destDir, false);
    }

    /**
     * �����ļ�
     *
     * @param srcFilePath  Դ�ļ�·��
     * @param destFilePath Ŀ���ļ�·��
     * @return {@code true}: ���Ƴɹ�<br>{@code false}: ����ʧ��
     */
    public static boolean copyFile(String srcFilePath, String destFilePath) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * �����ļ�
     *
     * @param srcFile  Դ�ļ�
     * @param destFile Ŀ���ļ�
     * @return {@code true}: ���Ƴɹ�<br>{@code false}: ����ʧ��
     */
    public static boolean copyFile(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, false);
    }

    /**
     * �ƶ�Ŀ¼
     *
     * @param srcDirPath  ԴĿ¼·��
     * @param destDirPath Ŀ��Ŀ¼·��
     * @return {@code true}: �ƶ��ɹ�<br>{@code false}: �ƶ�ʧ��
     */
    public static boolean moveDir(String srcDirPath, String destDirPath) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * �ƶ�Ŀ¼
     *
     * @param srcDir  ԴĿ¼
     * @param destDir Ŀ��Ŀ¼
     * @return {@code true}: �ƶ��ɹ�<br>{@code false}: �ƶ�ʧ��
     */
    public static boolean moveDir(File srcDir, File destDir) {
        return copyOrMoveDir(srcDir, destDir, true);
    }

    /**
     * �ƶ��ļ�
     *
     * @param srcFilePath  Դ�ļ�·��
     * @param destFilePath Ŀ���ļ�·��
     * @return {@code true}: �ƶ��ɹ�<br>{@code false}: �ƶ�ʧ��
     */
    public static boolean moveFile(String srcFilePath, String destFilePath) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * �ƶ��ļ�
     *
     * @param srcFile  Դ�ļ�
     * @param destFile Ŀ���ļ�
     * @return {@code true}: �ƶ��ɹ�<br>{@code false}: �ƶ�ʧ��
     */
    public static boolean moveFile(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, true);
    }

    /**
     * ɾ��Ŀ¼
     *
     * @param dirPath Ŀ¼·��
     * @return {@code true}: ɾ���ɹ�<br>{@code false}: ɾ��ʧ��
     */
    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * ɾ��Ŀ¼
     *
     * @param dir Ŀ¼
     * @return {@code true}: ɾ���ɹ�<br>{@code false}: ɾ��ʧ��
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) return false;
        // Ŀ¼�����ڷ���true
        if (!dir.exists()) return true;
        // ����Ŀ¼����false
        if (!dir.isDirectory()) return false;
        // �����ļ����������ļ���
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (!deleteFile(file)) return false;
            } else if (file.isDirectory()) {
                if (!deleteDir(file)) return false;
            }
        }
        return dir.delete();
    }

    /**
     * ɾ���ļ�
     *
     * @param srcFilePath �ļ�·��
     * @return {@code true}: ɾ���ɹ�<br>{@code false}: ɾ��ʧ��
     */
    public static boolean deleteFile(String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * ɾ���ļ�
     *
     * @param file �ļ�
     * @return {@code true}: ɾ���ɹ�<br>{@code false}: ɾ��ʧ��
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * ��ȡĿ¼�������ļ�
     *
     * @param dirPath     Ŀ¼·��
     * @param isRecursive �Ƿ�ݹ����Ŀ¼
     * @return �ļ�����
     */
    public static List<File> listFilesInDir(String dirPath, boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * ��ȡĿ¼�������ļ�
     *
     * @param dir         Ŀ¼
     * @param isRecursive �Ƿ�ݹ����Ŀ¼
     * @return �ļ�����
     */
    public static List<File> listFilesInDir(File dir, boolean isRecursive) {
        if (isRecursive) return listFilesInDir(dir);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        Collections.addAll(list, files);
        return list;
    }

    /**
     * ��ȡĿ¼�������ļ�������Ŀ¼
     *
     * @param dir Ŀ¼
     * @return �ļ�����
     */
    public static List<File> listFilesInDir(File dir) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            list.add(file);
            if (file.isDirectory()) {
                list.addAll(listFilesInDir(file));
            }
        }
        return list;
    }

    /**
     * ��ȡĿ¼�����к�׺��Ϊsuffix���ļ�
     * <p>��Сд����</p>
     *
     * @param dirPath     Ŀ¼·��
     * @param isRecursive �Ƿ�ݹ����Ŀ¼
     * @return �ļ�����
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, String suffix, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix, isRecursive);
    }

    /**
     * ��ȡĿ¼�����к�׺��Ϊsuffix���ļ�
     * <p>��Сд����</p>
     *
     * @param dir         Ŀ¼
     * @param isRecursive �Ƿ�ݹ����Ŀ¼
     * @return �ļ�����
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix, boolean isRecursive) {
        if (isRecursive) return listFilesInDirWithFilter(dir, suffix);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * ��ȡĿ¼�����к�׺��Ϊsuffix���ļ�������Ŀ¼
     * <p>��Сд����</p>
     *
     * @param dirPath Ŀ¼·��
     * @return �ļ�����
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, String suffix) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix);
    }

    /**
     * ��ȡĿ¼�����к�׺��Ϊsuffix���ļ�������Ŀ¼
     * <p>��Сд����</p>
     *
     * @param dir Ŀ¼
     * @return �ļ�����
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                list.add(file);
            }
            if (file.isDirectory()) {
                list.addAll(listFilesInDirWithFilter(file, suffix));
            }
        }
        return list;
    }

    /**
     * ��ȡĿ¼�����з���filter���ļ�
     *
     * @param dirPath     Ŀ¼·��
     * @param isRecursive �Ƿ�ݹ����Ŀ¼
     * @return �ļ�����
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * ��ȡĿ¼�����з���filter���ļ�
     *
     * @param dir         Ŀ¼
     * @param isRecursive �Ƿ�ݹ����Ŀ¼
     * @return �ļ�����
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter, boolean isRecursive) {
        if (isRecursive) return listFilesInDirWithFilter(dir, filter);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * ��ȡĿ¼�����з���filter���ļ�������Ŀ¼
     *
     * @param dirPath Ŀ¼·��
     * @return �ļ�����
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * ��ȡĿ¼�����з���filter���ļ�������Ŀ¼
     *
     * @param dir Ŀ¼
     * @return �ļ�����
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(file);
            }
            if (file.isDirectory()) {
                list.addAll(listFilesInDirWithFilter(file, filter));
            }
        }
        return list;
    }

    /**
     * ��ȡĿ¼��ָ���ļ������ļ�������Ŀ¼
     * <p>��Сд����</p>
     *
     * @param dirPath Ŀ¼·��
     * @return �ļ�����
     */
    public static List<File> searchFileInDir(String dirPath, String fileName) {
        return searchFileInDir(getFileByPath(dirPath), fileName);
    }

    /**
     * ��ȡĿ¼��ָ���ļ������ļ�������Ŀ¼
     * <p>��Сд����</p>
     *
     * @param dir Ŀ¼
     * @return �ļ�����
     */
    public static List<File> searchFileInDir(File dir, String fileName) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().toUpperCase().equals(fileName.toUpperCase())) {
                list.add(file);
            }
            if (file.isDirectory()) {
                list.addAll(listFilesInDirWithFilter(file, fileName));
            }
        }
        return list;
    }

    /**
     * ��������д���ļ�
     *
     * @param filePath ·��
     * @param is       ������
     * @param append   �Ƿ�׷�����ļ�ĩ
     * @return {@code true}: д��ɹ�<br>{@code false}: д��ʧ��
     */
    public static boolean writeFileFromIS(String filePath, InputStream is, boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    /**
     * ��������д���ļ�
     *
     * @param file   �ļ�
     * @param is     ������
     * @param append �Ƿ�׷�����ļ�ĩ
     * @return {@code true}: д��ɹ�<br>{@code false}: д��ʧ��
     */
    public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
        if (file == null || is == null) return false;
        if (!createOrExistsFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[KB];
            int len;
            while ((len = is.read(data)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIO(is);
            closeIO(os);
        }
    }

    /**
     * ���ַ���д���ļ�
     *
     * @param filePath �ļ�·��
     * @param content  д������
     * @param append   �Ƿ�׷�����ļ�ĩ
     * @return {@code true}: д��ɹ�<br>{@code false}: д��ʧ��
     */
    public static boolean writeFileFromString(String filePath, String content, boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * ���ַ���д���ļ�
     *
     * @param file    �ļ�
     * @param content д������
     * @param append  �Ƿ�׷�����ļ�ĩ
     * @return {@code true}: д��ɹ�<br>{@code false}: д��ʧ��
     */
    public static boolean writeFileFromString(File file, String content, boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIO(fileWriter);
        }
    }

    /**
     * �򵥻�ȡ�ļ������ʽ
     *
     * @param filePath �ļ�·��
     * @return �ļ�����
     */
    public static String getFileCharsetSimple(String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * �򵥻�ȡ�ļ������ʽ
     *
     * @param file �ļ�
     * @return �ļ�����
     */
    public static String getFileCharsetSimple(File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is);
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * ��ȡ�ļ�����
     *
     * @param filePath �ļ�·��
     * @return �ļ�����
     */
    public static int getFileLines(String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    /**
     * ��ȡ�ļ�����
     *
     * @param file �ļ�
     * @return �ļ�����
     */
    public static int getFileLines(File file) {
        int count = 1;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[KB];
            int readChars;
            while ((readChars = is.read(buffer)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (buffer[i] == '\n') ++count;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is);
        }
        return count;
    }

    /**
     * ָ�����밴�ж�ȡ�ļ���List
     *
     * @param filePath    �ļ�·��
     * @param charsetName �����ʽ
     * @return �ļ�������
     */
    public static List<String> readFile2List(String filePath, String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    /**
     * ָ�����밴�ж�ȡ�ļ���List
     *
     * @param file        �ļ�
     * @param charsetName �����ʽ
     * @return �ļ�������
     */
    public static List<String> readFile2List(File file, String charsetName) {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * ָ�����밴�ж�ȡ�ļ���List
     *
     * @param filePath    �ļ�·��
     * @param st          ��Ҫ��ȡ�Ŀ�ʼ����
     * @param end         ��Ҫ��ȡ�Ľ�������
     * @param charsetName �����ʽ
     * @return �����ƶ��е�list
     */
    public static List<String> readFile2List(String filePath, int st, int end, String
            charsetName) {
        return readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    /**
     * ָ�����밴�ж�ȡ�ļ���List
     *
     * @param file        �ļ�
     * @param st          ��Ҫ��ȡ�Ŀ�ʼ����
     * @param end         ��Ҫ��ȡ�Ľ�������
     * @param charsetName �����ʽ
     * @return ������start�е�end�е�list
     */
    public static List<String> readFile2List(File file, int st, int end, String charsetName) {
        if (file == null) return null;
        if (st > end) return null;
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> list = new ArrayList<>();
            if (StringUtils.isSpace(charsetName)) {
                reader = new BufferedReader(new FileReader(file));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) break;
                if (st <= curLine && curLine <= end) list.add(line);
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIO(reader);
        }
    }

    /**
     * ָ�����밴�ж�ȡ�ļ����ַ�����
     *
     * @param filePath    �ļ�·��
     * @param charsetName �����ʽ
     * @return �ַ���
     */
    public static String readFile2String(String filePath, String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    /**
     * ָ�����밴�ж�ȡ�ļ����ַ�����
     *
     * @param file        �ļ�
     * @param charsetName �����ʽ
     * @return �ַ���
     */
    public static String readFile2String(File file, String charsetName) {
        if (file == null) return null;
        try {
            return ConvertUtils.inputStream2String(new FileInputStream(file), charsetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ָ�����밴�ж�ȡ�ļ����ַ�����
     *
     * @param filePath �ļ�·��
     * @return StringBuilder����
     */
    public static byte[] readFile2Bytes(String filePath) {
        return readFile2Bytes(getFileByPath(filePath));
    }

    /**
     * ָ�����밴�ж�ȡ�ļ����ַ�����
     *
     * @param file �ļ�
     * @return StringBuilder����
     */
    public static byte[] readFile2Bytes(File file) {
        if (file == null) return null;
        try {
            return ConvertUtils.inputStream2Bytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * byte��λת������λ��unit��
     *
     * @param size ��С
     * @param unit <ul>
     *             <li>{@link MemoryUnit#BYTE}: �ֽ�</li>
     *             <li>{@link MemoryUnit#KB}  : ǧ�ֽ�</li>
     *             <li>{@link MemoryUnit#MB}  : ��</li>
     *             <li>{@link MemoryUnit#GB}  : GB</li>
     *             </ul>
     * @return ��С��unitΪ��λ
     */
    public static double byte2Unit(long size, MemoryUnit unit) {
        switch (unit) {
            case BYTE:
                return (double) size / BYTE;
            case KB:
                return (double) size / KB;
            case MB:
                return (double) size / MB;
            case GB:
                return (double) size / GB;
        }
        return -1;
    }

    /**
     * ��ȡ�ļ���С
     * <p>���磺getFileSize(filePath, ConstUtils.MB); �����ļ���С��λΪMB</p>
     *
     * @param filePath �ļ�·��
     * @param unit     <ul>
     *                 <li>{@link MemoryUnit#BYTE}: �ֽ�</li>
     *                 <li>{@link MemoryUnit#KB}  : ǧ�ֽ�</li>
     *                 <li>{@link MemoryUnit#MB}  : ��</li>
     *                 <li>{@link MemoryUnit#GB}  : GB</li>
     *                 </ul>
     * @return �ļ���С��unitΪ��λ
     */
    public static double getFileSize(String filePath, MemoryUnit unit) {
        return getFileSize(getFileByPath(filePath), unit);
    }

    /**
     * ��ȡ�ļ���С
     * <p>���磺getFileSize(file, ConstUtils.MB); �����ļ���С��λΪMB</p>
     *
     * @param file �ļ�
     * @param unit <ul>
     *             <li>{@link MemoryUnit#BYTE}: �ֽ�</li>
     *             <li>{@link MemoryUnit#KB}  : ǧ�ֽ�</li>
     *             <li>{@link MemoryUnit#MB}  : ��</li>
     *             <li>{@link MemoryUnit#GB}  : GB</li>
     *             </ul>
     * @return �ļ���С��unitΪ��λ
     */
    public static double getFileSize(File file, MemoryUnit unit) {
        if (!isFileExists(file)) return -1;
        return byte2Unit(file.length(), unit);
    }

    /**
     * �ر�IO
     *
     * @param closeable closeable
     */
    public static void closeIO(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡȫ·���е��Ŀ¼
     *
     * @param file �ļ�
     * @return filePath�Ŀ¼
     */
    public static String getDirName(File file) {
        if (!isFileExists(file)) return "";
        return getDirName(file.getPath());
    }

    /**
     * ��ȡȫ·���е��Ŀ¼
     *
     * @param filePath �ļ�·��
     * @return filePath�Ŀ¼
     */
    public static String getDirName(String filePath) {
        if (StringUtils.isSpace(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    /**
     * ��ȡȫ·���е��ļ���
     *
     * @param file �ļ�
     * @return �ļ���
     */
    public static String getFileName(File file) {
        if (!isFileExists(file)) return "";
        return getFileName(file.getPath());
    }

    /**
     * ��ȡȫ·���е��ļ���
     *
     * @param filePath �ļ�·��
     * @return �ļ���
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isSpace(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * ��ȡȫ·���еĲ�����չ�����ļ���
     *
     * @param file �ļ�
     * @return ������չ�����ļ���
     */
    public static String getFileNameNoExtension(File file) {
        if (!isFileExists(file)) return "";
        return getFileNameNoExtension(file.getPath());
    }

    /**
     * ��ȡȫ·���еĲ�����չ�����ļ���
     *
     * @param filePath �ļ�·��
     * @return ������չ�����ļ���
     */
    public static String getFileNameNoExtension(String filePath) {
        if (StringUtils.isSpace(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }


    /**
     * ��ȡȫ·���е��ļ���չ��
     *
     * @param file �ļ�
     * @return �ļ���չ��
     */
    public static String getFileExtension(File file) {
        if (!isFileExists(file)) return "";
        return getFileExtension(file.getPath());
    }

    /**
     * ��ȡȫ·���е��ļ���չ��
     *
     * @param filePath �ļ�·��
     * @return �ļ���չ��
     */
    public static String getFileExtension(String filePath) {
        if (StringUtils.isSpace(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }
}