package com.example.androiddemo.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;

import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.receiver.BaseBroadcastReceiver;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;
import com.example.androiddemo.utils.SystemServiceUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：管理加速度计
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2015-4-25		Create		
 * </pre>
 */
public class AccelerometerEngine extends CommonCallbacks implements LinearAccelerometerSensor.IDataReceiver, BaseBroadcastReceiver.IBaseBroadcastReceiver{
	private static final String TAG = AccelerometerEngine.class.getSimpleName();
	
	private static final int sCorrectionCount = 10;	//修正次数

	public static float sMaxShakeAmplitudeThreshold = 18.5f;	//摇动幅度阈值
	public static float sMaxShakeAmplitudeProportionThreshold = 0.39f;	//摇动幅度占最大值的比例
	public static float sMinShakeAmplitudeThreshold = 3f;	//采集幅度阈值
	public static float sMinShakeAmplitudeProportionThreshold = 0.1f;	//采集幅度占最大值的比例
	
	public static int sVibrationInterval = 500;	//振动间隔
	public static int sVibrationDuration = 500;	//振动时长
	
	private static AccelerometerEngine sAccelerometerManager = null;
	private LinearAccelerometerSensor mAccelerometerSensor = null;
	private Float[] mGravity = new Float[]{0f, 0f, 0f};
	private List<Float> mHistoryGravityArray = new ArrayList<Float>(sCorrectionCount);
	private Float[] mLinearAcceleration = new Float[]{0f, 0f, 0f};
	private boolean mHasCorrected = false;	//是否修正
	private Runnable mRunnable = null;
	private Runnable mSleepRunnable = null;
	private boolean mIsSleep = false;
	private int mStepCount = 0;
	private boolean mIsStartCount = false;
	private List<Float> mAccelerationCollectionList = new ArrayList<Float>();
	
	private float mMaxRange = 0f;
	
	private WakeLock mWakeLock = null;
	private BaseBroadcastReceiver mBaseBroadcastReceiver = null;
	
	public static class ValuePair {
		public ValuePair(float minValue, float maxValue) {
			mMinValue = minValue;
			mMaxValue = minValue;
		}
		public float mMaxValue = 0f;
		public float mMinValue = 0f;
	}
	
	public static synchronized AccelerometerEngine getInstance() {
		if (null == sAccelerometerManager) {
			sAccelerometerManager = new AccelerometerEngine();
		}
		return sAccelerometerManager;
	}
	
	public void start() {
		LogUtil.d(TAG, "start");
		reset();
		updateThresHold();
		doCallbacks(OperationCode.OP_CODE_SHAKE_INIT, 0, 0, AndroidDemoUtil.argumentsToString(
				mAccelerometerSensor.getMaxDelay(), mAccelerometerSensor.getMinDelay(),
				mAccelerometerSensor.getMaximumRange()), null);
		mBaseBroadcastReceiver.register(AndroidDemoUtil.APPLICATION_CONTEXT, AndroidDemoUtil.createIntentFilter(Intent.ACTION_SCREEN_OFF, Intent.ACTION_SCREEN_ON), this);
//		if (null != mWakeLock) {
//			mWakeLock.release();
//			mWakeLock = null;
//		} else {
//			mWakeLock = SystemServiceUtil.getPowerManager().newWakeLock(
//					PowerManager.PARTIAL_WAKE_LOCK, getClass().getSimpleName());
//			mWakeLock.acquire();
//		}
		LinearAccelerometerSensor.getInstance().start(this);
	}
	
	public void stop() {
		LogUtil.d(TAG, "stop");
		mBaseBroadcastReceiver.unregister(AndroidDemoUtil.APPLICATION_CONTEXT);
		LinearAccelerometerSensor.getInstance().stop(this);
		if (null != mWakeLock) {
			mWakeLock.release();
			mWakeLock = null;
		}
		reset();
	}
	
	public void resume() {
		LinearAccelerometerSensor.getInstance().resume(this);
	}
	
	public void pause() {
		LinearAccelerometerSensor.getInstance().pause(this);
	}
	
	public void updateThresHold() {
		sMaxShakeAmplitudeThreshold = mAccelerometerSensor.getMaximumRange() * sMaxShakeAmplitudeProportionThreshold;
		sMinShakeAmplitudeThreshold = mAccelerometerSensor.getMaximumRange() * sMinShakeAmplitudeProportionThreshold;
	}
	
	public float getMaxRange() {
		return sAccelerometerManager.getMaxRange();
	}
	
	private AccelerometerEngine() {
		mAccelerometerSensor = LinearAccelerometerSensor.getInstance();
		sMaxShakeAmplitudeThreshold = (int)(mAccelerometerSensor.getMaximumRange() / 2);
		mRunnable = new Runnable() {

			@Override
			public void run() {
//				int lastPos = Integer.MIN_VALUE;
//				int count = 0;
//				for (int i = 0; i < mAccelerationCollectionList.size(); ++i) {
//					float sample = mAccelerationCollectionList.get(i);
//					if (sample > sMaxShakeAmplitudeThreshold) {
//						if (lastPos + 1 != i && lastPos + 2 != i) {
//							++count;
//						}
//						lastPos = i;
//					}
//				}
//
				doCallbacks(OperationCode.OP_CODE_SHAKE, 0, 0,
						AndroidDemoUtil.argumentsToString(mStepCount, mMaxRange,
								sMaxShakeAmplitudeProportionThreshold,
								sMaxShakeAmplitudeThreshold,
								sMinShakeAmplitudeProportionThreshold,
								sMinShakeAmplitudeThreshold), mAccelerationCollectionList);
				if (mStepCount > 0) {
					SystemServiceUtil.getVibratorService().cancel();
					long[] vibratorArray = new long[mStepCount * 2];
					if (mStepCount < 3) {
						for (int i = 0; i < vibratorArray.length; ++i) {
							vibratorArray[i] = 0 == i % 2 ? sVibrationInterval : sVibrationDuration;
						}
					} else {
						vibratorArray = new long[] { sVibrationInterval, sVibrationDuration };
					}
					LogUtil.d(TAG, "mAccelerationCollectionList", mAccelerationCollectionList);
					SystemServiceUtil.getVibratorService().vibrate(vibratorArray, -1);
				}
				mAccelerationCollectionList.clear();
				mStepCount = 0;
				mIsStartCount = false;
				mMaxRange = 0f;
			}
		};
		
		mSleepRunnable = new Runnable() {
			
			@Override
			public void run() {
				mIsSleep = false;
			}
		};
		mBaseBroadcastReceiver = new BaseBroadcastReceiver();
	}

	private String getRawValue(SensorEvent se) {
		return String.format("Raw values:X-%8.4f, Y-%8.4f, Z-%8.4f" + AndroidDemoUtil.LINE_SEPARATOR, se.values[0], se.values[1], se.values[2]);
	}
	
	private String getGravity() {
		return String.format("Gravity:X-%8.4f, Y-%8.4f, Z-%8.4f" + AndroidDemoUtil.LINE_SEPARATOR, mGravity[0], mGravity[1], mGravity[2]);
	}
	
	private String getLinearAcceleration() {
		return String.format("Linear acceleration:X-%8.4f, Y-%8.4f, Z-%8.4f" + AndroidDemoUtil.LINE_SEPARATOR, mLinearAcceleration[0], mLinearAcceleration[1], mLinearAcceleration[2]);
	}
	
	private void reset() {
		mHistoryGravityArray.clear();
		mAccelerationCollectionList.clear();
		mHasCorrected = false;
		Arrays.fill(mLinearAcceleration, Float.valueOf(0));
		Arrays.fill(mGravity, Float.valueOf(0));
		mStepCount = 0;
		mIsStartCount = false;
		mIsSleep = false;
		mMaxRange = 0f;
	}

	@Override
	public void onAccelerationChanged(float x, float y, float z) {
		LogUtil.d(TAG, x, y, z);
	}

	@Override
	public void onReciveBroadcast(Context context, Intent intent) {
		if (TextUtils.equals(intent.getAction(), Intent.ACTION_SCREEN_ON)) {
			LogUtil.d(TAG, "ACTION_SCREEN_ON");
		} else if (TextUtils.equals(intent.getAction(), Intent.ACTION_SCREEN_OFF)) {
			LogUtil.d(TAG, "ACTION_SCREEN_OFF");
			pause();
			resume();
		}
	}	
}

