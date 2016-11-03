package com.hiepkhach9x.baseTruyenHK.task;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.Pair;

import com.hiepkhach9x.baseTruyenHK.contentProvider.BookProvider;
import com.hiepkhach9x.baseTruyenHK.database.DbConstants;
import com.hiepkhach9x.baseTruyenHK.entities.Page;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.baseTruyenHK.utils.FileUtils;
import com.hiepkhach9x.baseTruyenHK.utils.Utilities;
import com.hiepkhach9x.truyentxt.utils.BookPreferences;
import com.hiepkhach9x.truyentxt.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HungHN on 2/20/2016.
 */
public class SplitAndSaveBookTask extends AsyncTask<String, Integer, List<String>> {

    private SplitBookListener splitBookListener;
    private Setting setting;
    private Context context;
    private ContentResolver contentResolver;

    public SplitAndSaveBookTask(Context context,Setting setting) {
        this.setting = setting;
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (hasListener()) {
            splitBookListener.splitBookStart();
        }
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        List<String> loadFromData = getDataBook();
        Setting loadDataSetting =  getSettingBook();
        if((loadDataSetting != null)
                && (loadDataSetting.equals(setting))
                && (loadFromData.size() > 0)) {
            return loadFromData;
        } else {
            contentResolver.delete(BookProvider.CONTENT_URI_DATA, null, null);
            contentResolver.update(BookProvider.CONTENT_URI_SETTING,Utilities.genValueFromSetting(setting),null,null);
        }
        String filePath = strings[0];
        long time = System.currentTimeMillis();
        ArrayList<StringBuilder> dataBookRead = FileUtils.readDataFileFromAsset(filePath);
        StringBuilder builder = new StringBuilder();
        LogUtils.d("Load text: " + (System.currentTimeMillis() - time));
        ArrayList<String> lstSplitText = new ArrayList<>();
        if(dataBookRead.size() > 0) {
            int count = 0;
            StringBuilder temp = new StringBuilder("");
            while (count < dataBookRead.size()) {
                temp = new StringBuilder();
                builder.append(dataBookRead.get(count));
                int offsetI = 0;
                int offsetII;
                int contentLength = builder.length();
                StaticLayout layout = new StaticLayout(builder, setting.getTextPaint(), setting.getWidth() - (setting.getHorizontalPading()), Layout.Alignment.ALIGN_NORMAL, setting.getSpacingMult(), setting.getSpacingAdd(), false);
                int totalLines = layout.getLineCount();
                int linePerPage = layout.getLineForVertical(setting.getHeight() - setting.getVerticalPadding()) - 1;
                int i = 0;
                do {
                    int line;
                    if(linePerPage * (i + 1) <= totalLines -1) {
                        line = linePerPage * (i + 1);
                        offsetII = layout.getOffsetForHorizontal(line, setting.getWidth() - setting.getHorizontalPading());
                        String sub = builder.substring(offsetI, offsetII).trim();
                        offsetI = offsetII;
                        if(offsetII < contentLength) {
                            lstSplitText.add(sub);
                            ContentValues values = new ContentValues();
                            values.put(DbConstants.KEY_COL_ID,i);
                            values.put(DbConstants.KEY_COL_DATA,sub);
                            if(!isExist(i)) {
                                contentResolver.insert(BookProvider.CONTENT_URI_DATA, values);
                            } else {
                                String whereClause = DbConstants.KEY_COL_ID + "=?";
                                String[] whereArgs = new String[]{String.valueOf(i)};
                                contentResolver.update(BookProvider.CONTENT_URI_DATA,values,whereClause,whereArgs);
                            }
                        } else {
                            temp.append(sub);
                        }
                    } else {
                        temp.append(builder.substring(offsetI,contentLength));
                        offsetII = contentLength;
                    }
                    i++;
                    if (isCancelled()) {
                        return lstSplitText;
                    }
                } while ((offsetII < contentLength));
                builder = new StringBuilder();
                builder.append(temp);
                count ++;
            }
            lstSplitText.add(temp.toString());
        }

        LogUtils.d("split page: " + (System.currentTimeMillis() - time));
        return lstSplitText;
    }

    boolean isExist(int id) {
        String selections = DbConstants.KEY_COL_ID + "=?";
        String[] selectArgs = new String[]{String.valueOf(id)};
        Cursor cursor = contentResolver.query(BookProvider.CONTENT_URI_DATA,
                null, selections, selectArgs, null);
        boolean isExist = false;
        if (cursor != null && cursor.getCount() > 0) {
            isExist = true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return isExist;
    }

    private List<String> getDataBook() {
        ArrayList<String> lstSplitText = new ArrayList<>();
        Cursor cursor = contentResolver.query(BookProvider.CONTENT_URI_DATA,null,null,null,DbConstants.KEY_COL_ID + " ASC");
        if(cursor !=null) {
            while (cursor.moveToNext()) {
                Page page = Utilities.genPageFromCursor(cursor);
                lstSplitText.add(page.getData());
            }
            cursor.close();
        }
        return lstSplitText;
    }

    private Setting getSettingBook() {
        Setting setting = null;
        Cursor cursor = contentResolver.query(BookProvider.CONTENT_URI_SETTING,null,null,null,null);
        if(cursor !=null) {
            cursor.moveToFirst();
            setting = Utilities.genSettingFromCursor(cursor);
            cursor.close();
        }
        return setting;
    }


    @Override
    protected void onCancelled(List<String> strings) {
        if (hasListener()) {
            splitBookListener.splitBookError(strings);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (hasListener()) {
            splitBookListener.splitBookProcessUpdatePercent(values[0]);
        }
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        if (hasListener()) {
            splitBookListener.splitBookFinish(strings);
        }
    }

    public void setSplitBookListener(SplitBookListener splitBookListener) {
        this.splitBookListener = splitBookListener;
    }

    public void removeSplitBookListener() {
        this.splitBookListener = null;
    }

    private boolean hasListener() {
        return splitBookListener != null;
    }
}
