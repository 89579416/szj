package com.s.z.j.fragment.slidingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.s.z.j.R;
import com.s.z.j.ui.slidingmenu.SlidingMainActivity;

/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description 侧边栏菜单
 */
public class LeftFragment extends Fragment implements OnClickListener{
	private View todayView;
	private View settingsView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_sliding_left_menu, null);
		findViews(view);
		
		return view;
	}
	
	
	public void findViews(View view) {
		todayView = view.findViewById(R.id.tvToday);
		settingsView = view.findViewById(R.id.tvMySettings);
		
		todayView.setOnClickListener(this);
		settingsView.setOnClickListener(this);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		Fragment newContent = null;
		String title = null;
		switch (v.getId()) {
		case R.id.tvToday: // 今日
			newContent = new TodayFragment();
			title = getString(R.string.today);
			break;
		case R.id.tvMySettings: // 设置
			newContent = new SettingsFragment();
			title = getString(R.string.settings);
			break;
		default:
			break;
		}
		if (newContent != null) {
			switchFragment(newContent, title);
		}
	}
	
	/**
	 * 切换fragment
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment, String title) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof SlidingMainActivity) {
			SlidingMainActivity fca = (SlidingMainActivity) getActivity();
			fca.switchConent(fragment, title);
		}
	}
	
}
