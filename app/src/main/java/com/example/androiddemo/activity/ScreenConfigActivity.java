package com.example.androiddemo.activity;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.androiddemo.R;
import com.example.androiddemo.model.IUIInitialization;
import com.example.androiddemo.view.LabelTextView;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 *
 * Description��
 * 
 * History��
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-11		Create		
 * </pre>
 */
public class ScreenConfigActivity extends DemoBaseActivity implements IUIInitialization {

	private LabelTextView mScreenWidthLTV = null;
	private LabelTextView mScreenHeightLTV = null;
	private LabelTextView mScreenDensityLTV = null;
	private LabelTextView mScreenDpiLTV = null;
	private LabelTextView mScreenXDpiLTV = null;
	private LabelTextView mScreenYDpiLTV = null;
	private LabelTextView mScreenScaledDensityLTV = null;
	private View mLineView = null;
	private LabelTextView mLineWidthLTV = null;
	private TextView mTextView = null;
	private LabelTextView mTextSizeLTV = null;
	
	@Override
	public void initLayout() {
		setContentView(R.layout.screen_config_layout);
	}
	
	@Override
	public void bindView() {
		mScreenWidthLTV = (LabelTextView)findViewById(R.id.screen_width);
		mScreenHeightLTV = (LabelTextView)findViewById(R.id.screen_height);
		mScreenDensityLTV = (LabelTextView)findViewById(R.id.screen_density);
		mScreenDpiLTV = (LabelTextView)findViewById(R.id.screen_dpi);
		mScreenXDpiLTV = (LabelTextView)findViewById(R.id.screen_xdpi);
		mScreenYDpiLTV = (LabelTextView)findViewById(R.id.screen_ydpi);
		mScreenScaledDensityLTV = (LabelTextView)findViewById(R.id.screen_scaled_density);
		mLineView = findViewById(R.id.line);
		mLineWidthLTV = (LabelTextView)findViewById(R.id.line_pixel_width);
		mTextView = (TextView)findViewById(R.id.text);
		mTextSizeLTV = (LabelTextView)findViewById(R.id.text_size);
	}
	
	@Override
	public void initView() {
	}
	
	@Override
	public void refreshView() {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		mScreenWidthLTV.setContent(String.valueOf(dm.widthPixels));
		mScreenHeightLTV.setContent(String.valueOf(dm.heightPixels));
		mScreenDensityLTV.setContent(String.valueOf(dm.density));
		mScreenDpiLTV.setContent(String.valueOf(dm.densityDpi));
		mScreenXDpiLTV.setContent(String.valueOf(dm.xdpi));
		mScreenYDpiLTV.setContent(String.valueOf(dm.ydpi));
		mScreenScaledDensityLTV.setContent(String.valueOf(dm.scaledDensity));
		mLineWidthLTV
				.setContent(String.valueOf(mLineView.getLayoutParams().width)
						+ "(px)/"
						+ String.valueOf(mLineView.getLayoutParams().width
								/ dm.density) + "(dp)");
		mTextSizeLTV.setContent(String.valueOf(mTextView.getTextSize())
				+ "(px)/"
				+ String.valueOf(mTextView.getTextSize() / dm.scaledDensity)
				+ "(sp)");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		refreshView();
	}

	@Override
	public void initData(Context context, AttributeSet attrs) {		
	}

	@Override
	public void updateView() {
		// TODO 自动生成的方法存根
		
	}
}

