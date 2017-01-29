package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class NavBarActivity extends Activity {

	/**
	 * UI�ؼ�
	 */
	ListView mListView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navbar_layout);
	}
	
	/**
	 * ˽�й��ߺ���
	 */
	private void bindView() {
		mListView = (ListView)findViewById(R.id.list_view);
	}
}
