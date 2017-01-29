package com.example.androiddemo.activity;

import android.animation.ObjectAnimator;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.ThreadUtils;

import java.util.ArrayList;

public class BlankActivity extends DemoBaseActivity implements OnClickListener {
	static {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private CheckBox mCheckBox = null;
	private Button mAddContactsBtn = null;
	private Button mNewContactsBtn = null;
	private Button mTestBtn = null;
	private Button mConsumeMemoryBtn = null;
	private TextView mTextView = null;
	private TextView mTestTextView = null;
	private EditText mEditText = null;
	private LinearLayout mList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		super.onCreate(savedInstanceState);
		bindUI();
		updateUI();
		getWindow().setBackgroundDrawable(null);
		AndroidDemoUtil.lightOn();
	}
	
	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.add_contacts_btn:
				AddContact();
				break;
			case R.id.new_contacts_btn:
				newContact();
				break;
			case R.id.test_btn:
				int keycode = KeyEvent.KEYCODE_0;
				for (keycode = 1; keycode < 300; ++keycode) {
					mEditText.onKeyDown(keycode, new KeyEvent(KeyEvent.ACTION_DOWN, keycode));
				}
				break;
			case R.id.consume_memory_btn:
				consumeMemory();
				break;
			default:
				break;
			}
		} catch (Exception e) {
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, this + "onSaveInstanceState");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(TAG, this + "onRestoreInstanceState");
	}

	/**
	 * ˽�й��ߺ���
	 */
	void bindUI() {
		setContentView(R.layout.blank_layout);

		mCheckBox = (CheckBox) findViewById(R.id.checkbox);
		mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(BlankActivity.this, "xxx", 0).show();
			}
		});
		mAddContactsBtn = (Button) findViewById(R.id.add_contacts_btn);
		mAddContactsBtn.setOnClickListener(this);
		mNewContactsBtn = (Button) findViewById(R.id.new_contacts_btn);
		mNewContactsBtn.setOnClickListener(this);
		mTestBtn = (Button) findViewById(R.id.test_btn);
		mTestBtn.setOnClickListener(this);
		mConsumeMemoryBtn = (Button) findViewById(R.id.consume_memory_btn);
		mConsumeMemoryBtn.setOnClickListener(this);
		mTextView = (TextView)findViewById(R.id.text_view);
		mEditText = (EditText)findViewById(R.id.edittext1);
		mList = (LinearLayout)findViewById(R.id.list);
		mTestTextView = (TextView)findViewById(R.id.test_text_view);
		EditText et = (EditText)LayoutInflater.from(this).inflate(R.layout.edit_text_layout, null);
		mList.addView(et, 10);

		ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(mTestTextView, "translationX", 0,
				AndroidDemoUtil.dip2px(200) * 1f);
		
		translationXAnim.setDuration(3000).start();
		ThreadUtils.runOnMainThread(new Runnable() {
			
			@Override
			public void run() {
				mTestTextView.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
						.getDrawable(R.drawable.ic_launcher), null, null);
				mTestTextView.clearAnimation();
				
			}
		}, 1500);
	}
	
	private void updateUI() {
		String str = String.format(getString(R.string.format_string), TextUtils.htmlEncode(getString(R.string.my_name)));
//		str = getString(R.string.style_string);
		mEditText.setText("xxxx");
		mEditText.setText(null);
		mTextView.setText(String.valueOf(mTextView.getTextScaleX()));
		mTextView.setText(String.valueOf(getResources().getDisplayMetrics().density));
	}

	private void AddContact() throws Exception {
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = getBaseContext().getContentResolver();
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		ContentProviderOperation op1 = ContentProviderOperation.newInsert(uri)
				.withValue("account_name", null).build();
		operations.add(op1);

		uri = Uri.parse("content://com.android.contacts/data");
		ContentProviderOperation op2 = ContentProviderOperation.newInsert(uri)
				.withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/name").withValue("data2", "!!!!!")
				.build();
		operations.add(op2);
		
		for (Integer i = 0; i < 110; ++i) {

			ContentProviderOperation op3 = ContentProviderOperation.newInsert(uri)
					.withValueBackReference("raw_contact_id", 0)
					.withValue("mimetype", "vnd.android.cursor.item/phone_v2")
					.withValue("data1", i.toString()).withValue("data2", "2").build();
			operations.add(op3);
		}

		ContentProviderOperation op4 = ContentProviderOperation.newInsert(uri)
				.withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/email_v2")
				.withValue("data1", "asdfasfad@163.com").withValue("data2", "2").build();
		operations.add(op4);

		resolver.applyBatch("com.android.contacts", operations);
	}
	
	private void newContact() {
		 Intent intent = new Intent(Intent.ACTION_INSERT);
         intent.setType("vnd.android.cursor.dir/person");
         intent.setType("vnd.android.cursor.dir/contact");
         intent.setType("vnd.android.cursor.dir/raw_contact");
         startActivity(intent);
	}

	private void mapTest() {
		Uri uri = Uri.parse("geo:" + "30.5403,104.06908");
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}

	private void webTest() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://www.qq.com"));
		startActivity(intent);
	}

	private void copyDB() {
		AndroidDemoUtil.copyFile("/data/data/com/tencent/pb/databases/contactsv2.db",
				"/storage/sdcard0/contactsv2.db");
		AndroidDemoUtil.copyFile("/data/data/com/tencent/pb/databases/pb.db", "/storage/sdcard0/pb.db");
	}

	private void consumeMemory() {

	}
}
