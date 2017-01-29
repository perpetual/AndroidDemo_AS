package com.example.androiddemo.activity;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;

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
 * Gary		2015-4-7		Create		
 * </pre>
 */
public class AsyncTaskActivity extends DemoSuperActivity {
	private static final String TAG = AsyncTaskDemo.class.getSimpleName();

	private AsyncTaskDemo mAsyncTaskDemo = null;

	class AsyncTaskDemo extends AsyncTask<Integer, String, Long> {

		@Override
		protected Long doInBackground(Integer... params) {
			LogUtil.d(TAG, "doInBackground:", Thread.currentThread().getId(),
					"params", Arrays.asList(params));
			String[] strings = new String[] { "11", "22", "33", "44" };
			for (Integer i : params) {
				publishProgress(strings[i]);
			}
			return 333L;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			LogUtil.d(TAG, "onProgressUpdate:", Thread.currentThread().getId(),
					"values", Arrays.asList(values));
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			LogUtil.d(TAG, "onPreExecute:" + Thread.currentThread().getId());
		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			LogUtil.d(TAG, "onPostExecute:" + Thread.currentThread().getId(),
					"result", result);
		}
	}

	@Override
	public void initData(Context context, AttributeSet attrs) {
		mAsyncTaskDemo = new AsyncTaskDemo();
	}

	@Override
	protected String getTopButtonText() {
		return "start asynctask";
	}

	@Override
	protected void doTopButtonClick() {
		mAsyncTaskDemo.execute(1, 2, 3, 4);
	}
}