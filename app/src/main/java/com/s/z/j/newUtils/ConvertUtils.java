package com.s.z.j.newUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/13
 *     desc  : ת����ع�����
 * </pre>
 */
public class ConvertUtils {

    static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * ÿ1��byteתΪ2��hex�ַ�
     * <p>���磺</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param src byte����
     * @return 16���ƴ�д�ַ���
     */
    public static String bytes2HexString(byte[] src) {
        char[] res = new char[src.length << 1];
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }
        return new String(res);
    }

    /**
     * ÿ2��hex�ַ�תΪ1��byte
     * <p>���磺</p>
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString ʮ�������ַ���
     * @return byte����
     */
    public static byte[] hexString2Bytes(String hexString) {
        int len = hexString.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("���Ȳ���ż��");
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] res = new byte[len >>> 1];
        for (int i = 0; i < len; i += 2) {
            res[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return res;
    }

    /**
     * ����hex�ַ�תΪ10����
     *
     * @param hexChar hex�����ֽ�
     * @return 0..15
     */
    private static int hex2Dec(char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * charArrתbyteArr
     *
     * @param chars ��ת��char����
     * @return byte����
     */
    public static byte[] chars2Bytes(char[] chars) {
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * byteArrתcharArr
     *
     * @param bytes ��ת��byte����
     * @return char����
     */
    public static char[] bytes2Chars(byte[] bytes) {
        int len = bytes.length;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * ��������תΪ�ֽ�����
     *
     * @param is ������
     * @return �ֽ�����
     */
    public static byte[] inputStream2Bytes(InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[ConstUtils.KB];
            int len;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            FileUtils.closeIO(is);
        }
    }

    /**
     * ���ֽ�����תΪ������
     *
     * @param bytes �ֽ�����
     * @return ������
     */
    public static InputStream bytes2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * ָ�����뽫������תΪ�ַ���
     *
     * @param is          ������
     * @param charsetName �����ʽ
     * @return �ַ���
     */
    public static String inputStream2String(InputStream is, String charsetName) {
        if (is == null) return null;
        BufferedReader reader;
        try {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(is));
            } else {
                reader = new BufferedReader(new InputStreamReader(is, charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");// windowsϵͳ����Ϊ\r\n��LinuxΪ\n
            }
            // Ҫȥ�����Ļ��з�
            return sb.delete(sb.length() - 2, sb.length()).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            FileUtils.closeIO(is);
        }
    }

    /**
     * ָ�����뽫�ַ���תΪ������
     *
     * @param string      �ַ���
     * @param charsetName �����ʽ
     * @return ������
     */
    public static InputStream string2InputStream(String string, String charsetName) {
        if (string == null || StringUtils.isSpace(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
