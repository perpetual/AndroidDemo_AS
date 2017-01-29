package com.example.androiddemo.view;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.AndroidDemoUtil;
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
public class MaskView extends CustomView {
	private static final String TAG = MaskView.class.getSimpleName();
	private Drawable mMaskDrawable = null;
	private final Paint mBitmapPaint = new Paint();
	private final Paint mMaskPaint = new Paint();
	private Bitmap mNormalMaskBitmap = null;
	private Bitmap mPressMaskBitmap = null;
	private Bitmap mBitmap = null;
    private BitmapShader mBitmapShader = null;
    private BitmapShader mMaskNormalBitmapShader = null;
    private BitmapShader mMaskPressBitmapShader = null;
    private boolean mIsBitmapCenter = true;
    private float mBitmapRotation = 0f;
    private Rect mBitmapRect = new Rect();
	private final int[] PRESSED_STATE = View.PRESSED_ENABLED_STATE_SET;
	
	public MaskView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setMaskDrawable(Drawable drawable) {
		if (drawable == mMaskDrawable) {
			return;
		}
		mMaskDrawable = drawable;
		LogUtil.d(TAG, "setMaskDrawable", drawable);
		mNormalMaskBitmap = AndroidDemoUtil.getBitmapFromDrawable(drawable, getMeasuredWidth(), getMeasuredHeight());
		if (null != drawable) {
			drawable.setState(PRESSED_STATE);
		}
		mPressMaskBitmap = AndroidDemoUtil.getBitmapFromDrawable(drawable, getMeasuredWidth(), getMeasuredHeight());
		if (null != mNormalMaskBitmap) {
			mMaskNormalBitmapShader = new BitmapShader(mNormalMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		}
		if (null != mPressMaskBitmap) {
			mMaskPressBitmapShader = new BitmapShader(mPressMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		}
		invalidate();
	}
	
	public void setImageSaturation(float saturation) {
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(saturation);
		ColorMatrixColorFilter ccf = new ColorMatrixColorFilter(cm);
		mBitmapPaint.setColorFilter(ccf);
		invalidate();
	}
	
	public void setImageAlpha(float alpha) {
		mBitmapPaint.setAlpha(Math.round(alpha * 0xFF));
		invalidate();
	}
	
	public void setImageCenter(boolean center) {
		mIsBitmapCenter = center;
		invalidate();
	}
	
	public void setImageRotation(float degree) {
		mBitmapRotation = degree;
		invalidate();
	}
	
	public void setImageMatrix(Matrix matrix) {
		invalidate();
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		LogUtil.d(TAG, "initData", mBitmapPaint);
	}
	
	@Override
	public void initView() {
		super.initView();
		setBackgroundResource(android.R.color.holo_green_light);
		setClickable(true);
	}
	
	@Override
	public void updateView() {
		super.updateView();
	}

	@Override
	public void setImageResource(int resId) {
		mBitmap = AndroidDemoUtil.getBitmapFromDrawable(getResources().getDrawable(resId));
		if (null != mBitmap) {
			mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			mBitmapRect.set(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
//			mBitmapPaint.setShader(mBitmapShader);
		}
		invalidate();
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (null != mBitmap) {
			canvas.save();
			if (mIsBitmapCenter) {
				mBitmapRect.offsetTo((getWidth() - mBitmap.getWidth()) / 2, (getHeight() - mBitmap.getHeight()) / 2);
			} else {
				mBitmapRect.setEmpty();
			}
			canvas.rotate(mBitmapRotation, mBitmapRect.centerX(), mBitmapRect.centerY());
			canvas.drawBitmap(mBitmap, null, mBitmapRect, mBitmapPaint);
			canvas.restore();
		}
		
		if (null != mMaskDrawable) {
			if (!StateSet.stateSetMatches(PRESSED_STATE, getDrawableState()) && null != mMaskNormalBitmapShader) {
				mMaskPaint.setShader(mMaskNormalBitmapShader);
				canvas.drawRect(0, 0, mNormalMaskBitmap.getWidth(), mNormalMaskBitmap.getHeight(), mMaskPaint);
			} else if (StateSet.stateSetMatches(PRESSED_STATE, getDrawableState()) && null != mMaskPressBitmapShader) {
				mMaskPaint.setShader(mMaskPressBitmapShader);
				canvas.drawRect(0, 0, mPressMaskBitmap.getWidth(), mPressMaskBitmap.getHeight(), mMaskPaint);
			}	
		}
		
	}
	
	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		LogUtil.d(TAG, Arrays.toString(getDrawableState()), Arrays.toString(PRESSED_STATE));
		invalidate();
	}
}

