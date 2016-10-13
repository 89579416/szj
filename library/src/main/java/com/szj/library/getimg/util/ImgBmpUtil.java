package com.szj.library.getimg.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fan on 2014-12-19.
 */
public class ImgBmpUtil {

    /**
     *  对图片压缩处理
     * @param bmp 压缩的图片
     * @param localFile 压缩完成后保存到本地的图片
     */
    public static Bitmap compressBitmap(Bitmap bmp, String localFile){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;//个人喜欢从80开始,
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 300) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        //-----------------------------return bitmap----------------------------------
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

        File file = new File(localFile);

        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(  new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static String compressByScale(String srcPath){


        return getSmallBitmap(srcPath);
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        newOpts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
//
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f
//        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;//be=1表示不缩放
//        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
//        newOpts.inSampleSize = be;//设置缩放比例
//        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//        return compress(bitmap);
    }

    /**
     * 压缩图片
     * @param bmp
     * @return
     */
    public static String compress(Bitmap bmp) {


        try {
            if(bmp == null){
                return "";
            }
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "compressed.png");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 这里100表示不压缩，将不压缩的数据存放到baos中
            int per = 90;

            int length = baos.toByteArray().length;
            int leng2 = length/1024;
            while ( leng2 > 100) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
                if(per <= 20) {
                    break;
                }
                baos.reset();// 重置baos即清空baos
                bmp.compress(Bitmap.CompressFormat.PNG, per, baos);// 将图片压缩为原来的(100-per)%，把压缩后的数据存放到baos中
                per -= 10;// 每次都减少10
            }

            int aa = leng2;
            f.createNewFile();
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 回收图片，清理内存
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
                bmp = null;
                System.gc();
            }
            return f.getPath();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static String getSmallBitmap(String filePath) {

        // 判断 旋转 角度
        int pictureDegree =  PickPhotoUtil.getInstance().readPictureDegree(filePath);


        final BitmapFactory.Options options = new BitmapFactory.Options();
		/* 设置为true,可以不把图片读到内存中,但依然可以计算出图片的大小，这正是我们需要的 */
        options.inJustDecodeBounds = true;
        File file = new File(filePath);
        InputStream is = null;
        try {
            if (file.exists()) {

                is = new FileInputStream(file);
                // BitmapFactory.decodeFile(filePath, options);
                BitmapFactory.decodeStream(is, null, options);

                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, 400, 200);

                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;

                Log.i("info", "options.inSampleSize=" + options.inSampleSize);
                // 这样重新获取一个新的is输入流,就可以解决decodeStream(is,null, options)返回null的问题
                is = new FileInputStream(file);

                Bitmap bm = BitmapFactory.decodeStream(is, null, options);

                //处理 选择图片
                if(pictureDegree !=0) {
                    //旋转图片 动作
                    Matrix matrix = new Matrix();;
                    matrix.postRotate(pictureDegree);
                    // 创建新的图片
                    bm= Bitmap.createBitmap(bm, 0, 0,
                            bm.getWidth(), bm.getHeight(), matrix, true);
                }

                return saveMyBitmap(bm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int  calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		/* 压缩一张图片。我们需要知道这张图片的原始大小，然后根据我们设定的压缩比例进行压缩。 */
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
		/*
		 * 1、如果图片的原始高度或者宽带大约我们期望的宽带和高度，我们需要计算出缩放比例的数值。否则就不缩放
		 * 2、如果使用大的值作位压缩倍数，则压缩出来的图片大小会小于我们设定的值
		 * 3、如果使用小的值作位压缩倍数，则压缩出来的图片大小会大于我们设定的值
		 */
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 将压缩后的图片保存到sd卡
     *
     * @param bitmap
     */
    private static String saveMyBitmap(Bitmap bitmap) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/bptemp");
        if (!dir.exists()) {
            dir.mkdir();
        }
        String strdate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File imageFile = new File(dir, strdate+"r_n_verify.jpg");
        if (imageFile.exists()) {
            if (imageFile.isFile()) {
                imageFile.delete();// 先删掉原来的，再另外创建一张图片（如果不先将已存在的图片删除，部分手机在保存时会出问题）
                imageFile = new File(dir, strdate+"r_n_verify.jpg");
            }
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(imageFile);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)) {
                try {
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fOut != null) {
                try {
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        // 回收图片，清理内存
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
            System.gc();
        }

        return imageFile.getPath();
    }

}
