package com.szj.library.camera;

/**
 * Created by Administrator on 2016/4/29 0029.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.szj.library.R;


/**
 * Created by Fan on 2014-12-19.
 */
public class ChoosePicturePopupWindow extends PopupWindow {
    private Button btn_take_photo, btn_album_photo, btn_cancel;
    private View mMenuView;

    public ChoosePicturePopupWindow(Activity context,View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context   .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_choose_picture, null);
        btn_take_photo = (Button) mMenuView.findViewById(R.id.pop_take_photo_btn);
        btn_album_photo = (Button) mMenuView.findViewById(R.id.pop_choose_album_btn);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);

        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });

        //设置按钮监听
        btn_album_photo.setOnClickListener(itemsOnClick);
        btn_take_photo.setOnClickListener(itemsOnClick);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);

        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);

        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);

        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);

        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 设置第一个按钮文字
     * @param text
     */
    public void setFirstText(String text) {
        btn_take_photo.setText(text);
    }

    /**
     * 设置第二个按钮文字
     * @param text
     */
    public void setSecondText(String text){
        btn_album_photo.setText(text);
    }

    /**
     * 只显示一个按钮
     */
    public void hideSecondBtn(){
        btn_album_photo.setVisibility(View.GONE);
    }

    public Button getBtn_take_photo() {
        return btn_take_photo;
    }

    public Button getBtn_album_photo() {
        return btn_album_photo;
    }

    public Button getBtn_cancel() {
        return btn_cancel;
    }
}
