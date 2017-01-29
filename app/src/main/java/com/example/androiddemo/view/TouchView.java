package com.example.androiddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class TouchView extends RelativeLayout {

	public TouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (MotionEvent.ACTION_DOWN == ev.getAction()) {
			Log.d("xxx", "" + this.getTag() + ":dispatchTouchEvent:" + ev.getAction() + ":" + false);
		}
		boolean b = super.dispatchTouchEvent(ev);
		return b;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (MotionEvent.ACTION_DOWN == ev.getAction()) {
			Log.d("xxx", "" + this.getTag() + ":onInterceptTouchEvent:" + ev.getAction() + ":" + false);
		}
		boolean b = super.onInterceptTouchEvent(ev);
		return b;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			Log.d("xxx", "" + this.getTag() + ":onTouchEvent:" + event.getAction() + ":" + false);
		}
		boolean b = super.onTouchEvent(event);
		return b;
	}
}
