package com.example.androiddemo.tools;

import java.util.HashSet;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-3-26		Create		
 * </pre>
 */
public class CommonCallbacks {

	public static interface ICallback {
		public void callback(final int opCode, int arg1, int arg2, String str, Object object);
	}
	
	private HashSet<ICallback> mCallbackSet = null;
	
	public CommonCallbacks() {
		mCallbackSet = new HashSet<ICallback>();
	}
	
	public void add(ICallback callback) {
		if (null == callback) {
			return;
		}
		mCallbackSet.add(callback);
	}
	
	public void remove(ICallback callback) {
		if (null == callback) {
			return;
		}
		mCallbackSet.remove(callback);
	}
	
	public void removeAll() {
		mCallbackSet.clear();
	}
	
	public void doCallbacks(final int opCode, int arg1, int arg2, String str, Object object) {
		for (ICallback callback : mCallbackSet) {
			callback.callback(opCode, arg1, arg2, str, object);
		}
	}
}

