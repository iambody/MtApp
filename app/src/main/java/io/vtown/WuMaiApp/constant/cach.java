package io.vtown.WuMaiApp.constant;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by datutu on 2017/1/17.
 */

public class cach {

    public static void Save(Context CP, String Key, String alee) {
        SharedPreferences sp = CP.getSharedPreferences("sda", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(Key, alee);
        ed.commit();
    }

    public static String Get(Context CP, String Key) {
        SharedPreferences sp = CP.getSharedPreferences("sda", Context.MODE_PRIVATE);
        return sp.getString(Key, "");
    }
}
