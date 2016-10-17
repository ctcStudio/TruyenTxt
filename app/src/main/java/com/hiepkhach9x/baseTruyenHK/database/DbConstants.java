package com.hiepkhach9x.baseTruyenHK.database;

/**
 * Created by ntq on 10/11/16.
 */
public class DbConstants {

    public static final String KEY_BRACKET_OPEN = "(";
    public static final String KEY_BRACKET_CLOSE = ")";
    public static final String KEY_DOT = ".";
    public static final String KEY_COMMA = ",";
    public static final String KEY_QUESTION = "?";
    public static final String KEY_SPACE = " ";
    public static final String KEY_SEMICOLON = ";";
    public static final String KEY_HASHTAG = "#";
    public static final String KEY_FORWARD_SLASH= "/";

    public static final String KEY_TABLE_PAGE_NAME = "tblPage";
    public static final String KEY_COL_ID = "id";
    public static final String KEY_COL_DATA = "data";

    public static final String KEY_TABLE_SETTING_NAME = "tblSetting";
    public static final String KEY_COL_SETTING_ID = "id";
    public static final String KEY_COL_SETTING_WIDTH = "width";
    public static final String KEY_COL_SETTING_HEIGHT = "height";
    public static final String KEY_COL_SETTING_TEXT_SIZE = "textSize";
    public static final String KEY_COL_SETTING_TEXT_COLOR = "textColor";
    public static final String KEY_COL_SETTING_PAGE_COLOR = "pageColor";
    public static final String KEY_COL_SETTING_HOR_PADDING = "horizontalPadding";
    public static final String KEY_COL_SETTING_VER_PADDING = "verticalPadding";
    public static final String KEY_COL_SETTING_SPACE_ADD = "spaceAdd";
    public static final String KEY_COL_SETTING_SPACE_MUL = "spaceMul";
    public static final String KEY_COL_SETTING_FONT = "font";

    public static final String[] SETTING_COL = new String[]{KEY_COL_SETTING_WIDTH, KEY_COL_SETTING_HEIGHT,
            KEY_COL_SETTING_TEXT_SIZE, KEY_COL_SETTING_TEXT_COLOR, KEY_COL_SETTING_PAGE_COLOR,
            KEY_COL_SETTING_HOR_PADDING, KEY_COL_SETTING_VER_PADDING, KEY_COL_SETTING_SPACE_ADD,
            KEY_COL_SETTING_SPACE_MUL, KEY_COL_SETTING_FONT};
}
