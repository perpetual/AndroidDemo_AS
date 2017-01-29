package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DemoListActivity extends Activity implements OnClickListener{
	
	private static final int ITEM_COUNT = 30;
	
	private class DataHolder {
		public TextView mTextView = null;
	}

	private Button mTextBtn = null;
	private ListView mListView = null;
	private BaseAdapter mAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		initData();
		bindUI();
	}
	
	@Override
	public void onClick(View v) {
		mListView.setSelection(0);
	}
	
	/**
	 * ˽�й�����
	 */
	private void initData() {
		mAdapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				DataHolder dataHolder = null;
				
				if (null == view) {
					view = LayoutInflater.from(DemoListActivity.this).inflate(R.layout.main_list_item, null);
					dataHolder = new DataHolder();
					dataHolder.mTextView = (TextView)view.findViewById(R.id.title1);
					view.setTag(dataHolder);
				} else {
					dataHolder = (DataHolder)view.getTag();
				}
				dataHolder.mTextView.setText(String.valueOf(position));
				return view;
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
				return ITEM_COUNT;
			}
		};
	}
	
	private void bindUI() {
		mTextBtn = (Button)findViewById(R.id.text_btn);
		mTextBtn.setOnClickListener(this);
		mListView = (ListView)findViewById(R.id.list_view);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				mListView.setSelection(0);
				Toast.makeText(DemoListActivity.this, "xxxxx", Toast.LENGTH_LONG).show();
				return true;
			}
		});
	}
}
