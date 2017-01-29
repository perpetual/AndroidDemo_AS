package com.example.androiddemo.activity;

import java.util.List;

import android.content.Intent;
import android.hardware.Sensor;
import android.view.View;
import android.widget.ListView;

import com.example.androiddemo.utils.SystemServiceUtil;


/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-24		Create		
 * </pre>
 */
public class SensorListActivity extends SuperListActivity<Sensor> {
	
	@Override
	protected List<Sensor> getDataSource() {
		return SystemServiceUtil.getSensorManager().getSensorList(Sensor.TYPE_ALL);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (mListAdapter.getItem(position).getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			startActivity(new Intent(this, AccelerometerActivity.class));
			break;

		default:
			break;
		}
	}
}

