package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.string;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2014-5-19		Create		
 * </pre>
 */
public class FragmentActivity extends Activity {
	private static final String TAG = "FragmentActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI();
	}
	
	/**
	 * 私有工具函数
	 */
	private void initUI() {
		initActionBar();
	}
	
	private void initActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.test);
		getActionBar().setSubtitle(R.string.test2);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);		
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
	}
	
	/**
	 * 重载函数
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return true;
	}
}

