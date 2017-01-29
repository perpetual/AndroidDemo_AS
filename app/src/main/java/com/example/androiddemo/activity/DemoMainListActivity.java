package com.example.androiddemo.activity;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

public class DemoMainListActivity extends SuperListActivity<String> {	
	private String[] mTitleArray = null;

	private static final int MSG_CODE_NAVIGATE_TO = 0x100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtil.d(TAG, AndroidDemoUtil.deviceInfo2String());
		super.onCreate(savedInstanceState);
	}
	
	private void navigateTo(boolean isScrollTo) {
		Class<?> cls = ServiceActivity.class;
		if (isScrollTo) {
			int scrollIndex = getDataSource().indexOf(cls.getSimpleName());
			mListAdapter.highLightBackground(scrollIndex);
			setSelection(scrollIndex);
		} else {
			Intent intent = new Intent(this, cls);
			startActivity(intent);
			finish();
		}
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
