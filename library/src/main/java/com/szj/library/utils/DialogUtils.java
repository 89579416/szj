package com.szj.library.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * DialogUtil
 * 自定义Dialog
 * 目前是用的别人写好的。我调用一下
 * Created by Administrator on 2016/5/12 0012.
 */
public class DialogUtils {
    public static KProgressHUD hud;
    /**
     * 创建并显示
     * @param context
     * @param str
     * @return
     */
    public static KProgressHUD show(Context context,String str) {
        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(str)
                .setDimAmount(0.5f)//设置 Dialog 周围的颜色。系统默认的是半透明的灰色。值设为0则为完全透明。
                .setCancellable(true).show();
        return hud;
    }

    /**
     * 关闭
     */
    public static void dismiss() {
        //防止 不关闭
//        Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
        try{
            if (hud != null) {
                hud.dismiss();
                hud = null;
            }
        }catch (Exception e){}

//            }
//        };
//        handler.sendEmptyMessage(1);
    }

}
