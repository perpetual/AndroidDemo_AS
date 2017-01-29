package com.example.androiddemo.utils;

import android.util.Log;

public class LogUtil {

	public static void v(String tag, Object... datas) {
		log(Log.VERBOSE, tag, datas);
	}

	public static void d(String tag, Object... datas) {
		log(Log.DEBUG, tag, datas);
	}

	public static void i(String tag, Object... datas) {
		log(Log.INFO, tag, datas);
	}

	public static void w(String tag, Object... datas) {
		log(Log.WARN, tag, datas);
	}

	public static void e(String tag, Object... datas) {
		log(Log.ERROR, tag, datas);
	}

	
	private static void log(final int type, String tag, Object... datas) {
		if (tag == null || datas == null)
			return;

		String data = AndroidDemoUtil.converArrayToString(datas);

		switch (type) {
		case Log.VERBOSE:
			Log.v(tag, data);
			break;
		case Log.DEBUG:
			Log.d(tag, data);
			break;
		case Log.INFO:
			Log.i(tag, data);
			break;
		case Log.WARN:
			Log.w(tag, data);
			break;
		case Log.ERROR:
			Log.e(tag, data);
			break;
		case Log.ASSERT:
			Log.e(tag, data);
			break;
		default:
			break;
		}
	}
}
