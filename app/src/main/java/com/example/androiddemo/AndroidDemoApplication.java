package com.example.androiddemo;

import android.app.Application;

import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-22		Create		
 * </pre>
 */
public class AndroidDemoApplication extends Application {
	private static final String TAG = AndroidDemoApplication.class.getSimpleName();
	
	@Override
	public void onCreate() {
		super.onCreate();
		LogUtil.d(TAG, "AndroidDemoApplication:onCreate");
		AndroidDemoUtil.APPLICATION_CONTEXT = getApplicationContext();
		AndroidDemoUtil.RESOURCES = getResources();
	}
}

