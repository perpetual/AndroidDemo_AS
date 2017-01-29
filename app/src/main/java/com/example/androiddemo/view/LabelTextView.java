package com.example.androiddemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androiddemo.R;
import com.example.androiddemo.model.IUIInitialization;

/**
 * 
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-28		Create		
 * </pre>
 */
public class LabelTextView extends LinearLayout implements IUIInitialization {

	private Context mContext = null;
	private String mLabel = null;
	private String mContent = null;
	
	private TextView mLabelTextView = null;
	private TextView mContentTextView = null;

	public LabelTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initData(context, attrs);
		initLayout();
		bindView();
		initView();
	}

	public void setLabel(String label) {
		mLabel = label;
		if (TextUtils.isEmpty(label)) {
			return;
		}
		mLabelTextView.setText(label);
	}
	
	public void setContent(String content) {
		mContent = content;
		if (TextUtils.isEmpty(content)) {
			return;
		}
		mContentTextView.setText(content);
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.LabelTextView);
		for (int i = 0; i < typedArray.getIndexCount(); ++i) {
			int attr = typedArray.getIndex(i);
			switch (attr) {
			case R.styleable.LabelTextView_label:
				mLabel = typedArray.getString(i);
				break;
			case R.styleable.LabelTextView_content:
				mContent = typedArray.getString(i);
				break;
			default:
				break;
			}
		}
		typedArray.recycle();
	}

	@Override
	public void initLayout() {
		LayoutInflater.from(mContext).inflate(R.layout.label_text_view, this,
				true);
	}

	@Override
	public void bindView() {
		mLabelTextView = (TextView)findViewById(R.id.label);
		mContentTextView = (TextView)findViewById(R.id.content);
	}

	@Override
	public void initView() {
		refreshView();
	}

	@Override
	public void refreshView() {
		if (!TextUtils.isEmpty(mLabel)) {
			mLabelTextView.setText(mLabel + ":");
		}
		setContent(mContent);
	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub
		
	}
}
