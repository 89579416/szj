package com.szj.library.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szj.library.R;

/**
 *  Created by MasterFan on 2015/1/14.
 */
public class MTFListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private MTFProgress mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	
	//
	private RelativeLayout headerViewContent;
	private TextView	   headerTimeView;

	public MTFListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MTFListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.mtf_listview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

//		mArrowImageView = (ImageView)findViewById(R.id.xlistview_header_arrow);
		headerViewContent = (RelativeLayout) mContainer.findViewById(R.id.xlistview_header_content);
		headerTimeView	  = (TextView) mContainer.findViewById(R.id.xlistview_header_time);

		mHintTextView = (TextView)findViewById(R.id.xlistview_header_hint_textview);
		mProgressBar = (MTFProgress)findViewById(R.id.xlistview_header_progressbar);
	}


	public RelativeLayout getHeaderViewContent() {
		return headerViewContent;
	}

	public TextView getHeaderTimeView() {
		return headerTimeView;
	}

	/**
	 * Master set progress
	 * @param progress
	 */
	public void setProgress(int progress, int headViewHeight){
		if(progress >= headViewHeight) progress = headViewHeight;
		progress = progress * 360 / headViewHeight;
		mProgressBar.setProgress(progress);
	}

	public void startRotateProgress(){
		if(mProgressBar.isSpinning()) {
			mProgressBar.stopSpinning();
		}
		mProgressBar.resetCount();
		mProgressBar.setText("");
		mProgressBar.spin();
	}

	public void stopRotateProgress(){
		if(mProgressBar.isSpinning()) {
			mProgressBar.stopSpinning();
		}
	}

	public void setState(int state) {
		if (state == mState) return ;

		if (state == STATE_REFRESHING) {	// 显示进度
//			mArrowImageView.clearAnimation();
//			mArrowImageView.setVisibility(View.INVISIBLE);
//			mProgressBar.setVisibility(View.VISIBLE);
		} else {	// 显示箭头图片
//			mArrowImageView.setVisibility(View.VISIBLE);
//			mProgressBar.setVisibility(View.INVISIBLE);
		}

		switch(state){
		case STATE_NORMAL:
			if (mState == STATE_READY) {
//				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
//				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText(R.string.xlistview_header_hint_normal);
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mHintTextView.setText(R.string.xlistview_header_hint_ready);
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText(R.string.xlistview_header_hint_loading);
			break;
			default:
		}

		mState = state;
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

}
