package com.hiepkhach9x.truyentxt.utils;

import android.util.Log;

import com.hiepkhach9x.baseTruyenHK.utils.Config;
import com.hiepkhach9x.baseTruyenHK.utils.Constants;

/**
 * Created by HungHN on 2/20/2016.
 */
public class LogUtils {
    public static void e(String message) {
        if (Config.DEBUG) {
            Log.e(Constants.TAG, message);
        }
    }

    public static void d(String message) {
        if (Config.DEBUG) {
            Log.d(Constants.TAG, message);
        }
    }

    public static void i(String message) {
        if (Config.DEBUG) {
            Log.i(Constants.TAG, message);
        }
    }
}
