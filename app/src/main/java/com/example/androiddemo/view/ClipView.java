package com.example.androiddemo.view;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.androiddemo.utils.LogUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-5-30		Create		
 * </pre>
 */
public class ClipView extends CustomView {
	private static final String TAG = ClipView.class.getSimpleName();
	private Drawable mMaskDrawable = null;
	private final Paint mBitmapPaint = new Paint();
	private Bitmap mNormalMaskBitmap = null;
	private Bitmap mPressMaskBitmap = null;
	private Canvas mTempCanvas = null;
    private BitmapShader mBitmapShader = null;
	
	public ClipView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setMaskDrawable(Drawable drawable) {
		LogUtil.d(TAG, "setMaskDrawable", drawable);
		mMaskDrawable = drawable;
		updateBitmapFromDrawable(mMaskDrawable);
		invalidate();
	}
	
	private Canvas getCanvas(Bitmap bitmap) {
		if (null == mTempCanvas) {
			mTempCanvas = new Canvas();
		}
		if (null != bitmap) {
			mTempCanvas.setBitmap(bitmap);
		}
		return mTempCanvas;
	}
	
	private void updateBitmapFromDrawable(Drawable drawable) {
		if (null == drawable) {
			return;
		}
		if (drawable instanceof ColorDrawable) {
            mNormalMaskBitmap = mPressMaskBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
		} else {
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			mNormalMaskBitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			drawable.draw(getCanvas(mNormalMaskBitmap));

			drawable.setState(View.PRESSED_STATE_SET);
			mPressMaskBitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			drawable.draw(getCanvas(mPressMaskBitmap));
		}
		if (StateSet.stateSetMatches(View.PRESSED_STATE_SET, getDrawableState())) {
		    mBitmapShader = new BitmapShader(mPressMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		} else {
		    mBitmapShader = new BitmapShader(mNormalMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		}
	    mBitmapPaint.setAntiAlias(true);
	    mBitmapPaint.setShader(mBitmapShader);
	}
	
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		LogUtil.d(TAG, "initData", mBitmapPaint);
	}
	
	@Override
	public void initView() {
		super.initView();
//		setImageResource(R.drawable.resource);
//		setBackgroundResource(R.drawable.ic_launcher);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		LogUtil.d(TAG, "dispatchTouchEvent", event.getAction());
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		LogUtil.d(TAG, "onTouchEvent", event.getAction());
		return super.onTouchEvent(event);
	}
	
	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
	}
	
	@Override
	public void setBackgroundResource(int resid) {
		super.setBackgroundResource(resid);
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (null != mPressMaskBitmap && StateSet.stateSetMatches(View.PRESSED_STATE_SET, getDrawableState())) {
			canvas.drawRect(0, 0, mPressMaskBitmap.getWidth(), mPressMaskBitmap.getHeight(), mBitmapPaint);
		} else if (null != mNormalMaskBitmap){
			canvas.drawRect(0, 0, mNormalMaskBitmap.getWidth(), mNormalMaskBitmap.getHeight(), mBitmapPaint);
		} else if (null != mMaskDrawable) {
			mMaskDrawable.setBounds(0, 0, getWidth(), getHeight());
			mMaskDrawable.setState(getDrawableState());
			mMaskDrawable.draw(canvas);
		}		
	}
	
	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		LogUtil.d(TAG, Arrays.toString(getDrawableState()), Arrays.toString(View.PRESSED_STATE_SET));
		if (null == mPressMaskBitmap || null == mNormalMaskBitmap) {
			return;
		}
		if (StateSet.stateSetMatches(View.PRESSED_STATE_SET, getDrawableState())) {
		    mBitmapShader = new BitmapShader(mPressMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		} else {
		    mBitmapShader = new BitmapShader(mNormalMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		}
		mBitmapShader.setLocalMatrix(null);
	    mBitmapPaint.setAntiAlias(true);
	    mBitmapPaint.setShader(mBitmapShader);
		invalidate();
	}
}

