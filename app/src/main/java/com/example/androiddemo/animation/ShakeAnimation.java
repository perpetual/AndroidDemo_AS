package com.example.androiddemo.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：晃动动画
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-27		Create		
 * </pre>
 */
public class ShakeAnimation extends TranslateAnimation {
	
	public ShakeAnimation(View view) {
		super(view.getWidth(), view.getWidth() + 15, view.getHeight(), view.getHeight());
		setRepeatCount(1);
		setRepeatMode(Animation.REVERSE);
		setDuration(500);
	}
}

