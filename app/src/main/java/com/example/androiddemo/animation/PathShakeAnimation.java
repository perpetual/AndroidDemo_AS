package com.example.androiddemo.animation;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-7-3		Create		
 * </pre>
 */
public class PathShakeAnimation extends AnimationSet {
	
	private static final int SINGLE_ANIMATION_DURATION = 17;
	private static final float SHAKE_OFFSET = 12;	
	private static final float[][] SHAKE_ANIMATION_PATH = new float[][] {
			{ 0f, SHAKE_OFFSET }, {0f, 0f},
			{ 0f, -SHAKE_OFFSET }, {0f, 0f},
			{ -SHAKE_OFFSET, 0f }, {0f, 0f},
			{ SHAKE_OFFSET, 0f }, {0f, 0f},
			{ SHAKE_OFFSET, SHAKE_OFFSET },{0f, 0f},
			{ -SHAKE_OFFSET, -SHAKE_OFFSET }, {0f, 0f},
			{ -SHAKE_OFFSET, SHAKE_OFFSET }, {0f, 0f},
			{ SHAKE_OFFSET, -SHAKE_OFFSET }, {0f, 0f}};
	private static final int ANIMATION_COUNT = SHAKE_ANIMATION_PATH.length;
	
	public PathShakeAnimation() {
		super(true);
		updateAnimation();
	}
	
	public void updateAnimation() {
		getAnimations().clear();
		float[] lastLocation = new float[] { 0f, 0f };
		for (int i = 0; i < ANIMATION_COUNT; ++i) {
			TranslateAnimation animation = new TranslateAnimation(lastLocation[0],
					lastLocation[0] = SHAKE_ANIMATION_PATH[i % ANIMATION_COUNT][0],
					lastLocation[1], lastLocation[1] = SHAKE_ANIMATION_PATH[i % ANIMATION_COUNT][1]);
			animation.setDuration(SINGLE_ANIMATION_DURATION);
			animation.setStartOffset(i * animation.getDuration());
			addAnimation(animation);
		}
	}
}
	

