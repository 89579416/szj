package com.s.z.j.newUtils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import java.lang.reflect.Method;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : ��Ļ��ع�����
 * </pre>
 */
public class ScreenUtils {

    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * ��ȡ��Ļ�Ŀ��px
     *
     * @param context ������
     * @return ��Ļ��px
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// ������һ�Ű�ֽ
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// ����ֽ���ÿ��
        return outMetrics.widthPixels;
    }

    /**
     * ��ȡ��Ļ�ĸ߶�px
     *
     * @param context ������
     * @return ��Ļ��px
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// ������һ�Ű�ֽ
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// ����ֽ���ÿ��
        return outMetrics.heightPixels;
    }

    /**
     * ����͸��״̬��(api����19����ʹ��)
     * <p>����Activity��onCreat()�е���</p>
     * <p>���ڶ����ؼ������м����������������ݳ�����״̬��֮��</p>
     * <p>android:clipToPadding="true"</p>
     * <p>android:fitsSystemWindows="true"</p>
     *
     * @param activity activity
     */
    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //͸��״̬��
            activity.getWindow().addFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //͸��������
            activity.getWindow().addFlags(LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * ����״̬��
     * <p>Ҳ��������ȫ����һ��Ҫ��setContentView֮ǰ���ã����򱨴�</p>
     * <p>�˷���Activity���Լ̳�AppCompatActivity</p>
     * <p>������ʱ��״̬������ʾһ�������أ�����QQ�Ļ�ӭ����</p>
     * <p>�������ļ���Activity������android:theme="@android:style/Theme.NoTitleBar.Fullscreen"</p>
     * <p>�������������Activity���ܼ̳�AppCompatActivity���ᱨ��</p>
     *
     * @param activity activity
     */
    public static void hideStatusBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * ��ȡ״̬���߶�
     *
     * @param context ������
     * @return ״̬���߶�
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * �ж�״̬���Ƿ����
     *
     * @param activity activity
     * @return {@code true}: ����<br>{@code false}: ������
     */
    public static boolean isStatusBarExists(Activity activity) {
        LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & LayoutParams.FLAG_FULLSCREEN) != LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * ��ȡActionBar�߶�
     *
     * @param activity activity
     * @return ActionBar�߶�
     */
    public static int getActionBarHeight(Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /**
     * ��ʾ֪ͨ��
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context        ������
     * @param isSettingPanel {@code true}: ������<br>{@code false}: ��֪ͨ
     */
    public static void showNotificationBar(Context context, boolean isSettingPanel) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand"
                : (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
        invokePanels(context, methodName);
    }

    /**
     * ����֪ͨ��
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context ������
     */
    public static void hideNotificationBar(Context context) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        invokePanels(context, methodName);
    }

    /**
     * ���份��֪ͨ��
     *
     * @param context    ������
     * @param methodName ������
     */
    private static void invokePanels(Context context, String methodName) {
        try {
            Object service = context.getSystemService("statusbar");
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ������ĻΪ����
     * <p>����һ�־�����Activity�м�����android:screenOrientation="landscape"</p>
     * <p>������Activity��android:configChangesʱ�����������µ��ø����������ڣ��к���ʱ��ִ��һ�Σ�������ʱ��ִ������</p>
     * <p>����Activity��android:configChanges="orientation"ʱ���������ǻ����µ��ø����������ڣ��кᡢ����ʱֻ��ִ��һ��</p>
     * <p>����Activity��android:configChanges="orientation|keyboardHidden|screenSize"��4.0���ϱ�������һ��������ʱ
     * �����������µ��ø����������ڣ�ֻ��ִ��onConfigurationChanged����</p>
     *
     * @param activity activity
     */
    public static void setLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * ��ȡ��ǰ��Ļ��ͼ������״̬��
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * ��ȡ��ǰ��Ļ��ͼ��������״̬��
     * <p>��Ҫ�õ������ȡ״̬���߶�getStatusBarHeight�ķ���</p>
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * �ж��Ƿ�����
     *
     * @param context ������
     * @return {@code true}: ��<br>{@code false}: ��
     */
    public static boolean isScreenLock(Context context) {
        KeyguardManager km = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }
}