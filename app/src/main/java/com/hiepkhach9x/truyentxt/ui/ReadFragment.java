package com.hiepkhach9x.truyentxt.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiepkhach9x.baseTruyenHK.ui.BaseReadFragment;
import com.hiepkhach9x.baseTruyenHK.view.MyJustifiedTextView;
import com.hiepkhach9x.truyentxt.R;
import com.hiepkhach9x.truyentxt.utils.BookPreferences;
import com.hiepkhach9x.truyentxt.utils.LogUtils;

/**
 * Created by HungHN on 2/20/2016.
 */
public class ReadFragment extends BaseReadFragment {
    private static final String KEY_BOOK_DATA = "key_book_data";

    private MyJustifiedTextView mTxtRead;

    private OnReadCallback onReadCallback;

    public static ReadFragment newInstance(String page) {
        ReadFragment fragment = new ReadFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_BOOK_DATA, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            onReadCallback = (OnReadCallback) activity;
        }
    }

    private void getDataBundle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        mPageData = bundle.getString(KEY_BOOK_DATA);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getDataBundle(savedInstanceState);
        } else {
            getDataBundle(getArguments());
        }
        mSetting = BookPreferences.getInstance().getSetting();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTxtRead = (MyJustifiedTextView) view.findViewById(R.id.read_content);
        mTxtRead.setText(mPageData);
        mTxtRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReadCallback != null) {
                    onReadCallback.onContentClick();
                }
            }
        });
        hideLoading();
    }

    public interface OnReadCallback {
        void onContentClick();
    }
}
