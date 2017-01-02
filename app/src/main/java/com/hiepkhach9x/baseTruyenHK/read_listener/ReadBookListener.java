package com.hiepkhach9x.baseTruyenHK.read_listener;

import com.hiepkhach9x.baseTruyenHK.entities.Setting;

import java.util.ArrayList;

/**
 * Created by hungh on 1/1/2017.
 */

public interface ReadBookListener {

    void setSetting(Setting setting);

    void readDataBook(String book);

    void syncSettingBook(Setting setting,String book);

    void cancel();
}
