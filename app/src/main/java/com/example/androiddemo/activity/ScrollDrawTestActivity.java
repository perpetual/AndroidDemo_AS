package com.example.androiddemo.activity;

import android.content.Context;
import android.graphics.Canvas;
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

    private Random mRandom = new Random(System.currentTimeMillis());
    private float mTotal;
    private int mCount;

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
                Log.d("xxx", "getView0");
                if (null == convertView || true) {
                    ret = new ComplexLayout(ScrollDrawTestActivity.this);
                    long diff = System.currentTimeMillis() - start;
                    mTotal += diff;
                    ++mCount;
                    Log.d("xxx", "getView1:" + mTotal / mCount);
                    ret.invalidate();
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
    private static final String TAG = "ComplexLayout";

    public ComplexLayout(Context context) {
        super(context);
        Log.d(TAG, "ComplexLayout1");
        LayoutInflater.from(context).inflate(R.layout.complex_layout, this);
        setBackgroundResource(R.drawable.blue);
        Log.d(TAG, "ComplexLayout1");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure1");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure2");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout1");
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout2");
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG, "draw1");
        super.draw(canvas);
        Log.d(TAG, "draw2");
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.d(TAG, "drawChild1");
        boolean ret = super.drawChild(canvas, child, drawingTime);
        Log.d(TAG, "drawChild2");
        return ret;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw1");
        super.onDraw(canvas);
        Log.d(TAG, "onDraw2");
    }

    private static void setBackground(View view, int resId) {
        view.setBackgroundResource(resId);
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; ++i) {
                setBackground(vg.getChildAt(i), resId);
            }
        }
    }

}
