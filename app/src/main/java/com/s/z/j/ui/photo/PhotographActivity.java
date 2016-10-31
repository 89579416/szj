package com.s.z.j.ui.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.s.z.j.R;
import com.s.z.j.utils.FileUtil;
import com.szj.library.camera.ImageUploadGetUrl;
import com.szj.library.getimg.util.GetPictureBack;
import com.szj.library.ui.BaseActivity;
import com.szj.library.utils.L;
import com.szj.library.utils.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Administrator on 2016-10-27.
 */
@ContentView(R.layout.activity_photograph)
public class PhotographActivity extends BaseActivity implements View.OnClickListener,GetPictureBack {

    private ImageUploadGetUrl getPictureUtil;//选择图片
    @ViewInject(R.id.photograph_new_picture_layout)
    LinearLayout pictureLayout;

    @ViewInject(R.id.add_booth_new_picture_img)
    private ImageView headImageView;

    private File file;//最后要上传的图片文件

    @Override
    public void initialize(Bundle savedInstanceState) {
        pictureLayout.setOnClickListener(this);
        getPictureUtil = new ImageUploadGetUrl(PhotographActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.photograph_new_picture_layout:
                getPictureUtil.showdiaViewwithAspect(PhotographActivity.this,PhotographActivity.this.findViewById(R.id.photograph_new_picture_layout),1,1);
                break;
        }
    }



    @Override
    public void getBack(String url) {
        L.i("rl=" + url);
        File f = new File(url);
        if(f == null){
            T.s(PhotographActivity.this, "图片保存失败");
            return;
        }else{
            Bitmap bitmap = FileUtil.getLoacalBitmap(url);
            if(bitmap != null){
                headImageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("AAAA", "onActivityResult返回啦！requestCode=" + requestCode + "    resultCode=" + resultCode);
        switch (requestCode) {
            case 6709://截图返回
                L.i("case 6709 开始处理图片");
                if(resultCode == -1){
                    if(getPictureUtil!=null){
                        L.i("getPictureUtil!=null");
                        getPictureUtil.onActivityResult(requestCode, resultCode, data);
                    }
                }
                break;
            case 5://相册返回
                if(resultCode == -1){
                    if(getPictureUtil!=null){
                        L.i("getPictureUtil!=null");
                        getPictureUtil.onActivityResult(requestCode, resultCode, data);
                    }else{
                        L.i("getPictureUtil=======null");
                    }
                }
                break;
            case 100://相机返回
                if(resultCode == -1){
                    if(getPictureUtil!=null){
                        L.i("getPictureUtil!=null");
                        getPictureUtil.onActivityResult(requestCode, resultCode, data);
                    }else{
                        T.s(getApplication(),"保存相片失败，请重新拍照或从相册选择");
                    }
                }
                break;
        }
    }
}
