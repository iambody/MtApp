package io.vtown.WuMaiApp.module;

import io.vtown.WuMaiApp.Utilss.SaveUiUtils;

/**
 * Created by datutu on 2017/1/18.
 */

public class BShare extends BBase {
    private String Share_log;
    private String Share_picpath;
    private String title;
    private String content;


    public String getShare_log() {
        return Share_log;
    }

    public void setShare_log(String share_log) {
        Share_log = share_log;
    }

    public String getShare_picpath() {
        return Share_picpath;
    }

    public void setShare_picpath(String share_picpath) {
        Share_picpath = share_picpath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
