//package com.szj.library.getimg.ui;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import com.szj.library.R;
//
//
///**
// * Created by Administrator on 2016/3/2 0002.
// */
//public class SdCardUriTestActivity extends Activity {
//    /** Called when the activity is first created. */
//    private Button btnTake = null;
//    private ImageView lblImage = null;
//
//    private static final int PHOTO_PICKED_WITH_DATA = 3021;
//    private static final int CAMERA_WITH_DATA = 3023;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        btnTake = (Button)findViewById(R.id.button1);
//        lblImage = (ImageView)findViewById(R.id.lblImage);
//        btnTake.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_WITH_DATA);
//            }
//        });
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        if(resultCode!=RESULT_OK)
//            return;
//        switch(requestCode){
//            case CAMERA_WITH_DATA:
//                final Bitmap photo = data.getParcelableExtra("data");
//                if(photo!=null){
//                    doCropPhoto(photo);
//                }
//            case PHOTO_PICKED_WITH_DATA:
//                Bitmap photo1 = data.getParcelableExtra("data");
//                if(photo1!=null){
//                    lblImage.setImageBitmap(photo1);
//                }
//
//        }
//    }
//
//    protected void doCropPhoto(Bitmap data){
//        Intent intent = getCropImageIntent(data);
//        startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
//    }
//
//    public static Intent getCropImageIntent(Bitmap data) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setType("image/*");
//        intent.putExtra("data", data);
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 128);
//        intent.putExtra("outputY", 128);
//        intent.putExtra("return-data", true);
//        return intent;
//    }
//}