package com.example.androiddemo.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestActivity extends DemoSuperActivity {

	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
        TestString.testPlus();
        TestString.testConcat();
        TestString.testStringBuffer();
        TestString.testStringBuilder();
	}

	@Override
	protected String getTopButtonText() {
		return "TEST";
	}
	
	@Override
	protected void doTopButtonClick() {
		PackageManager pm = getPackageManager();
		updateTextView(TEXT_VIEW_TOP, AndroidDemoUtil.argumentsToString(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)), false);
	}
}

class TestString {
    private static final String TAG = "TestString";

    public static int getLength(CharSequence cs) {
        return TextUtils.isEmpty(cs) ? 0 : cs.length();
    }

    public static int ensureInRange(int start, int end, int value) {
        if (start > end) {
            start += end;
            end = start - end;
            start = start - end;
        }
        return Math.max(Math.min(end, value), start);
    }

    public static CharSequence getSubString(CharSequence text, int length) {
        return getSubString(text, 0, length);
    }

    public static CharSequence getUnemptyString(CharSequence str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    public static CharSequence getSubString(CharSequence text, int start,
                                            int length) {
        final int len = getLength(text);
        start = ensureInRange(0, len, start);
        int end = ensureInRange(0, len, start + length);
        return TextUtils.substring(getUnemptyString(text), start, end);
    }

    public static String getRandomString(int length) { // length表示生成字符串的长度

        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 生成字符串从此序列中取
//        Random random = new Random(System.nanoTime());
//        StringBuffer builder = new StringBuffer();
//        for (int i = 0; i < length; i++) {
//            int number = random.nextInt(base.length());
//            builder.append(base.charAt(number));
//        }

        return base;

    }

    public static void testPlus() {
        String s = "";
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            String str = getRandomString(20);
            s = s + String.valueOf(getSubString(str, 10));
        }
        long te = System.currentTimeMillis();
        LogUtil.d(TAG, "+ cost {} ms", te - ts);
    }

    public static void testConcat() {
        String s = "";
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            String str = getRandomString(20);
            s = s.concat(String.valueOf(getSubString(str, 10)));
        }
        long te = System.currentTimeMillis();
        LogUtil.d(TAG, "concat cost {} ms", te - ts);
    }

    public static void testStringBuffer() {
        StringBuffer sb = new StringBuffer();
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            String str = getRandomString(20);
            sb.append(String.valueOf(getSubString(str, 10)));
        }
        sb.toString();
        long te = System.currentTimeMillis();
        LogUtil.d(TAG, "StringBuffer cost {} ms", te - ts);
    }

    public static void testStringBuilder() {
        StringBuilder sb = new StringBuilder();
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            String str = getRandomString(20);
            sb.append(String.valueOf(getSubString(str, 10)));
        }
        sb.toString();
        long te = System.currentTimeMillis();
        LogUtil.d(TAG, "StringBuilder cost {} ms", te - ts);
    }
}