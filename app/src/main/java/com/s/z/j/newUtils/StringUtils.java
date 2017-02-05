package com.s.z.j.newUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/16
 *     desc  : �ַ�����ع�����
 * </pre>
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * �ж��ַ����Ƿ�Ϊnull�򳤶�Ϊ0
     *
     * @param string ��У���ַ���
     * @return {@code true}: ��<br> {@code false}: ��Ϊ��
     */
    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    /**
     * �ж��ַ����Ƿ�Ϊnull��ȫΪ�ո�
     *
     * @param string ��У���ַ���
     * @return {@code true}: null��ȫ�ո�<br> {@code false}: ��Ϊnull�Ҳ�ȫ�ո�
     */
    public static boolean isSpace(String string) {
        return (string == null || string.trim().length() == 0);
    }

    /**
     * nullתΪ����Ϊ0���ַ���
     *
     * @param string ��ת�ַ���
     * @return stringΪnullתΪ����Ϊ0�ַ��������򲻸ı�
     */
    public static String null2Length0(String string) {
        return string == null ? "" : string;
    }

    /**
     * �����ַ�������
     *
     * @param string �ַ���
     * @return null����0����������������
     */
    public static int length(CharSequence string) {
        return string == null ? 0 : string.length();
    }

    /**
     * ����ĸ��д
     *
     * @param string ��ת�ַ���
     * @return ����ĸ��д�ַ���
     */
    public static String upperFirstLetter(String string) {
        if (isEmpty(string) || !Character.isLowerCase(string.charAt(0))) {
            return string;
        }
        return String.valueOf((char) (string.charAt(0) - 32)) + string.substring(1);
    }

    /**
     * ����ĸСд
     *
     * @param string ��ת�ַ���
     * @return ����ĸСд�ַ���
     */
    public static String lowerFirstLetter(String string) {
        if (isEmpty(string) || !Character.isUpperCase(string.charAt(0))) {
            return string;
        }
        return String.valueOf((char) (string.charAt(0) + 32)) + string.substring(1);
    }

    /**
     * ת��Ϊ����ַ�
     *
     * @param string ��ת�ַ���
     * @return ����ַ���
     */
    public static String toDBC(String string) {
        if (isEmpty(string)) {
            return string;
        }
        char[] chars = string.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * ת��Ϊȫ���ַ�
     *
     * @param string ��ת�ַ���
     * @return ȫ���ַ���
     */
    public static String toSBC(String string) {
        if (isEmpty(string)) {
            return string;
        }
        char[] chars = string.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }
}