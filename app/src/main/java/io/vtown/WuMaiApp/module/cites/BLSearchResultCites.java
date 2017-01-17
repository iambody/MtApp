package io.vtown.WuMaiApp.module.cites;

import io.vtown.WuMaiApp.module.BBase;

/**
 * Created by Yihuihua on 2017/1/17.
 */

public class BLSearchResultCites extends BBase {
    private String areaname;//"areaname":"保定高碑店",
    private String areaid;// "areaid":101090221

    public BLSearchResultCites(){
        super();
    }

    public BLSearchResultCites(String areaname, String areaid) {
        this.areaname = areaname;
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
}
