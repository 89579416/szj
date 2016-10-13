package com.szj.library.widget.listview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by MasterFan on 2015/1/14.
 */
public class MTFListView extends MasterListView {

	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;
	public FrameLayout bannerView;

	public MTFListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// mGestureDetector = new GestureDetector(new YScrollDetector());
		setFadingEdgeLength(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		mGestureDetector = new GestureDetector(new YScrollDetector());

		return super.onInterceptTouchEvent(ev)
				&& mGestureDetector.onTouchEvent(ev);
	}

	class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,	float distanceX, float distanceY) {

			if (distanceY != 0 && distanceX != 0) {

			}

			if (null != bannerView) {
				Rect rect = new Rect();
				bannerView.getHitRect(rect);

				if (null != e1) {
					if (rect.contains((int) e1.getX(), (int) e1.getY())) {
						return false;
					}
				}

				if (null != e2) {
					if (rect.contains((int) e2.getX(), (int) e2.getY())) {
						return false;
					}
				}

			}
			// if(Math.abs(distanceY) >= Math.abs(distanceX))
			// {
			// Log.e("listview", "********************** distanceX :" +
			// distanceX + "  distanceY" + distanceY + "\n");
			// return true;
			// }
			// Log.e("listview", "-------------------------- distanceX :" +
			// distanceX + "  distanceY" + distanceY + "\n");
			return true;
		}
	}

}
