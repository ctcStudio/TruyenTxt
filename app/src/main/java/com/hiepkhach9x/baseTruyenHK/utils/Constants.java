package com.hiepkhach9x.baseTruyenHK.utils;

import android.content.Context;
import android.content.res.Resources;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.truyentxt.R;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by HungHN on 2/20/2016.
 */
public class Constants {

    public static final String TAG = "HungHN";

    public final static String APP_PREFERENCES = "APP_PREFERENCES";

    public final static String DATA_CHAP = "DATA_CHAP";

    public final static String CHAP_NAME = "CHAP_NAME";

    public final static String MAX_CHAP = "MAX_CHAP";

    public final static String DATA_SETTING = "SETTING";

    public final static String DATA_BOOKMARK = "DATA_BOOKMARK";

    public final static int COMPLEX_UNIT_SP = 1;

    public final static int COMPLEX_UNIT_DIP = 2;

    public final static int REAL_POS = 2;

    public static final String SEPARATOR = "/";

    public static final String KEY_BOOK_CONTENT = "key_book_content";

    public static final HashMap<String,BookData> BOOK_DATA_APP = new HashMap<>();

    public static final int KEY_SPACING_MUL_SMALL = 1;
    public static final int KEY_SPACING_MUL_NORMAL = KEY_SPACING_MUL_SMALL + 1;
    public static final int KEY_SPACING_MUL_LARGE = KEY_SPACING_MUL_NORMAL + 1;
    public static final HashMap<Integer,Float> SPACING_MUL_MAP = new HashMap<>();

    public static final int KEY_SPACING_LINE_SMALL = 1;
    public static final int KEY_SPACING_LINE_NORMAL = KEY_SPACING_LINE_SMALL + 1;
    public static final int KEY_SPACING_LINE_LARGE = KEY_SPACING_LINE_NORMAL + 1;
    public static final HashMap<Integer,Float> SPACING_LINE_MAP = new HashMap<>();

    public static final int KEY_SPACING_HOR_SMALL = 1;
    public static final int KEY_SPACING_HOR_NORMAL = KEY_SPACING_HOR_SMALL + 1;
    public static final int KEY_SPACING_HOR_LARGE = KEY_SPACING_HOR_NORMAL + 1;
    public static final HashMap<Integer,Integer> SPACING_HOR_MAP = new HashMap<>();

    public static final int KEY_SPACING_VER_SMALL = 1;
    public static final int KEY_SPACING_VER_NORMAL = KEY_SPACING_VER_SMALL + 1;
    public static final int KEY_SPACING_VER_LARGE = KEY_SPACING_VER_NORMAL + 1;
    public static final HashMap<Integer,Integer> SPACING_VER_MAP = new HashMap<>();

    public static final int KEY_TEXT_SIZE_SMALLER = 1;
    public static final int KEY_TEXT_SIZE_SMALL = KEY_TEXT_SIZE_SMALLER + 1;
    public static final int KEY_TEXT_SIZE_NORMAL = KEY_TEXT_SIZE_SMALL + 1;
    public static final int KEY_TEXT_SIZE_LARGE = KEY_TEXT_SIZE_NORMAL + 1;
    public static final int KEY_TEXT_SIZE_LARGER = KEY_TEXT_SIZE_LARGE + 1;
    public static final HashMap<Integer,Integer> TEXT_SIZE_MAP = new HashMap<>();

    public static final int KEY_FONT_DROID_SANS = 1;
    public static final int KEY_FONT_DROID_SERIF = KEY_FONT_DROID_SANS + 1;
    public static final int KEY_FONT_ROBOTO = KEY_FONT_DROID_SERIF + 1;
    public static final HashMap<Integer,String> FONT_MAP = new HashMap<>();


    static {
        //
        SPACING_MUL_MAP.put(KEY_SPACING_MUL_SMALL,0.75f);
        SPACING_MUL_MAP.put(KEY_SPACING_MUL_NORMAL,1.0f);
        SPACING_MUL_MAP.put(KEY_SPACING_MUL_LARGE,1.25f);
        //
        SPACING_LINE_MAP.put(KEY_SPACING_LINE_SMALL,8.0f);
        SPACING_LINE_MAP.put(KEY_SPACING_LINE_NORMAL,10.0f);
        SPACING_LINE_MAP.put(KEY_SPACING_LINE_LARGE,12.0f);
        //
        SPACING_HOR_MAP.put(KEY_SPACING_HOR_SMALL, R.dimen.common_size_4dp);
        SPACING_HOR_MAP.put(KEY_SPACING_HOR_NORMAL,R.dimen.common_size_6dp);
        SPACING_HOR_MAP.put(KEY_SPACING_HOR_LARGE,R.dimen.common_size_10dp);
        //
        SPACING_VER_MAP.put(KEY_SPACING_VER_SMALL, R.dimen.common_size_4dp);
        SPACING_VER_MAP.put(KEY_SPACING_VER_NORMAL,R.dimen.common_size_6dp);
        SPACING_VER_MAP.put(KEY_SPACING_VER_LARGE,R.dimen.common_size_8dp);
        //
        TEXT_SIZE_MAP.put(KEY_TEXT_SIZE_SMALLER, R.dimen.text_size_smaller);
        TEXT_SIZE_MAP.put(KEY_TEXT_SIZE_SMALL,R.dimen.text_size_small);
        TEXT_SIZE_MAP.put(KEY_TEXT_SIZE_NORMAL,R.dimen.text_size_normal);
        TEXT_SIZE_MAP.put(KEY_TEXT_SIZE_LARGE,R.dimen.text_size_large);
        TEXT_SIZE_MAP.put(KEY_TEXT_SIZE_LARGER,R.dimen.text_size_larger);
        //
        FONT_MAP.put(KEY_FONT_DROID_SANS, "DroidSans.ttf");
        FONT_MAP.put(KEY_FONT_DROID_SERIF,"DroidSerif-Regular.ttf");
        FONT_MAP.put(KEY_FONT_ROBOTO,"Roboto-Regular.ttf");
    }

    public static float getFloatFromDimen(Resources resources, int dimen) {
        return resources.getDimension(dimen);
    }

    public static float getIntFromDimen(Resources resources, int dimen) {
        return resources.getDimensionPixelSize(dimen);
    }
}
