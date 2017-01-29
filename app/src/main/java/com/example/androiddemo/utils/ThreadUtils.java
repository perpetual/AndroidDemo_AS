package com.example.androiddemo.utils;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

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
 * Gary		2015-4-26		Create		
 * </pre>
 */
public class ThreadUtils {

	public static Handler sHandler = new Handler(Looper.getMainLooper());

	public static void runOnMainThread(Runnable con) {
		if(isMainThread()){
			con.run();
		}else{
			sHandler.post(con);
		}
	}

	public static void runOnMainThread(Runnable con, long delayed) {
		sHandler.removeCallbacks(con);
		sHandler.postDelayed(con, delayed);
	}

	public static boolean isMainThread() {
		return Looper.getMainLooper().getThread() == Thread.currentThread();
	}

	public static void cancleAction(Runnable con) {
		sHandler.removeCallbacks(con);
	}
	
	public static void runOnBackground(Runnable con){
		AsyncTask.execute(con);
	}



}
