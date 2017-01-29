package com.example.androiddemo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.androiddemo.R;

public class TouchEventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bindUI();
	}

	private void bindUI() {
		setContentView(R.layout.touch_event_layout);
	}
}
