package com.example.androiddemo.model;

import android.content.Context;
import android.util.AttributeSet;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 *
 * Description��
 * 
 * History��
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-11		Create		
 * </pre>
 */
public interface IUIInitialization {
	
	public void initData(Context context, AttributeSet attrs);
	
	public void initLayout();
	
	public void bindView();
	
	public void initView();
	
	public void updateView();
	
	public void refreshView();
}

