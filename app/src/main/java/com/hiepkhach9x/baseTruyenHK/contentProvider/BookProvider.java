package com.hiepkhach9x.baseTruyenHK.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.hiepkhach9x.baseTruyenHK.database.BookDbHelper;
import com.hiepkhach9x.baseTruyenHK.database.DbConstants;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.utils.Config;
import com.hiepkhach9x.baseTruyenHK.utils.Constants;
import com.hiepkhach9x.truyentxt.utils.BookPreferences;

import java.io.IOException;

/**
 * Created by HungHN on 10/11/16.
 */
public class BookProvider extends ContentProvider {

    private SQLiteDatabase bookDb;

    public static final String AUTHORITY = "com.book.provider." + Config.BOOK_ID;
    public static final String URI_DATA = "content://" + AUTHORITY + DbConstants.KEY_FORWARD_SLASH + DbConstants.KEY_TABLE_PAGE_NAME;
    public static final String URI_SETTING = "content://" + AUTHORITY + DbConstants.KEY_FORWARD_SLASH + DbConstants.KEY_TABLE_SETTING_NAME;
    public static final Uri CONTENT_URI_DATA = Uri.parse(URI_DATA);
    public static final Uri CONTENT_URI_SETTING = Uri.parse(URI_SETTING);

    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;

    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DbConstants.KEY_TABLE_PAGE_NAME, TYPE_ONE);
        sUriMatcher.addURI(AUTHORITY, DbConstants.KEY_TABLE_PAGE_NAME + DbConstants.KEY_HASHTAG, TYPE_TWO);
        sUriMatcher.addURI(AUTHORITY, DbConstants.KEY_TABLE_SETTING_NAME, TYPE_THREE);
    }

    @Override
    public boolean onCreate() {
        BookDbHelper dbHelper = new BookDbHelper(getContext());
        bookDb = dbHelper.getReadableDatabase();
        return bookDb != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DbConstants.KEY_TABLE_PAGE_NAME);
        switch (sUriMatcher.match(uri)) {
            case TYPE_ONE:
                break;
            case TYPE_TWO:
                if (TextUtils.isEmpty(uri.getLastPathSegment())) {
                    sqLiteQueryBuilder.appendWhere(DbConstants.KEY_COL_ID + "=" + uri.getLastPathSegment());
                }
                break;
            case TYPE_THREE:
                Setting setting = BookPreferences.getInstance().getSetting();
                if(setting ==null) {
                    return null;
                } else {
                    MatrixCursor settingCursor = new MatrixCursor(DbConstants.SETTING_COL);
                    MatrixCursor.RowBuilder rowBuilder = settingCursor.newRow();
                    rowBuilder.add(setting.getWidth());
                    rowBuilder.add(setting.getHeight());
                    rowBuilder.add(setting.getTextSize());
                    rowBuilder.add(setting.getTextColor());
                    rowBuilder.add(setting.getPageColor());
                    rowBuilder.add(setting.getHorizontalPading());
                    rowBuilder.add(setting.getVerticalPadding());
                    rowBuilder.add(setting.getSpacingAdd());
                    rowBuilder.add(setting.getSpacingMult());
                    rowBuilder.add(setting.getFont());
                    return settingCursor;
                }
            default:
                throw new IllegalArgumentException("unknown URI: " + uri);
        }

        Cursor cursor = null;
        try {
            cursor = sqLiteQueryBuilder.query(bookDb, projection, selection, selectionArgs, null, null, sortOrder);
            if (getContext() != null) {
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case TYPE_ONE:
                return "vnd.android.cursor.dir/" + AUTHORITY;
            case TYPE_TWO:
                return "vnd.android.cursor.item/" + AUTHORITY;
            case TYPE_THREE:
                return "vnd.android.cursor.dir/" + AUTHORITY + DbConstants.KEY_DOT + Config.BOOK_SETTING;
            default:
                throw new IllegalArgumentException("can not support URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        switch (sUriMatcher.match(uri)) {
            case TYPE_ONE:
            case TYPE_TWO:
            long rowId = bookDb.insert(DbConstants.KEY_TABLE_PAGE_NAME, null, contentValues);
            if (rowId > -1) {
                Uri localUri = ContentUris.withAppendedId(CONTENT_URI_DATA, rowId);
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(localUri, null);
                }
                return localUri;
            }
                Log.e ("BookProvider", "failed to add to a record into " + uri);
            case TYPE_THREE:
                Setting setting = BookPreferences.getInstance().getSetting();
                if(setting !=null) {
                    if (getContext() != null) {
                        getContext().getContentResolver().notifyChange(CONTENT_URI_SETTING, null);
                    }
                    return CONTENT_URI_SETTING;
                }

                Log.e("settingProvider","failed to save to a setting into " + uri);
            default:
                throw new IllegalArgumentException("unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case TYPE_ONE:
                break;
            case TYPE_TWO:
                if (TextUtils.isEmpty(uri.getLastPathSegment())) {
                    if (TextUtils.isEmpty(selection)) {
                        selection = DbConstants.KEY_COL_ID + "=" + uri.getLastPathSegment();
                    } else {
                        selection = DbConstants.KEY_COL_ID + "=" + uri.getPathSegments().get(1) +
                                DbConstants.KEY_SPACE + "and" + DbConstants.KEY_SPACE +
                                DbConstants.KEY_BRACKET_OPEN + selection + DbConstants.KEY_BRACKET_CLOSE;
                    }
                }
                break;
            case TYPE_THREE:
                BookPreferences.getInstance().removeSetting();
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return 1;
            default:
                throw new IllegalArgumentException("Unknow URI_DATA: " + uri);
        }

        int count = bookDb.delete(DbConstants.KEY_TABLE_PAGE_NAME, selection, selectionArgs);
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case TYPE_ONE:
                break;
            case TYPE_TWO:
                if (uri.getPathSegments().size() > 0) {
                    if (TextUtils.isEmpty(uri.getLastPathSegment())) {
                        selection = DbConstants.KEY_COL_ID + "=" + uri.getLastPathSegment();
                    } else {
                        selection = DbConstants.KEY_COL_ID + "=" + uri.getPathSegments().get(1) +
                                DbConstants.KEY_SPACE + "and" + DbConstants.KEY_SPACE +
                                DbConstants.KEY_BRACKET_OPEN + selection + DbConstants.KEY_BRACKET_CLOSE;
                    }
                }
                break;
            case TYPE_THREE:
                Setting setting = BookPreferences.getInstance().getSetting();
                if(setting == null) {
                    setting = new Setting();
                }
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_WIDTH))
                    setting.setWidth(contentValues.getAsInteger(DbConstants.KEY_COL_SETTING_WIDTH));
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_HEIGHT))
                    setting.setHeight(contentValues.getAsInteger(DbConstants.KEY_COL_SETTING_HEIGHT));
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_TEXT_SIZE))
                    setting.setTextSize(contentValues.getAsFloat(DbConstants.KEY_COL_SETTING_TEXT_SIZE));
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_TEXT_COLOR))
                    setting.setTextColor(contentValues.getAsInteger(DbConstants.KEY_COL_SETTING_TEXT_COLOR));
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_PAGE_COLOR))
                    setting.setPageColor(contentValues.getAsInteger(DbConstants.KEY_COL_SETTING_PAGE_COLOR));
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_HOR_PADDING))
                    setting.setHorizontalPading(contentValues.getAsInteger(DbConstants.KEY_COL_SETTING_HOR_PADDING));
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_VER_PADDING))
                    setting.setVerticalPadding(contentValues.getAsInteger(DbConstants.KEY_COL_SETTING_VER_PADDING));
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_SPACE_ADD))
                    setting.setSpacingAdd(contentValues.getAsFloat(DbConstants.KEY_COL_SETTING_SPACE_ADD));
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_SPACE_MUL))
                setting.setSpacingMult(contentValues.getAsFloat(DbConstants.KEY_COL_SETTING_SPACE_MUL));
                if (contentValues.containsKey(DbConstants.KEY_COL_SETTING_FONT))
                    setting.setFont(contentValues.getAsString(DbConstants.KEY_COL_SETTING_FONT));

                BookPreferences.getInstance().saveSetting(setting);
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return 1;
            default:
                throw new IllegalArgumentException("Unknown URI_DATA: " + uri);
        }

        int count = bookDb.update(DbConstants.KEY_TABLE_PAGE_NAME, contentValues, selection, selectionArgs);
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }
}
