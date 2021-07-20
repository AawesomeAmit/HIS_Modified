package com.his.android.Activity.OxySmart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class BackGround extends View {

	public static DisplayMetrics dm;
	public static float xPX2MMUnit = 0.0f;
	public static float yPX2MMUnit = 0.0f;
	private float width = 0.0f;
	public static float height = 0.0f;

	protected static final int minHeight = 4;

	public static float gridHeigh = 0.0f;

	public static int gridCnt = 0;

	public static int mWidth = 0;
	public static int mHeight = 0;

	private int backgroundColor = 0;

	private Paint mPaint;

	public BackGround(Context context) {
		super(context);
		initScreen(context);
	}

	public BackGround(Context context, AttributeSet attrs) {
		super(context, attrs);
		initScreen(context);
	}

	public BackGround(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initScreen(context);
	}

	private void initScreen(Context context) {
		WindowManager wmManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		dm = new DisplayMetrics();
		wmManager.getDefaultDisplay().getMetrics(dm);
		xPX2MMUnit = 25.4f / dm.densityDpi;
		yPX2MMUnit = 25.4f / dm.densityDpi;
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(1);
		backgroundColor = Color.WHITE;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	private boolean isDrawBG = true;

	/**
	 * 
	 * @param isDrawBG
	 */
	public void setDrawBG(boolean isDrawBG) {
		this.isDrawBG = isDrawBG;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!isDrawBG)
			return;
		canvas.drawColor(backgroundColor);
		if (gridCnt < 2) {
			return;
		}
		mPaint.setStrokeWidth(1);
		mPaint.setColor(Color.rgb(0xba, 0xba, 0xba));
		// 绘制纵坐标
		for (float i = 0; i < width; i += gridHeigh) {
			canvas.drawLine(fMMgetPxforX(i), 0, fMMgetPxforX(i), mHeight,
					mPaint);
		}

		int i = gridCnt / 2;
		for (int j = 0; j < i; j++) {
			canvas.drawLine(0, fMMgetPxfory(fPXgetMMforY(mHeight / 2)
					- gridHeigh * j), mWidth,
					fMMgetPxfory(fPXgetMMforY(mHeight / 2) - gridHeigh * j),
					mPaint);
		}

		for (int j = 0; j < i; j++) {
			canvas.drawLine(0, fMMgetPxfory(fPXgetMMforY(mHeight / 2)
					+ gridHeigh * j), mWidth,
					fMMgetPxfory(fPXgetMMforY(mHeight / 2) + gridHeigh * j),
					mPaint);
		}
		drawScale(canvas);
	}

	private boolean isDrawScale = false;

	public void setDrawScale(boolean isDrawScale) {
		this.isDrawScale = isDrawScale;
	}

	private void drawScale(Canvas canvas) {
		if (gridHeigh > 1 && isDrawScale) {
			int h = mHeight / gridCnt;
			mPaint.setColor(Color.BLUE);
			mPaint.setStrokeWidth(dm.density);
			float i = (h * gain) / 2f;
			canvas.drawLine(0, mHeight / 2 - i, h / 2, mHeight / 2 - i, mPaint);
			canvas.drawLine(0, mHeight / 2 + i, h / 2, mHeight / 2 + i, mPaint);
			canvas.drawLine(h / 4, mHeight / 2 - i, h / 4, mHeight / 2 + i,
					mPaint);
		}
	}

	private float gain = 2;

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
		if (gain == 0) {
			this.gain = 0.5f;
		} else
			this.gain = gain;
		postInvalidate();
	}

	/**
	 * 
	 * @param mm
	 * @return
	 */
	public static float fMMgetPxforX(float mm) {
		return mm / xPX2MMUnit;
	}

	/**
	 * 
	 * @param px
	 * @return
	 */
	public static float fPXgetMMforX(int px) {
		return px * xPX2MMUnit;
	}

	/**
	 * 
	 * @param mm
	 * @return
	 */
	public static float fMMgetPxfory(float mm) {
		return mm / yPX2MMUnit;
	}

	/**
	 * 
	 * @param px
	 * @return
	 */
	public static float fPXgetMMforY(int px) {
		return px * yPX2MMUnit;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		setViewHeight(w, h);
	}

	public void setViewHeight(int w, int h) {
		// 将屏幕的像素都转换为毫米单位
		this.width = fPXgetMMforX(w);
		height = fPXgetMMforY(h);
		mHeight = h;
		mWidth = w;

		gridCnt = 6;
		gridHeigh = (float) (height / gridCnt);
		postInvalidate();
	}

	public int getGridCnt() {
		return gridCnt;
	}

	public void setGridCnt(int gridCnt) {
		BackGround.gridCnt = gridCnt;
	}

	public float getGridHeigh() {
		return gridHeigh;
	}

	public void setGridHeigh(float gridHeigh) {
		BackGround.gridHeigh = gridHeigh;
	}

}
