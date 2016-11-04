package com.hiepkhach9x.truyentxt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.SplitAndSaveBookTask;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.baseTruyenHK.utils.Config;
import com.hiepkhach9x.baseTruyenHK.utils.Constants;
import com.hiepkhach9x.truyentxt.R;
import com.hiepkhach9x.truyentxt.utils.BookPreferences;
import com.hiepkhach9x.truyentxt.utils.LogUtils;
import com.victor.loading.book.BookLoading;

import java.util.List;

/**
 * Created by HungHN on 2/20/2016
 */
public class SplashActivity extends AppCompatActivity implements SplitBookListener {

    private BookLoading mLoading;
    private TextView mProcess;
    private BookData mBookData;
    private Setting mSetting;
    SplitAndSaveBookTask processSplitBookTask;

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
            float textSize = Constants.getFloatFromDimen(getResources(),R.dimen.text_size_normal);
            LogUtils.d("Text size: " + textSize);
            mSetting.setTextSize(textSize);
            mSetting.setWidth(width);
            mSetting.setHeight(height);
            int horPadding = getResources().getDimensionPixelSize(Constants.SPACING_HOR_MAP.get(Constants.KEY_SPACING_HOR_NORMAL));
            mSetting.setHorizontalPading(horPadding);
            int verPadding = getResources().getDimensionPixelSize(Constants.SPACING_VER_MAP.get(Constants.KEY_SPACING_VER_NORMAL));
            mSetting.setVerticalPadding(verPadding);

            BookPreferences.getInstance().saveSetting(mSetting);
        }

        mLoading = (BookLoading) findViewById(R.id.book_loading);
        mProcess = (TextView) findViewById(R.id.percent);
        mBookData = new BookData();
        mBookData.setTitle("Tru Tien");
        mBookData.setAuthor("Tieu Dinh");
        String filePath = Config.BOOK_FOLDER + Constants.SEPARATOR + Config.BOOK_NAME;
        mBookData.setPath(filePath);
        processSplitBookTask = new SplitAndSaveBookTask(this,mSetting);
        processSplitBookTask.setSplitBookListener(this);
        processSplitBookTask.execute(filePath);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(processSplitBookTask !=null) {
            processSplitBookTask.cancel(true);
        }
    }

    @Override
    public void splitBookStart() {
        mProcess.setText("0%");
        mLoading.start();
    }

    @Override
    public void splitBookProcessUpdatePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProcess.setText(percent + "%");
            }
        });
    }

    @Override
    public void splitBookFinish(List<String> lstPage) {
        mLoading.stop();
        LogUtils.d("Size page: " + lstPage.size());
        mBookData.setPages(lstPage);
        Constants.BOOK_DATA_APP.put(Constants.KEY_BOOK_CONTENT, mBookData);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void splitBookError(List<String> lstPage) {
        mProcess.setText("100%");
        mLoading.setVisibility(View.GONE);
        mBookData.setPages(lstPage);
        Constants.BOOK_DATA_APP.put(Constants.KEY_BOOK_CONTENT, mBookData);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
