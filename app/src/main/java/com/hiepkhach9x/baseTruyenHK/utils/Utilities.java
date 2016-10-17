package com.hiepkhach9x.baseTruyenHK.utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.hiepkhach9x.baseTruyenHK.database.DbConstants;
import com.hiepkhach9x.baseTruyenHK.entities.Page;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;

/**
 * Created by HungHN on 10/17/16.
 */
public class Utilities {

    public static Page genPageFromCursor(Cursor cursor) {
        Page page = new Page();
        int idIndex = cursor.getColumnIndex(DbConstants.KEY_COL_ID);
        if (idIndex >= 0)
            page.setId(cursor.getInt(idIndex));
        int dataIndex = cursor.getColumnIndex(DbConstants.KEY_COL_DATA);
        if (dataIndex >= 0)
            page.setData(cursor.getString(dataIndex));
        return page;
    }

    public static Setting genSettingFromCursor(Cursor cursor) {
        Setting setting = new Setting();
        int widthIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_WIDTH);
        if (widthIndex >= 0)
            setting.setWidth(cursor.getInt(widthIndex));

        int heightIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_HEIGHT);
        if (heightIndex >= 0)
            setting.setHeight(cursor.getInt(heightIndex));

        int textSizeIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_TEXT_SIZE);
        if (textSizeIndex >= 0)
            setting.setTextSize(cursor.getFloat(textSizeIndex));

        int textColorIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_TEXT_COLOR);
        if (textColorIndex >= 0)
            setting.setTextColor(cursor.getInt(textColorIndex));

        int pageColorIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_PAGE_COLOR);
        if (pageColorIndex >= 0)
            setting.setPageColor(cursor.getInt(pageColorIndex));

        int horPaddingIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_HOR_PADDING);
        if (horPaddingIndex >= 0)
            setting.setHorizontalPading(cursor.getInt(horPaddingIndex));

        int verPaddingIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_VER_PADDING);
        if (verPaddingIndex >= 0)
            setting.setVerticalPadding(cursor.getInt(verPaddingIndex));

        int spaceAddIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_SPACE_ADD);
        if (spaceAddIndex >= 0)
            setting.setSpacingAdd(cursor.getFloat(spaceAddIndex));

        int spaceMulIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_SPACE_MUL);
        if (spaceMulIndex >= 0)
            setting.setSpacingMult(cursor.getFloat(spaceMulIndex));

        int fontIndex = cursor.getColumnIndex(DbConstants.KEY_COL_SETTING_FONT);
        if (fontIndex >= 0)
            setting.setFont(cursor.getString(fontIndex));
        return setting;
    }

    public static ContentValues genValueFromSetting(Setting setting) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.KEY_COL_SETTING_WIDTH,setting.getWidth());
        contentValues.put(DbConstants.KEY_COL_SETTING_HEIGHT,setting.getHeight());
        contentValues.put(DbConstants.KEY_COL_SETTING_TEXT_SIZE,setting.getTextSize());
        contentValues.put(DbConstants.KEY_COL_SETTING_TEXT_COLOR,setting.getTextColor());
        contentValues.put(DbConstants.KEY_COL_SETTING_PAGE_COLOR,setting.getPageColor());
        contentValues.put(DbConstants.KEY_COL_SETTING_HOR_PADDING,setting.getHorizontalPading());
        contentValues.put(DbConstants.KEY_COL_SETTING_VER_PADDING,setting.getVerticalPadding());
        contentValues.put(DbConstants.KEY_COL_SETTING_SPACE_ADD,setting.getSpacingAdd());
        contentValues.put(DbConstants.KEY_COL_SETTING_SPACE_MUL,setting.getSpacingMult());
        contentValues.put(DbConstants.KEY_COL_SETTING_FONT,setting.getFont());
        return contentValues;
    }
}
