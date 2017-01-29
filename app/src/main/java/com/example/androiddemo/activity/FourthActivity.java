package com.example.androiddemo.activity;

import android.content.Intent;

public class FourthActivity extends TestActivity {

	@Override
	protected void doTopButtonClick() {
		Intent intent = new Intent(this, SecondActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}
}
