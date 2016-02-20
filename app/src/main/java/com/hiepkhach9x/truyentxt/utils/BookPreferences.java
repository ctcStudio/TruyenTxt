package com.hiepkhach9x.truyentxt.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hiepkhach9x.truyentxt.BookApplication;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;

/**
 * Created by HungHN on 2/20/2016.
 */
public class BookPreferences {
    private static final String TAG = "BookPreferences";
    private static final String BOOK_PREFERENCE = "com.hiepkhach9x.baseTruyenHK.BookPreferences";
    private static final String KEY_SETTING = "key_setting";
    private static Context mContext;
    private Gson mGson = new Gson();

    public BookPreferences() {
        this.mContext = BookApplication.get();
    }

    public static BookPreferences getInstance() {
        return new BookPreferences();
    }

    private SharedPreferences.Editor getEditor() {
        if (mContext == null) {
            return null;
        }
        return mContext.getSharedPreferences(BOOK_PREFERENCE,
                Context.MODE_PRIVATE).edit();
    }

    private SharedPreferences getSharedPreferences() {
        if (mContext == null) {
            return null;
        }
        return mContext.getSharedPreferences(BOOK_PREFERENCE,
                Context.MODE_PRIVATE);
    }

    public void registerOnChange(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        if (listener != null) {
            getSharedPreferences().registerOnSharedPreferenceChangeListener(
                    listener);
        }
    }

    public void unregisterOnChange(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        if (listener != null) {
            getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                    listener);
        }
    }

    public void apply() {
        getEditor().apply();
    }

    public void clear() {
        getEditor().clear().commit();
    }

    public boolean saveSetting(Setting bookSetting) {
        String strSetting = mGson.toJson(bookSetting);
        return getEditor().putString(KEY_SETTING, strSetting).commit();
    }

    public Setting getSetting() {
        String strSetting = getSharedPreferences().getString(KEY_SETTING, "");
        Setting bookSetting = mGson.fromJson(strSetting, Setting.class);
        return bookSetting;
    }
}
