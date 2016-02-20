package com.hiepkhach9x.baseTruyenHK.task.implement;

import java.util.List;

/**
 * Created by HungHN on 2/20/2016.
 */
public interface SplitBookListener {
    public void splitBookStart();
    public void splitBookProcessUpdatePercent(int percent);
    public void splitBookFinish(List<String> lstPage);
    public void splitBookError(List<String> lstPage);
}
