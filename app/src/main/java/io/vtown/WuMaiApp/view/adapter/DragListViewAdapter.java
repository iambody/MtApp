package io.vtown.WuMaiApp.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.vtown.WuMaiApp.module.cites.BLSearchResultCites;

public abstract class DragListViewAdapter extends BaseAdapter {

    public Context mContext;
    public List<BLSearchResultCites> mDragDatas;
    public List<BLSearchResultCites> mInfodata ;

    public DragListViewAdapter(Context context, List<BLSearchResultCites> dataList){
        this.mContext = context;
        this.mDragDatas = dataList;


    }

    @Override
    public int getCount() {
        return mDragDatas == null ? 0 : mDragDatas.size();
    }

    @Override
    public BLSearchResultCites getItem(int position) {
        return mDragDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    public abstract View getItemView(int position, View convertView, ViewGroup parent);

    public void swapData(int from, int to){
        Collections.swap(mDragDatas, from, to);
        Collections.swap(mInfodata, from, to);

        notifyDataSetChanged();
    }

    public void setInfoData(List<BLSearchResultCites> infodata){
        this.mInfodata = infodata;
        notifyDataSetChanged();
    }

    public List<BLSearchResultCites> getData(){
        return this.mDragDatas;
    }

    public void deleteData(int position) {
        mDragDatas.remove(position);
        mInfodata.remove(position);
        notifyDataSetChanged();
    }
}