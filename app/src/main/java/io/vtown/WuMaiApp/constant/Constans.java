package io.vtown.WuMaiApp.constant;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.vtown.WuMaiApp.constant.encrypt.RSAUtils;
import io.vtown.WuMaiApp.constant.encrypt.StringEncrypt;
import io.vtown.WuMaiApp.module.sign.Bsign;

/**
 * Created by datutu on 2017/1/10.
 */

public class Constans {

    /**
     * 开发环境 的host
     */
    public static String Host = "http://dev.vt.api.v-town.cn";
    /**
     * 测试环境的host
     */
//	 public static String Host = "https://testvtapi.v-town.cc";

    /**
     * 生产环境host
     */
//    public static String Host = "https://api.v-town.cc";


    /**
     * 开发环境key
     */
    public static String SignKey = "GriE93gIGp$5bDjQ4rc20FzxWGghTIau";
    /**
     * 测试环境key
     */
//	 public static String SignKey = "3vh4xN3@G02ixajB*^@PHkxfz88AKk%O";
    /**
     * 正式环境的key
     */
//    public static String SignKey = "hkf%t5SMv1HtrVS!Y%B!NPNS!!0cWgy";

            public static final int City_Count = 5;

    //************************************URL*****************************************************************
//根据名称获取城市的id
    public static final String GetCityId = Host + "/v1/aqi/area/index";



    /*
    * 获取多个城市的数据
    * */
    public static final String Area_Info = Host + "/v1/aqi/weather/getareainfo";
    public static final String GetCityTodyData=Host+"/v1/aqi/weather/gettoday";
    public static final String UpData=Host+"/v1/api/api/get-version";

    //*************************************URL****************************************************************

    /**
     * 入参签名
     */
    public static HashMap<String, String> Sign(HashMap<String, String> map) {
        HashMap<String, String> da = new HashMap<String, String>();
        map.put("timestamp", Constans.TimeStamp());
        da = map;
        List<Bsign> mBlComments = new ArrayList<Bsign>();
        for (Iterator iter = da.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry element = (Map.Entry) iter.next();
            String strKey = String.valueOf(element.getKey());
            String strObj = String.valueOf(element.getValue());
            mBlComments.add(new Bsign(strKey, strObj));
        }
        UpComparator m = new UpComparator();
        Collections.sort(mBlComments, m);
        String K = "";
        for (Bsign h : mBlComments) {
            K = K + h.getId() + h.getTitle();
        }

        K = K + SignKey;
        if (map.containsKey("keyword")) {
            map.put("keyword", URLEncoder.encode(map.get("keyword")));
        }
        if (map.containsKey("title")) {
            map.put("title", URLEncoder.encode(map.get("title")));
        }
        map.put("sign", Constans.SHA(K));
        return map;

    }

    /**
     * 获取时间戳
     */
    public static String TimeStamp() {
        return System.currentTimeMillis() + "";
    }

    /**
     * SHA256方法
     */
    public static String SHA(String key) {
        return StringEncrypt.Encrypt(key, "");
    }

    /**
     * RSA加密的方法
     */
    public static String RSA(String str, Context CT) {
        try {
            return RSAUtils.GetRsA(CT, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 获取设备唯一号
     */
    public static String GetPhoneId(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context
                .getSystemService(context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }

    // 版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    // 版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
