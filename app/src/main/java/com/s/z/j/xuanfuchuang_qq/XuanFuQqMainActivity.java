package com.s.z.j.xuanfuchuang_qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.s.z.j.R;

public class XuanFuQqMainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_qq);
		Button startFloatWindow = (Button) findViewById(R.id.start_float_window);
		startFloatWindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(XuanFuQqMainActivity.this, FloatWindowQqService.class);
				startService(intent);
				finish();
			}
		});
	}
}
