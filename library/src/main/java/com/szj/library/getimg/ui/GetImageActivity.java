//package com.szj.library.getimg.ui;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//
//import com.szj.library.R;
//import com.szj.library.getimg.util.DialogLoadingUtil;
//import com.szj.library.getimg.util.FileUtils;
//import com.szj.library.getimg.util.GetPictureBack;
//import com.szj.library.getimg.util.ImgBmpUtil;
//import com.szj.library.utils.A;
//import com.szj.library.utils.T;
//
//import java.io.File;
//import java.util.ArrayList;
//
///**
// * 获取设备图库图片或调用相机拍照
// * Created by Administrator on 2016/3/1 0001.
// */
//public class GetImageActivity extends Activity implements GetPictureBack {
//
//    Button btn1;
//    Button btn2;
//    ImageView imageView;
//    private DialogLoadingUtil dialogLoadingUtil;
//    //请求 加载图片
//    private static final int REQUEST_IMAGE = 2;
//    // 请求加载系统照相机
//    private static final int REQUEST_CAMERA = 100;
//    // 是否 截图
//    private boolean isShootme = true;
//    // 照相后 保存的 文件
//    private File mTmpFile;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_get_image);
//        imageView = (ImageView) findViewById(R.id.qrcode_bitmap);
//        dialogLoadingUtil = new DialogLoadingUtil(this, R.style.MDialog_load, getResources().getString(R.string.on_processing));
//
//        btn1 = (Button) findViewById(R.id.button1);
//        btn2 = (Button) findViewById(R.id.button2);
//
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showCameraAction(GetImageActivity.this);
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSelectAction(GetImageActivity.this);
//            }
//        });
//
//    }
//
//
//    @Override
//    public void getBack(String url) {
//        /***/
//        Bitmap bitmap = BitmapFactory.decodeFile(url);
//        imageView.setImageBitmap(bitmap);
//        Log.i("AAAA", "进入到 getBack url=" + url);
//        File file2 = new File(url);
//        if (file2 == null) {
//            T.s(GetImageActivity.this, "图片保存失败");
//            return;
//        }
////        Log.i("AAAA", "图片地址：" + file2.getPath());
////        Intent intent = new Intent();
////        intent.putExtra("url",file2.getPath());
////        setResult(-1,intent);
////        finish();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//      android.util.Log.i("AAAA", "GetImageActivity页面的onActivityResult");
//        if(requestCode == REQUEST_IMAGE){
//            if(resultCode == -1) {// 选择 相片 返回
//                Log.i(A.A,"选择 相片 返回");
//                ArrayList<String> mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                if(mSelectPath == null){
//                    Log.i(A.A,"mSelectPath == null");
//                    return;
//                }
//                if(mSelectPath.size()==0){
//                    Log.i(A.A,"mSelectPath.size()==0");
//                    return;
//                }
//                if(isShootme == false){// 不需要截图 处理得时候，直接返回
//                    Log.i(A.A," 不需要截图 处理得时候，直接返回:"+mSelectPath.get(0));
//                    sendFileUrl(mSelectPath.get(0));
//                    return;
//                }else{// 请求 截图
//                    return;
//                }
//            }
//        }
//        // 相机拍照完成后，返回图片路径
//        if(requestCode == REQUEST_CAMERA){
//            Log.i(A.A,"相机拍照完成后，返回图片路径");
//            if(resultCode == Activity.RESULT_OK) {
//                if (mTmpFile != null) {
//
//                    if(isShootme == false){// 不需要截图 处理得时候，直接返回
//                        Log.i(A.A,"不需要截图 处理得时候，直接返回");
//                        sendFileUrl(mTmpFile.toString());
//                        return;
//                    }else{
//
//                    }
//                }
//            }else{
//                if(mTmpFile != null && mTmpFile.exists()){
//                    mTmpFile.delete();
//                }
//            }
//        }
//    }
//
//    /**
//     * 返回 图片 URl
//     * @param imgUrl
//     */
//    private final void sendFileUrl(final String imgUrl){
//        dialogLoadingUtil =   new DialogLoadingUtil(this, R.style.MDialog_load,"正在处理....");
//        dialogLoadingUtil.show();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String str = ImgBmpUtil.compressByScale(imgUrl);
//                Message msg = new Message();
//                msg.what =200;
//                msg.obj = str;
//                handler.sendMessage(msg);
//            }
//        }).start();
//    }
//
//    private Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 200:
//                    if(dialogLoadingUtil !=null){
//                        dialogLoadingUtil .dismiss();
//                    }
//                    String str = (String) msg.obj;
//                    if(str == null){
//                        return;
//                    }
//                    File file2 = new File(str);
//                    if (file2 == null) {
//                        T.s(GetImageActivity.this, "文件上传失败，请重新上传！");
//                        return;
//                    }
//                    getBack(str);
//            }
//        }
//    };
//
//    /**
//     * 选择 相册
//     * @param activity
//     */
//    public void showSelectAction(Activity activity) {
//        if(activity == null){
//            return;
//        }
//
//        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
//        // 是否显示拍摄图片
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
//        // 最大可选择图片数量
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
//        // 选择模式
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);// 设置 为 多选
//        // 默认选择
////                if (mSelectPath != null && mSelectPath.size() > 0) {
////                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
////                }
//        activity.startActivityForResult(intent, REQUEST_IMAGE);
//    }
//    /**
//     * 选择相机
//     */
//    public void showCameraAction(Activity activity) {
//        // 跳转到系统照相机
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(cameraIntent.resolveActivity(activity.getPackageManager()) != null){
//            // 设置系统相机拍照后的输出路径
//            // 创建临时文件
//            mTmpFile = FileUtils.createTmpFile(activity);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
//            activity.startActivityForResult(cameraIntent, REQUEST_CAMERA);
//        }else{
//            Toast.makeText(activity, R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//}
///**下面是正常代码*/
//
////package com.facsimilemedia.www.facsimileclient.ui;
////
////import android.app.Activity;
////import android.content.Intent;
////import android.graphics.Bitmap;
////import android.graphics.BitmapFactory;
////import android.os.Bundle;
////import android.view.View;
////import android.widget.Button;
////import android.widget.ImageView;
////import android.widget.Toast;
////
////import com.alibaba.sdk.android.oss.ClientConfiguration;
////import com.alibaba.sdk.android.oss.ClientException;
////import com.alibaba.sdk.android.oss.OSS;
////import com.alibaba.sdk.android.oss.OSSClient;
////import com.alibaba.sdk.android.oss.ServiceException;
////import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
////import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
////import com.alibaba.sdk.android.oss.common.OSSConstants;
////import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
////import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
////import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
////import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
////import com.alibaba.sdk.android.oss.common.utils.IOUtils;
////import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
////import com.alibaba.sdk.android.oss.model.PutObjectRequest;
////import com.alibaba.sdk.android.oss.model.PutObjectResult;
////import com.facsimilemedia.www.facsimileclient.R;
////import com.facsimilemedia.www.facsimileclient.entity.OssData;
////import com.facsimilemedia.www.facsimileclient.model.IBusinessResponseListener;
////import com.facsimilemedia.www.facsimileclient.test.AssumedRoleUser;
////import com.facsimilemedia.www.facsimileclient.test.Credentials;
////import com.facsimilemedia.www.facsimileclient.test.GetTokens;
////import com.facsimilemedia.www.facsimileclient.test.KeyEntity;
////import com.facsimilemedia.www.facsimileclient.test.MyTime;
////import com.facsimilemedia.www.facsimileclient.test.Tooken;
////import com.facsimilemedia.www.facsimileclient.util.DialogLoadingUtil;
////import com.facsimilemedia.www.facsimileclient.util.GetPictureBack;
////import com.facsimilemedia.www.facsimileclient.util.ImageUploadGetUrl;
////import com.facsimilemedia.www.facsimileclient.util.Log;
////import com.facsimilemedia.www.facsimileclient.util.T;
////import com.google.gson.Gson;
//////import com.google.gson.Gson;
////
////import org.json.JSONException;
////import org.json.JSONObject;
////
////import java.io.File;
////import java.io.InputStream;
////import java.net.HttpURLConnection;
////import java.net.URL;
////import java.text.DateFormat;
////import java.text.ParseException;
////import java.text.SimpleDateFormat;
////import java.util.Date;
////import java.util.TimeZone;
////
/////**
//// * 获取设备图库图片或调用相机拍照
//// * Created by Administrator on 2016/3/1 0001.
//// */
////public class GetImageActivity extends Activity implements IBusinessResponseListener<String>, GetPictureBack {
////
////    Button btn1;
////    Button btn2;
////    ImageView imageView;
////
////    private DialogLoadingUtil dialogLoadingUtil;
////
////    private ImageUploadGetUrl getPictureUtil;
////    Tooken tooken;
////
////
////    /**
////     * 上传日志参数
////     */
////    private static String accessKey; //
////
////    private static String screctKey;
////    private static long Expiration; //
////    private static String Expiration1;
////    private static String SecurityToken;
////
////    private OssData ossData;
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_get_image);
////        btn1 = (Button) findViewById(R.id.button1);
////        btn2 = (Button) findViewById(R.id.button2);
////        imageView = (ImageView) findViewById(R.id.qrcode_bitmap);
////        dialogLoadingUtil = new DialogLoadingUtil(this, R.style.MDialog_load, getResources().getString(R.string.on_processing));
////
////        btn1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Log.i("AAAA", "点进来了");
//////                getToken();
////                getPictureUtil = new ImageUploadGetUrl(GetImageActivity.this);
////                getPictureUtil.showdiaViewwithAspect(GetImageActivity.this, GetImageActivity.this.findViewById(R.id.button1), 1, 1);
////
////            }
////        });
////
////    }
////
////
////    @Override
////    public void getBack(String url) {
////        /***/
////        Bitmap bitmap = BitmapFactory.decodeFile(url);
////        // imageView.setImageURI(Uri.fromFile(new File(filePath)));
////        imageView.setImageBitmap(bitmap);
////        Log.i("AAAA", "进入到 getBack url=" + url);
////        File file2 = new File(url);
////        if (file2 == null) {
////            T.s(GetImageActivity.this, "图片保存失败");
////            return;
////        }
////        // myProfileModel.uploadImg(pushImgListener, file2);//这里就是上传图片了
////        Log.i("AAAA", "图片地址：" + file2.getPath());
//////        putFile(file2.getPath());
////         T.getCallback().invoke(url);
////         finish();
////    }
////
////    @Override
////    public void onBusinessResponse(String result) {
////
////    }
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (getPictureUtil != null) {
////            getPictureUtil.onActivityResult(requestCode, resultCode, data);
////        }
////    }
////
////    public void getToken() {
////        tooken = Tooken.getInstance();
////
////        new Thread() {
////            @Override
////            public void run() {
////                GetTokens getTokens = new GetTokens(GetImageActivity.this);
////                getTokens.getT();
////                Log.i("AAAA", "token=-----------------------" + tooken.getAccess_token());
////                boolean a = false;
////                while (a == false) {
////                    try {
////                        Thread.sleep(1000);
////                        if (tooken.getAccess_token() != null && !"".equals(tooken.getAccess_token())) {
////                            Log.i("AAAA", "token有数据了，准备进入getOssInfo");
////                            getOssInfo();
////                            a = true;
////                        } else {
////                            a = false;
////                        }
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////        }.start();
////
////
////    }
////
////    public void getOssInfo() {
////        Log.i("AAAA", "进入getOssInfo方法");
////        Log.i("AAAA", "getOssInfo开始");
////        GetTokens getTokens = new GetTokens(GetImageActivity.this);
////        getTokens.getOs(tooken.getAccess_token());
////        ossData = GetTokens.getOssData();
//////        boolean a = false;
//////        while (a == false) {
//////            if (ossData != null) {
//////                if(ossData.getAccessid() != null && !"".equals(ossData.getAccessid())){
//////
//////                }else {
//////                    try {
//////                        Thread.sleep(2000);
//////                        ossData = GetTokens.getOssData();
//////                    } catch (InterruptedException e) {
//////                        e.printStackTrace();
//////                    }
//////                }
//////            }else{
//////                try {
//////                    Thread.sleep(2000);
//////                    ossData = GetTokens.getOssData();
//////                } catch (InterruptedException e) {
//////                    e.printStackTrace();
//////                }
//////            }
//////        }
////    }
////
////    public void putFile(String filePath) {
////        Log.i("AAAA","进到putFile");
//////--------------------------------------------------------------------
////        String endpoint = ossData.getHost();
////
////        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的访问控制章节
////        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(ossData.getAccessid(), ossData.getSignature());
////
////        ClientConfiguration conf = new ClientConfiguration();
////        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
////        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
////        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
////        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
////
////        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
////
////        // -----------------------------------------------------------------------------------------------
////        /***/
////        /***/
////        // 构造上传请求
////        PutObjectRequest put = new PutObjectRequest(ossData.getDir(), ossData.getPolicy(), filePath);
////
////        // 异步上传时可以设置进度回调
////        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
////            @Override
////            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
////                Log.i("AAAA", "currentSize: " + currentSize + " totalSize: " + totalSize);
////            }
////        });
////
////        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
////            @Override
////            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
////                Log.i("AAAA", "UploadSuccess");
////            }
////
////            @Override
////            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
////                // 请求异常
////                if (clientExcepion != null) {
////                    // 本地异常如网络异常等
////                    clientExcepion.printStackTrace();
////                    Log.i("AAAA", "clientExcepion != null");
////                }
////                if (serviceException != null) {
////                    // 服务异常
////                    Log.i("AAAA", "serviceException != null");
////                    Log.i("AAAA", serviceException.getRequestId());
////                    Log.i("AAAA", serviceException.getHostId());
////                    Log.i("AAAA", serviceException.getRawMessage());
////                }
////            }
////        });
////
////    }
////}
