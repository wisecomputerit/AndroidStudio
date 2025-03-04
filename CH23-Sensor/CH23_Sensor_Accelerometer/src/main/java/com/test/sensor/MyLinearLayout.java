package com.test.sensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {
	private int x1, y1, radius, delta;
	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		radius = 30;
		x1 = 50;
		y1 = 50;
		
		delta = 5;
		
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(0xFFFFFF00);
		paint.setStyle(Paint.Style.FILL);
		
		if(x1 < radius) x1 = radius;
		if(y1 < radius) y1 = radius;
		if(x1 > getWidth()-radius-delta) x1 = getWidth()-radius-delta;
		if(y1 > getHeight()-radius-delta) y1 = getHeight()-radius-delta;
		
		canvas.drawCircle(x1, y1, radius, paint);
		
		paint.setColor(0xFFFF0000);
		
		
		
	}
	
	public void setPosition(float gx, float gy) {
		this.x1 -= (int)(gx * 20);
		this.y1 += (int)(gy * 20);
		
		invalidate();
	}

}
