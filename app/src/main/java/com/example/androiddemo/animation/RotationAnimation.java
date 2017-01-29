package com.example.androiddemo.animation;

import android.view.View;
import android.view.animation.RotateAnimation;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-5-7		Create		
 * </pre>
 */
public class RotationAnimation extends RotateAnimation  {

	public RotationAnimation(View view) {
		super(0, 360, view.getWidth() / 2, view.getHeight() / 2);
		setDuration(200);
	}
}

