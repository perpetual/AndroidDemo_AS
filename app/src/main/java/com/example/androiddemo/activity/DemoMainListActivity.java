package com.example.androiddemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.example.androiddemo.R;
import com.example.androiddemo.SynchronizeDemo;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DemoMainListActivity extends SuperListActivity<String> {	
	private String[] mTitleArray = null;

	private static final int MSG_CODE_NAVIGATE_TO = 0x100;

	private static final Class<?> INIT_ACTIVITY = ScrollDrawTestActivity.class;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtil.d(TAG, AndroidDemoUtil.deviceInfo2String());
		super.onCreate(savedInstanceState);
	}
	
	private void navigateTo(boolean isScrollTo) {
		if (isScrollTo) {
			int scrollIndex = getDataSource().indexOf(INIT_ACTIVITY.getSimpleName());
			mListAdapter.highLightBackground(scrollIndex);
			setSelection(scrollIndex);
		} else {
			Intent intent = new Intent(this, INIT_ACTIVITY);
			startActivity(intent);
			finish();
		}
	}

	private class DataItem implements Comparable {
		public int mData;

		public DataItem(int data) {
			mData = data;
		}

		@Override
		public String toString() {
			return String.valueOf(mData);
		}

		@Override
		public int compareTo(@NonNull Object o) {
			return mData < ((DataItem)o).mData ? -1 : 1;
		}

	}

	private void testEntry() {
		testEntry2();
	}

	private void testEntry1() {
		List<DataItem> list = new ArrayList<>();
		Integer[] array =
				{0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 2, 1, 0, 0, 0, 2, 30, 0, 3};
		for (int i = 0; i < array.length; ++i) {
			list.add(new DataItem(array[i]));
		}
		Collections.sort(list);
		Log.d(TAG, "testEntry" + list + Build.VERSION.SDK + Build.VERSION.SDK_INT);
	}

	private void testEntry2() {
		String lock1 = "lock1";
		String lock2 = "lock2";
		String lock3 = "lock3";
		new Thread(new SynchronizeDemo(lock1), "Thread1").start();
		new Thread(new SynchronizeDemo(lock1), "Thread2").start();
		new Thread(new SynchronizeDemo(lock3), "Thread3").start();
//		new Thread(new SynchronizeDemo(null), "Thread4").start();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Class<?> cls = null;
		try {
			String packageName = getPackageName();
			String classString = packageName + ".activity." + mTitleArray[position];
			cls = Class.forName(classString);
			Intent intent = new Intent(DemoMainListActivity.this, cls);
			startActivity(intent);
		} catch (Exception e) {
			AndroidDemoUtil.showLongToast(e.toString());
		}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
		menu.add("");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return false;
	}


	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		mTitleArray = getResources().getStringArray(R.array.demo_list);
		testEntry();
	}

	@Override
	public void initView() {
		super.initView();
	}

	@Override
	public void refreshView() {
		super.refreshView();
		mHandler.sendEmptyMessageDelayed(MSG_CODE_NAVIGATE_TO, 500);
	}
	
	@Override
	protected List<String> getDataSource() {
		Arrays.sort(mTitleArray);
		return Arrays.asList(mTitleArray);
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		boolean handled = false;
		switch (msg.what) {
		case MSG_CODE_NAVIGATE_TO:
			navigateTo(true);
			break;

		default:
			break;
		}
		return handled || super.handleMessage(msg);
	}
}
