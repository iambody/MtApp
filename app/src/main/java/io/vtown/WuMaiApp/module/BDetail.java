package io.vtown.WuMaiApp.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datutu on 2017/1/17.
 */

public class BDetail {
    private String date;
    private List<BAqi> list = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BAqi> getList() {
        return list;
    }

    public void setList(List<BAqi> list) {
        this.list = list;
    }
}
