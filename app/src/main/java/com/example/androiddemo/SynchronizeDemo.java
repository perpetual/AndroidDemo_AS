package com.example.androiddemo;

import android.media.JetPlayer;

import com.example.androiddemo.utils.LogUtil;
import com.example.androiddemo.utils.ThreadUtils;

/**
 * Created by garyzhao on 2017/6/9.
 */

public class SynchronizeDemo implements Runnable{
    private static final String TAG = "SynchronizeDemo";

    private static int sCount = 0;
    private String mObject;

    public SynchronizeDemo(String lock) {
        mObject = lock;
    }

    private static void func(Object lock) {
        sCount += 1;
        LogUtil.w(TAG, "run", Thread.currentThread().getName(), "lock", lock, "sCount", sCount);
    }

    private static void running(Object lock) {
        int count = 5;
        while (count-- > 0) {
            if (null == lock) {
                func(lock);
            } else {
                synchronized (lock) {
                    func(lock);
                }
            }
            ThreadUtils.sleep(50);
        }
    }

    @Override
    public void run() {
        running(mObject);
    }
}
