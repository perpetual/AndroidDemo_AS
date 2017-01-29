package com.example.androiddemo.animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-27		Create		
 * </pre>
 */
public class BreatheAniamation extends AlphaAnimation {

	public BreatheAniamation() {
		super(1.0f, 0.2f);
		setDuration(200);
		setRepeatMode(Animation.REVERSE);
		Interpolator interpolator = new LinearInterpolator();
		setInterpolator(interpolator);
		setRepeatCount(5);
	}
}

