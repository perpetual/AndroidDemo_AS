package com.example.androiddemo.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;

import com.example.androiddemo.model.IUIInitialization;
import com.example.androiddemo.tools.SuperListAdapter;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description锛�
 * 
 * History锛�
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-24		Create		
 * </pre>
 */
public abstract class SuperListActivity<T> extends ListActivity implements IUIInitialization, Handler.Callback {
	protected static String TAG = DemoBaseActivity.class.getSimpleName();

	protected SuperListAdapter<T> mListAdapter = null;
	
	protected abstract List<T> getDataSource();
	
	protected Handler mHandler = null;
	
	private void updateData() {
		mListAdapter.updateData(getDataSource());
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TAG = this.getClass().getSimpleName();
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		initData(this, null);
		initLayout();
		bindView();
		initView();
	}

	@Override
	public void initData(Context context, AttributeSet attrs) {
		mListAdapter = new SuperListAdapter<T>(context);
		mHandler = new Handler(Looper.getMainLooper(), this);
	}

	@Override
	public void initLayout() {		
	}

	@Override
	public void bindView() {		
	}

	@Override
	public void initView() {	
		setListAdapter(mListAdapter);
		LogUtil.d(TAG, getListView().isScrollContainer(), getListAdapter(), mListAdapter);
	}

	@Override
	public void updateView() {
		updateData();	
	}

	@Override
	public void refreshView() {
		updateView();
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		Log.d(TAG, "onPostCreate1");
	}
	
	@Override
	public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onPostCreate(savedInstanceState, persistentState);
		Log.d(TAG, "onPostCreate2");
	}
	
	@Override
	protected void onPostResume() {
		super.onPostResume();
		Log.d(TAG, "onPostResume");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, this + "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, this + "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshView();
		Log.d(TAG, this + "onResume");
		AndroidDemoUtil.showLongToast("taskID:" + getTaskId());
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, this + "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, this + "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, this + "onDestroy");
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
}