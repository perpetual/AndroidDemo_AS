package com.example.androiddemo.activity;

import android.content.Intent;

public class FirstActivity extends TestActivity {
	
	@Override
	protected void doTopButtonClick() {
		boolean b = getIntent().getBooleanExtra("START_SELF", false);
		Intent intent = null;
		if (b) {
			intent = new Intent(this, FirstActivity.class);
			intent.putExtra("START_SELF", true);
		} else {
			intent = new Intent(this, SecondActivity.class);
//			intent = new Intent();
//			intent.setAction("com.example.androiddemo2.xxxxxx");
//			intent.setComponent(new ComponentName("com.example.androiddemo2", "MainActivity"));
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		startActivity(intent);
//		Dialog dlg = new Dialog(this);
//		dlg.show();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}
}
