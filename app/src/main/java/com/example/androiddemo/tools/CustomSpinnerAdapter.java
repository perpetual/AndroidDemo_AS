package com.example.androiddemo.tools;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.example.androiddemo.view.ListItemView;

public class CustomSpinnerAdapter implements SpinnerAdapter {

	private Context mContext = null;
	private String[] mStringArray = null;
	
	public CustomSpinnerAdapter(Context context) {
		mContext = context;
	}
	
	public void setDataSource(String[] stringArray) {
		mStringArray = stringArray;
	}
	
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {		
	}

	@Override
	public int getCount() {
		return null == mStringArray ? 0 : mStringArray.length;
	}

	@Override
	public Object getItem(int position) {
		return null == mStringArray ? "xxx" : mStringArray[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (null == convertView || !(convertView instanceof ListItemView)) {
			listItemView = new ListItemView(mContext);
		} else if (convertView instanceof ListItemView) {
			listItemView = (ListItemView)convertView;
		}
		listItemView.setMainText((String)getItem(position));
		return listItemView;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent);
	}

}
