package io.vtown.WuMaiApp.module;

/**
 * Created by datutu on 2017/1/13.
 */

public class BAqi extends BBase {
    private String aqi;//": 40,

    private int aqi_level;//": 0,
    private String hour;//": "现在"

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }


    public int getAqi_level() {
        return aqi_level;
    }

    public void setAqi_level(int aqi_level) {
        this.aqi_level = aqi_level;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
