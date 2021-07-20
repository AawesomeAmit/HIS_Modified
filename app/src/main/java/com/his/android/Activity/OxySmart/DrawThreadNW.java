package com.his.android.Activity.OxySmart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;

import com.creative.base.BaseDate.Wave;

/**
 * draw pod wave by view
 */
public class DrawThreadNW extends BaseDraw {

	private final int ySpo2Max = 200; //130
	protected int gain = 2;
	private float zoomSpo2 = 0.0f;
	private String msg;

	public DrawThreadNW(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DrawThreadNW(Context context) {
		super(context);
	}

	public DrawThreadNW(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public synchronized void Continue() {
		super.Continue();
		cleanWaveData();
	}

	@Override
	public void cleanWaveData() {
		OxySmartActivity.SPO_WAVE.clear();
		super.cleanWaveData();
	}

	@Override
	public void run() {
		super.run();
		synchronized (this) {
			while (!stop) {
				try {
					if (pause) {
						this.wait();
					}
					if (OxySmartActivity.SPO_WAVE.size() > 0) {
						Wave data = OxySmartActivity.SPO_WAVE.remove(0);
						addData(data.data);
						if (data.flag == 1) { //发送搏动标记
							mHandler.sendEmptyMessage(OxySmartActivity.MSG_DATA_PULSE);
						}
						
						//设置参数，调整波形，adjust wave
						if (OxySmartActivity.SPO_WAVE.size() > 20) {
							Thread.sleep(12); //18
						} else {
							Thread.sleep(25); //25
						}							
					} else {
						Thread.sleep(500);
					}					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			cleanWaveData();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//如果是eclipse、androidstudio的编辑模式下,跳过以下代码
		if(isInEditMode()){
			return; 
		}
		
		if (msg != null && !msg.equals(""))
			drawMsg(canvas);
		paint.setPathEffect(cornerPathEffect);
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(dm.density);
		Path path = new Path();
		path.moveTo(0, gethPx(data2draw[0]));
		for (int i = 0; i < data2draw.length; i++) {
			path.lineTo(i * stepx, gethPx(data2draw[i]));
		}
		canvas.drawPath(path, paint);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(5);
		canvas.drawLine(arraycnt * stepx, 0, arraycnt * stepx, height, paint);
	}

	private void drawMsg(Canvas canvas) {
		Paint mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(dm.density * 2);
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(dm.density * 20);
		canvas.drawText(msg, (weight - mPaint.measureText(msg)) / 2,
				height / 2, mPaint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		zoomSpo2 = (float) (height / ySpo2Max);
	}

	private float gethPx(int data) {
		return height - zoomSpo2 * data;
	}

	/**
	 * 
	 * @param gain
	 */
	public void setGain(int gain) {
		this.gain = gain == 0 ? 2 : gain;
	}

	public void drawMsg(String msg) {
		this.msg = msg;
		postInvalidate();
	}
}
