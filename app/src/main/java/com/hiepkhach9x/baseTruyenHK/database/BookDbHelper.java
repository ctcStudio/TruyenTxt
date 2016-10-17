package com.hiepkhach9x.baseTruyenHK.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ntq on 10/11/16.
 */
public class BookDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbBook";

    private static final String BOOK_TABLE_PAGE_CREATE =
            "CREATE TABLE " + DbConstants.KEY_TABLE_PAGE_NAME + DbConstants.KEY_SPACE + DbConstants.KEY_BRACKET_OPEN +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_ID + " INTEGER PRIMARY KEY NOT NULL" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_DATA + " TEXT NOT NULL" +
                    DbConstants.KEY_SPACE + DbConstants.KEY_BRACKET_CLOSE + DbConstants.KEY_SEMICOLON;

    /*
    private static final String BOOK_TABLE_SETTING_CREATE =
            "CREATE TABLE " + DbConstants.KEY_TABLE_SETTING_NAME + DbConstants.KEY_SPACE + DbConstants.KEY_BRACKET_OPEN +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_ID + " INTEGER PRIMARY KEY NOT NULL" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_WIDTH + " INTEGER NOT NULL" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_HEIGHT + " INTEGER NOT NULL" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_TEXT_SIZE + " REAL" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_TEXT_COLOR + " INTEGER" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_PAGE_COLOR + " INTEGER" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_HOR_PADDING + " INTEGER" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_VER_PADDING + " INTEGER" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_SPACE_ADD + " REAL" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_SPACE_MUL + " REAL" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_COL_SETTING_FONT + " TEXT" + DbConstants.KEY_COMMA +
                    DbConstants.KEY_SPACE + DbConstants.KEY_BRACKET_CLOSE + DbConstants.KEY_SEMICOLON;
                    */

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(BOOK_TABLE_PAGE_CREATE);
        //sqLiteDatabase.execSQL(BOOK_TABLE_SETTING_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
