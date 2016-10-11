package com.hiepkhach9x.baseTruyenHK.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hiepkhach9x.baseTruyenHK.database.BookDbHelper;
import com.hiepkhach9x.baseTruyenHK.database.DbConstants;
import com.hiepkhach9x.baseTruyenHK.utils.Config;

/**
 * Created by HungHN on 10/11/16.
 */
public class BookProvider extends ContentProvider {

    private SQLiteDatabase bookDb;

    public static final String AUTHORITY = "com.book.provider." + Config.BOOK_ID;
    public static final String URI = "content://" + AUTHORITY + DbConstants.KEY_FORWARD_SLASH + DbConstants.KEY_TABLE_PAGE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URI);

    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;

    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DbConstants.KEY_TABLE_PAGE_NAME, TYPE_ONE);
        sUriMatcher.addURI(AUTHORITY, DbConstants.KEY_TABLE_PAGE_NAME + DbConstants.KEY_HASHTAG, TYPE_TWO);
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
            default:
                throw new IllegalArgumentException("can not support URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        long rowId = bookDb.insert(DbConstants.KEY_TABLE_PAGE_NAME, null, contentValues);
        if (rowId > -1) {
            Uri localUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(localUri, null);
            }
            return localUri;
        }
        throw new SQLException("failed to add to a record into " + uri);
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
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
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
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        int count = bookDb.update(DbConstants.KEY_TABLE_PAGE_NAME, contentValues, selection, selectionArgs);
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }
}
