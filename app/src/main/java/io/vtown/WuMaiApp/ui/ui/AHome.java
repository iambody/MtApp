package io.vtown.WuMaiApp.ui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vtown.WuMaiApp.Net.vollynet.NHttpBaseStr;
import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.DimensionPixelUtil;
import io.vtown.WuMaiApp.constant.Constans;
import io.vtown.WuMaiApp.constant.PromptManager;
import io.vtown.WuMaiApp.interf.IHttpResult;
import io.vtown.WuMaiApp.ui.ABase;
import io.vtown.WuMaiApp.view.homeslid.MyScrollView;
import io.vtown.WuMaiApp.view.homeslid.SlidingDetailsLayout;

/**
 * Created by datutu on 2017/1/12.
 */

public class AHome extends ABase {
    public static final String Tag_CityName = "tagcity";
    private String CityName;

    @Bind(R.id.test)
    TextView test;
    @Bind(R.id.home_up_scrollview)
    MyScrollView homeUpScrollview;
    @Bind(R.id.home_scroll_note)
    TextView homeScrollNote;
    @Bind(R.id.home_down_scrollview)
    MyScrollView homeDownScrollview;
    @Bind(R.id.home_slidinghomeLayout)
    SlidingDetailsLayout homeSlidinghomeLayout;
    @Bind(R.id.home_up_scrollview_in_lay)
    RelativeLayout homeUpScrollviewInLay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        IBaseBund();
        IBaseView();

    }

    private void IBaseBund() {
        CityName = getIntent().getStringExtra(Tag_CityName);
        IGetCityCode(CityName);
    }


    private void IBaseView() {
        //设置监听
        homeSlidinghomeLayout.setPositionChangListener(new SlidingDetailsLayout.PositionChangListener() {
            @Override
            public void position(int positon) {
                if (positon == 0) {
                    homeScrollNote.setText("上拉查看详情");
                    Toast.makeText(BaseContext, "我到了上边", Toast.LENGTH_SHORT).show();
                } else {
                    homeScrollNote.setText("下拉返回主页");
                    Toast.makeText(BaseContext, "我到了下边", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBottom() {
                homeScrollNote.setText("松开查看详情");
            }

            @Override
            public void backBottom() {
                homeScrollNote.setText("上拉查看详情");
            }

            @Override
            public void onTop() {
                homeScrollNote.setText("松开返回主页");
            }

            @Override
            public void backTop() {
                {
                    homeScrollNote.setText("下拉返回主页");
                }
            }

        });
        //开始配置上边的scrollview 我需要进行处理 设置上边布局是整个屏幕
//        LinearLayout.LayoutParams ps = new LinearLayout.LayoutParams(screenWidth, screenHeight);
//        homeUpScrollview.setLayoutParams(ps);
        FrameLayout.LayoutParams inps = new FrameLayout.LayoutParams(screenWidth, screenHeight - DimensionPixelUtil.dip2px(BaseContext, 26));
        homeUpScrollviewInLay.setLayoutParams(inps);

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
                PromptManager.ShowCustomToast(BaseContext, Data);
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
}
