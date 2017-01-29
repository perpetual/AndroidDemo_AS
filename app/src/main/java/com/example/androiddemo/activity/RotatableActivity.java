package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;
import com.example.androiddemo.utils.AndroidDemoUtil;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RotatableActivity extends DemoBaseActivity implements OnClickListener {

	static private final int PICK_REQUEST = 1337;
	private String CONTACT = "contact";
	private Button mPickBtn = null;
	private Button mViewBtn = null;
	private Uri mContact = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData(savedInstanceState);
		bindUI();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		
		switch (v.getId()) {
		case R.id.pick:
			intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_REQUEST);
			break;
		case R.id.view:
			intent = new Intent(Intent.ACTION_VIEW, mContact);
			AndroidDemoUtil.showLongToast(mContact.toString());
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (PICK_REQUEST == requestCode) {
			if (RESULT_OK == resultCode) {
				mContact = data.getData();
				mViewBtn.setEnabled(true);
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		AndroidDemoUtil.showToast("onSaveInstanceState");
		if (null != mContact) {
			outState.putString(CONTACT, mContact.toString());
		}
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		return mContact;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		bindUI();
	}
	
	/**
	 * ˽�й��ߺ���
	 */
	private void initData(Bundle bundle) {
//		restoreMe(bundle);
//		 ();
	}
	
	private void bindUI() {
		setContentView(R.layout.rotatable_layout);

		mPickBtn = (Button)findViewById(R.id.pick);
		mPickBtn.setOnClickListener(this);
		mViewBtn = (Button)findViewById(R.id.view);
		mViewBtn.setOnClickListener(this);
		mViewBtn.setEnabled(null != mContact);
	}
	
	private void restoreMe(Bundle bundle) {
		if (null != bundle) {
			String str = bundle.getString(CONTACT);
			if (null != str && str.length() > 0) {
				mContact = Uri.parse(str);
			}
		}
	}
	
	private void restoreMe() {
		Object obj = getLastNonConfigurationInstance();
		if (null != obj && obj instanceof Uri) {
			mContact = (Uri)obj;
		}
	}
}
