package com.hiepkhach9x.baseTruyenHK.read_listener.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.read_listener.ReadBookListener;
import com.hiepkhach9x.baseTruyenHK.task.SplitAndSaveBookTask;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.truyentxt.utils.BookPreferences;

/**
 * Created by hungh on 1/2/2017.
 */

public class TxtReadBook implements ReadBookListener {
    private Context context;
    private SplitBookListener splitBookListener;
    private Setting setting;
    private SplitAndSaveBookTask processSplitBookTask;

    public TxtReadBook(Context context) {
        this.context = context;
        if (context instanceof SplitBookListener) {
            this.splitBookListener = (SplitBookListener) context;
        }
        this.setting = BookPreferences.getInstance().getSetting();
    }

    public void setSplitBookListener(SplitBookListener splitBookListener) {
        this.splitBookListener = splitBookListener;
    }

    @Override
    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    @Override
    public void readDataBook(String book) {
        if (processSplitBookTask != null
                && processSplitBookTask.getStatus() == AsyncTask.Status.RUNNING) {
            return;
        }
        processSplitBookTask = new SplitAndSaveBookTask(context, setting);
        processSplitBookTask.setSplitBookListener(splitBookListener);
        processSplitBookTask.execute(book);
    }

    @Override
    public void syncSettingBook(Setting setting, String book) {
        this.setting = setting;
        if (processSplitBookTask != null
                && processSplitBookTask.getStatus() == AsyncTask.Status.RUNNING) {
            return;
        }
        processSplitBookTask = new SplitAndSaveBookTask(context, setting);
        processSplitBookTask.setSplitBookListener(splitBookListener);
        processSplitBookTask.execute(book);
    }

    @Override
    public void cancel() {
        if (processSplitBookTask != null
                && processSplitBookTask.getStatus() == AsyncTask.Status.RUNNING) {
            processSplitBookTask.cancel(true);
        }
    }
}
