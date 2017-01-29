package com.example.androiddemo.tools;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;
import com.example.androiddemo.utils.MathUtil;
import com.example.androiddemo.utils.SystemServiceUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description�����Լ��ټƣ��Ѿ���������Ӱ��
 * 
 * History��
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-5-5		Create		
 * </pre>
 */
public class LinearAccelerometerSensor implements SensorEventListener{

	private static String TAG = LinearAccelerometerSensor.class.getSimpleName();
	
	private static LinearAccelerometerSensor sInstance = null;	

	private static final float ALPHA = 0.75f;
	private static final int CORRECTION_COUNT = 10; // ��������
	private static final float CORRECTION_LIMIT = 0.05f; // �������ٶȿɽ��̶ܳ�
	private static float sAccelerometerSensorMaxRange = getInstance().getMaximumRange();

	private Sensor mAccelerometerSensor = null;
	private Float[] mHistoryLinearAcceleration = new Float[]{0f, 0f, 0f};
	private Float[] mGravity = new Float[] { Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY };
	private List<Float> mHistoryGravityArray = new ArrayList<Float>(CORRECTION_COUNT);
	private Float[] mCorrectedValue = new Float[]{0f, 0f, 0f};
	private Float[] mLinearAcceleration = new Float[] { 0f, 0f, 0f };
	private boolean mHasGravityCorrected = false; // �Ƿ�����
	private float mCorrectedGravity = -1f;
	private HashSet<IDataReceiver> mDataReceivers = new HashSet<IDataReceiver>();
	
	public static interface IDataReceiver {
		public void onAccelerationChanged(float x, float y, float z);
	}
	
	public static synchronized LinearAccelerometerSensor getInstance() {
		if (null == sInstance) {
			sInstance = new LinearAccelerometerSensor();
		}
		return sInstance;
	}
	
	public void start(IDataReceiver dataReciever) {
		LogUtil.d(TAG, "start", dataReciever);
		resetData();
		resume(dataReciever);
		mDataReceivers.add(dataReciever);
	}
	
	public void stop(IDataReceiver dataReciever) {
		LogUtil.d(TAG, "stop", dataReciever);
		pause(dataReciever);
		mDataReceivers.remove(dataReciever);
		resetData();
	}
	
	public void resume(IDataReceiver dataReciever) {
		boolean b = SystemServiceUtil.getSensorManager().registerListener(this, mAccelerometerSensor,
				SensorManager.SENSOR_DELAY_UI); // ���60~70ms�ɼ�һ��
		LogUtil.d("xxx", b);
	}
	
	public void pause(IDataReceiver dataReciever) {
		SystemServiceUtil.getSensorManager().unregisterListener(this, mAccelerometerSensor);
	}
	
	public void addDataReceiver(IDataReceiver dataReceiver) {
		mDataReceivers.add(dataReceiver);
	}
	
	/**
	 * ��ȡ����������ֵ
	 * @return
	 *
	 * @author garyzhao in 2015-5-6
	 */
	public float getCorrectedGravity() {
		return mCorrectedGravity;
	}
	
	/**
	 * �������Ƿ��Ѿ�У׼
	 * @return
	 *
	 * @author garyzhao in 2015-5-20
	 */
	public boolean isCorrected() {
		return mHasGravityCorrected;
	}
	/*
	 * ˽�к���
	 */
	private LinearAccelerometerSensor() {
		mAccelerometerSensor = SystemServiceUtil.getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	private void resetData() {
		mHistoryGravityArray.clear();
		Arrays.fill(mLinearAcceleration, Float.valueOf(0));
		Arrays.fill(mGravity, Float.valueOf(0));
		Arrays.fill(mCorrectedValue, Float.valueOf(0f));
		Arrays.fill(mHistoryLinearAcceleration, Float.valueOf(0));
		mHasGravityCorrected = false;
		mCorrectedGravity = -1f;
	}
	
	/*
	 * ��������
	 */
	private boolean updateHistoryGravity(Float[] gravityArray) {
		if (null == gravityArray) {
			return false;
		}
		if (mHasGravityCorrected) {
			return true;
		}
		float newInstantaneousGravity = (float) MathUtil.getScaleFloatValue(false, gravityArray[0],
				gravityArray[1], gravityArray[2]);
		float gravitySum = 0;
		Float lastAverageGravity = 0f;
		Float currentAverageGravity = 0f;
		for (float gravity : mHistoryGravityArray) {
			gravitySum += gravity;
		}
		if (mHistoryGravityArray.size() > 1) {
			lastAverageGravity = gravitySum / mHistoryGravityArray.size();
		}
		if (mHistoryGravityArray.size() >= CORRECTION_COUNT) {
			mHistoryGravityArray.remove(0);
		}
		mHistoryGravityArray.add(newInstantaneousGravity);
		gravitySum = 0;
		for (float gravity : mHistoryGravityArray) {
			gravitySum += gravity;
		}
		currentAverageGravity = gravitySum / mHistoryGravityArray.size();
		return mHasGravityCorrected = Math.abs(currentAverageGravity - lastAverageGravity) < CORRECTION_LIMIT;
	}
	
	/**
	 * �����Լ��ٶȵ�ֵ��������
	 * @param originalValue
	 * @param correctedValue
	 * @return
	 *
	 * @author garyzhao in 2015-5-5
	 */
	public static float correctValue(float originalValue, float correctedValue) {
		if (correctedValue >= -0.0001 && correctedValue <= 0.0001) {
			return 0f;
		}
		if (correctedValue / originalValue >= 0.5f) {
			return correctedValue;
		}
		return 0f;
	}
	

	public float getMaximumRange() {
		return mAccelerometerSensor.getMaximumRange();
	}
	
	public float getMaxDelay() {
		float maxDelay = 0f;
		try {
			maxDelay = mAccelerometerSensor.getMaxDelay();
		} catch (Throwable e) {
		}
		return maxDelay;
	}
	
	public float getMinDelay() {
		float minDelay = 0f;
		try {
			minDelay = mAccelerometerSensor.getMinDelay();
		} catch (Throwable e) {
		}
		return minDelay;
	}
	
	public int getType() {
		return mAccelerometerSensor.getType();
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		LogUtil.d(TAG, "onSensorChanged", event);
		sAccelerometerSensorMaxRange = Math.max(sAccelerometerSensorMaxRange, event.values[0]);
		sAccelerometerSensorMaxRange = Math.max(sAccelerometerSensorMaxRange, event.values[1]);
		sAccelerometerSensorMaxRange = Math.max(sAccelerometerSensorMaxRange, event.values[2]);
		
		if (!mHasGravityCorrected) {
			mGravity[0] = ALPHA * mGravity[0] + (1 - ALPHA) * event.values[0];
			mGravity[1] = ALPHA * mGravity[1] + (1 - ALPHA) * event.values[1];
			mGravity[2] = ALPHA * mGravity[2] + (1 - ALPHA) * event.values[2];
			updateHistoryGravity(mGravity);
			return;
		} else if (mCorrectedGravity < 0f){
			mCorrectedGravity = MathUtil.getScaleFloatValue(false, mGravity[0], mGravity[1], mGravity[2]);
			LogUtil.d(TAG, "mCorrectedGravity", mCorrectedGravity, mHistoryGravityArray.size());
		}
		
		mLinearAcceleration[0] = event.values[0] - mGravity[0];
		mLinearAcceleration[1] = event.values[1] - mGravity[1];
		mLinearAcceleration[2] = event.values[2] - mGravity[2];		

		for (int i = 0; i < mCorrectedValue.length; ++i) {
			mCorrectedValue[i] = ALPHA * (mCorrectedValue[i] + mLinearAcceleration[i] - mHistoryLinearAcceleration[i]);
		}
		System.arraycopy(mLinearAcceleration, 0, mHistoryLinearAcceleration, 0,
				mLinearAcceleration.length);
		for (int i = 0; i < mCorrectedValue.length; ++i) {
			mCorrectedValue[i] = correctValue(mHistoryLinearAcceleration[i], mCorrectedValue[i]);
		}
		for (IDataReceiver dataReceiver : mDataReceivers) {
			dataReceiver.onAccelerationChanged(mCorrectedValue[0], mCorrectedValue[1], mCorrectedValue[2]);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		LogUtil.d(TAG, "onAccuracyChanged", sensor, accuracy);
	}
	
	@Override
	public String toString() {
		return AndroidDemoUtil.argumentsToString(sAccelerometerSensorMaxRange);
	}
}

