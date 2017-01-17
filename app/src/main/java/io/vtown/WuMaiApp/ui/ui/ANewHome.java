package io.vtown.WuMaiApp.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import io.vtown.WuMaiApp.Utilss.SaveBitMapUtiils;
import io.vtown.WuMaiApp.Utilss.StrUtils;
import io.vtown.WuMaiApp.constant.Constans;
import io.vtown.WuMaiApp.constant.PromptManager;
import io.vtown.WuMaiApp.constant.Spuit;
import io.vtown.WuMaiApp.fragment.FHome;
import io.vtown.WuMaiApp.interf.IHttpResult;
import io.vtown.WuMaiApp.module.BHome;
import io.vtown.WuMaiApp.module.BMessage;
import io.vtown.WuMaiApp.module.cites.BLSearchResultCites;
import io.vtown.WuMaiApp.ui.ABase;
import io.vtown.WuMaiApp.view.Xcircleindicator;


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
    @Bind(R.id.newhome_out_lay)
    RelativeLayout newhomeOutLay;
    @Bind(R.id.newhome_circleindicator)
    Xcircleindicator newhomeCircleindicator;

    public static final String Tag_CityName = "tagcity";
    private String GetCityName;
    private String GetCityCode;


    private List<FHome> FragmentLs = new ArrayList<>();
    private FragmentPagerAdapter fragmentPagerAdapter;

    //当前所在的fragment的数据
    private BHome CurrentHome;
    private List<BLSearchResultCites> Citys = new ArrayList<>();
    //当前的位置
    private int CurrentPostion = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newhome);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        IBund();


    }

    private void IBund() {
        GetCityName = getIntent().getStringExtra(Tag_CityName);
        if (Spuit.BaiDuMap_Location_Get(BaseActiviy) != null) {
            GetCityName = Spuit.BaiDuMap_Location_Get(BaseActiviy).getAreaname();
            GetCityCode = Spuit.BaiDuMap_Location_Get(BaseActiviy).getAreaid();
            Citys = Spuit.AllCity_Get(BaseActiviy);
            IBase();
        } else {
            IGetCityCode(GetCityName);
        }


    }


    private void IBase() {
        for (int i = 0; i < Citys.size(); i++) {
            BLSearchResultCites data = Citys.get(i);
            FHome MyFhome = SetScreeSize(new FHome(), data.getAreaid(), data.getAreaname());
            FragmentLs.add(MyFhome);
        }

        fragmentPagerAdapter = new MyViewPage(getSupportFragmentManager());
        newhomeViewpage.setAdapter(fragmentPagerAdapter);
        newhomeCircleindicator.initData(FragmentLs.size(), CurrentPostion);
        //设置当前的页面
        newhomeCircleindicator.setCurrentPage(CurrentPostion);
        newhomeViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CurrentPostion = position;
                newhomeCircleindicator.setCurrentPage(CurrentPostion);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private FHome SetScreeSize(FHome f, String CityCode, String CityName) {
        Bundle mybudl = new Bundle();
        mybudl.putInt("screenWidth", screenWidth);
        mybudl.putInt("screenHeight", screenHeight);
        mybudl.putString("citycode", CityCode);
        mybudl.putString("cityname", CityName);
        f.setArguments(mybudl);
        return f;
    }

    @OnClick({R.id.newhome_city_bt, R.id.newhome_share_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newhome_city_bt:
                PromptManager.SkipActivity1(BaseActiviy, new Intent(BaseActiviy, ACitys.class));
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
            case BMessage.Tage_F_To_Home_Data:
                CurrentHome = message.getMyBHome();
                StrUtils.SetTxt(newhomeCityTitle, "北京-" + CurrentHome.getAqi_detail());

                break;
        }

    }

    private void IGetCityCode(final String cityName) {
        PromptManager.showLoading(BaseContext);
        NHttpBaseStr BaNHttpBaseStr = new NHttpBaseStr(BaseContext);
        BaNHttpBaseStr.setPostResult(new IHttpResult<String>() {
            @Override
            public void getResult(int Code, String Msg, String Data) {
                if (!StrUtils.isEmpty(Data)) {
                    List<BLSearchResultCites> ls = JSON.parseArray(Data, BLSearchResultCites.class);
                    GetCityCode = ls.get(0).getAreaid();
                    Citys.add(new BLSearchResultCites(GetCityName, GetCityCode));
                    Spuit.BaiDuMap_Location_Save(BaseContext, new BLSearchResultCites(GetCityName, GetCityCode));
                    IBase();
                }
            }

            @Override
            public void onError(String error, int LoadType) {
                PromptManager.ShowCustomToast(BaseContext, error);
                IGetCityCode(cityName);
            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("keyword", cityName);
        map.put("count", "1");
        BaNHttpBaseStr.getData(Constans.GetCityId, map, Request.Method.GET);
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
