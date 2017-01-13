package io.vtown.WuMaiApp.module;

/**
 * Created by datutu on 2017/1/13.
 */

public class BAqi extends BBase {
    private String aqi;//": 40,
    private String p25;//": 28,
    private String api_level;//": 0,
    private String hour;//": "现在"

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getP25() {
        return p25;
    }

    public void setP25(String p25) {
        this.p25 = p25;
    }

    public String getApi_level() {
        return api_level;
    }

    public void setApi_level(String api_level) {
        this.api_level = api_level;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
