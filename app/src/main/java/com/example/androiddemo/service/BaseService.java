package com.example.androiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

public class BaseService extends Service {

	private final String TAG = AndroidDemoUtil.getClassName(BaseService.class);
	
	@Override
	public void onCreate() {
		super.onCreate();
		LogUtil.d(TAG, "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtil.d(TAG, "onStartCommand", "intent", intent, "flags", flags, "startId", startId);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.d(TAG, "onDestroy");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		LogUtil.d(TAG, "onConfigurationChanged", "newConfig", newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		LogUtil.d(TAG, "onLowMemory");
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		LogUtil.d(TAG, "onTrimMemory", "level", level);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		LogUtil.d(TAG, "onUnbind", "intent", intent);
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		LogUtil.d(TAG, "onRebind", "intent", intent);
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		super.onTaskRemoved(rootIntent);
		LogUtil.d(TAG, "onTaskRemoved", "rootIntent", rootIntent);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		LogUtil.d(TAG, "onBind", "intent", intent);
		return null;
	}

	@Override
	public boolean stopService(Intent name) {
		LogUtil.d(TAG, "stopService", "name", name);
		return super.stopService(name);
	}
}
