package io.vtown.WuMaiApp.ui.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import io.vtown.WuMaiApp.constant.Spuit;
import io.vtown.WuMaiApp.interf.IHttpResult;
import io.vtown.WuMaiApp.module.BMessage;
import io.vtown.WuMaiApp.module.cites.BLSearchResultCites;
import io.vtown.WuMaiApp.ui.ABase;
import io.vtown.WuMaiApp.view.adapter.DragListViewAdapter;
import io.vtown.WuMaiApp.view.custom.DragListView;

/**
 * Created by datutu on 2017/1/13.
 */

public class ACitys extends ABase {

    @Bind(R.id.dvl_drag_list)
    DragListView dvl_drag_list;
    @Bind(R.id.iv_add_city)
    ImageView ivAddCity;
    @Bind(R.id.tv_frist_city_name)
    TextView tvFristCityName;
    @Bind(R.id.layout_frist_item)
    LinearLayout layoutFristItem;
    @Bind(R.id.tv_frist_city_aqi_detail)
    TextView tvFristCityAqiDetail;
    @Bind(R.id.tv_frist_city_aqi)
    TextView tvFristCityAqi;

    private List<BLSearchResultCites> mNoDragData = new ArrayList<BLSearchResultCites>();
    private List<BLSearchResultCites> mCites = new ArrayList<BLSearchResultCites>();
    private List<BLSearchResultCites> mArea_infos;
    private MyAdapter mListAdapter;
    private BLSearchResultCites mFristCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initCache();
        initTitleView();
        setFristCityInfo(mFristCity);

        getAreaInfo(mCites);
        initView();


    }

    private void initTitleView() {
        View title_layout = findViewById(R.id.title_layout);
        title_layout.findViewById(R.id.iv_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isDrag()) {
                    backTo();
                } else {
                    ACitys.this.finish();
                }


            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setFristCityInfo(BLSearchResultCites info) {
        if (info != null) {//如果获取的缓存为空就不显示定位城市
            layoutFristItem.setVisibility(View.VISIBLE);

            if (!StrUtils.isEmpty(info.getAqi_detail())) {
                mFristCity.setAqi(info.getAqi());
                mFristCity.setAqi_level(info.getAqi_level());
                mFristCity.setAqi_detail(info.getAqi_detail());
                Spuit.BaiDuMap_Location_Save(BaseContext, mFristCity);
                switch (info.getAqi_level()) {
                    case 0:
                        layoutFristItem.setBackgroundResource(R.drawable.aqi_lv0);
                        break;

                    case 1:
                        layoutFristItem.setBackgroundResource(R.drawable.aqi_lv1);
                        break;

                    case 2:
                        layoutFristItem.setBackgroundResource(R.drawable.aqi_lv2);
                        break;

                    case 3:
                        layoutFristItem.setBackgroundResource(R.drawable.aqi_lv3);
                        break;

                    case 4:
                        layoutFristItem.setBackgroundResource(R.drawable.aqi_lv4);
                        break;

                    case 5:
                        layoutFristItem.setBackgroundResource(R.drawable.aqi_lv5);
                        break;

                    case 6:
                        layoutFristItem.setBackgroundResource(R.drawable.aqi_lv6);
                        break;
                }
                tvFristCityAqiDetail.setText(info.getAqi_detail());
                tvFristCityAqi.setText(info.getAqi() + "");
            }

            StrUtils.SetTxt(tvFristCityName, mFristCity.getAreaname());
        }else{
            layoutFristItem.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListAdapter != null) {
            Spuit.Location_City_Save(BaseContext, mListAdapter.getData());
        }
    }


    private void initCache() {
        mFristCity = Spuit.BaiDuMap_Location_Get(BaseContext);
        mCites = Spuit.Location_City_Get(BaseContext);
        mNoDragData = mCites;
    }

    private String getAreaid_Str(List<BLSearchResultCites> data) {
        String frist_id = "";
        if(mFristCity != null){
            if (data.size() > 0) {
                frist_id = mFristCity.getAreaid() + ",";
                for (int i = 0; i < data.size(); i++) {
                    if (i != data.size() - 1) {
                        frist_id += data.get(i).getAreaid() + ",";
                    } else {
                        frist_id += data.get(i).getAreaid();
                    }
                }
            } else {
                frist_id = mFristCity.getAreaid();
            }
        }else{
            for (int i = 0; i < data.size(); i++) {
                if (i != data.size() - 1) {
                    frist_id += data.get(i).getAreaid() + ",";
                } else {
                    frist_id += data.get(i).getAreaid();
                }
            }
        }
        return frist_id;
    }


    private void getAreaInfo(List<BLSearchResultCites> data) {
        String areaid_str = getAreaid_Str(data);
        NHttpBaseStr BaNHttpBaseStr = new NHttpBaseStr(BaseContext);
        BaNHttpBaseStr.setPostResult(new IHttpResult<String>() {
            @Override
            public void getResult(int Code, String Msg, String Data) {
                if (StrUtils.isEmpty(Data)) {
                    return;
                }

                mArea_infos = JSON.parseArray(Data, BLSearchResultCites.class);
                if (mArea_infos.size() > 0) {
                    if(mFristCity != null){
                        setFristCityInfo(mArea_infos.get(0));//设置第一个城市数据
                        mArea_infos.remove(0);//去除第一个的数据
                    }
                    //mListAdapter.setInfoData(mArea_infos);
                    FreashCahceDetailInf(mCites, mArea_infos);
                    initView();//刷新AP
                }

            }

            @Override
            public void onError(String error, int LoadType) {
                PromptManager.ShowCustomToast(BaseContext, error);
            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("areaid_string", areaid_str);
        BaNHttpBaseStr.getData(Constans.Area_Info, map, Request.Method.GET);
    }

    /*
    * 把获取到的aqi等数据放到地点list
    * */
    private void FreashCahceDetailInf(List<BLSearchResultCites> data, List<BLSearchResultCites> mArea_infos) {
        for (int i = 0; i < mArea_infos.size(); i++) {
            data.get(i).setAqi(mArea_infos.get(i).getAqi());
            data.get(i).setAqi_level(mArea_infos.get(i).getAqi_level());
            data.get(i).setAqi_detail(mArea_infos.get(i).getAqi_detail());
        }
        mCites = data;
    }

    private void initView() {
        if (mListAdapter == null) {
            mListAdapter = new MyAdapter(this, mCites);
            dvl_drag_list.setAdapter(mListAdapter);
        } else {
            mListAdapter.notifyDataSetChanged();
        }

    }

    @OnClick(R.id.iv_add_city)
    public void onClick() {
        if(mCites.size() == Constans.City_Count){
            PromptManager.ShowCustomToast(BaseContext,"您保存的城市已达上线，建议删除后再搜索");
        }
        Intent intent = new Intent(BaseContext, AAddCity.class);
        startActivity(intent);
    }

    public class MyAdapter extends DragListViewAdapter {


        public MyAdapter(Context context, List<BLSearchResultCites> dataList) {
            super(context, dataList);

        }

        @Override
        public View getItemView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(BaseContext).inflate(R.layout.item_drag_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.ll_item_drag_list_layout = (LinearLayout) convertView.findViewById(R.id.ll_item_drag_list_layout);
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name_drag_list);
                viewHolder.delete_city = (LinearLayout) convertView.findViewById(R.id.delete_city);
                viewHolder.tv_item_drag_list_aqi = (TextView) convertView.findViewById(R.id.tv_item_drag_list_aqi);
                viewHolder.tv_item_drag_list_aqi_detail = (TextView) convertView.findViewById(R.id.tv_item_drag_list_aqi_detail);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            BLSearchResultCites info_data = mDragDatas.get(position);

            if (!StrUtils.isEmpty(mDragDatas.get(position).getAqi_detail())) {


                switch (info_data.getAqi_level()) {
                    case 0:
                        viewHolder.ll_item_drag_list_layout.setBackgroundResource(R.drawable.aqi_lv0);
                        break;

                    case 1:
                        viewHolder.ll_item_drag_list_layout.setBackgroundResource(R.drawable.aqi_lv1);
                        break;

                    case 2:
                        viewHolder.ll_item_drag_list_layout.setBackgroundResource(R.drawable.aqi_lv2);
                        break;

                    case 3:
                        viewHolder.ll_item_drag_list_layout.setBackgroundResource(R.drawable.aqi_lv3);
                        break;

                    case 4:
                        viewHolder.ll_item_drag_list_layout.setBackgroundResource(R.drawable.aqi_lv4);
                        break;

                    case 5:
                        viewHolder.ll_item_drag_list_layout.setBackgroundResource(R.drawable.aqi_lv5);
                        break;

                    case 6:
                        viewHolder.ll_item_drag_list_layout.setBackgroundResource(R.drawable.aqi_lv6);
                        break;
                }


                viewHolder.tv_item_drag_list_aqi.setText(info_data.getAqi() + "");
                viewHolder.tv_item_drag_list_aqi_detail.setText(info_data.getAqi_detail());
            }


            viewHolder.name.setText(info_data.getAreaname());
            viewHolder.delete_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteData(position);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView name, tv_item_drag_list_aqi, tv_item_drag_list_aqi_detail;
            LinearLayout delete_city, ll_item_drag_list_layout;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ReciveMessage(BMessage message) {
        if (BMessage.Tage_Select_City == message.getTage_Message()) {
            ACitys.this.finish();
        }
    }


    @Override
    public void onBackPressed() {
        if (isDrag()) {
            backTo();
        } else {
            super.onBackPressed();
        }
    }


    private void backTo() {

        Intent intent = new Intent(BaseContext, ANewHome.class);
        intent.putExtra(ANewHome.Tage_IsFromCity, true);
        intent.putExtra("isdrag", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ACitys.this.finish();

    }

    /*
    * 判断是否删除过，是否移动过
    * */
    private boolean isDrag() {
        boolean flag = false;
        List<BLSearchResultCites> data = mListAdapter.getData();
        List<BLSearchResultCites> noDrags = mListAdapter.getmNoDragDatas();
        if (!mListAdapter.isDelete) {
            for (int i = 0; i < noDrags.size(); i++) {
                if (!noDrags.get(i).getAreaid().equals(data.get(i).getAreaid())) {
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
