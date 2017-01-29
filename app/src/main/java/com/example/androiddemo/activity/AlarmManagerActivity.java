package com.example.androiddemo.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.AndroidDemoUtil;

public class AlarmManagerActivity extends Activity {
	public static class AlarmReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			AndroidDemoUtil.showToast("hahahha");
		}		
	}

	private AlarmManager mAlarmManager = null;
	private Intent mIntent = null;
	PendingIntent mSender = null;
	private Button mStartBtn = null;
	private Button mStopBtn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		bindUI();
	}
	
	/**
	 * ˽�й��ߺ���
	 */
	private void initData() {
		mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		mIntent = new Intent(AlarmManagerActivity.this, AlarmReceiver.class);
		mIntent.setAction("repeating");
		mSender = PendingIntent.getBroadcast(AlarmManagerActivity.this, 0, mIntent, 0);
	}
	
	private void bindUI () {
		setContentView(R.layout.alarm_manager_layout);
		mStartBtn = (Button)findViewById(R.id.start);
		mStopBtn = (Button)findViewById(R.id.stop);
		mStartBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long firstTime = SystemClock.elapsedRealtime();
				mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,  firstTime, 5000, mSender);
			}
		});
		mStopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAlarmManager.cancel(mSender);
			}
		});
	}
}
