package com.hiepkhach9x.truyentxt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.ProcessSplitBookTask;
import com.hiepkhach9x.baseTruyenHK.task.SplitAndSaveBookTask;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.truyentxt.R;
import com.hiepkhach9x.truyentxt.utils.BookPreferences;
import com.hiepkhach9x.baseTruyenHK.utils.Config;
import com.hiepkhach9x.baseTruyenHK.utils.Constants;
import com.hiepkhach9x.truyentxt.utils.LogUtils;

import java.util.List;

/**
 * Created by HungHN on 2/20/2016
 */
public class SplashActivity extends AppCompatActivity implements SplitBookListener {

    private ProgressBar mLoading;
    private BookData mBookData;
    private Setting mSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);

        mSetting = BookPreferences.getInstance().getSetting();
        if (mSetting == null) {
            mSetting = new Setting();
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            float textSize = getResources().getDimension(R.dimen.text_size_small);
            LogUtils.d("Text size: " + textSize);
            mSetting.setTextSize(textSize);
            mSetting.setWidth(width);
            mSetting.setHeight(height);
            BookPreferences.getInstance().saveSetting(mSetting);
        }

        mLoading = (ProgressBar) findViewById(R.id.loading);
        mBookData = new BookData();
        mBookData.setTitle("Tru Tien");
        mBookData.setAuthor("Tieu Dinh");
        String filePath = Config.BOOK_FOLDER + Constants.SEPARATOR + Config.BOOK_NAME;
        mBookData.setPath(filePath);
        SplitAndSaveBookTask processSplitBookTask = new SplitAndSaveBookTask(this);
        processSplitBookTask.setSplitBookListener(this);
        processSplitBookTask.execute(filePath);
    }

    @Override
    public void splitBookStart() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void splitBookProcessUpdatePercent(int percent) {

    }

    @Override
    public void splitBookFinish(List<String> lstPage) {
        mLoading.setVisibility(View.GONE);
        LogUtils.d("Size page: " + lstPage.size());
        mBookData.setPages(lstPage);
        Constants.BOOK_DATA_APP.put(Constants.KEY_BOOK_CONTENT, mBookData);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void splitBookError(List<String> lstPage) {
        mLoading.setVisibility(View.GONE);
        mBookData.setPages(lstPage);
        Constants.BOOK_DATA_APP.put(Constants.KEY_BOOK_CONTENT, mBookData);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
