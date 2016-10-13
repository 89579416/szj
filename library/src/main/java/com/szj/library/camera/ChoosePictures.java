package com.szj.library.camera;

/**
 * Created by Administrator on 2016/4/29 0029.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.szj.library.utils.T;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * *************************************************************
 * 作       者  : zhougc
 * 创建时间 : ：2014-12-30  10:11
 * 文件描述 :选择本地图片，照相
 * 版权声明 : Copyright (C) 2014 深圳艾泰尔科技有限公司
 * **************************************************************
 */
public class ChoosePictures {


    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static String IMAGE_FILE_LOCATION = "file:///sdcard/itelImageCache/temp.jpg";
    private static String IMAGE_FILE_LOCATION2 = "/mnt/sdcard/itelImageCache/temp.jpg";

    public final static  boolean isSdCard(){

        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 打开照相机
     */
    public static void Opencamera(Activity activity,Uri mFileUri,int returnCode){

        if(!isSdCard()){
            T.s(activity,"无SD卡，无法照相！");
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
        try {
            activity.startActivityForResult(intent, returnCode);
        }catch (ActivityNotFoundException aa){
            T.s(activity, "无法打开相机！");
            return;
        }
    }

    /**
     * 选择照片
     */
    public static void selectPhoto(Activity activity,int returnCode){

        if(!isSdCard()){
            T.s(activity,"无SD卡，无法照相！");
        }
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, returnCode);
    }

    // isCamera 是否照相 切图
//    public static Uri cropImageUri(Activity activity,Uri uri, int outputX, int outputY, int requestCode){
//        Intent intent = new Intent("com.android.camera.action.CROP");
////        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//
//        SimpleDateFormat sDateFormat = new SimpleDateFormat(
//                "yyyyMMddhhmmss");
//        String address = sDateFormat.format(new java.util.Date());
//        Uri imageUri = Uri.parse("file:///sdcard/formats/" + address
//                + ".JPEG");
//
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//
//        intent.putExtra("return-data", false);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", false); // no face detection
//        activity.startActivityForResult(intent, requestCode);
//
//        String SDPATH = Environment.getExternalStorageDirectory()+ "/formats/"+ address + ".JPEG";
//            return Uri.parse(SDPATH);
//    }

    public static void cropImageUri(Activity activity,Uri uri, int outputX, int outputY, int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, requestCode);
    }


    /** Create a file Uri for saving an image or video */
    public final static Uri getOutputMediaFileUri() {
        int type = MEDIA_TYPE_IMAGE;
        File file = getOutputMediaFile(type);
        Assert.assertNotNull("getOutputMediaFileUri file", file);
        return Uri.fromFile(file);
    }

    public final static String getOutputMediaFileString() {
        int type = MEDIA_TYPE_IMAGE;
        File file = getOutputMediaFile(type);
        Assert.assertNotNull("getOutputMediaFileUri file", file);
        return file.getAbsolutePath();
    }

    public final static Uri getOutputMediaCameraUri() {

        String sdcardState = Environment.getExternalStorageState();
        String sdcardPathDir = Environment
                .getExternalStorageDirectory().getPath() + "/tempImage/";
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
            // 有sd卡，是否有myImage文件夹
            File fileDir = new File(sdcardPathDir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            // 是否有headImg文件
            file = new File(sdcardPathDir + System.currentTimeMillis()
                    + ".JPEG");
        }
        if (file != null) {
//            String path = file.getPath();
            return Uri.fromFile(file);
        }
        return null;
    }

    /** 得到不同手机版本的 文件目录 Create a File for saving an image or video */
    private static File getOutputMediaFile(int type) {
        String state = Environment.getExternalStorageState();
        Assert.assertTrue("external media is mounted",
                TextUtils.equals(state, Environment.MEDIA_MOUNTED));

        File mediaStorageDir;
        if (Build.VERSION.SDK_INT > 8) {
            mediaStorageDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else {
            mediaStorageDir = new File(
                    Environment.getExternalStorageDirectory(), "Pictures");
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (mediaStorageDir.mkdirs() || mediaStorageDir.isDirectory()) {
                Log.v("test", "directory is ok");
            } else {
                Assert.fail("failed to create directory");
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            Assert.fail("Invalid media type");
            return null;
        }
        Assert.assertNotNull("media file is not null", mediaFile);
        Log.v("test", "will store file at " + mediaFile.toString());
        return mediaFile;
    }


    /**
     * 根据 照相URi 得到 文件
     * @param mFileUri
     * @return
     */
    public final static File getFileFromUri(Uri mFileUri) {
        if (mFileUri != null) {

            try {
                URI uri;
                if (mFileUri.toString().startsWith("file://")) {
                    // normal path
                    uri = URI.create(mFileUri.toString());
                } else {
                    // support path
                    uri = URI.create("file://" + mFileUri.toString());
                }
                File file = new File(uri);
                if (file != null) {
                    if (file.canRead()) {
                        return file;
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /** 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

//        if(!DocumentsContract.isDocumentUri(context, uri)){
//
//            return selectImage(context,data);
//        }

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String selectImage(Context context,Intent data){
        if(data == null)
            return null;
        Uri selectedImage = data.getData();
        //      Log.e(TAG, selectedImage.toString());
        if(selectedImage!=null){
            String uriStr=selectedImage.toString();
            String path=uriStr.substring(10,uriStr.length());
            if(path.startsWith("com.sec.android.gallery3d")){
                Log.e("test", "It's auto backup pic path:"+selectedImage.toString());
                return null;
            }
        }
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * 得到 图片旋转 的角度
     *
     * @param filepath
     * @return
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            Log.e("test", "cannot read exif", ex);
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return 图片的旋转角度
     */
    public static  int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

}
