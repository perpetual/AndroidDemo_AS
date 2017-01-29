package com.example.androiddemo.service;

import java.util.Currency;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.example.androiddemo.R;
import com.example.androiddemo.activity.FirstActivity;
import com.example.androiddemo.activity.ServiceActivity;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;
import com.example.androiddemo.utils.SystemServiceUtil;

/**
 * 
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 * 
 * Description锛�
 * 
 * History锛�
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-7		Create
 * </pre>
 */
public class BackgroundDemoService extends BaseService {
	private static final String TAG = BackgroundDemoService.class.getSimpleName();
	
	private class ServiceRunnable implements Runnable {

		private int mCounter = -1;
		private int mStartId = 0;

		public ServiceRunnable(int counter, int startId) {
			mCounter = counter;
			mStartId = startId;
		}

		@Override
		public void run() {
			while (true) {
				try {
					LogUtil.v(TAG, "Current process id", android.os.Process.myPid(), "currentThread",
							Thread.currentThread().getId(), "Sleeping for 2 seconds. counter = ",
							mCounter, "StartId", mStartId, "TopActivityName", AndroidDemoUtil.getTopActivityName(), Thread.currentThread().getState());
//					stopSelfResult(mStartId);
					Thread.sleep(1500);
				} catch (Exception e) {
				}
			}
		}
	}

	private NotificationManager mNotificationMananger = null;
	public static final String EXTRAS_KEY = "counter";
	private ThreadGroup mMyThreads = new ThreadGroup("ServiceWorker");
	private Thread mMyThread = null;

	@Override
	public void onCreate() {
		super.onCreate();
		LogUtil.d(TAG, "onCreate", "Current process id", android.os.Process.myPid(), "currentThread", Thread.currentThread().getId());
		mNotificationMananger = SystemServiceUtil.getNotificationManager();
		AndroidDemoUtil.showDemoNotification(getBaseContext(), ServiceActivity.class);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		LogUtil.d(TAG, "onStartCommand", "intent", intent, "flags", flags, "startId", startId);
		int counter = intent.getExtras().getInt(EXTRAS_KEY);
		if (null == mMyThread) {
			mMyThread = new Thread(mMyThreads, new ServiceRunnable(counter, startId));
		}
		Thread.State state = mMyThread.getState();
		LogUtil.d(TAG, "onStartCommand", mMyThread.getId(), state, mMyThread.isAlive());
		if (Thread.State.NEW == state) {
			mMyThread.start();			
		}
		mMyThread.run();
		return START_STICKY;
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mMyThread.interrupt();
		mMyThreads.interrupt();
		LogUtil.d(TAG, "onDestroy", mMyThread.getState());
		mNotificationMananger.cancelAll();
	}
}