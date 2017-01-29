package com.example.androiddemo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androiddemo.R;

public class ListItemView extends LinearLayout {

	private TextView mMainTextView = null;
	
	public ListItemView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.main_list_textview, this);
		bindView();
	}

	public void setMainText(String str) {
		mMainTextView.setText(str);
	}


	private void bindView () {
		mMainTextView = (TextView) findViewById(R.id.main_text);
	}
}
