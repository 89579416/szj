package com.s.z.j.newUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static com.s.z.j.newUtils.ConvertUtils.*;
import static com.s.z.j.newUtils.ConvertUtils.bytes2HexString;
import static com.s.z.j.newUtils.ConvertUtils.hexString2Bytes;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : ���ܽ�����صĹ�����
 * </pre>
 */
public class EncryptUtils {

    private EncryptUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /*********************** ��ϣ������� ***********************/
    /**
     * MD2����
     *
     * @param data �����ַ���
     * @return 16��������
     */
    public static String encryptMD2ToString(String data) {
        return encryptMD2ToString(data.getBytes());
    }

    /**
     * MD2����
     *
     * @param data �����ֽ�����
     * @return 16��������
     */
    public static String encryptMD2ToString(byte[] data) {
        return bytes2HexString(encryptMD2(data));
    }

    /**
     * MD2����
     *
     * @param data �����ֽ�����
     * @return �����ֽ�����
     */
    public static byte[] encryptMD2(byte[] data) {
        return encryptAlgorithm(data, "MD2");
    }

    /**
     * MD5����
     *
     * @param data �����ַ���
     * @return 16��������
     */
    public static String encryptMD5ToString(String data) {
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * MD5����
     *
     * @param data �����ַ���
     * @param salt ��
     * @return 16���Ƽ�������
     */
    public static String encryptMD5ToString(String data, String salt) {
        return bytes2HexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * MD5����
     *
     * @param data �����ֽ�����
     * @return 16��������
     */
    public static String encryptMD5ToString(byte[] data) {
        return bytes2HexString(encryptMD5(data));
    }

    /**
     * MD5����
     *
     * @param data �����ֽ�����
     * @param salt ���ֽ�����
     * @return 16���Ƽ�������
     */
    public static String encryptMD5ToString(byte[] data, byte[] salt) {
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return bytes2HexString(encryptMD5(dataSalt));
    }

    /**
     * MD5����
     *
     * @param data �����ֽ�����
     * @return �����ֽ�����
     */
    public static byte[] encryptMD5(byte[] data) {
        return encryptAlgorithm(data, "MD5");
    }

    /**
     * SHA1����
     *
     * @param data �����ַ���
     * @return 16��������
     */
    public static String encryptSHA1ToString(String data) {
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * SHA1����
     *
     * @param data �����ֽ�����
     * @return 16��������
     */
    public static String encryptSHA1ToString(byte[] data) {
        return bytes2HexString(encryptSHA1(data));
    }

    /**
     * SHA1����
     *
     * @param data �����ֽ�����
     * @return �����ֽ�����
     */
    public static byte[] encryptSHA1(byte[] data) {
        return encryptAlgorithm(data, "SHA-1");
    }

    /**
     * SHA224����
     *
     * @param data �����ַ���
     * @return 16��������
     */
    public static String encryptSHA224ToString(String data) {
        return encryptSHA224ToString(data.getBytes());
    }

    /**
     * SHA224����
     *
     * @param data �����ֽ�����
     * @return 16��������
     */
    public static String encryptSHA224ToString(byte[] data) {
        return bytes2HexString(encryptSHA224(data));
    }

    /**
     * SHA224����
     *
     * @param data �����ֽ�����
     * @return �����ֽ�����
     */
    public static byte[] encryptSHA224(byte[] data) {
        return encryptAlgorithm(data, "SHA-224");
    }

    /**
     * SHA256����
     *
     * @param data �����ַ���
     * @return 16��������
     */
    public static String encryptSHA256ToString(String data) {
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * SHA256����
     *
     * @param data �����ֽ�����
     * @return 16��������
     */
    public static String encryptSHA256ToString(byte[] data) {
        return bytes2HexString(encryptSHA256(data));
    }

    /**
     * SHA256����
     *
     * @param data �����ֽ�����
     * @return �����ֽ�����
     */
    public static byte[] encryptSHA256(byte[] data) {
        return encryptAlgorithm(data, "SHA-256");
    }

    /**
     * SHA384����
     *
     * @param data �����ַ���
     * @return 16��������
     */
    public static String encryptSHA384ToString(String data) {
        return encryptSHA384ToString(data.getBytes());
    }

    /**
     * SHA384����
     *
     * @param data �����ֽ�����
     * @return 16��������
     */
    public static String encryptSHA384ToString(byte[] data) {
        return bytes2HexString(encryptSHA384(data));
    }

    /**
     * SHA384����
     *
     * @param data �����ֽ�����
     * @return �����ֽ�����
     */
    public static byte[] encryptSHA384(byte[] data) {
        return encryptAlgorithm(data, "SHA-384");
    }

    /**
     * SHA512����
     *
     * @param data �����ַ���
     * @return 16��������
     */
    public static String encryptSHA512ToString(String data) {
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * SHA512����
     *
     * @param data �����ֽ�����
     * @return 16��������
     */
    public static String encryptSHA512ToString(byte[] data) {
        return bytes2HexString(encryptSHA512(data));
    }

    /**
     * SHA512����
     *
     * @param data �����ֽ�����
     * @return �����ֽ�����
     */
    public static byte[] encryptSHA512(byte[] data) {
        return encryptAlgorithm(data, "SHA-512");
    }

    /**
     * ��data����algorithm�㷨����
     *
     * @param data      �����ֽ�����
     * @param algorithm �����㷨
     * @return �����ֽ�����
     */
    private static byte[] encryptAlgorithm(byte[] data, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * ��ȡ�ļ���MD5У����
     *
     * @param filePath �ļ�·��
     * @return �ļ���16��������
     */
    public static String encryptMD5File2String(String filePath) {
        return encryptMD5File2String(new File(filePath));
    }

    /**
     * ��ȡ�ļ���MD5У����
     *
     * @param filePath �ļ�·��
     * @return �ļ���MD5У����
     */
    public static byte[] encryptMD5File(String filePath) {
        return encryptMD5File(new File(filePath));
    }

    /**
     * ��ȡ�ļ���MD5У����
     *
     * @param file �ļ�
     * @return �ļ���16��������
     */
    public static String encryptMD5File2String(File file) {
        return encryptMD5File(file) != null ? bytes2HexString(encryptMD5File(file)) : "";
    }

    /**
     * ��ȡ�ļ���MD5У����
     *
     * @param file �ļ�
     * @return �ļ���MD5У����
     */
    public static byte[] encryptMD5File(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel channel = in.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(buffer);
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

    /************************ DES������� ***********************/
    /**
     * DESת��
     * <p>���㷨����/����ģʽ/��䷽ʽ</p>
     * <p>����ģʽ�У��������뱾ģʽECB�����ܿ���ģʽCBC�����ܷ���ģʽCFB���������ģʽOFB</p>
     * <p>��䷽ʽ�У�NoPadding��ZerosPadding��PKCS5Padding</p>
     */
    public static String DES_Transformation = "DES/ECB/NoPadding";
    private static final String DES_Algorithm = "DES";

    /**
     * @param data           ����
     * @param key            ��Կ
     * @param algorithm      ���ú���DES�㷨
     * @param transformation ת��
     * @param isEncrypt      �Ƿ����
     * @return ���Ļ������ģ�������DES��3DES��AES
     */
    public static byte[] DESTemplet(byte[] data, byte[] key, String algorithm, String transformation, boolean isEncrypt) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            SecureRandom random = new SecureRandom();
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES���ܺ�תΪBase64����
     *
     * @param data ����
     * @param key  8�ֽ���Կ
     * @return Base64����
     */
    public static byte[] encryptDES2Base64(byte[] data, byte[] key) {
        return EncodeUtils.base64Encode(encryptDES(data, key));
    }

    /**
     * DES���ܺ�תΪ16����
     *
     * @param data ����
     * @param key  8�ֽ���Կ
     * @return 16��������
     */
    public static String encryptDES2HexString(byte[] data, byte[] key) {
        return bytes2HexString(encryptDES(data, key));
    }

    /**
     * DES����
     *
     * @param data ����
     * @param key  8�ֽ���Կ
     * @return ����
     */
    public static byte[] encryptDES(byte[] data, byte[] key) {
        return DESTemplet(data, key, DES_Algorithm, DES_Transformation, true);
    }

    /**
     * DES����Base64��������
     *
     * @param data Base64��������
     * @param key  8�ֽ���Կ
     * @return ����
     */
    public static byte[] decryptBase64DES(byte[] data, byte[] key) {
        return decryptDES(EncodeUtils.base64Decode(data), key);
    }

    /**
     * DES����16��������
     *
     * @param data 16��������
     * @param key  8�ֽ���Կ
     * @return ����
     */
    public static byte[] decryptHexStringDES(String data, byte[] key) {
        return decryptDES(hexString2Bytes(data), key);
    }

    /**
     * DES����
     *
     * @param data ����
     * @param key  8�ֽ���Կ
     * @return ����
     */
    public static byte[] decryptDES(byte[] data, byte[] key) {
        return DESTemplet(data, key, DES_Algorithm, DES_Transformation, false);
    }

    /************************ 3DES������� ***********************/
    /**
     * 3DESת��
     * <p>���㷨����/����ģʽ/��䷽ʽ</p>
     * <p>����ģʽ�У��������뱾ģʽECB�����ܿ���ģʽCBC�����ܷ���ģʽCFB���������ģʽOFB</p>
     * <p>��䷽ʽ�У�NoPadding��ZerosPadding��PKCS5Padding</p>
     */
    public static String TripleDES_Transformation = "DESede/ECB/NoPadding";
    private static final String TripleDES_Algorithm = "DESede";


    /**
     * 3DES���ܺ�תΪBase64����
     *
     * @param data ����
     * @param key  24�ֽ���Կ
     * @return Base64����
     */
    public static byte[] encrypt3DES2Base64(byte[] data, byte[] key) {
        return EncodeUtils.base64Encode(encrypt3DES(data, key));
    }

    /**
     * 3DES���ܺ�תΪ16����
     *
     * @param data ����
     * @param key  24�ֽ���Կ
     * @return 16��������
     */
    public static String encrypt3DES2HexString(byte[] data, byte[] key) {
        return bytes2HexString(encrypt3DES(data, key));
    }

    /**
     * 3DES����
     *
     * @param data ����
     * @param key  24�ֽ���Կ
     * @return ����
     */
    public static byte[] encrypt3DES(byte[] data, byte[] key) {
        return DESTemplet(data, key, TripleDES_Algorithm, TripleDES_Transformation, true);
    }

    /**
     * 3DES����Base64��������
     *
     * @param data Base64��������
     * @param key  24�ֽ���Կ
     * @return ����
     */
    public static byte[] decryptBase64_3DES(byte[] data, byte[] key) {
        return decrypt3DES(EncodeUtils.base64Decode(data), key);
    }

    /**
     * 3DES����16��������
     *
     * @param data 16��������
     * @param key  24�ֽ���Կ
     * @return ����
     */
    public static byte[] decryptHexString3DES(String data, byte[] key) {
        return decrypt3DES(hexString2Bytes(data), key);
    }

    /**
     * 3DES����
     *
     * @param data ����
     * @param key  24�ֽ���Կ
     * @return ����
     */
    public static byte[] decrypt3DES(byte[] data, byte[] key) {
        return DESTemplet(data, key, TripleDES_Algorithm, TripleDES_Transformation, false);
    }

    /************************ AES������� ***********************/
    /**
     * AESת��
     * <p>���㷨����/����ģʽ/��䷽ʽ</p>
     * <p>����ģʽ�У��������뱾ģʽECB�����ܿ���ģʽCBC�����ܷ���ģʽCFB���������ģʽOFB</p>
     * <p>��䷽ʽ�У�NoPadding��ZerosPadding��PKCS5Padding</p>
     */
    public static String AES_Transformation = "AES/ECB/NoPadding";
    private static final String AES_Algorithm = "AES";


    /**
     * AES���ܺ�תΪBase64����
     *
     * @param data ����
     * @param key  16��24��32�ֽ���Կ
     * @return Base64����
     */
    public static byte[] encryptAES2Base64(byte[] data, byte[] key) {
        return EncodeUtils.base64Encode(encryptAES(data, key));
    }

    /**
     * AES���ܺ�תΪ16����
     *
     * @param data ����
     * @param key  16��24��32�ֽ���Կ
     * @return 16��������
     */
    public static String encryptAES2HexString(byte[] data, byte[] key) {
        return bytes2HexString(encryptAES(data, key));
    }

    /**
     * AES����
     *
     * @param data ����
     * @param key  16��24��32�ֽ���Կ
     * @return ����
     */
    public static byte[] encryptAES(byte[] data, byte[] key) {
        return DESTemplet(data, key, AES_Algorithm, AES_Transformation, true);
    }

    /**
     * AES����Base64��������
     *
     * @param data Base64��������
     * @param key  16��24��32�ֽ���Կ
     * @return ����
     */
    public static byte[] decryptBase64AES(byte[] data, byte[] key) {
        return decryptAES(EncodeUtils.base64Decode(data), key);
    }

    /**
     * AES����16��������
     *
     * @param data 16��������
     * @param key  16��24��32�ֽ���Կ
     * @return ����
     */
    public static byte[] decryptHexStringAES(String data, byte[] key) {
        return decryptAES(hexString2Bytes(data), key);
    }

    /**
     * AES����
     *
     * @param data ����
     * @param key  16��24��32�ֽ���Կ
     * @return ����
     */
    public static byte[] decryptAES(byte[] data, byte[] key) {
        return DESTemplet(data, key, AES_Algorithm, AES_Transformation, false);
    }
}