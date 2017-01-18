package io.vtown.WuMaiApp.module.cites;

import io.vtown.WuMaiApp.module.BBase;

/**
 * Created by Yihuihua on 2017/1/17.
 */

public class BLSearchResultCites extends BBase {
    private String areaname;//"areaname":"保定高碑店",
    private String areaid;// "areaid":101090221

    private int aqi;//"aqi":34,
    private int aqi_level;//      "aqi_level":0,
    private String aqi_detail;//   "aqi_detail":"优",

    public BLSearchResultCites(){
        super();
    }

    public BLSearchResultCites(String areaname, String areaid) {
        this.areaname = areaname;
        this.areaid = areaid;
    }


    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public int getAqi_level() {
        return aqi_level;
    }

    public void setAqi_level(int aqi_level) {
        this.aqi_level = aqi_level;
    }

    public String getAqi_detail() {
        return aqi_detail;
    }

    public void setAqi_detail(String aqi_detail) {
        this.aqi_detail = aqi_detail;
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
