package io.vtown.WuMaiApp.constant;

import android.content.Context;
import android.content.SharedPreferences;

import com.baidu.location.BDLocation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by datutu on 2017/1/13.
 */

public class Spuit {
    public static final int CLZ_BYTE = 1;
    public static final int CLZ_SHORT = 2;
    public static final int CLZ_INTEGER = 3;
    public static final int CLZ_LONG = 4;
    public static final int CLZ_STRING = 5;
    public static final int CLZ_BOOLEAN = 6;
    public static final int CLZ_FLOAT = 7;
    public static final int CLZ_DOUBLE = 8;
    public static final Map<Class<?>, Integer> TYPES;

    static {
        TYPES = new HashMap<Class<?>, Integer>();
        TYPES.put(byte.class, CLZ_BYTE);
        TYPES.put(short.class, CLZ_SHORT);
        TYPES.put(int.class, CLZ_INTEGER);
        TYPES.put(long.class, CLZ_LONG);
        TYPES.put(String.class, CLZ_STRING);
        TYPES.put(boolean.class, CLZ_BOOLEAN);
        TYPES.put(float.class, CLZ_FLOAT);
        TYPES.put(double.class, CLZ_DOUBLE);
    }


    private static String Sp_SaveBaiDuLoaction = "baicu_laoction_sp";


    /**
     * 先保存第一次定位的城市
     *
     * @param xx
     * @param MapLocation
     */
    public static void BaiDuMap_Location_Save(Context xx, BDLocation MapLocation) {
        SharedPreferences sp = xx.getSharedPreferences(Sp_SaveBaiDuLoaction,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Class<? extends BDLocation> clazz = MapLocation.getClass();
        Field[] arrFiled = clazz.getDeclaredFields();
        try {
            for (Field f : arrFiled) {
                int type = TYPES.get(f.getType());
                switch (type) {
                    case CLZ_BYTE:
                    case CLZ_SHORT:
                    case CLZ_INTEGER:
                        editor.putInt(f.getName(), f.getInt(MapLocation));
                        break;
                    case CLZ_LONG:
                        editor.putLong(f.getName(), f.getLong(MapLocation));
                        break;
                    case CLZ_STRING:
                        editor.putString(f.getName(), (String) f.get(MapLocation));
                        break;

                    case CLZ_BOOLEAN:
                        editor.putBoolean(f.getName(), f.getBoolean(MapLocation));
                        break;
                }
            }
            editor.commit();
        } catch (
                IllegalArgumentException e
                ) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    public static void BaiDuMap_Location_Save(Context xx) {
//
//        SharedPreferences sp = xx.getSharedPreferences(Sp_SaveBaiDuLoaction,
//                Context.MODE_PRIVATE);
//        BDLocation BLocation = new BDLocation();
//
//        Class<? extends BDLocation> clazz = BLocation.getClass();
//        Field[] arrFiled = clazz.getDeclaredFields();
//        try {
//            for (Field f : arrFiled) {
////                String str = sp.getString(f.getName(), null);
////                f.set(mBUser, str);
//                int type = TYPES.get(f.getType());
//                switch (type) {
//                    case CLZ_BYTE:
//                    case CLZ_SHORT:
//                    case CLZ_INTEGER:
//                        int str = sp.getInt(f.getName(), 0);
//                        f.set(BLocation, str);
//                        break;
//                    case CLZ_LONG:
//
//                        break;
//                    case CLZ_STRING:
//
//                        break;
//
//                    case CLZ_BOOLEAN:
//
//                        break;
//                }
//
//
//            }
//        } catch (IllegalArgumentException e) {
//        }
//
//    }
}
