package com.example.androiddemo.activity;

import com.example.androiddemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class HelloJniActivity extends Activity {

	static {
		System.loadLibrary("HelloJni");
	}
	
	public native String  stringFromJni();
	public native String  unimplementedStringFromJni();
	public native int getIntAtIndex(int[] intArray, int i);
	public native int add(int a, int b);
	
	private TextView mTitleTV = null;
	
	private void bindView() {
		mTitleTV = (TextView)findViewById(R.id.title);
	}
	
	private void initView() {
		String str = stringFromJni() + " 3 + 4 = " + add(3, 4) + "," + getIntAtIndex(new int[] {11, 22, 33, 44}, 2);
		mTitleTV.setText(str);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_jni_layout);
		bindView();
		initView();
	}
}
