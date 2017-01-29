package com.example.androiddemo.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UIProxyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (null == intent) {
			finish();
			return;
		}
		
		intent.setClass(this, DemoMainListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		finish();
	}
}
