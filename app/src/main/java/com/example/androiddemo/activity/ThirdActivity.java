package com.example.androiddemo.activity;

import android.content.Intent;

public class ThirdActivity extends TestActivity {

	@Override
	protected void doTopButtonClick() {
		Intent intent = new Intent(this, FirstActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		intent.putExtra("START_SELF", true);
		startActivity(intent);
	}
}
