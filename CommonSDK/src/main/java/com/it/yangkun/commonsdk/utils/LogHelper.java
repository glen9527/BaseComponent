package com.it.yangkun.commonsdk.utils;

import android.os.Environment;

import com.blankj.ALog;
import com.blankj.utilcode.util.FileUtils;
import com.it.yangkun.commonsdk.core.Constants;

/**
 * Created by yangk on 2018/10/18.
 */

public class LogHelper {

    public static void i(String TAG, String objects) {
        logConfig(Constants.ILogPath.Log_I_Path);
        ALog.i(TAG, objects);
    }

    public static void http(String TAG, String objects) {
        logConfig(Constants.ILogPath.Log_HTTP_Path);
        ALog.i(TAG, objects);
    }

    public static void d(String TAG, String objects) {
        logConfig(Constants.ILogPath.Log_D_Path);
        ALog.i(TAG, objects);
    }
    public static void e(String TAG, String objects) {
        logConfig(Constants.ILogPath.Log_E_Path);
        ALog.i(TAG, objects);
    }


    private static void logConfig(String path) {
        String logPath = Environment.getExternalStorageDirectory() + path;
        if (!FileUtils.isDir(logPath)) {
            FileUtils.createOrExistsDir(logPath);
        }
        ALog.getConfig().setDir(logPath);
    }


}
