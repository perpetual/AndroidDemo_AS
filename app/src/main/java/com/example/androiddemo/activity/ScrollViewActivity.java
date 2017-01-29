package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;
import com.example.androiddemo.utils.LogUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ScrollViewActivity extends Activity {
	private static final String TAG = "ScrollViewActivity";
	View mScrollView = null;
	ListView mListView = null;
	BaseAdapter mAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (null == convertView) {
				convertView = LayoutInflater.from(ScrollViewActivity.this).inflate(android.R.layout.test_list_item, null);

			}
			if (convertView instanceof TextView) {
				TextView tv = (TextView)convertView;
				tv.setText(String.valueOf(position));
			}
			return convertView;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
		
		@Override
		public int getCount() {
			return 100;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scroll_view_layout);
		bindUI();
	}

	/**
	 * 私有工具函数
	 */
	private void bindUI() {
		mScrollView = findViewById(R.id.scroll_view);
		mListView = (ListView)findViewById(R.id.list_view);
		mScrollView.setScrollContainer(true);
		mListView.setScrollContainer(true);
		LogUtil.d(TAG, "bindUI", mScrollView.isScrollContainer(), mListView.isScrollContainer());
	}
}
