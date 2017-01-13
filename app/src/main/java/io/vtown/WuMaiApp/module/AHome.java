package io.vtown.WuMaiApp.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datutu on 2017/1/13.
 */

public class AHome extends BBase {
    private String count;//": 24,
    private String startTime;//": "2017011317",
    private String endTime;//": "2017011416",
    private String reqTime;//": "20170113170549",
    private int aqi_level;//": 0,
    private String aqi;//": 40,
    private String aqi_detail;//": "优",
    private List<BAqi> list = new ArrayList<>();

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public int getAqi_level() {
        return aqi_level;
    }

    public void setAqi_level(int aqi_level) {
        this.aqi_level = aqi_level;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getAqi_detail() {
        return aqi_detail;
    }

    public void setAqi_detail(String aqi_detail) {
        this.aqi_detail = aqi_detail;
    }

    public List<BAqi> getList() {
        return list;
    }

    public void setList(List<BAqi> list) {
        this.list = list;
    }
}
