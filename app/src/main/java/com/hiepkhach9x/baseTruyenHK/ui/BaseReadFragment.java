package com.hiepkhach9x.baseTruyenHK.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.truyentxt.R;

/**
 * Created by HungHN on 2/20/2016.
 */
public class BaseReadFragment extends Fragment {

    protected BookData mBookData;
    protected Setting mSetting;
    private ProgressBar mLoading;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoading = (ProgressBar) view.findViewById(R.id.loading);
    }

    protected void showLoading() {
        if (mLoading != null) {
            mLoading.setVisibility(View.VISIBLE);
        }
    }

    protected void hideLoading() {
        if (mLoading != null) {
            mLoading.setVisibility(View.GONE);
        }
    }
}
