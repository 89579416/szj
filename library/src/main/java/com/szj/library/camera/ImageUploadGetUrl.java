package com.szj.library.camera;



import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.szj.library.R;
import com.szj.library.getimg.util.DialogLoadingUtil;
import com.szj.library.getimg.util.FileUtils;
import com.szj.library.getimg.util.GetPictureBack;
import com.szj.library.getimg.util.ImgBmpUtil;
import com.szj.library.utils.DialogUtils;
import com.szj.library.utils.L;
import com.szj.library.utils.T;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by zgc on 2015/5/8.
 * 得到 选择图片 或照相的 图片 地址
 */
public class ImageUploadGetUrl implements View.OnClickListener{
    private final static String TAG = "ImageGetUrl";
    private GetPictureBack getPictureBack;
    private boolean isSetONactivityResult = false;// 是否设置了 onActivityResult
    private ChoosePicturePopupWindow menuWindow;
    private static final int REQUEST_IMAGE = 5;//请求 加载图片
    private static final int REQUEST_CAMERA = 100; // 请求加载系统照相机
    private Activity activity;
    private boolean isShootme = false;// 是否 截图
    private boolean issquare;//  截图时， 是否为 正方形
    private File mTmpFile;// 照相后 保存的 文件
    private int x;
    private int y;
    private int width;
    private int height;

    public ImageUploadGetUrl(GetPictureBack getPictureBack){
        this.getPictureBack = getPictureBack;
        if(isSetONactivityResult == false){
            Log.e(TAG,"onActivityResult()  ==   null");
        }
    }
    /**
     * 图片弹出框，提示用户 选择图片 还是 拍照
     * @param activity
     * @param showView
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void showdiaView(Activity activity,View showView){
        this.activity = activity;
        this.isShootme = false;
        menuWindow = new ChoosePicturePopupWindow(activity,this);
        menuWindow.showAtLocation(showView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        menuWindow.update();
    }

    /**
     * 弹出提示框，并截图后处理
     * @param activity
     * @param showView
     * @param x  宽度比
     * @param y 高度比
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void showdiaViewwithAspect(Activity activity,View showView,int x, int y){
        this.activity = activity;
        this.isShootme = true;
        this.x = x;
        this.y = y;
        menuWindow = new ChoosePicturePopupWindow(activity,this);
        menuWindow.showAtLocation(showView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        menuWindow.update();
    }
    /**
     * 弹出提示框，并截图后处理
     * @param activity
     * @param showView
     * @param width  宽度
     * @param height 高度
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void showdiaViewwithMaxSize(Activity activity,View showView,int width, int height){
        this.activity = activity;
        this.isShootme = true;
        this.width = width;
        this.height = height;
        menuWindow = new ChoosePicturePopupWindow(activity,this);
        menuWindow.showAtLocation(showView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        menuWindow.update();
    }

    /**
     * 选择相册或拍照后返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == -1) {// 选择 相片 返回
                ArrayList<String> mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if(mSelectPath == null){
                    return;
                }
                if(mSelectPath.size()==0){
                    return;
                }
                /** 2.我修改为大于1M时进行截图*/
//                File file = new File(mSelectPath.get(0));
//                try {
//                    if(getFileSize(file)>1048576){
//                        L.i("图片大于1M，开始截图");
//                        crop(mSelectPath.get(0));
//                    }else{
//                        sendFileUrl(mSelectPath.get(0));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                /** 1.下面的代码是原版，根据参数控制是否要截图*/
//                if(isShootme == false){// 不需要截图 处理得时候，直接返回
//                    sendFileUrl(mSelectPath.get(0));
//                    return;
//                }else{// 请求 截图
//                    crop(mSelectPath.get(0));
//                    return;
//                }
                /**  3.按照要求不截图，进行压缩*/
                sendFileUrl(mSelectPath.get(0));
            }
        }
        // 相机拍照完成后，返回图片路径
        if(requestCode == REQUEST_CAMERA){
            if(resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    /** 2.我修改为大于1M时进行截图*/
//                    try {
//                        if(getFileSize(mTmpFile)>1048576){
//                            L.i("图片大于1M，开始截图");
//                            crop(mTmpFile.toString());
//                        }else{
//                            sendFileUrl(mTmpFile.toString());
//                            return;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    /** 1.下面的代码是原版，根据参数控制是否要截图*/
//                    if(isShootme == false){// 不需要截图 处理得时候，直接返回
//
//                        sendFileUrl(mTmpFile.toString());
//                        return;
//                    }else{
//
//                        crop(mTmpFile.toString());
//                    }
                    /**  3.按照要求不截图，进行压缩*/
                    sendFileUrl(mTmpFile.toString());
                }
            }else{
                if(mTmpFile != null && mTmpFile.exists()){
                    mTmpFile.delete();
                }
            }
        }
        if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    /**
     * 截图后 返回
     * @param resultCode
     * @param result
     */
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == -1) {
            Uri uri = Crop.getOutput(result);
            File file2 = ChoosePictures.getFileFromUri(uri);
            if(file2 == null){
                return;
            }
            if(file2.exists() == false){
                return;
            }
            sendFileUrl(file2.toString());
        } else if (resultCode == Crop.RESULT_ERROR) {
            String mes = Crop.getError(result).getMessage();
            if(!TextUtils.isEmpty(mes)){
                Toast.makeText(activity,mes, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onClick(View view) {
        if(menuWindow != null) menuWindow.dismiss();
        int i = view.getId();
        if (i == R.id.pop_choose_album_btn) {
            showSelectAction(activity);
        } else if (i == R.id.pop_take_photo_btn) {
            showCameraAction(activity);
        }
    }

    /**
     * 选择 相册
     * @param activity
     */
    public void showSelectAction(Activity activity) {
        this.activity = activity;
        if(activity == null){
            return;
        }
        try {
            Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
            // 是否显示拍摄图片
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
            // 最大可选择图片数量
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
            // 选择模式
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);// 设置 为 多选
            // 默认选择
//                if (mSelectPath != null && mSelectPath.size() > 0) {
//                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
//                }
            activity.startActivityForResult(intent, REQUEST_IMAGE);
        }catch (Exception e){
          T.s(activity,"打开相册失败");
        }
    }

    /**
     * 选择相机
     */
    public void showCameraAction(Activity activity) {
        this.activity = activity;
        // 跳转到系统照相机
        try {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
                // 设置系统相机拍照后的输出路径
                // 创建临时文件
                mTmpFile = FileUtils.createTmpFile(activity);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                activity.startActivityForResult(cameraIntent, REQUEST_CAMERA);
            } else {
                Toast.makeText(activity, R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            T.s(activity,"打开相机失败,请检查相关权限");
        }
    }

    /**
     * 返回 图片 URl
     * @param imgUrl
     */
    private final void sendFileUrl(final String imgUrl){
        DialogUtils.show(activity,"正在处理...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = ImgBmpUtil.compressByScale(imgUrl);
                Message msg = new Message();
                msg.what =200;
                msg.obj = str;
                handler.sendMessage(msg);
            }
        }).start();
    }
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                   DialogUtils.dismiss();
                    String str = (String) msg.obj;
                    if(str == null){
                        return;
                    }
                    File file2 = new File(str);
                    if (file2 == null) {
                        L.i( "文件保存失败，请重新获取！");
                        T.s(activity,"文件保存失败，请重新获取！");
                        return;
                    }
                    getPictureBack.getBack(str);
            }
        }
    };

    /**
     * 截图
     * @param fileUrl
     */
    private void crop(String fileUrl){
        L.i("开始进入截图");
        Uri destination = Uri.fromFile(new File(activity.getCacheDir(), "cropped"));
        if(x !=0&&y!=0){
            Crop.of( Uri.fromFile(new File(fileUrl)), destination).withAspect(x,y).start(activity);
        }else{
            if(width !=0&&height !=0){
                Crop.of(Uri.fromFile(new File(fileUrl)), destination).withAspect(x,y).start(activity);
            }else{
                Crop.of(Uri.fromFile(new File(fileUrl)), destination).withAspect(1,1).start(activity);
            }
        }
    }
}
