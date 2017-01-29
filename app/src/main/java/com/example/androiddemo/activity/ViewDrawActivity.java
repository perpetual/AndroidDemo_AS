package com.example.androiddemo.activity;

import android.view.View;

import com.example.androiddemo.R;
import com.example.androiddemo.view.MaskView;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 * 
 * Description
 * 
 * History��
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-15		Create
 * </pre>
 */
public class ViewDrawActivity extends DemoSuperActivity {

	private MaskView mClipView = null;

	@Override
	public void initView() {
		super.initView();
		mClipView.setImageResource(R.drawable.ic_launcher);
		mClipView.setImageSaturation(0.5f);
		mClipView.setImageCenter(true);
		mClipView.setImageRotation(45 / 2);
		getCustomView().setOnClickListener(this);
	}
	
	@Override
	public void bindView() {
		super.bindView();
		mClipView = (MaskView) findViewById(R.id.clip_view);
	}
	
	@Override
	protected String getLeftButtonText() {
		return "透明";
	}
	
	@Override
	protected String getRightButtonText() {
		return "恢复";
	}
	
	@Override
	protected String getTopButtonText() {
		return "mask";
	}
	
	@Override
	protected String getBottomButtonText() {
		return "unmask";
	}
	
	@Override
	protected void doLeftButtonClick() {
		mClipView.setImageAlpha(0.5f);
	}
	
	@Override
	protected void doRightButtonClick() {
		mClipView.setImageAlpha(1.f);
	}
	
	@Override
	protected void doTopButtonClick() {

		mClipView.setMaskDrawable(getResources().getDrawable(R.drawable.multi_state));
	}
	
	@Override
	protected void doBotttomButtonClick() {
		mClipView.setMaskDrawable(null);
	}
	
	@Override
	protected int getCustomViewAreaLayoutResource() {
		return R.layout.common_clip_view_layout;
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
	}
}
