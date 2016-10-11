package com.hiepkhach9x.baseTruyenHK.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.truyentxt.R;
import com.hiepkhach9x.baseTruyenHK.utils.Constants;

/**
 * Created by HungHN on 2/20/2016.
 */
public abstract class BaseReadActivity extends AppCompatActivity implements SplitBookListener {
    protected BookData mBookData;
    protected Setting mSetting;
    private ProgressBar mLoading;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBookData = Constants.BOOK_DATA_APP.get(Constants.KEY_BOOK_CONTENT);
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
}