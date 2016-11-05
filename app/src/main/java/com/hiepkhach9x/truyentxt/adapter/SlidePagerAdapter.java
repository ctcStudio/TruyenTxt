package com.hiepkhach9x.truyentxt.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hiepkhach9x.truyentxt.ui.ReadFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HungHN on 5/10/2015.
 */
public class SlidePagerAdapter extends FragmentStatePagerAdapter {

    private List<String> listPage = new ArrayList<String>();

    public SlidePagerAdapter(FragmentManager fm, List<String> lst) {
        super(fm);
        listPage = lst;
    }

    public List<String> getListPage() {
        return listPage;
    }

    public void setListPage(List<String> listPage) {
        if (this.listPage != null) {
            this.listPage.clear();
            this.listPage.addAll(listPage);
        } else {
            this.listPage = listPage;
        }
        notifyDataSetChanged();
    }

    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return ReadFragment.newInstance(listPage.get(position));
    }

    @Override
    public int getCount() {
        return listPage.size();
    }
}

