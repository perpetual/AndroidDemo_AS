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
    public static Object sObject1;
    public static Object sObject2;

    private static void func() {
        sCount += 1;
        LogUtil.w(TAG, "run", Thread.currentThread().getName(), "sCount", sCount);
    }

    private synchronized static void running() {
        int count = 5;
        String[] indexs = Thread.currentThread().getName().split("_");
        int index = Integer.valueOf(indexs[1]);

        while (count-- > 0) {
            switch (index) {
                case 1:
                    synchronized (sObject1) {
                        func();
                    }
                    break;
                case 2:
                    synchronized (sObject2) {
                        func();
                    }
                    break;
                default:
                    func();
                    break;
            }
            ThreadUtils.sleep(50);
        }
    }

    @Override
    public void run() {
        running();
    }
}
