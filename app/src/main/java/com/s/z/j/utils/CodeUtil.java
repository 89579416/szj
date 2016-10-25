package com.s.z.j.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * Created by Administrator on 2016-10-25.
 */
public class CodeUtil {
    /**
     * 用字符串生成二维码
     * @param
     * @return
     * @throws WriterException
     */
    public static Bitmap Create2DCode(String text) throws WriterException {
        L.i("开始用字符串生成二维码");
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType , Object>();
        hints.put(EncodeHintType.CHARACTER_SET , "utf-8") ;
        hints.put(EncodeHintType.MARGIN , 0) ;
        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE , 400, 400,hints) ;
        int width = matrix.getWidth() ;
        int height = matrix.getHeight() ;
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int [] pixels = new int[width * height] ;
        for ( int y = 0 ; y < height; y++) {
            for (int x = 0; x < width ; x++) {
                if (matrix.get(x, y)){
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) ;
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels , 0, width, 0 , 0, width, height) ;
        return bitmap ;
    }

}
