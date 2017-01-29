package com.example.androiddemo.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;
import com.example.androiddemo.R.menu;
import com.example.androiddemo.R.string;
import com.example.androiddemo.utils.AndroidDemoUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListViewActivity extends Activity {

	private Object mObj = new Object();
	private MyFilter mMyFilter = null;
	private List<String> mStringList = new ArrayList<String>();
	private long mStartRun = 0;
	private long mStopRun = 0;
	private int mDrawCount = 1;

	private class ListViewAdapter extends BaseAdapter implements Filterable {

		@Override
		public int getCount() {
			return mStringList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (0 == mStartRun) {
				mStartRun = System.currentTimeMillis();
			}
			TextView textView = null;
			View view1 = null;
			if (null == convertView) {
				view1 = LayoutInflater.from(ListViewActivity.this).inflate(
						R.layout.main_list_item, null);
			} else {
				view1 = convertView;
			}
			CheckedTextView view2 = (CheckedTextView) LayoutInflater.from(
					ListViewActivity.this).inflate(
					android.R.layout.simple_list_item_multiple_choice, null);
			// textView = (TextView)view1.findViewById(R.id.title1);
			textView = (TextView) view1.findViewWithTag(getResources()
					.getString(R.string.test));
			textView.setText(mStringList.get(position));
			view1.setMinimumHeight(44 + 5 * position);
			Bitmap bitmap = view1.getDrawingCache();
			if (null == bitmap) {
//				Log.d("xxx", "fewgsee");
			} else {
				mImageView.setImageBitmap(bitmap);
			}
			mStopRun = System.currentTimeMillis();
			Log.d("xxx", String.valueOf((float)(mStopRun - mStartRun) / mDrawCount++));
			return view1;
		}

		@Override
		public Filter getFilter() {
			return null == mMyFilter ? new MyFilter() : mMyFilter;
		}
	}

	class MyFilter extends Filter {

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			synchronized (mObj) {
				if (null == results || results.count < 1) {
					mStringList = getOriginalData();
					mListViewAdapter.notifyDataSetInvalidated();
				} else {
					mStringList = (List<String>) (results.values);
					mListViewAdapter.notifyDataSetChanged();
				}
			}
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults filterResults = new FilterResults();
			List<String> list = null;
			if (constraint.length() < 1) {
				list = getOriginalData();
			} else {
				list = new ArrayList<String>();
				for (String str : mStringList) {
					if (str.contains(constraint)) {
						list.add(str);
					}
				}
			}
			filterResults.values = list;
			filterResults.count = list.size();
			return filterResults;
		}
	}

	private class MultiChoiceModeCallback implements
			ListView.MultiChoiceModeListener {

		/**
		 * ����ActionMode����:����ListView�Ժ������ɰ�Ч��������ɰ����ActionMode�� ������������Ӳ˵�����ݰ�ť�ȡ�
		 */
		public boolean onCreateActionMode(ActionMode mActionMode, Menu menu) {
			MenuInflater inflater = getMenuInflater();

			/**
			 * ����ActionMode�Ĳ˵���
			 */
			inflater.inflate(R.menu.list_select_menu, menu);

			/**
			 * ����ActionMode�ı��⡣
			 */
			mActionMode.setTitle("Select Items");

			setSubtitle(mActionMode);

			return true;
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		/**
		 * ����ActionMode�Ĳ˵������ô˷�����
		 */
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.share:/* menu��Ŀ */
				AndroidDemoUtil.showToast("Shared "
						+ mListView.getCheckedItemCount() + " items");
				mode.finish();
				break;
			default:
				AndroidDemoUtil.showToast("Clicked " + item.getTitle());
				break;
			}
			return true;
		}

		public void onDestroyActionMode(ActionMode mode) {
			AndroidDemoUtil.showToast("onDestroyActionMode");
		}

		/**
		 * ��ѡ��ListView��һ����Ŀʱ�ᴥ����
		 */
		public void onItemCheckedStateChanged(ActionMode mActionMode,
				int position, long id, boolean checked) {
			setSubtitle(mActionMode);
		}

		private void setSubtitle(ActionMode mode) {
			final int checkedCount = mListView.getCheckedItemCount();

			switch (checkedCount) {
			case 0:
				mode.setSubtitle(null);
				break;
			case 1:
				mode.setSubtitle("1 item selected");
				break;
			default:
				mode.setSubtitle(checkedCount + " items selected");
				break;
			}
		}
	}

	private ListViewAdapter mListViewAdapter = null;
	private EditText mEditText = null;
	private Button mBtn1 = null;
	private View mLinearView = null;
	private View mImageView1 = null;
	private View mImageView2 = null;
	private View mImageView3 = null;
	private ImageView mImageView = null;
	private RelativeLayout mRelative = null;
	private ListView mListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_layout);
		initData();
		bindUI();
		updateUI();
	}

	/**
	 * ˽�й��ߺ���
	 */
	private void initData() {
		mListViewAdapter = new ListViewAdapter();
		mStringList = getOriginalData();
	}

	private void bindUI() {
		mLinearView = findViewById(R.id.btn_linear_layout);
		mEditText = (EditText) findViewById(R.id.edit_text);
		mBtn1 = (Button) findViewById(R.id.btn1);
		mImageView = (ImageView)findViewById(R.id.image_view);
		mListView = (ListView) findViewById(R.id.list_view);

		mBtn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		mImageView1 = findViewById(R.id.image_view1);
		mImageView2 = findViewById(R.id.image_view2);
		mImageView3 = findViewById(R.id.image_view3);
		mImageView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WindowManager wm = (WindowManager) getApplicationContext()
						.getSystemService("window");
				WindowManager.LayoutParams param = new WindowManager.LayoutParams();
				param.type = 2002;
				param.format = 1;
				param.flags = 40;
				param.width = 200;
				param.height = 800;
				View view = new View(ListViewActivity.this);
				view.setBackgroundColor(0x8800ff00);
				// mRelative.addView(view);
				AndroidDemoUtil.showToast("xxxx");
				// wm.addView(view, param);
			}
		});

		// mListView.requestFocus();
		mListView.setAdapter(mListViewAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AndroidDemoUtil.showToast("onItemClick:" + mListView.isFocused()
						+ ":" + view.isFocused());
			}
		});

		mListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						AndroidDemoUtil.showToast("onItemLongClick:"
								+ mListView.getContentDescription().toString());
						return true;
					}
				});

		AndroidDemoUtil.showLongToast("" + mListView.getPersistentDrawingCache());
	}

	public void clickTest(View v) {
		AndroidDemoUtil.showToast("clickTest");
	}

	private void updateUI() {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
	}

	private List<String> getOriginalData() {
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < 400; ++i) {
			list.add("xxx" + String.valueOf(i));
		}
		return list;
	}
}
