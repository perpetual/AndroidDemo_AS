package com.example.androiddemo.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.example.androiddemo.activity.ServiceActivity;
import com.example.androiddemo.service.IRemoteDemoService1;
import com.example.androiddemo.service.IRemoteDemoService2;
import com.example.androiddemo.service.RemoteDemoService1;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-8		Create		
 * </pre>
 */
public class RemoteDemoManager {
	private static final String TAG = RemoteDemoManager.class.getSimpleName();
	private static RemoteDemoManager sInstance = null;
	private Handler mMainHandler = null;
	private boolean mIsBind = false;
	private IRemoteDemoService1 mRemoteDemoService1 = null;
	private IRemoteDemoService2 mRemoteDemoService2 = null;
	private ServiceConnection mRemoteDemoServiceConnection1 = null;
	private ServiceConnection mRemoteDemoServiceConnection2 = null;
	
	public static RemoteDemoManager getInstance() {
		if (null == sInstance) {
			sInstance = new RemoteDemoManager();
		}
		return sInstance;
	}

	public void bindService() {
		if (!isServiceBind()) {
			Intent intent = new Intent(AndroidDemoUtil.APPLICATION_CONTEXT, RemoteDemoService1.class);
			AndroidDemoUtil.APPLICATION_CONTEXT.bindService(intent, mRemoteDemoServiceConnection1,
					Context.BIND_AUTO_CREATE);
			LogUtil.d(TAG, "bindService", AndroidDemoUtil.getCurrentProcessName());
			mIsBind = true;
		}
	}
	
	public void unbindService() {
		if (isServiceBind()) {
			AndroidDemoUtil.APPLICATION_CONTEXT.unbindService(mRemoteDemoServiceConnection1);
			mIsBind = false;
		}
	}

	public boolean isServiceConnected() {
		return null != mRemoteDemoService1;
	}
	
	public boolean isServiceBind() {
		return mIsBind;
	}
	
	public IRemoteDemoService1 getRemoteDemoService1() {
		return mRemoteDemoService1;
	}
	
	/**
	 * 私有工具函数
	 */
	private RemoteDemoManager() {
		mMainHandler = new Handler(Looper.getMainLooper());
		
		mRemoteDemoServiceConnection1 = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				LogUtil.d(TAG, "onServiceDisconnected");
				mRemoteDemoService1 = null;
				AndroidDemoUtil.showDemoNotification(null, null);
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(TAG, "onServiceConnected");
				mRemoteDemoService1 = IRemoteDemoService1.Stub.asInterface(service);
				LogUtil.d(TAG, "onServiceConnected", "service", service, "mRemoteDemoService1", mRemoteDemoService1);
				AndroidDemoUtil.showDemoNotification(null, ServiceActivity.class);
			}
		};
		
		mRemoteDemoServiceConnection2 = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {				
			}
		};
	}
}

