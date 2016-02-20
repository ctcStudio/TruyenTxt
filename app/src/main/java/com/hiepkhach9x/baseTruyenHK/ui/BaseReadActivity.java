package com.hiepkhach9x.baseTruyenHK.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.ProcessSplitBookTask;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.truyentxt.R;

/**
 * Created by HungHN on 2/20/2016.
 */
public abstract class BaseReadActivity extends AppCompatActivity implements SplitBookListener {
    protected BookData mBookData;
    protected Setting mSetting;
    private ProgressBar mLoading;
    private ProgressDialog mDialog;
    private ProcessSplitBookTask splitBookTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showLoading() {
        if (mLoading == null) {
            mLoading = (ProgressBar) findViewById(R.id.loading);
        }
        mLoading.setVisibility(View.VISIBLE);

    }

    protected void showDialogLoading() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setMessage("Loading...");
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    protected void hideLoading() {
        if (mLoading == null) {
            mLoading = (ProgressBar) findViewById(R.id.loading);
        }
        mLoading.setVisibility(View.GONE);
    }

    protected void hideDialogLoading() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setMessage("Loading...");
        }
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    protected void makeSplitBookToPage() {
        if (splitBookTask != null) {
            switch (splitBookTask.getStatus()) {
                case PENDING:
                    splitBookTask.setSplitBookListenner(this);
                    splitBookTask.execute(mBookData);
                case RUNNING:
                    splitBookTask.cancel(true);
                    splitBookTask = null;
                    splitBookTask = new ProcessSplitBookTask(mSetting);
                    splitBookTask.setSplitBookListenner(this);
                    splitBookTask.execute(mBookData);
                case FINISHED:
                    splitBookTask = new ProcessSplitBookTask(mSetting);
                    splitBookTask.setSplitBookListenner(this);
                    splitBookTask.execute(mBookData);
            }
        } else {
            splitBookTask = new ProcessSplitBookTask(mSetting);
            splitBookTask.setSplitBookListenner(this);
            splitBookTask.execute(mBookData);
        }
    }
}