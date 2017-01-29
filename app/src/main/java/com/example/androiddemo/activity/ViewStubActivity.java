package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

public class ViewStubActivity extends Activity {

	ViewStub mViewStub = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_stub_layout);
		bindView();
		initView();
	}

	/**
	 * ˽�й��ߺ���
	 */
	private void bindView() {
		mViewStub = (ViewStub) findViewById(R.id.mystub);
		mViewStub.setVisibility(View.VISIBLE);
		View view = findViewById(R.id.myinflate);
	}

	private void initView() {
//		mViewStub.inflate();
		mViewStub.setVisibility(View.VISIBLE);
	}
}
