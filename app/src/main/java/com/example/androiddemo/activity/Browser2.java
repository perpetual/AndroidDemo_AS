package com.example.androiddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Browser2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView webView = new WebView(this);
		setContentView(webView);
		webView.loadUrl("http://www.baidu.com");
	}
}
