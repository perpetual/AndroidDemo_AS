package com.example.androiddemo.activity;

import com.example.androiddemo.utils.SystemServiceUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2015-4-26		Create		
 * </pre>
 */
public class VibratorActivity extends DemoSuperActivity {

	@Override
	protected String getTopButtonText() {
		return "vibrator attrib";
	}
	
	@Override
	protected void doTopButtonClick() {
	}
	
	@Override
	protected String getLeftButtonText() {
		return "vibrator";
	}
	
	@Override
	protected void doLeftButtonClick() {
		SystemServiceUtil.getVibratorService().vibrate(200);
	}
	
	@Override
	protected String getRightButtonText() {
		return "vibrator custom";
	}
	
	@Override
	protected void doRightButtonClick() {
		SystemServiceUtil.getVibratorService().vibrate(new long[]{1000, 1000, 3000, 3000, 5000, 5000}, 1);
	}
	
	@Override
	protected String getBottomButtonText() {
		return "cancel vibrator";
	}
	
	@Override
	protected void doBotttomButtonClick() {
		SystemServiceUtil.getVibratorService().cancel();
	}
}

