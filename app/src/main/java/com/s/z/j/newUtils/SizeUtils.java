package com.s.z.j.newUtils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : �ߴ���ع�����
 * </pre>
 */
public class SizeUtils {

    private SizeUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * dpתpx
     *
     * @param context ������
     * @param dpValue dpֵ
     * @return pxֵ
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * pxתdp
     *
     * @param context ������
     * @param pxValue pxֵ
     * @return dpֵ
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * spתpx
     *
     * @param context ������
     * @param spValue spֵ
     * @return pxֵ
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * pxתsp
     *
     * @param context ������
     * @param pxValue pxֵ
     * @return spֵ
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * ���ֵ�λת��
     * <p>�÷���������TypedValue</p>
     *
     * @param unit    ��λ
     * @param value   ֵ
     * @param metrics DisplayMetrics
     * @return ת�����
     */
    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    /**
     * ��onCreate()����ǿ�л�ȡView�ĳߴ�
     * <p>��ص�onGetSizeListener�ӿڣ���onGetSize�л�ȡview���</p>
     * <p>�÷�ʾ��������ʾ</p>
     * <pre>
     * SizeUtils.forceGetViewSize(view);
     * SizeUtils.setListener(new SizeUtils.onGetSizeListener() {
     *    {@code @Override}
     *     public void onGetSize(View view) {
     *         Log.d("tag", view.getWidth() + " " + view.getHeight());
     *     }
     * });
     * </pre>
     *
     * @param view ��ͼ
     */
    public static void forceGetViewSize(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onGetSize(view);
                }
            }
        });
    }

    /**
     * ��ȡ��View�ߴ�ļ���
     */
    public interface onGetSizeListener {
        void onGetSize(View view);
    }

    public static void setListener(onGetSizeListener listener) {
        mListener = listener;
    }

    private static onGetSizeListener mListener;

    /**
     * ListView����ǰ����View�ߴ磬��headerView
     * <p>�õ�ʱ��ȥ��ע�Ϳ�����ListView�м���</p>
     * <p>��������ע�ʹ���</p>
     *
     * @param view ��ͼ
     */
    public static void measureViewInLV(View view) {
        Log.i("tips", "U should copy the following code.");
        /*
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
        */
    }
}
