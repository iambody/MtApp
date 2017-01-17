package io.vtown.WuMaiApp.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vtown.WuMaiApp.Net.vollynet.NHttpBaseStr;
import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.StrUtils;
import io.vtown.WuMaiApp.constant.Constans;
import io.vtown.WuMaiApp.constant.PromptManager;
import io.vtown.WuMaiApp.fragment.FHome;
import io.vtown.WuMaiApp.interf.IHttpResult;
import io.vtown.WuMaiApp.module.BHome;
import io.vtown.WuMaiApp.module.BMessage;
import io.vtown.WuMaiApp.ui.ABase;


/**
 * Created by datutu on 2017/1/16.
 */

public class ANewHome extends ABase {
    @Bind(R.id.newhome_viewpage)
    ViewPager newhomeViewpage;
    @Bind(R.id.newhome_city_bt)
    ImageView newhomeCityBt;
    @Bind(R.id.newhome_city_title)
    TextView newhomeCityTitle;
    @Bind(R.id.newhome_share_bt)
    ImageView newhomeShareBt;
    @Bind(R.id.newhome_title_up_lay)
    LinearLayout newhomeTitleUpLay;


    private List<FHome> FragmentLs = new ArrayList<>();
    private FragmentPagerAdapter fragmentPagerAdapter;
    private FHome Citydefault;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newhome);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        IBase();
        IData();
    }

    private void IData() {
        /**
         *  一般情况下第一次进来需要请求数据
         *  之后进来先显示缓存数据在偷偷刷新即可
         */
        NetHomeData("101010100");
    }


    private void IBase() {
        Citydefault = SetScreeSize(new FHome());
        FragmentLs.add(Citydefault);
        fragmentPagerAdapter = new MyViewPage(getSupportFragmentManager());
        newhomeViewpage.setAdapter(fragmentPagerAdapter);
    }

    private FHome SetScreeSize(FHome f) {
        Bundle mybudl = new Bundle();
        mybudl.putInt("screenWidth", screenWidth);
        mybudl.putInt("screenHeight", screenHeight);
        f.setArguments(mybudl);
        return f;
    }

    @OnClick({R.id.newhome_city_bt, R.id.newhome_share_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newhome_city_bt:
                PromptManager.SkipActivity1(BaseActiviy,new Intent(BaseActiviy,ACitys.class));
                break;
            case R.id.newhome_share_bt:
                break;
        }
    }

    private class MyViewPage extends FragmentPagerAdapter {
        public MyViewPage(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentLs.get(position);
        }

        @Override
        public int getCount() {
            return FragmentLs.size();
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTitles[position];
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ReciveMessage(BMessage message) {
        switch (message.getTage_Message()) {
            case BMessage.Tage_HomeScrollY:
                int ScrollY = message.getHomeScrollY();
                if (ScrollY < 20) {
                    newhomeTitleUpLay.getBackground().mutate().setAlpha(0);
                    newhomeTitleUpLay.setVisibility(View.GONE);
                } else if (ScrollY >= 20 && ScrollY < 256) {//860
                    newhomeTitleUpLay.getBackground().mutate().setAlpha((ScrollY));//scrollY-100
                    newhomeTitleUpLay.setVisibility(View.VISIBLE);
                } else {
                    newhomeTitleUpLay.getBackground().mutate().setAlpha(255);
                    newhomeTitleUpLay.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    private void IGetCityCode(String cityName) {
        NHttpBaseStr BaNHttpBaseStr = new NHttpBaseStr(BaseContext);
        BaNHttpBaseStr.setPostResult(new IHttpResult<String>() {
            @Override
            public void getResult(int Code, String Msg, String Data) {
                PromptManager.ShowCustomToast(BaseContext, Data);
            }

            @Override
            public void onError(String error, int LoadType) {
                PromptManager.ShowCustomToast(BaseContext, error);
            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("keyword", cityName);
        map.put("count", "1");
        BaNHttpBaseStr.getData(Constans.GetCityId, map, Request.Method.GET);
    }

    /**
     * 获取首页数据
     */
    private void NetHomeData(String CityId) {
        NHttpBaseStr BaNHttpBaseStr = new NHttpBaseStr(BaseContext);
        BaNHttpBaseStr.setPostResult(new IHttpResult<String>() {
            @Override
            public void getResult(int Code, String Msg, String Data) {
                if (!StrUtils.isEmpty(Data)) {

                    IFrashData(JSON.parseObject(Data, BHome.class));
                }
            }

            @Override
            public void onError(String error, int LoadType) {
                PromptManager.ShowCustomToast(BaseContext, error);
            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("areaid", CityId);

        BaNHttpBaseStr.getData(Constans.GetCityTodyData, map, Request.Method.GET);
    }

    /**
     * 获取数据后进行刷新数据
     */

    private void IFrashData(BHome myHome) {
        FragmentLs.get(0).FrashView(myHome);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().unregister(this);
            ButterKnife.unbind(this);
        } catch (Exception e) {

        }

    }
}
