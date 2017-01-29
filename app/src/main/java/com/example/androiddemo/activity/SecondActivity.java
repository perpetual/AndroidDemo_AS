package com.example.androiddemo.activity;

import android.content.Intent;

public class SecondActivity extends TestActivity {

	@Override
	protected void doTopButtonClick() {
		Intent intent = new Intent(this, ThirdActivity.class);
		startActivity(intent);
	}
}
