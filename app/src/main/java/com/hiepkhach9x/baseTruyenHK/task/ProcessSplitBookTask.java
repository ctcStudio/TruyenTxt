package com.hiepkhach9x.baseTruyenHK.task;

import android.os.AsyncTask;

import com.hiepkhach9x.baseTruyenHK.entities.BookData;
import com.hiepkhach9x.baseTruyenHK.entities.Setting;
import com.hiepkhach9x.baseTruyenHK.task.implement.SplitBookListener;

import java.util.List;
import java.util.Set;

/**
 * Created by HungHN on 2/20/2016.
 */
public class ProcessSplitBookTask extends AsyncTask<BookData,Integer,List<String>> {

    private SplitBookListener splitBookListenner;
    private Setting setting;

    public ProcessSplitBookTask(Setting setting){
        this.setting = setting;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(BookData... bookDatas) {
        return null;
    }

    @Override
    protected void onCancelled(List<String> strings) {
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
    }

    public void setSplitBookListenner(SplitBookListener splitBookListenner) {
        this.splitBookListenner = splitBookListenner;
    }

    public void removeSplitBookListenner(){
        this.splitBookListenner = null;
    }
}
