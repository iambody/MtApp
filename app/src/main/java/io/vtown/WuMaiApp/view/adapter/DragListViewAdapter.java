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
    public List<BLSearchResultCites> mNoDragDatas = new ArrayList<BLSearchResultCites>() ;
    public boolean isDrag = false;//是否移动过
    public boolean isDelete = false;//是否删除过

    public DragListViewAdapter(Context context, List<BLSearchResultCites> dataList){
        this.mContext = context;
        this.mDragDatas = dataList;
        this.mNoDragDatas.clear();
        this.mNoDragDatas.addAll(dataList);


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
        //Collections.swap(mInfodata, from, to);
        isDrag = true;
        notifyDataSetChanged();
    }

    public void setInfoData(List<BLSearchResultCites> infodata){
        this.mNoDragDatas = infodata;
        notifyDataSetChanged();
    }

    public List<BLSearchResultCites> getData(){
        return this.mDragDatas;
    }

    public List<BLSearchResultCites> getmNoDragDatas(){
        return this.mNoDragDatas;
    }

    public void deleteData(int position) {
        mDragDatas.remove(position);
        //mInfodata.remove(position);
        isDelete = true;
        notifyDataSetChanged();
    }
}