package com.example.androiddemo.activity;

import android.content.pm.PackageManager;

import com.example.androiddemo.utils.AndroidDemoUtil;

public class TestActivity extends DemoSuperActivity {

	@Override
	protected String getTopButtonText() {
		return "TEST";
	}
	
	@Override
	protected void doTopButtonClick() {
		PackageManager pm = getPackageManager();
		updateTextView(TEXT_VIEW_TOP, AndroidDemoUtil.argumentsToString(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)), false);
	}
}
