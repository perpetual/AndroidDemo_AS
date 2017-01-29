package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.SystemServiceUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-8-13		Create		
 * </pre>
 */
public class WindowActivity extends DemoSuperActivity {

	private View mWindowView = null;
	private WindowManager mWindowManager = null;
	private WindowManager.LayoutParams mWindowLayoutParam = null;
	
	private void showWindow() {
		try {
			mWindowManager.addView(mWindowView, mWindowLayoutParam);
		} catch (Exception e) {
		}
	}
	
	private void hideWindow() {
		try {
			mWindowManager.removeView(mWindowView);
		} catch (Exception e) {
		}
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		mWindowLayoutParam = new WindowManager.LayoutParams();
		mWindowLayoutParam.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		mWindowLayoutParam.format = 1; // 透明的图片，2.3的系统必须要
		mWindowLayoutParam.width = LayoutParams.MATCH_PARENT;
		mWindowLayoutParam.height = LayoutParams.WRAP_CONTENT;
		mWindowLayoutParam.gravity = Gravity.TOP;
		mWindowLayoutParam.x = 0;
		mWindowLayoutParam.y = 0;
		mWindowLayoutParam.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE 
				| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		
		mWindowView = LayoutInflater.from(this).inflate(R.layout.voip_status_bar, null);
		mWindowManager = SystemServiceUtil.getWindowManager();
	}
	
	@Override
	public void initView() {
		super.initView();
	}
	
	@Override
	protected void doTopButtonClick() {
		showWindow();
	}
	
	@Override
	protected void doBotttomButtonClick() {
		super.doBotttomButtonClick();
		hideWindow();
	}
	
	@Override
	protected String getBottomButtonText() {
		return "close window";
	}
	
	@Override
	protected String getTopButtonText() {
		return "show window";
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		hideWindow();
	}
}

