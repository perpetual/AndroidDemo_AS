package com.example.androiddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.androiddemo.R;

/**
 * Created by Gary on 2017/8/20.
 */

public class DrawTestView extends RelativeLayout {
    public DrawTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.draw_test_layout, this);
    }

}
