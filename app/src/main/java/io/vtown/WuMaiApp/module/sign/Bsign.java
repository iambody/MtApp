package io.vtown.WuMaiApp.module.sign;

import io.vtown.WuMaiApp.module.BBase;

/**
 * Created by datutu on 2017/1/13.
 */

public class Bsign extends BBase {
    private String id;
    private String title;

    public Bsign() {
    }

    public Bsign(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
