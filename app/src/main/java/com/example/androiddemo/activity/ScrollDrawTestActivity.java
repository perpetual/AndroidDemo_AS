package com.example.androiddemo.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.example.androiddemo.R;

import java.util.Random;

/**
 * Created by Gary on 2017/8/15.
 */

public class ScrollDrawTestActivity extends DemoListActivity {

    Random mRandom = new Random(System.currentTimeMillis());

    @Override
    protected ListAdapter initAdapter() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return 50;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View ret = convertView;
                long start = System.currentTimeMillis();
                if (null == convertView) {
                    ret = new ComplexLayout(ScrollDrawTestActivity.this);
                    Log.d("xxx", "getView1:" + (System.currentTimeMillis() - start));
                } else {
                    ViewGroup.LayoutParams lp = ret.getLayoutParams();
                    lp.width = 300 + (100 * (mRandom.nextInt(100) % 3));
                    ret.setLayoutParams(lp);
                    Log.d("xxx", "getView2:" + (System.currentTimeMillis() - start));
                }
                return ret;
            }
        };
    }
}

class ComplexLayout extends RelativeLayout {

    public ComplexLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.complex_layout, this);
    }
}
