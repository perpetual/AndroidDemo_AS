package com.example.androiddemo.animation;

import java.util.ArrayList;

import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

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
 * garyzhao		2015-5-7		Create		
 * </pre>
 */
public class RandomShakeAnimation extends AnimationSet {

	private static final String TAG = PathShakeAnimation.class.getSimpleName();
	
	private static final int ANIMATION_COUNT = 8;
	private static final int SINGLE_ANIMATION_DURATION = 20;
	private static final int SHAKE_OFFSET = 3;
	
	private ArrayList<TranslateAnimation> mTranslationAnimationList = new ArrayList<TranslateAnimation>();
	
	public RandomShakeAnimation() {
		super(true);
		updateTranslationAnimationList();
	}
	
	/*
	 * 私有函数
	 */
	private void updateTranslationAnimationList() {
		mTranslationAnimationList.clear();
		for (int i = 0; i < ANIMATION_COUNT; ++i) {
			float offset1 = getRandomOffset();
			float offset2 = getRandomOffset();
			LogUtil.d(TAG, offset1, offset2);
			TranslateAnimation animation = new TranslateAnimation(0, offset1, 0, offset2);
			animation.setDuration(SINGLE_ANIMATION_DURATION);
			animation.setStartOffset(i * animation.getDuration());
			addAnimation(animation);
		}
	}
	
	private float getRandomOffset() {
		return AndroidDemoUtil.dip2px(SHAKE_OFFSET) - (float) Math.random() * AndroidDemoUtil.dip2px(2 * SHAKE_OFFSET);
	}
}

