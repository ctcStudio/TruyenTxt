package com.hiepkhach9x.baseTruyenHK.task;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.hiepkhach9x.baseTruyenHK.task.implement.LoadBookListener;
import com.hiepkhach9x.baseTruyenHK.utils.FileUtils;
import com.hiepkhach9x.truyentxt.BookApplication;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HungHN on 3/16/2016.
 */
public class LoadDataTask extends AsyncTask<String, Void, StringBuilder> {

    public static final int FILE_ASSETS = 1;
    public static final int FILE_SDCARD = 2;

    private int fileType = FILE_ASSETS;

    private LoadBookListener ls;

    public LoadDataTask(int fileType) {
        this.fileType = fileType;
    }

    public void setLs(LoadBookListener ls) {
        this.ls = ls;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (ls != null) {
            ls.loadBookStart();
        }
    }

    @Override
    protected StringBuilder doInBackground(String... params) {
        String filePath = params[0];
        if (fileType == FILE_ASSETS) {
            return FileUtils.readFileFromAsset(filePath);
        } else if (fileType == FILE_SDCARD) {
            return FileUtils.readFileFromDisk(filePath);
        }
        return null;
    }

    @Override
    protected void onPostExecute(StringBuilder stringBuilder) {
        super.onPostExecute(stringBuilder);
        if (ls != null) {
            ls.loadBookFinish(stringBuilder);
        }
    }
}
