package com.s.z.j.xuanfuchuang_360;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.s.z.j.R;

/**
 *
 */
public class Xuanfu360MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_360);
		Button startFloatWindow = (Button) findViewById(R.id.start_float_window);
		startFloatWindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Xuanfu360MainActivity.this, FloatWindow360Service.class);
				startService(intent);
				finish();
			}
		});
	}
}
