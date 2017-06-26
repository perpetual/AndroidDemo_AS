package com.example.androiddemo.activity;

import android.os.Environment;

import com.example.androiddemo.utils.AndroidDemoUtil;

/**
 * Created by garyzhao on 2017/6/26.
 */

public class SystemInfoActivity extends DemoSuperActivity {

    @Override
    public void initView() {
        super.initView();
        StringBuilder sb = new StringBuilder();
        sb.append("getFilesDir:").append(getFilesDir().getAbsolutePath()).append(AndroidDemoUtil.LINE_SEPARATOR);
        sb.append("getCacheDir:").append(getCacheDir().getAbsolutePath()).append(AndroidDemoUtil.LINE_SEPARATOR);
        sb.append("getObbDir:").append(getObbDir().getAbsolutePath()).append(AndroidDemoUtil.LINE_SEPARATOR);
        sb.append("getExternalCacheDir:").append(getExternalCacheDir().getAbsolutePath()).append(AndroidDemoUtil.LINE_SEPARATOR);
        sb.append("getExternalFilesDir:").append(getExternalFilesDir(Environment.DIRECTORY_ALARMS).getAbsolutePath()).append(AndroidDemoUtil.LINE_SEPARATOR);
        sb.append("getDatabasePath:").append(getDatabasePath("xxx").getAbsolutePath()).append(AndroidDemoUtil.LINE_SEPARATOR);
        if (AndroidDemoUtil.isSDKVersionAtLeast(24)) {
            sb.append("getDataDir:").append(getDataDir().getAbsolutePath()).append(AndroidDemoUtil.LINE_SEPARATOR);
            sb.append("getCodeCacheDir:").append(getCodeCacheDir().getAbsolutePath()).append(AndroidDemoUtil.LINE_SEPARATOR);
        }
        updateTextView(TEXT_VIEW_TOP, sb.toString(), false);
    }
}
