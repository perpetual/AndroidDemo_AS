package com.example.androiddemo.tools;


import java.util.Arrays;
import java.util.HashSet;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.androiddemo.utils.SystemServiceUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description�������ǣ������ж���ת�Ƕ�
 * 
 * History��
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-5-19		Create		
 * </pre>
 */
public class GyroscopeSensor implements SensorEventListener{

	private static GyroscopeSensor sInstance = null;
	private Sensor mSensor = null;
	private HashSet<GyroscopeSensor.IDataReceiver> mDataReceivers = null;
	
	private static final float ALPHA = 0.75f;
	private float[] mHistoryGyroscope = new float[]{0f, 0f, 0f};
	private float[] mDeltaValue = new float[]{0f, 0f, 0f};

	public static interface IDataReceiver {
		public void onAngularChanged(float x, float y, float z);
	}
	
	public static synchronized GyroscopeSensor getInstance() {
		if (null == sInstance) {
			sInstance = new GyroscopeSensor();
		}
		return sInstance;
	}
	
	public void start(IDataReceiver dataReceiver) {
		if (null != dataReceiver) {
			mDataReceivers.add(dataReceiver);
		}
		Arrays.fill(mDeltaValue, 0f);
		Arrays.fill(mHistoryGyroscope, 0f);
		SystemServiceUtil.getSensorManager().registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_UI); // ���60~70ms�ɼ�һ��
	}
	
	public void stop(IDataReceiver dataReceiver) {
		if (null != dataReceiver) {
			mDataReceivers.remove(dataReceiver);
		}
		SystemServiceUtil.getSensorManager().unregisterListener(this, mSensor);
	}
	
	private GyroscopeSensor() {
		mSensor = SystemServiceUtil.getSensorManager().getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		mDataReceivers = new HashSet<GyroscopeSensor.IDataReceiver>();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		for (int i = 0; i < mDeltaValue.length; ++i) {
			mDeltaValue[i] = ALPHA
					* (mDeltaValue[i] + event.values[i] - mHistoryGyroscope[i]);
		}
		System.arraycopy(event.values, 0, mHistoryGyroscope, 0, 3);
		for (int i = 0; i < mDeltaValue.length; ++i) {
			mDeltaValue[i] = LinearAccelerometerSensor.correctValue(mHistoryGyroscope[i], mDeltaValue[i]);
		}
		for (IDataReceiver dr : mDataReceivers) {
			dr.onAngularChanged(mDeltaValue[0], mDeltaValue[1], mDeltaValue[2]);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {		
	}
}

