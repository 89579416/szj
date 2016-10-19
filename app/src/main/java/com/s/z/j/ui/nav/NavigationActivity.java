package com.s.z.j.ui.nav;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.s.z.j.R;
import com.szj.library.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 *引导页面
 * Created by Administrator on 2016/5/12 0012.
 */
public class NavigationActivity extends Activity {
    private ViewPager viewPager;
    private List<View> mImageViews; // 滑动的图片集合
    private int[] imageResId; // 图片ID集合
    private int currentItem = 0; // 当前图片的索引号
    private GestureDetector gestureDetector; // 用户滑动
    /**
     * 记录当前分页ID
     */
    private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3

    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_navigation);

        gestureDetector = new GestureDetector(new GuideViewTouch());

        // 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;
        imageResId = new int[]{R.mipmap.a1, R.mipmap.a2, R.mipmap.a3};
        mImageViews = new ArrayList<View>();
        // 初始化图片资源
        LayoutInflater viewInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 1
        View convertView1 = viewInflater.inflate(R.layout.guide_item, null);
        LinearLayout linearLayout1 = (LinearLayout) convertView1.findViewById(R.id.guide_item);
        linearLayout1.setBackgroundResource(imageResId[0]);
        mImageViews.add(linearLayout1);
        // 2
        View convertView2 = viewInflater.inflate(R.layout.guide_item, null);
        LinearLayout linearLayout2 = (LinearLayout) convertView2.findViewById(R.id.guide_item);
        linearLayout2.setBackgroundResource(imageResId[1]);
        mImageViews.add(linearLayout2);
        // 3
        View convertView3 = viewInflater.inflate(R.layout.guide_item, null);
        LinearLayout linearLayout3 = (LinearLayout) convertView3.findViewById(R.id.guide_item);
        linearLayout3.setBackgroundResource(imageResId[2]);
        mImageViews.add(linearLayout3);
        // button监听
        btn = (Button) convertView3.findViewById(R.id.start);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                goToLoginActivity();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.navigation_pager);
        viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    private class GuideViewTouch extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (currentItem == 2) {
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY()) && (e1.getX() - e2.getX() <= (-flaggingWidth) || e1.getX() - e2.getX() >= flaggingWidth)) {
                    if (e1.getX() - e2.getX() >= flaggingWidth) {
                        goToLoginActivity();
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * 进入主界面
     */
    void goToLoginActivity() {
//        Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
//        startActivity(intent);
        T.s(this,"准备进入其他界面");
        finish();
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements OnPageChangeListener {
        public void onPageSelected(int position) {
            currentItem = position;
            if(currentItem==2){
                btn.setVisibility(View.VISIBLE);
            }else {
                btn.setVisibility(View.GONE);
            }
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    /**
     * 填充ViewPager页面的适配器
     *
     * @author Administrator
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mImageViews.get(arg1));
            return mImageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToLoginActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}