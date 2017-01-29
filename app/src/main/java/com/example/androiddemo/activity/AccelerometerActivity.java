package com.example.androiddemo.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.androiddemo.R;
import com.example.androiddemo.animation.BreatheAniamation;
import com.example.androiddemo.animation.ShakeAnimation;
import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.tools.AccelerometerEngine;
import com.example.androiddemo.tools.CommonCallbacks.ICallback;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2015-4-25		Create		
 * </pre>
 */
public class AccelerometerActivity extends DemoSuperActivity implements ICallback, OnSeekBarChangeListener{

	private static final int sViberationMinDuration = 100;
	private static final int sViberationMaxDuration = 3000;
	private static final int sViberationMinInterval = 100;
	private static final int sViberationMaxInterval = 2000;
	private static final int sShakeMaxAmplitude = 60;
	
	private TextView mAmplitudeThresholdTextView = null;
	private SeekBar mAmplitudeThresholdSeekBar = null;
	private TextView mVibrationDurationTextView = null;
	private SeekBar mVibrationDurationSeekBar = null;
	private TextView mVibrationIntervalTextView = null;
	private SeekBar mVibrationIntervalSeekBar = null;
	private TextView mFrequencyTextView = null;
	private SeekBar mFrequencySeekBar = null;
	
	private ShakeAnimation mShakeAnimation = null;
	private BreatheAniamation mFlickerAnimation = null;
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		AccelerometerEngine.getInstance().add(this);
		mFlickerAnimation = new BreatheAniamation();
	}

	@Override
	public void bindView() {
		super.bindView();
		mAmplitudeThresholdTextView = (TextView) findViewById(R.id.amplitude_threshold_text_view);
		mAmplitudeThresholdSeekBar = (SeekBar) findViewById(R.id.amplitude_threshold_seek_bar);
		mVibrationDurationTextView = (TextView) findViewById(R.id.vibration_duration_text_view);
		mVibrationDurationSeekBar = (SeekBar) findViewById(R.id.vibration_duration_seek_bar);
		mVibrationIntervalTextView = (TextView) findViewById(R.id.vibration_interval_text_view);
		mVibrationIntervalSeekBar = (SeekBar) findViewById(R.id.vibration_interval_seek_bar);
		mFrequencyTextView = (TextView) findViewById(R.id.frequency_text_view);
		mFrequencySeekBar = (SeekBar) findViewById(R.id.frequency_seek_bar);
	}

	@Override
	public void initView() {
		super.initView();
		mAmplitudeThresholdSeekBar.setMax(sShakeMaxAmplitude - Math.round(AccelerometerEngine.sMinShakeAmplitudeThreshold));
		mAmplitudeThresholdSeekBar.setProgress(Math.round(AccelerometerEngine.sMaxShakeAmplitudeThreshold - AccelerometerEngine.sMinShakeAmplitudeThreshold));
		mAmplitudeThresholdSeekBar.setOnSeekBarChangeListener(this);
		mAmplitudeThresholdSeekBar.setKeyProgressIncrement(1);
		
		mVibrationDurationSeekBar.setMax(sViberationMaxDuration - sViberationMinDuration);
		mVibrationDurationSeekBar.setProgress(AccelerometerEngine.sVibrationDuration - sViberationMinDuration);
		mVibrationDurationSeekBar.setOnSeekBarChangeListener(this);
		mVibrationDurationSeekBar.setKeyProgressIncrement(100);
		
		mVibrationIntervalSeekBar.setMax(sViberationMaxInterval - sViberationMinInterval);
		mVibrationIntervalSeekBar.setProgress(AccelerometerEngine.sVibrationInterval - sViberationMinInterval);
		mVibrationIntervalSeekBar.setOnSeekBarChangeListener(this);
		mVibrationIntervalSeekBar.setKeyProgressIncrement(100);
		mShakeAnimation = new ShakeAnimation(getCustomView());
		
		mFrequencySeekBar.setMax(20);
		mFrequencySeekBar.setProgress(10);
		
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		getWindow().addFlags(params.flags);
		AccelerometerEngine.getInstance().start();
	}
	
	@Override
	public void updateView() {
		mAmplitudeThresholdTextView.setText(String.valueOf(AccelerometerEngine.sMaxShakeAmplitudeThreshold));
		mVibrationDurationTextView.setText(String.valueOf(AccelerometerEngine.sVibrationDuration));
		mVibrationIntervalTextView.setText(String.valueOf(AccelerometerEngine.sVibrationInterval));
	}
	
	@Override
	public void refreshView() {
		updateView();
	}
	
	@Override
	public void callback(int opCode, int arg1, int arg2, String str,
			Object object) {
		switch (opCode) {
		case OperationCode.OP_CODE_SENSOR_STATE_CHANGED:
			updateTextView(TEXT_VIEW_TOP, str, false);
			break;
		case OperationCode.OP_CODE_ACCURACY_CHANGED_CHANGED:
			updateTextView(TEXT_VIEW_BOTTOM, str, true);
			break;
		case OperationCode.OP_CODE_SHAKE:
			updateTextView(TEXT_VIEW_LEFT, str, false);
			updateTextView(TEXT_VIEW_BOTTOM, null == object ? "" : object.toString(), false);
			break;
		case OperationCode.OP_CODE_SHAKE_INIT:
			updateTextView(TEXT_VIEW_RIGHT, str, false);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected String getLeftButtonText() {
		return "start";
	}
	
	@Override
	protected void doLeftButtonClick() {
		AccelerometerEngine.getInstance().start();
	}
	
	@Override
	protected String getRightButtonText() {
		return "stop";
	}
	
	@Override
	protected void doRightButtonClick() {
		AccelerometerEngine.getInstance().stop();
	}
	
	@Override
	protected String getBottomButtonText() {
		return "shake";
	}
	
	@Override
	protected void doBotttomButtonClick() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mShakeAnimation);
	}
	
	@Override
	protected int getCustomViewAreaLayoutResource() {
		return R.layout.common_custom_view_layout;
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AccelerometerEngine.getInstance().stop();
	}
	
	@Override
	protected int getOperationAreaLayoutResource() {
		return R.layout.accelerometer_operation_layout;
	}

	@Override
	protected String getTopButtonText() {
		return "Flicker";
	}
	
	@Override
	protected void doTopButtonClick() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mFlickerAnimation);
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.amplitude_threshold_seek_bar:
			AccelerometerEngine.sMaxShakeAmplitudeThreshold = progress + AccelerometerEngine.sMinShakeAmplitudeThreshold;
			break;
		case R.id.vibration_interval_seek_bar:
			AccelerometerEngine.sVibrationInterval = progress + sViberationMinInterval;
			break;
		case R.id.vibration_duration_seek_bar:
			AccelerometerEngine.sVibrationDuration = progress + sViberationMinDuration;
			break;
		default:
			break;
		}
		refreshView();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
}

