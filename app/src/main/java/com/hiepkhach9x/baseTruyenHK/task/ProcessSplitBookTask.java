package com.hiepkhach9x.baseTruyenHK.task;

import android.os.AsyncTask;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;

import java.util.List;

/**
 * Created by HungHN on 2/20/2016.
 */
public class ProcessSplitBookTask extends AsyncTask<BookData, Integer, List<String>> {

    private SplitBookListener splitBookListener;
    private Setting setting;

    public ProcessSplitBookTask(Setting setting) {
        this.setting = setting;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (hasListener()) {
            splitBookListener.splitBookStart();
        }
    }

    @Override
    protected List<String> doInBackground(BookData... bookDatas) {

        return null;
    }

    @Override
    protected void onCancelled(List<String> strings) {
        if (hasListener()) {
            splitBookListener.splitBookError(strings);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (hasListener()) {
            splitBookListener.splitBookProcessUpdatePercent(values[0]);
        }
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        if (hasListener()) {
            splitBookListener.splitBookFinish(strings);
        }
    }

    public void setSplitBookListener(SplitBookListener splitBookListener) {
        this.splitBookListener = splitBookListener;
    }

    public void removeSplitBookListener() {
        this.splitBookListener = null;
    }

    private boolean hasListener() {
        return splitBookListener != null;
    }
}
