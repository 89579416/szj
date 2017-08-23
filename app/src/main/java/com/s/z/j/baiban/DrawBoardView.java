package com.s.z.j.baiban;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawBoardView extends View {

    //定義防止線條有鋸齒的常數
    private static final boolean GESTURE_RENDERING_ANTIALIAS = true;
    private static final boolean DITHER_FLAG = true;

	// 底圖 bitmap
    private Bitmap mBackgroundBitmap = null;
    // View 的整個長寬範圍
    private Rect mWholeRect;

    private Bitmap mBitmap = null;	// 圖層繪圖
    private Canvas mCanvas = null;	// 圖層畫布
    private Paint mDefaultPaint = new Paint();	// 空白畫筆

    private boolean mCapturing = true;	// 是否擷取狀態

	//定義繪圖的基本參數：線的 width, color
    private float mPaintWidth = 5f;
    private int mPaintColor = Color.RED;

    private int mPenMode = 1;	//1:為畫筆， 0:為板擦
    private Paint mPaint;

    private Path mPath;

    private List<Path> mDrawList = new ArrayList<Path>();
    private List<Paint> mPaintsList = new ArrayList<Paint>();
    private List<Rect> mRectsList = new ArrayList<Rect>();
    private int mTotalAction = 0;	// 記錄動作的次數

    private float mCurveStartX, mCurveStartY;	// 按下的點
    private float mX, mY;						// 移動的點
    private float mCurveEndX, mCurveEndY;		// 放開的點
 
    private final Rect mInvalidRect = new Rect();
    private int mInvalidateExtraBorder = 10;


    // 建構子
    public DrawBoardView(Context context) {
		super(context);
		init(context);
	}

    // 建構子
    public DrawBoardView(Context context, AttributeSet attrs) {
    	super(context, attrs);
		init(context);
	}

    // 建構子
    public DrawBoardView(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle);
		init(context);
	}

    // 初始化
    private void init(Context context) {
		setWillNotDraw(false);
    }


	private void setPaint(Paint thePaint) {
		if (mPenMode != 0) {
			thePaint.setStrokeWidth(mPaintWidth);
			thePaint.setColor(mPaintColor);
			thePaint.setStyle(Paint.Style.STROKE);
			thePaint.setStrokeJoin(Paint.Join.ROUND);
			thePaint.setStrokeCap(Paint.Cap.ROUND);
			thePaint.setAntiAlias(GESTURE_RENDERING_ANTIALIAS);
			thePaint.setDither(DITHER_FLAG);
			thePaint.setXfermode(null);
		} else { // 橡皮檫模式，先以黑色顯示範圍
			thePaint.setStrokeWidth(mPaintWidth);
			thePaint.setColor(Color.BLACK);
			thePaint.setAntiAlias(GESTURE_RENDERING_ANTIALIAS);
			thePaint.setDither(DITHER_FLAG);
			thePaint.setXfermode(null);
		}
	}


    @Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.mWholeRect = new Rect(0, 0, w, h);

		mBitmap = null;
		mCanvas = null;
		mBitmap = Bitmap.createBitmap(this.mWholeRect.width(), this.mWholeRect.height(), Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);

		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
    	if (mBackgroundBitmap != null) mCanvas.drawBitmap(mBackgroundBitmap, null, this.mWholeRect, null);

    	for (int i = 0; i < mTotalAction; i++) {
            mCanvas.drawPath(mDrawList.get(i), mPaintsList.get(i));
    	}

    	if (mPath != null && mPaint != null) mCanvas.drawPath(mPath, mPaint);

    	canvas.drawBitmap(mBitmap, 0, 0, mDefaultPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
            mCapturing = true;
            mPaint = new Paint();
            setPaint(mPaint);

            mPath = new Path();

            touchDown(event);
            invalidate();

			return true;

		case MotionEvent.ACTION_MOVE:
        	if (mCapturing) {
                Rect rect = touchMove(event);
                if (rect != null) {
                    invalidate(rect);
                }
        	}

			return true;

		case MotionEvent.ACTION_UP:
			touchUp(event);
            invalidate();

            mCapturing = false;

            // 先清除已復原的動作
            int totalAction = mDrawList.size();
            while (totalAction > mTotalAction) {
            	totalAction -= 1;
            	mPaintsList.remove(totalAction);
            	mRectsList.remove(totalAction);
            	mDrawList.remove(totalAction);
            }

            // 再加入新的動作
        	mTotalAction += 1;

        	if (mPenMode == 0) {
 			   mBitmap = null;
 			   mCanvas = null;
 			   mBitmap = Bitmap.createBitmap(this.mWholeRect.width(), this.mWholeRect.height(), Bitmap.Config.ARGB_8888);
 			   mCanvas = new Canvas(mBitmap);

 			   // 橡皮擦屬性
 			   mPaint.setColor(Color.TRANSPARENT);
 			   mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        	}
            mPaintsList.add(mPaint);

            mDrawList.add(mPath);

        	mPaint = null;
        	mPath = null;

        	return true;
		}

		return false;
	}

	private void touchDown(MotionEvent event) {
	       float x = event.getX();
	       float y = event.getY();

	       mCurveStartX = x;
	       mCurveStartY = y;
	       mX = x;
	       mY = y;
	       mCurveEndX = x;
	       mCurveEndY = y;

	       mPath.moveTo(x, y);

	       final int border = mInvalidateExtraBorder;
	       mInvalidRect.set((int) x - border, (int) y - border, (int) x + border, (int) y + border);
	}

	private Rect touchMove(MotionEvent event) {
	       Rect areaToRefresh = null;

	       final float x = event.getX();
	       final float y = event.getY();

	       final float previousX = mX;
	       final float previousY = mY;

	       areaToRefresh = mInvalidRect;

	       // start with the curve end
	       final int border = mInvalidateExtraBorder;
	       areaToRefresh.set((int) mCurveEndX - border, (int) mCurveEndY - border,
	               (int) mCurveEndX + border, (int) mCurveEndY + border);

	       float cX = mCurveEndX = (x + previousX) / 2;
	       float cY = mCurveEndY = (y + previousY) / 2;

	       mPath.quadTo(previousX, previousY, cX, cY);

	       // union with the control point of the new curve
	       areaToRefresh.union((int) previousX - border, (int) previousY - border,
	               (int) previousX + border, (int) previousY + border);

	       // union with the end point of the new curve
	       areaToRefresh.union((int) cX - border, (int) cY - border, (int) cX
	               + border, (int) cY + border);

	       mX = x;
	       mY = y;

	       return areaToRefresh;
	}

	private void touchUp(MotionEvent event) {
	       float x = event.getX();
	       float y = event.getY();

	       mCurveEndX = x;
	       mCurveEndY = y;
	}


	/**
	 * 復原上一動作
	 * return: false 表不能再undo； true 表能繼續undo
	 */
	public boolean undo() {
		   int totalAction = mDrawList.size();
		   if (mTotalAction > 0) {
			   mTotalAction -= 1;

			   mBitmap = null;
			   mCanvas = null;
			   mBitmap = Bitmap.createBitmap(this.mWholeRect.width(), this.mWholeRect.height(), Bitmap.Config.ARGB_8888);
			   mCanvas = new Canvas(mBitmap);

			   invalidate();
		   }

		   if (totalAction == 0 || mTotalAction <= 0) {
			   return false;
		   } else {
			   return true;
		   }
	}


	/**
	 * 取消復原動作
	 * return: false 表不能再 reUndo； true 表能繼續 reUndo
	 */
	public boolean reUndo() {
		   int totalAction = mDrawList.size();
		   if (mTotalAction < totalAction) {
			   mTotalAction += 1;

			   mBitmap = null;
			   mCanvas = null;
			   mBitmap = Bitmap.createBitmap(this.mWholeRect.width(), this.mWholeRect.height(), Bitmap.Config.ARGB_8888);
			   mCanvas = new Canvas(mBitmap);

			   invalidate();
		   }

		   if (totalAction == 0 || mTotalAction >= totalAction) {
			   return false;
		   } else {
			   return true;
		   }
	}


	/**
	 * 檢查可復原動作的狀態
	 * return: 0 表不能再 undo，也不能再 reUndo；1 表不能再 undo，但可再 reUndo；2 表可再 undo，但不能再 reUndo；3 表可再 undo，也可再 reUndo；
	 */
	public int checkUndoStatus() {
		   int totalAction = mDrawList.size();
		   int result = 0;

		   if (totalAction == 0) {	//沒有任何動作資訊
			   result = 0;
		   } else if (mTotalAction <= 0) {	//有動作資訊，但已「復原」至最前端
			   result = 1;
		   } else if (mTotalAction >= totalAction) {	//有動作資訊，但已「取消復原」至最後端
			   result = 2;
		   } else {
			   result = 3;
		   }

		   return result;
	}


	/**
	 * 清除整個 View 的畫面
	 */
	public void clear() {
		   mBackgroundBitmap = null;

		   mDrawList.removeAll(mDrawList);
		   mPaintsList.removeAll(mPaintsList);
		   mRectsList.removeAll(mRectsList);
		   mTotalAction = 0;

		   mBitmap = null;
		   mCanvas = null;
		   mBitmap = Bitmap.createBitmap(this.mWholeRect.width(), this.mWholeRect.height(), Bitmap.Config.ARGB_8888);
		   mCanvas = new Canvas(mBitmap);

		   invalidate();
	}


	/**
	 * 設定整個 View 的畫面圖示
	 */
	public void setWholeViewBitmap(Bitmap bitmap) {
		mBackgroundBitmap = bitmap;
		invalidate();
	} 


	/**
     * 設定為畫筆或板擦
     * @param penMode: 1 為畫筆， 0 為板擦
     */
    public void setPenMode(int penMode) {
    	if (penMode == 0) {
    		this.mPenMode = 0;
    	} else {
    		this.mPenMode = 1;
    	}
	}

    /**
     * 取得是畫筆或板擦
     * @return: 1 為畫筆， 0 為板擦
     */
    public int getPenMode() {
		return this.mPenMode;
	}


	/**
     * 設定畫筆或板擦的寬度
     * @param width
     */
    public void setPaintStrokeWidth(float width) {
        this.mPaintWidth = width;
    }

    /**
     * 取得畫筆或板擦的寬度
     */
    public float getPaintStrokeWidth() {
        return this.mPaintWidth;
    }


	/**
     * 設定畫筆的顏色
     * @param width
     */
    public void setPaintColor(int color) {
        this.mPaintColor = color;
    }

    /**
     * 取得畫筆的顏色
     */
    public int getPaintColor() {
        return this.mPaintColor;
    }

}
