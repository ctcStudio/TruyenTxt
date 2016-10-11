package com.hiepkhach9x.baseTruyenHK.entities;

/**
 * Created by ntq on 10/11/16.
 */
public class Page {
    private int id;
    private String data;

    public Page() {

    }

    public Page(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
