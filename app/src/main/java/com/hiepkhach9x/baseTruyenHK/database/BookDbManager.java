package com.hiepkhach9x.baseTruyenHK.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hiepkhach9x.baseTruyenHK.entities.Page;
import com.hiepkhach9x.baseTruyenHK.BookApplication;

import java.util.ArrayList;


public class BookDbManager {
    private SQLiteDatabase mSqLiteDatabase;
    private BookDbHelper mDbHelper;
    private static BookDbManager mDbManager;

    private BookDbManager(Context context) {
        mDbHelper = new BookDbHelper(context);
    }

    public static BookDbManager getInstance() {
        if (mDbManager == null) {
            mDbManager = new BookDbManager(BookApplication.get());
        }

        return mDbManager.open();
    }

    public BookDbManager open() {
        mSqLiteDatabase = mDbHelper.getWritableDatabase();
        return this;
    }


    public synchronized boolean isExist(int id) {
        String selections = DbConstants.KEY_COL_ID + "=?";
        String[] selectArgs = new String[]{String.valueOf(id)};
        Cursor cursor = mSqLiteDatabase.query(DbConstants.KEY_TABLE_PAGE_NAME,
                null, selections, selectArgs, null, null, null);
        boolean isExist = false;
        if (cursor != null && cursor.getCount() > 0) {
            isExist = true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return isExist;
    }

    public synchronized boolean create(Page page) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.KEY_COL_ID, page.getId());
        values.put(DbConstants.KEY_COL_DATA, page.getData());

        long rowId = mSqLiteDatabase.insert(DbConstants.KEY_TABLE_PAGE_NAME, null,
                values);
        return rowId > 0;
    }

    public synchronized boolean update(Page page) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.KEY_COL_DATA, page.getData());
        String whereClause = DbConstants.KEY_COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(page.getId())};

        long rowId = mSqLiteDatabase.update(DbConstants.KEY_TABLE_PAGE_NAME, values,whereClause,
                whereArgs);
        return rowId > 0;
    }

    public synchronized Cursor getCursorAllPage() {
        Cursor cursor = mSqLiteDatabase.query(DbConstants.KEY_TABLE_PAGE_NAME,
                null, null, null, null, null, null);
        return cursor;
    }

    public synchronized ArrayList<Page> getListPage() {
        Cursor cursor = getCursorAllPage();

        ArrayList<Page> listPage = null;
        if (cursor != null) {
            listPage = new ArrayList<>();
            while (cursor.moveToNext()) {
                Page page = genPageFromCursor(cursor);
                listPage.add(page);
            }
            cursor.close();
        }
        return listPage;
    }

    public synchronized boolean delete(int id) {
        String whereClause = DbConstants.KEY_COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        long rowId = mSqLiteDatabase.delete(DbConstants.KEY_TABLE_PAGE_NAME,whereClause,
                whereArgs);
        return rowId > 0;
    }

    private Page genPageFromCursor(Cursor cursor) {
        Page page = new Page();
        int idIndex = cursor.getColumnIndex(DbConstants.KEY_COL_ID);
        page.setId(cursor.getInt(idIndex));
        int dataIndex = cursor.getColumnIndex(DbConstants.KEY_COL_DATA);
        page.setData(cursor.getString(dataIndex));
        return page;
    }

}