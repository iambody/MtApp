package io.vtown.WuMaiApp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;

import org.greenrobot.eventbus.EventBus;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vtown.WuMaiApp.Net.vollynet.NHttpBaseStr;
import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.DimensionPixelUtil;
import io.vtown.WuMaiApp.Utilss.SaveBitMapUtiils;
import io.vtown.WuMaiApp.Utilss.StrUtils;
import io.vtown.WuMaiApp.constant.Constans;
import io.vtown.WuMaiApp.constant.PromptManager;
import io.vtown.WuMaiApp.interf.IHttpResult;
import io.vtown.WuMaiApp.module.BAqi;
import io.vtown.WuMaiApp.module.BDetail;
import io.vtown.WuMaiApp.module.BHome;
import io.vtown.WuMaiApp.module.BMessage;
import io.vtown.WuMaiApp.view.custom.CompleteListView;
import io.vtown.WuMaiApp.view.custom.HomeScrollView;

/**
 * Created by datutu on 2017/1/16.
 */

public class FHome extends FLazy implements HomeScrollView.OnScrollListener {
    @Bind(R.id.fragment_home_homescrollview)
    HomeScrollView fragmentHomeHomescrollview;
    @Bind(R.id.fragment_hscrollview_lay)
    LinearLayout fragmentHscrollviewLay;
    @Bind(R.id.fragment_up_view_lay)
    RelativeLayout fragmentUpViewLay;
    @Bind(R.id.fragment_down_detail_ls)
    CompleteListView fragmentDownDetailLs;
    @Bind(R.id.fragment_home_out_lay)
    LinearLayout fragmentHomeOutLay;
    @Bind(R.id.fragment_up_city_name)
    TextView fragmentUpCityName;
    @Bind(R.id.fragment_up_city_level)
    TextView fragmentUpCityLevel;
    @Bind(R.id.fragment_up_city_level_desc)
    TextView fragmentUpCityLevelDesc;

    private String CityCode;

    private MyFragHomeAp myFragHomeAp;

    @Override
    protected void create(Bundle Mybundle) {
//        citycode
        CityCode = Mybundle.getString("citycode");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_weather;
    }

    @Override
    protected void initViewsAndEvents(View view) {

    }

    @Override
    protected void onFirstUserVisible() {
        IbaseView();
    }

    private void IbaseView() {
        fragmentHomeHomescrollview.setScrolListener(this);
        LinearLayout.LayoutParams inps = new LinearLayout.LayoutParams(screenWidth, screenHeight - DimensionPixelUtil.dip2px(FBaseActivity, 26));
        fragmentUpViewLay.setLayoutParams(inps);
        myFragHomeAp=new MyFragHomeAp();
        fragmentDownDetailLs.setAdapter(myFragHomeAp);
        //开始刷新数据
        NetHomeData(CityCode, true);
        fragmentHomeHomescrollview.smoothScrollTo(0,-20);
    }

    public void FrashHSView(BHome home) {
        fragmentHomeHomescrollview.smoothScrollTo(0,-20);
        for (int i = 0; i < home.getList().size(); i++) {
            BAqi MyAqi = home.getList().get(i);
            View ItemView = LayoutInflater.from(FBaseActivity).inflate(R.layout.item_home_hscrollview, null);
            ImageView Itemiv = (ImageView) ItemView.findViewById(R.id.item_home_hscrollview_iv);
            TextView ItemTxt = (TextView) ItemView.findViewById(R.id.item_home_hscrollview_txt);
            TextView Itemleel = (TextView) ItemView.findViewById(R.id.item_home_hscrollview_level);
            SetLevelIv(MyAqi.getAqi_level(), Itemiv);
            ItemTxt.setText(MyAqi.getHour());
            Itemleel.setText(MyAqi.getAqi() + "");
            fragmentHscrollviewLay.addView(ItemView);

        }
    }

    @Override
    protected void onUserVisible() {
        fragmentHomeHomescrollview.smoothScrollTo(0,-20);
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void DetoryViewAndThing() {

    }

    @Override
    public void onScroll(int scrollY) {
        BMessage bMessage = new BMessage(BMessage.Tage_HomeScrollY);
        bMessage.setHomeScrollY(scrollY);
        EventBus.getDefault().post(bMessage);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 刷新数据
     *
     * @param bHome
     */
    private void IFrashData(BHome bHome) {
        fragmentUpCityName.setText("北京朝阳区");
        fragmentUpCityLevel.setText(bHome.getAqi() + "");
        fragmentUpCityLevelDesc.setText(bHome.getAqi_detail());
        FrashHSView(bHome);
        SetLevelIv(bHome.getAqi_level(), fragmentHomeOutLay);
        myFragHomeAp.FrashData(bHome.getSeven_list());
        BMessage message=new BMessage(BMessage.Tage_F_To_Home_Data);
        message.setMyBHome(bHome);
        EventBus.getDefault().post(message);

//        SaveBitMapUtiils.SaveBitMap(fragmentUpViewLay);
    }

    /**
     * 获取首页数据
     */
    public void NetHomeData(String CityId, boolean isloading) {
        if (isloading) PromptManager.showLoading(FBaseActivity);
        NHttpBaseStr BaNHttpBaseStr = new NHttpBaseStr(FBaseActivity);
        BaNHttpBaseStr.setPostResult(new IHttpResult<String>() {
            @Override
            public void getResult(int Code, String Msg, String Data) {
                if (!StrUtils.isEmpty(Data)) {

                    IFrashData(JSON.parseObject(Data, BHome.class));
                }
            }


            @Override
            public void onError(String error, int LoadType) {

            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("areaid", CityId);
        map.put("is_all", "1");
        BaNHttpBaseStr.getData(Constans.GetCityTodyData, map, Request.Method.GET);
    }


    private class MyFragHomeAp extends BaseAdapter {
        private List<BDetail> DownDatas = new ArrayList<>();

        public void FrashData(List<BDetail> datas) {
            this.DownDatas = datas;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return DownDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return DownDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FMyItem myItem = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(FBaseActivity).inflate(R.layout.item_fragment_detail, null);
                myItem = new FMyItem();
                myItem.item_fragment_detail_up_txt = (TextView) convertView.findViewById(R.id.item_fragment_detail_up_txt);
                myItem.item_fragment_detail_v1 = convertView.findViewById(R.id.item_fragment_detail_v1);
                myItem.item_fragment_detail_v2 = convertView.findViewById(R.id.item_fragment_detail_v2);
                myItem.item_fragment_detail_v3 = convertView.findViewById(R.id.item_fragment_detail_v3);
                myItem.item_fragment_detail_v4 = convertView.findViewById(R.id.item_fragment_detail_v4);
                myItem.item_fragment_detail_v5 = convertView.findViewById(R.id.item_fragment_detail_v5);
                myItem.item_fragment_detail_v6 = convertView.findViewById(R.id.item_fragment_detail_v6);
                myItem.item_fragment_detail_v7 = convertView.findViewById(R.id.item_fragment_detail_v7);
                convertView.setTag(myItem);
            } else {
                myItem = (FMyItem) convertView.getTag();
            }
            BDetail ItemData = DownDatas.get(position);
            StrUtils.SetTxt(myItem.item_fragment_detail_up_txt, ItemData.getDate());
            ItemEightData(ItemData,0,myItem.item_fragment_detail_v1);
            ItemEightData(ItemData,1,myItem.item_fragment_detail_v2);
            ItemEightData(ItemData,2,myItem.item_fragment_detail_v3);
            ItemEightData(ItemData,3,myItem.item_fragment_detail_v4);
            ItemEightData(ItemData,4,myItem.item_fragment_detail_v5);
            ItemEightData(ItemData,5,myItem.item_fragment_detail_v6);
            ItemEightData(ItemData,6,myItem.item_fragment_detail_v7);
            return convertView;
        }

        class FMyItem {
            TextView item_fragment_detail_up_txt;
            View item_fragment_detail_v1;
            View item_fragment_detail_v2;
            View item_fragment_detail_v3;
            View item_fragment_detail_v4;
            View item_fragment_detail_v5;
            View item_fragment_detail_v6;
            View item_fragment_detail_v7;
            View item_fragment_detail_v8;
        }
    }


    /**
     * 详情中 统一处理8个的数据
     */
    private void ItemEightData(BDetail data, int postion, View VV) {
        TextView UpTxt = (TextView) VV.findViewById(R.id.comment_item_detail_view_up_txt);
        TextView DownTxt = (TextView) VV.findViewById(R.id.comment_item_detail_view_down_txt);
        ImageView IV = (ImageView) VV.findViewById(R.id.comment_item_detail_view_center_iv);
        StrUtils.SetTxt(UpTxt, data.getList().get(postion).getHour());
        StrUtils.SetTxt(DownTxt, data.getList().get(postion).getAqi());
        switch (data.getList().get(postion).getAqi_level()) {
            case 0:
                IV.setImageResource(R.mipmap.point_level1);
                break;
            case 1:
                IV.setImageResource(R.mipmap.point_level2);
                break;
            case 2:
                IV.setImageResource(R.mipmap.point_level3);
                break;
            case 3:
                IV.setImageResource(R.mipmap.point_level4);
                break;
            case 4:
                IV.setImageResource(R.mipmap.point_level5);
                break;
            case 5:
                IV.setImageResource(R.mipmap.point_level6);
                break;
            case 6:
                IV.setImageResource(R.mipmap.point_level7);
                break;

        }

    }
}
