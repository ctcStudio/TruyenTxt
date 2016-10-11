package com.hiepkhach9x.baseTruyenHK.task;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.Layout;
import android.text.StaticLayout;

import com.hiepkhach9x.baseTruyenHK.contentProvider.BookProvider;
import com.hiepkhach9x.baseTruyenHK.database.DbConstants;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.baseTruyenHK.utils.FileUtils;
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

    public SplitAndSaveBookTask(Context context) {
        setting = BookPreferences.getInstance().getSetting();
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
        String filePath = strings[0];
        long time = System.currentTimeMillis();
        StringBuilder content = FileUtils.readFileFromAsset(filePath);
        LogUtils.d("Load text: " + (System.currentTimeMillis() - time));
        ArrayList<String> lstSplitText = new ArrayList<>();
        int offsetI = 0;
        int offsetII = 0;
        String contentSplit = "";

        StaticLayout layout = new StaticLayout(content, setting.getTextPaint(), setting.getWidth() - (setting.getHorizontalPading()), Layout.Alignment.ALIGN_NORMAL, setting.getSpacingMult(), setting.getSpacingAdd(), false);
        int totalLines = layout.getLineCount();
        int linePerPage = layout.getLineForVertical(setting.getHeight() - setting.getVerticalPadding()) - 1;
        int i = 0;
        LogUtils.d("get Line per page: " + (System.currentTimeMillis() - time));
        do {
            int line = Math.min(linePerPage * (i + 1), totalLines - 1);
            offsetII = layout.getOffsetForHorizontal(line, setting.getWidth() - setting.getHorizontalPading());
            String sub = content.substring(offsetI, offsetII).trim();
            offsetI = offsetII;
            lstSplitText.add(sub);
            ContentValues values = new ContentValues();
            values.put(DbConstants.KEY_COL_ID,i);
            values.put(DbConstants.KEY_COL_DATA,sub);
            if(!isExist(i)) {
                contentResolver.insert(BookProvider.CONTENT_URI, values);
            } else {
                String whereClause = DbConstants.KEY_COL_ID + "=?";
                String[] whereArgs = new String[]{String.valueOf(i)};
                contentResolver.update(BookProvider.CONTENT_URI,values,whereClause,whereArgs);
            }
            i++;
            if (isCancelled()) {
                return lstSplitText;
            }
        } while (offsetII < content.length());
        LogUtils.d("split page: " + (System.currentTimeMillis() - time));
        return lstSplitText;
    }

    boolean isExist(int id) {
        String selections = DbConstants.KEY_COL_ID + "=?";
        String[] selectArgs = new String[]{String.valueOf(id)};
        Cursor cursor = contentResolver.query(BookProvider.CONTENT_URI,
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
