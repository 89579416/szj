package com.s.z.j.cameras;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.s.z.j.R;
import com.s.z.j.utils.L;

import java.io.IOException;

/**
 * 调用闪光灯
 * Created by Administrator on 2017-04-28.
 */
public class CameraMainActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback {

    TextView openSgdTv;
    TextView closeSgdTv;
    TextView openCamera;
    TextView closeCamera;
    TextView switchCamera;
    SurfaceView surfaceView;
    Context context;
    Camera camera;
    SurfaceHolder surfaceholder;
    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameras);
        initView();
    }

    public void initView() {
        context = this;
        openSgdTv = (TextView) findViewById(R.id.cameras_open_sanguangdeng_tv);
        openCamera = (TextView) findViewById(R.id.cameras_open_camera_tv);
        closeSgdTv = (TextView) findViewById(R.id.cameras_close_sanguangdeng_tv);
        closeCamera = (TextView) findViewById(R.id.cameras_close_camera_tv);
        switchCamera = (TextView) findViewById(R.id.cameras_switch_camera_tv);
        openSgdTv.setOnClickListener(this);
        openCamera.setOnClickListener(this);
        closeSgdTv.setOnClickListener(this);
        closeCamera.setOnClickListener(this);
        switchCamera.setOnClickListener(this);
        surfaceView = (SurfaceView) findViewById(R.id.cameras_surfaceview);
        surfaceholder = surfaceView.getHolder();
        surfaceholder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceholder.addCallback(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cameras_open_sanguangdeng_tv:
                openSgd();
                break;
            case R.id.cameras_close_sanguangdeng_tv:
                closeSgd();
                break;
            case R.id.cameras_open_camera_tv:
                openCamera();
                break;
            case R.id.cameras_close_camera_tv:
                closeCamera();
                break;
            case R.id.cameras_switch_camera_tv:
                switchCamera();
                break;
        }
    }

    /**
     * 打开闪光灯
     */
    public void openSgd() {
        try {
            camera = Camera.open();
            Camera.Parameters mParameters;
            mParameters = camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(mParameters);
        } catch (Exception ex) {
        }
    }

    /**
     * 关闭闪光灯
     */
    public void closeSgd() {
        try {
            Camera.Parameters mParameters;
            mParameters = camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(mParameters);
            camera.release();
        } catch (Exception ex) {
        }
    }

    public void openCamera() {
        if (cameraPosition == 1) {
            camera = Camera.open(0);//打开当前选中的摄像头
            L.i("打开第0个");
            try {
                camera.setPreviewDisplay(surfaceholder);//通过surfaceview显示取景画面
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            camera.setDisplayOrientation(90);
            camera.startPreview();//开始预览
        }else {
            camera = Camera.open(1);//打开当前选中的摄像头
            L.i("打开第1个");
            try {
                camera.setPreviewDisplay(surfaceholder);//通过surfaceview显示取景画面
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            camera.startPreview();//开始预览
        }


    }

    public void closeCamera() {
        if(camera != null) {
            camera.stopPreview();//停掉原来摄像头的预览
            camera.release();//释放资源
            camera = null;//取消原来摄像头
        }
    }

    /**
     * 切换摄相头
     */
    public void switchCamera() {
//切换前后摄像头
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraPosition == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    if(camera != null) {
                        camera.stopPreview();//停掉原来摄像头的预览
                        camera.release();//释放资源
                    }
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        camera.setPreviewDisplay(surfaceholder);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    camera.setDisplayOrientation(90);
                    camera.startPreview();//开始预览
                    cameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    if(camera != null) {
                        camera.stopPreview();//停掉原来摄像头的预览
                        camera.stopPreview();//停掉原来摄像头的预览
                        camera.release();//释放资源
                    }
                    camera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        camera.setPreviewDisplay(surfaceholder);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    camera.setDisplayOrientation(90);
                    camera.startPreview();//开始预览
                    cameraPosition = 1;
                    break;
                }
            }

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        L.i("surfacecreated");
        //获取camera对象
        camera = Camera.open();
        try {
            //设置预览监听
            camera.setPreviewDisplay(holder);
            Camera.Parameters parameters = camera.getParameters();

            if (this.getResources().getConfiguration().orientation
                    != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                camera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                parameters.set("orientation", "landscape");
                camera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }
            camera.setParameters(parameters);
            //启动摄像头预览
            camera.startPreview();
            L.i("camera.startpreview");

        } catch (IOException e) {
            e.printStackTrace();
            camera.release();
            L.i("camera.release");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        L.i("camera.surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
    }
}
