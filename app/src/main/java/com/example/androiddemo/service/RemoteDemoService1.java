package com.example.androiddemo.service;

import java.io.FileDescriptor;

import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

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
public class RemoteDemoService1 extends BaseService {

	private final String TAG = RemoteDemoService1.class.getSimpleName();
	private IBinder mRemoteDemoServiceBinder = null;
	
	public class RemoteDemoServiceBinder extends IRemoteDemoService1.Stub {

		@Override
		public double getQuote(String ticker) throws RemoteException {
			LogUtil.d(TAG, "getQuote", ticker, RemoteDemoService1.this);
			return 3.14;
		}

		@Override
		public void setRemoteBinder(IBinder binder) throws RemoteException {
			mRemoteDemoServiceBinder = binder;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		LogUtil.d(TAG, "RemoteDemoService1", "onBind", "intent", intent, AndroidDemoUtil.getCurrentProcessName());
		if (null == mRemoteDemoServiceBinder) {
			mRemoteDemoServiceBinder = new RemoteDemoServiceBinder() {
				
				@Override
				public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
					return false;
				}
				
				@Override
				public IInterface queryLocalInterface(String descriptor) {
					return null;
				}
				
				@Override
				public boolean pingBinder() {
					return false;
				}
				
				@Override
				public void linkToDeath(DeathRecipient recipient, int flags) {
					
				}
				
				@Override
				public boolean isBinderAlive() {
					return false;
				}
				
				@Override
				public String getInterfaceDescriptor() {
					return null;
				}
				
				@Override
				public void dumpAsync(FileDescriptor fd, String[] args) {
					
				}
				
				@Override
				public void dump(FileDescriptor fd, String[] args) {
					
				}
			};
		}
		return mRemoteDemoServiceBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		LogUtil.d(TAG, "RemoteDemoService1", "onUnbind", "intent", intent, AndroidDemoUtil.getCurrentProcessName());
		AndroidDemoUtil.showDemoNotification(getBaseContext(), null);
		boolean ret = super.onUnbind(intent);
		mRemoteDemoServiceBinder = null;
		return ret;
	}
}