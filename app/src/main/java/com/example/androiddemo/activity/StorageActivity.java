package com.example.androiddemo.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androiddemo.R;
import com.example.androiddemo.tools.CustomSpinnerAdapter;

public class StorageActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private TextView mDisplayView = null;
	private Button mExternalStorageDir = null;
	private Spinner mExternalStorageDirTypeSpinner = null;
	private int mExternalStorageDirType = 0;
	private CustomSpinnerAdapter mSpinnerAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storage_layout);
		initData();
		bindView();
		initView();
	}

	/**
	 * ˽�й��ߺ���
	 */
	private void initData() {
		mSpinnerAdapter = new CustomSpinnerAdapter(this);
		mSpinnerAdapter.setDataSource(getResources().getStringArray(
				R.array.media_spinner_list));
	}

	private void bindView() {
		mDisplayView = (TextView) findViewById(R.id.display_view);
		mExternalStorageDir = (Button) findViewById(R.id.external_storage_dir_btn);
		mExternalStorageDirTypeSpinner = (Spinner) findViewById(R.id.external_storage_dir_type);
	}

	private void initView() {
		// mExternalStorageDirTypeSpinner.setAdapter(mSpinnerAdapter);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.media_spinner_list));
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mExternalStorageDirTypeSpinner.setAdapter(arrayAdapter);
		mExternalStorageDirTypeSpinner.setOnItemSelectedListener(this);
		mExternalStorageDir.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		File file = null;
		switch (v.getId()) {
		case R.id.external_storage_dir_btn:
			switch (mExternalStorageDirType) {
			case 0:
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
				break;
			case 1:
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
				break;
			case 2:
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
				break;
			case 3:
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
				break;
			case 4:
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
				break;
			case 5:
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
				break;
			case 6:
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				break;
			case 7:
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
				break;
			case 8:
				file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		mDisplayView.setText(file.getAbsolutePath());
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		mExternalStorageDirType = position;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}
