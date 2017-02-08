package com.s.z.j.photo_wall_falls_demo;

import android.os.Bundle;
import android.view.Window;
import android.app.Activity;

import com.s.z.j.R;

/**
 * Android照片墙加强版，使用ViewPager实现画廊效果Demo
 */
public class PhotoWallFallsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_photo_wall_falls_main);
	}

}
