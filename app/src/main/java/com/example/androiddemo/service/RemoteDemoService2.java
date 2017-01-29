package com.example.androiddemo.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.androiddemo.R;
import com.example.androiddemo.activity.FirstActivity;

/**
 * 
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-7		Create		
 * </pre>
 */
public class RemoteDemoService2 extends BaseService {

	private NotificationManager mNotificationMananger = null;

	private void displayNotificationMessage(String message) {
		Intent intent = new Intent(this, FirstActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.addCategory(Intent.CATEGORY_LAUNCHER);
//		intent.setAction(Intent.ACTION_MAIN);
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		Notification notification = null;
		Notification.Builder notificationBuilder = new
				Notification.Builder(this);
		notificationBuilder.setWhen(System.currentTimeMillis());
		notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
		notificationBuilder.setContentText("Background  service is running");
		notificationBuilder.setContentTitle("Background Service");
		notificationBuilder.setContentText(message);
		notificationBuilder.setContentIntent(pendingIntent);
		notification = notificationBuilder.getNotification();
		notification.flags = Notification.FLAG_NO_CLEAR;
		mNotificationMananger.notify(0, notification);
	}

	private class RemoteService2Impl extends IRemoteDemoService2.Stub {

		@Override
		public String getQuote(String ticker, Person requester)
				throws RemoteException {
			return ticker + ":" + requester.mName;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mNotificationMananger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		displayNotificationMessage("RemoteService2 is running");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new RemoteService2Impl();
	}

	@Override
	public void onDestroy() {
		mNotificationMananger.cancelAll();
		super.onDestroy();
	}
}