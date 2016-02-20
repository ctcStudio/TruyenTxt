package com.hiepkhach9x.baseTruyenHK.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.ProcessSplitBookTask;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.truyentxt.R;

import java.util.List;

/**
 * Created by HungHN on 2/20/2016.
 */
public class BaseReadFragment extends Fragment implements SplitBookListener {

    protected BookData mBookData;
    protected Setting mSetting;
    private ProgressBar mLoading;
    private ProgressDialog mDialog;
    private ProcessSplitBookTask splitBookTask;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoading = (ProgressBar) view.findViewById(R.id.loading);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading...");
        mDialog.setCanceledOnTouchOutside(true);
    }

    protected void showLoading() {
        if (mLoading != null) {
            mLoading.setVisibility(View.VISIBLE);
        }
    }

    protected void showDialogLoading() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    protected void hideLoading() {
        if (mLoading != null) {
            mLoading.setVisibility(View.GONE);
        }
    }

    protected void hideDialogLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    protected void makeSplitBookToPage() {
        if(splitBookTask != null) {
            switch (splitBookTask.getStatus()){
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

    @Override
    public void splitBookStart() {

    }

    @Override
    public void splitBookProcessUpdatePercent(int percent) {

    }

    @Override
    public void splitBookFinish(List<String> lstPage) {

    }

    @Override
    public void splitBookError(List<String> lstPage) {

    }
}
