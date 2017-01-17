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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.StrUtils;
import io.vtown.WuMaiApp.constant.Spuit;
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

    private List<BLSearchResultCites> mDataList = new ArrayList<BLSearchResultCites>();
    private List<BLSearchResultCites> mCites;
    private MyAdapter mListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initCache();
        initView();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mListAdapter != null){
            Spuit.Location_City_Save(BaseContext,mListAdapter.getData());
        }

    }

    private void initCache() {
        mCites = Spuit.Location_City_Get(BaseContext);
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
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name_drag_list);
                viewHolder.delete_city = (LinearLayout)convertView.findViewById(R.id.delete_city);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
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
            TextView name;
            LinearLayout delete_city;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ReciveMessage(BMessage message) {
       if(BMessage.Tage_Select_City == message.getTage_Message()){
           BLSearchResultCites blSearchResultCites = message.getmCity();
           if(mCites.size()>0){
               for (int i=0;i<mCites.size();i++){
                   if(!mCites.get(i).getAreaname().equals(blSearchResultCites.getAreaname())){
                       mCites.add(blSearchResultCites);
                   }
               }
           }else{
               mCites.add(blSearchResultCites);
           }


           initView();
       }
            

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
