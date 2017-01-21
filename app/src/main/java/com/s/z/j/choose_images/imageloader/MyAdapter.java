package com.s.z.j.choose_images.imageloader;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.s.z.j.R;
import com.s.z.j.choose_images.utils.CommonAdapter;
import com.s.z.j.utils.T;

public class MyAdapter extends CommonAdapter<String> {

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static List<String> mSelectedImage = new LinkedList<String>();
    public int num = 9;
    private Context context;
    public Handler handler = null;

    /**
     * 文件夹路径
     */
    private String mDirPath;

    public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
                     String dirPath) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.mDirPath = dirPath;
    }

    @Override
    public void convert(final com.s.z.j.choose_images.utils.ViewHolder helper, final String item) {
        //设置no_pic
        helper.setImageResource(R.id.id_item_image, R.mipmap.pictures_no);
        //设置no_selected
        helper.setImageResource(R.id.id_item_select,
                R.mipmap.picture_unselected);
        //设置图片
        helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final ImageView mSelect = helper.getView(R.id.id_item_select);

        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mImageView.setOnClickListener(new OnClickListener() {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {
                    // 已经选择过该图片
                    if (mSelectedImage.contains(mDirPath + "/" + item)) {
                        mSelectedImage.remove(mDirPath + "/" + item);
                        mSelect.setImageResource(R.mipmap.picture_unselected);
                        mImageView.setColorFilter(null);
                    } else{   // 未选择该图片
                        if(mSelectedImage.size() < num) {
                            mSelectedImage.add(mDirPath + "/" + item);
                            mSelect.setImageResource(R.mipmap.pictures_selected);
                            mImageView.setColorFilter(Color.parseColor("#77000000"));
                        }else {
                            T.s(context,"最多选择9张图片");
                        }
                    }
                Message message = handler.obtainMessage();
                message.what = 1;
                message.arg1 = mSelectedImage.size();
                handler.sendMessage(message);

            }
        });
        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mSelectedImage.contains(mDirPath + "/" + item)) {
            mSelect.setImageResource(R.mipmap.pictures_selected);
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        }
    }
    public List<String> getSelectImage(){
        return mSelectedImage == null?null:mSelectedImage;
    }

    public void setHandler(Handler hand){
        handler = hand;
    }
}
