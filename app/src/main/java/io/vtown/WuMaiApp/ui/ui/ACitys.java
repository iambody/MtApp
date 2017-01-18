package io.vtown.WuMaiApp.ui.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    private List<BLSearchResultCites> mDataList = new ArrayList<BLSearchResultCites>();
    private List<BLSearchResultCites> mCites = new ArrayList<BLSearchResultCites>(Constans.City_Count);
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
        initFristView();
        //initView();

    }

    private void initFristView() {
        if (mFristCity != null) {
            StrUtils.SetTxt(tvFristCityName, mFristCity.getAreaname());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        getAreaInfo(mListAdapter.getData());

    }

    private void setFristCityInfo(BLSearchResultCites info) {
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

    @Override
    protected void onPause() {
        super.onPause();
        if (mListAdapter != null) {
            mCites = mListAdapter.getData();
            Spuit.Location_City_Save(BaseContext,mCites);
        }

    }


    private void initCache() {
        mFristCity = Spuit.BaiDuMap_Location_Get(BaseContext);
        mCites = Spuit.Location_City_Get(BaseContext);
    }

    private String getAreaid_Str(List<BLSearchResultCites> data) {
        String frist_id = "";
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
                    setFristCityInfo(mArea_infos.get(0));
                    mArea_infos.remove(0);
                    mListAdapter.setInfoData(mArea_infos);
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


                if (mInfodata != null && mInfodata.size() > 0) {
                    BLSearchResultCites info_data = mInfodata.get(position);

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


            viewHolder.name.setText(mDragDatas.get(position).getAreaname());
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
//            BLSearchResultCites blSearchResultCites = message.getmCity();
//            mCites = mListAdapter.getData();
//            if (mCites.size() > 0) {
//                for (int i = 0; i < mCites.size(); i++) {
//                    if (!mCites.get(i).getAreaid().equals(blSearchResultCites.getAreaid()) && !mFristCity.getAreaid().equals(blSearchResultCites.getAreaid())) {
//                        mCites.add(blSearchResultCites);
//                    }
//                }
//            } else {
//                if (!mFristCity.getAreaid().equals(blSearchResultCites.getAreaid())) {
//                    mCites.add(blSearchResultCites);
//                }
//            }
//
//            if (mListAdapter != null) {
//                Spuit.Location_City_Save(BaseContext,mCites);
//            }
//            //getAreaInfo(mCites);
//            //initView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
