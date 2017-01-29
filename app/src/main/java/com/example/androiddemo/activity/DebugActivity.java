package com.example.androiddemo.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;

import com.example.androiddemo.R;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 * 
 * Description：用于测试调试工具
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-31		Create
 * </pre>
 */
public class DebugActivity extends DemoSuperActivity {

	/**
	 * 内部类
	 */
	private class DebugClass {
		private String mName;
		private int age;
		public DebugClass(String name, int age) {
			this.mName = name;
			this.age = age;
		}
	}

	/**
	 * 内部工具函数
	 */
	private void matTest() {
		Map<String, DebugClass> map = new HashMap<String, DebugClass>();
		Object[] array = new Object[1000000];
		for (int i = 0; i < array.length; ++i) {
			String str = new Date().toString();
			DebugClass dc = new DebugClass(str, i);
			map.put(i + "garyzhao", dc);
			array[i] = dc;
		}
	}
	
	@Override
	public void initLayout() {
		setContentView(R.layout.sample_layout);
	}

	@Override
	public void initData(Context context, AttributeSet attrs) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
