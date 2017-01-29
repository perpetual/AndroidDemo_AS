package com.example.androiddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.androiddemo.R;
import com.example.androiddemo.model.IUIInitialization;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-7-22		Create		
 * </pre>
 */
public class CommonInputView extends LinearLayout implements IUIInitialization{

	private EditText mInputView1 = null;
	private EditText mInputView2 = null;
	
	public CommonInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initData(context, attrs);
		initLayout();
		bindView();
		initView();
	}

	public EditText getInputView1() {
		if (null == mInputView1) {
			throw new NullPointerException("getInputView1 null");
		}
		return mInputView1;
	}
	
	public EditText getInputView2() {
		if (null == mInputView2) {
			throw new NullPointerException("getInputView2 null");
		}
		return mInputView2;
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		bindView();
		initView();
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		
	}

	@Override
	public void initLayout() {
	}

	@Override
	public void bindView() {
		mInputView1 = (EditText) findViewById(R.id.common_input_view1);
		mInputView2 = (EditText) findViewById(R.id.common_input_view2);
	}

	@Override
	public void initView() {
	}

	@Override
	public void updateView() {
		
	}

	@Override
	public void refreshView() {
		
	}

}

