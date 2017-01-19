package io.vtown.WuMaiApp.constant;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vtown.WuMaiApp.Utilss.StrUtils;
import io.vtown.WuMaiApp.module.cites.BLSearchResultCites;

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
    /*
    * 保存搜索城市历史记录
    * */
    private static String Sp_Save_History_Search = "city_history_search";

    /*
    * 保存城市数据
    * */
    private static final String Sp_Save_City = "save_city";
    /**
     * 商品详情的保存
     */
    private static final String Sp_CityDetail = "citydetail_sp";


    /**
     * 先保存第一次定位的城市
     */
    public static void BaiDuMap_Location_Save(Context xx, BLSearchResultCites MapLocation) {
        SharedPreferences sp = xx.getSharedPreferences(Sp_SaveBaiDuLoaction,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("map_location", JSON.toJSONString(MapLocation));
        editor.commit();
    }

    /**
     * 获取第一个定位的城市
     */
    public static BLSearchResultCites BaiDuMap_Location_Get(Context xx) {
        SharedPreferences sp = xx.getSharedPreferences(Sp_SaveBaiDuLoaction,
                Context.MODE_PRIVATE);
        String Str = sp.getString("map_location", null);
        if (StrUtils.isEmpty(Str)) return null;
        return JSON.parseObject(Str, BLSearchResultCites.class);
    }

    /**
     * 获取所有的城市列表
     */
    public static List<BLSearchResultCites> AllCity_Get(Context XX) {
        List<BLSearchResultCites> alldatas = new ArrayList<>();
        alldatas.add(BaiDuMap_Location_Get(XX));
        alldatas.addAll(Location_City_Get(XX));
        return alldatas;
    }

    /**
     * 获取搜索城市历史记录
     *
     * @param context
     * @return
     */
    public static List<BLSearchResultCites> Search_City_History_Get(Context context) {
        List<BLSearchResultCites> datalist = new ArrayList<BLSearchResultCites>();
        SharedPreferences Sp = context.getSharedPreferences(
                Sp_Save_History_Search, Context.MODE_PRIVATE);
        String history_search = Sp.getString("history_search", "");
        if (StrUtils.isEmpty(history_search)) {
            return datalist;
        }
        datalist = JSON.parseArray(history_search, BLSearchResultCites.class);
        return datalist;
    }

    /**
     * 保存搜索城市历史记录
     *
     * @param
     * @param
     */
    public static void Search_City_History_Save(Context pcContext,
                                                List<BLSearchResultCites> cache) {
        String s = "";

        SharedPreferences Sp = pcContext.getSharedPreferences(
                Sp_Save_History_Search, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Sp.edit();
        if(cache.size() > 0){
            s = JSON.toJSONString(cache);
        }
        editor.putString("history_search", s);

        editor.commit();
    }


    /**
     * 获取搜索城市历史记录
     *
     * @param context
     * @return
     */
    public static List<BLSearchResultCites> Location_City_Get(Context context) {
        List<BLSearchResultCites> datalist = new ArrayList<BLSearchResultCites>();
        SharedPreferences Sp = context.getSharedPreferences(
                Sp_Save_City, Context.MODE_PRIVATE);
        String history_search = Sp.getString("locationcity", "");
        if (StrUtils.isEmpty(history_search)) {
            return datalist;
        }
        datalist = JSON.parseArray(history_search, BLSearchResultCites.class);
        return datalist;
    }

    /**
     * 保存搜索城市历史记录
     *
     * @param
     * @param
     */
    public static void Location_City_Save(Context pcContext,
                                          List<BLSearchResultCites> cache) {
        SharedPreferences Sp = pcContext.getSharedPreferences(
                Sp_Save_City, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Sp.edit();
        String s = JSON.toJSONString(cache);
        editor.putString("locationcity", s);

        editor.commit();
    }

    /**
     * 开始保存详情
     */
    public static void CityDetail_Save(String CityCode, String Detail, Context CX) {
        SharedPreferences Sp = CX.getSharedPreferences(
                Sp_CityDetail, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Sp.edit();
        editor.putString("cachecity" + CityCode, Detail);
        editor.commit();
    }

    public static String CityDetail_Get(String CityCode, Context CX) {
        SharedPreferences Sp = CX.getSharedPreferences(
                Sp_CityDetail, Context.MODE_PRIVATE);
        String citydetail = Sp.getString("cachecity" + CityCode, null);
        return citydetail;

    }
}
