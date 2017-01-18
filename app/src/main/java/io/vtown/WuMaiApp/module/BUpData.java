package io.vtown.WuMaiApp.module;

/**
 * Created by datutu on 2017/1/18.
 */

public class BUpData extends BBase {
    private int code;// ": 1,
    private String version;// ": "2.0.1",
    private String desc;// ": "升级",
    private int status;// ": 1,
    private String url;// ": "http://dev.vt.www.v-town.cn/vtown_2.0.1.apk"


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
