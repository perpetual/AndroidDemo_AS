package com.example.androiddemo.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Gary on 2017/8/15.
 */

public class ComplexLayout extends FrameLayout {

    private int mInitColor = 0xFFFFFFFF;
    private int mInitWidth = 900;

    public ComplexLayout(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View view = new FrameLayout(getContext());
        addView(view);
    }
}
