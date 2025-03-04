package com.test.sensor;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {
	
	private float current_degrees;
	
	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setRotate(float degrees){
		current_degrees = degrees %360.0f;
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
	
		int px = getMeasuredWidth();
		int py =this.getMeasuredHeight();
		canvas.rotate(current_degrees, px/2, py/2);
		super.onDraw(canvas);
	}

}
