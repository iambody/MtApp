package io.vtown.WuMaiApp.module;

/**
 * Created by datutu on 2017/1/16.
 */

public class BMessage extends BBase {
    private int Tage_Message;

    private int HomeScrollY;

    /**
     * fragment里面滑动scrollview时候通知移动距离
     */
    public static final int Tage_HomeScrollY = 10;

    public BMessage() {
    }


    public BMessage(int tage_Message) {
        Tage_Message = tage_Message;
    }

    public int getTage_Message() {
        return Tage_Message;
    }

    public void setTage_Message(int tage_Message) {
        Tage_Message = tage_Message;
    }

    public int getHomeScrollY() {
        return HomeScrollY;
    }

    public void setHomeScrollY(int homeScrollY) {
        HomeScrollY = homeScrollY;
    }
}
