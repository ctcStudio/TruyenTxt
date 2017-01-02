package com.hiepkhach9x.baseTruyenHK.read_listener.impl;

import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.read_listener.ReadBookListener;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.truyentxt.utils.BookPreferences;

import java.util.ArrayList;

/**
 * Created by hungh on 1/2/2017.
 */

public class EpubReadBook implements ReadBookListener {

    private SplitBookListener splitBookListener;
    private Setting setting;

    public EpubReadBook(SplitBookListener listener) {
        this.splitBookListener = listener;
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
    }

    @Override
    public void syncSettingBook(Setting setting,String book) {
    }

    @Override
    public void cancel() {

    }
}
