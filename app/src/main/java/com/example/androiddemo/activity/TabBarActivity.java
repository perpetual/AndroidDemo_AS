package com.example.androiddemo.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabBarActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("one").setIndicator("1").setContent(new Intent(this, Browser1.class)));
		tabHost.addTab(tabHost.newTabSpec("two").setIndicator("2").setContent(new Intent(this, Browser2.class)));
	}
}
