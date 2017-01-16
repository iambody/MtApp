package io.vtown.WuMaiApp.ui.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vtown.WuMaiApp.R;
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
    private List<String> mDataList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        mDataList.add("条目1");
        mDataList.add("条目2");
        mDataList.add("条目3");
        mDataList.add("条目4");
        mDataList.add("条目5");
        initView();

    }

    private void initView() {
        MyAdapter mListAdapter = new MyAdapter(this, mDataList);
        dvl_drag_list.setAdapter(mListAdapter);
    }

    @OnClick(R.id.iv_add_city)
    public void onClick() {
        Intent intent = new Intent(BaseContext, AAddCity.class);
        startActivity(intent);
    }

    public class MyAdapter extends DragListViewAdapter<String> {

        public MyAdapter(Context context, List<String> dataList) {
            super(context, dataList);
        }

        @Override
        public View getItemView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(BaseContext).inflate(R.layout.item_drag_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name_drag_list);
                viewHolder.desc = (TextView) convertView.findViewById(R.id.tv_desc_drag_list);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(mDragDatas.get(position));
            String s = mDragDatas.get(position) + "的描述";
            viewHolder.desc.setText(s);
            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView desc;
        }

    }
}
