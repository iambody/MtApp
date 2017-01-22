package io.vtown.WuMaiApp;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import java.util.ArrayList;
import java.util.List;

import io.vtown.WuMaiApp.module.BHome;
import io.vtown.WuMaiApp.service.LocationService;

/**
 * Created by datutu on 2017/1/10.
 */

public class MyApplication extends Application {
    public LocationService locationService;
    public Vibrator mVibrator;
    public BHome  myhomedata ;

    @Override
    public void onCreate() {
        super.onCreate();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());
    }

    public BHome getMyhomedata() {
        return myhomedata;
    }

    public void setMyhomedata(BHome myhomedata) {
        this.myhomedata = myhomedata;
    }
}
