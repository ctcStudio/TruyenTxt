package com.hiepkhach9x.truyentxt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.ui.BaseReadFragment;
import com.hiepkhach9x.baseTruyenHK.view.MyJustifiedTextView;
import com.hiepkhach9x.truyentxt.R;
import com.hiepkhach9x.truyentxt.utils.BookPreferences;

import java.util.List;

/**
 * Created by HungHN on 2/20/2016.
 */
public class ReadFragment extends BaseReadFragment {
    private static final String KEY_BOOKDATA = "key_bookdata";

    private MyJustifiedTextView mTxtRead;

    public static ReadFragment newInstance(BookData bookData) {
        ReadFragment fragment = new ReadFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BOOKDATA, bookData);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void getDataBundle(Bundle bundle){
        if(bundle == null){
            return;
        }
        mBookData = (BookData)bundle.getSerializable(KEY_BOOKDATA);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            getDataBundle(savedInstanceState);
        } else {
            getDataBundle(getArguments());
        }
        mSetting = BookPreferences.getInstance().getSetting();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTxtRead = (MyJustifiedTextView) view.findViewById(R.id.read_content);
    }
}
