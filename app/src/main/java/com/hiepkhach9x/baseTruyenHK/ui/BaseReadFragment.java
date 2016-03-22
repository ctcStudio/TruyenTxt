package com.hiepkhach9x.baseTruyenHK.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.ProcessSplitBookTask;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;
import com.hiepkhach9x.truyentxt.R;
import com.hiepkhach9x.truyentxt.ui.MainActivity;
import com.hiepkhach9x.truyentxt.utils.BookPreferences;

import java.util.List;

/**
 * Created by HungHN on 2/20/2016.
 */
public class BaseReadFragment extends Fragment {

    protected String mPageData;
    protected Setting mSetting;
    private ProgressBar mLoading;
    private ProgressDialog mDialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoading = (ProgressBar) view.findViewById(R.id.loading);
        mSetting = BookPreferences.getInstance().getSetting();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void showLoading() {
        if (mLoading != null) {
            mLoading.setVisibility(View.VISIBLE);
        }
    }

    protected void showDialogLoading() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage("Loading...");
            mDialog.setCanceledOnTouchOutside(true);
        }

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
}
