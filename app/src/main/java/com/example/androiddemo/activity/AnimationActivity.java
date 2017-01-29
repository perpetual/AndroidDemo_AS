package com.example.androiddemo.activity;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.androiddemo.R;
import com.example.androiddemo.animation.BreatheAniamation;
import com.example.androiddemo.animation.PathShakeAnimation;
import com.example.androiddemo.animation.Rotate3dAnimation;
import com.example.androiddemo.animation.ShakeAnimation;
import com.example.androiddemo.utils.ThreadUtils;


public class AnimationActivity extends DemoSuperActivity {

	private BreatheAniamation mCircleAnimation = null;
	private Rotate3dAnimation mRotate3dAnimation = null;
	private ShakeAnimation mShakeAnimation = null;
	private PathShakeAnimation mRandomShakeAnimation = null;

	private void startBreatheAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mCircleAnimation);
	}
	
	private void startRotate3dAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mRotate3dAnimation);
	}
	
	private void startShakeAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mShakeAnimation);
	}
	
	private void startRadomShakeAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mRandomShakeAnimation);
	}
	
	@Override
	protected String getLeftButtonText() {
		return "Breathe animation";
	}
	
	@Override
	protected void doLeftButtonClick() {
		startBreatheAnimation();
	}
	
	@Override
	protected String getRightButtonText() {
		return "Rotate3D animation";
	}
	
	@Override
	protected void doRightButtonClick() {
		startRotate3dAnimation();
	}
	
	@Override
	protected String getTopButtonText() {
		return "Shake animation";
	}
	
	@Override
	protected String getBottomButtonText() {
		return "Random shake animation";
	}
	
	@Override
	protected void doTopButtonClick() {
		startShakeAnimation();
	}
	
	@Override
	protected void doBotttomButtonClick() {
		startRadomShakeAnimation();
	}
	
	@Override
	protected int getCustomViewAreaLayoutResource() {
		return R.layout.common_custom_view_layout;
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		mCircleAnimation = new BreatheAniamation();
	}
	
	@Override
	public void initView() {
		super.initView();
		mShakeAnimation = new ShakeAnimation(getCustomView());
		mRotate3dAnimation = new Rotate3dAnimation(0, 180, getCustomView().getWidth() / 2,
				getCustomView().getHeight() / 4, 0, true);
		mRandomShakeAnimation = new PathShakeAnimation();
	}

}