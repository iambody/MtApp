package io.vtown.WuMaiApp.ui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;

import org.greenrobot.eventbus.EventBus;

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
import io.vtown.WuMaiApp.view.custom.SearchView;

/**
 * Created by Yihuihua on 2017/1/16.
 */
public class AAddCity extends ABase implements SearchView.SearchViewListener {

    @Bind(R.id.city_search_layout)
    SearchView citySearchLayout;
    //@Bind(R.id.city_lv_search_results)
    //ListView cityLvSearchResults;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 15;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;
    // @Bind(R.id.city_lv_history_search)
    // ListView cityLvHistorySearch;
    // @Bind(R.id.history_search_layout)
    //LinearLayout historySearchLayout;
    // @Bind(R.id.delete_all_history)
    // LinearLayout deleteAllHistory;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;
    /*
    * 获取的结果数据类型
    * */
    private static final int TYPE_RESULT_DATA = 11;

    /*
    * 获取的自动填充数据类型
    * */
    private static final int TYPE_AOTO_DATA = 12;

    private List<BLSearchResultCites> resultData;
    // private List<BLSearchResultCites> dbData;
    private SearchResultAdapter resultAdapter;
    private List<String> hintData;
    private HistorySearchAdapter hintAdapter;
    private List<String> autoCompleteData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ButterKnife.bind(this);

        initData();
        initView();
    }


    private void initView() {
        citySearchLayout.setSearchViewListener(this);
        citySearchLayout.setTipsHintAdapter(hintAdapter);

        //citySearchLayout.setTipsHintAdapter(hintAdapter);
        citySearchLayout.setAutoCompleteAdapter(autoCompleteAdapter);

        citySearchLayout.setResultAdapter(resultAdapter);

//        cityLvSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                BLSearchResultCites item = (BLSearchResultCites) resultAdapter.getItem(position);
//                BMessage message = new BMessage(BMessage.Tage_Select_City);
//                message.setmCity(item);
//                EventBus.getDefault().post(message);
//                Toast.makeText(BaseContext, position + "", Toast.LENGTH_SHORT).show();
//                AAddCity.this.finish();
//            }
//        });
    }

    private void initData() {
        hintData = Spuit.Search_City_History_Get(BaseContext);
        if(hintData.size() == 0 || hintData == null){
            citySearchLayout.setHistoryShow(false);
        }else{
            citySearchLayout.setHistoryShow(true);
        }
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    private void getResultData(List<BLSearchResultCites> data) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            resultData.addAll(data);
        }
        if (resultAdapter == null) {
            resultAdapter = new SearchResultAdapter(resultData, R.layout.item_result_list);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取自动补全adapter
     */
    private void getAutoCompleteData(List<BLSearchResultCites> data) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < data.size()
                    && count < hintSize; i++) {
                autoCompleteData.add(data.get(i).getAreaname());
                count++;
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, R.layout.item_aotu, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    private void getAreaData(final String data, int count, final int type) {
        if (StrUtils.isEmpty(data)) {
            return;
        }
        NHttpBaseStr BaNHttpBaseStr = new NHttpBaseStr(BaseContext);
        BaNHttpBaseStr.setPostResult(new IHttpResult<String>() {
            @Override
            public void getResult(int Code, String Msg, String Data) {
                if (StrUtils.isEmpty(Data)) {
                    return;
                }

                List<BLSearchResultCites> returnData = JSON.parseArray(Data, BLSearchResultCites.class);
                if (returnData.size() > 0) {
                    switch (type) {
                        case TYPE_RESULT_DATA:
                            getResultData(returnData);
                            break;
                        case TYPE_AOTO_DATA:
                            getAutoCompleteData(returnData);
                            break;
                    }
                }
                PromptManager.ShowCustomToast(BaseContext, Data);
            }

            @Override
            public void onError(String error, int LoadType) {
                PromptManager.ShowCustomToast(BaseContext, error);
            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("keyword", data);
        map.put("count", count + "");
        BaNHttpBaseStr.getData(Constans.GetCityId, map, Request.Method.GET);
    }

    private void getHintData() {

        if (hintAdapter == null) {
            // historySearchLayout.setVisibility(View.VISIBLE);
            hintAdapter = new HistorySearchAdapter(hintData, R.layout.item_search_hint);
            //cityLvHistorySearch.setAdapter(hintAdapter);
        } else {
            Spuit.Search_City_History_Save(BaseContext, hintData);
            //historySearchLayout.setVisibility(View.GONE);
            hintAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRefreshAutoComplete(String text) {
        getAreaData(text, hintSize, TYPE_AOTO_DATA);
    }

    @Override
    public void onSearch(String text) {
        if (!hintData.contains(text)) {
            hintData.add(text);
            getHintData();
        }
        //更新result数据
        getAreaData(text, 20, TYPE_RESULT_DATA);
//        //getResultData(text);
//        cityLvSearchResults.setVisibility(View.VISIBLE);
//        //第一次获取结果 还未配置适配器
//        if (cityLvSearchResults.getAdapter() == null) {
//            //获取搜索数据 设置适配器
//            cityLvSearchResults.setAdapter(resultAdapter);
//        } else {
//            //更新搜索数据
//            resultAdapter.notifyDataSetChanged();
//        }


        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickResultItem(BLSearchResultCites item) {
        //Toast.makeText(BaseContext, position + "", Toast.LENGTH_SHORT).show();
        AAddCity.this.finish();
    }


//    @OnClick(R.id.delete_all_history)
//    public void onClick() {
//
//        //清空历史记录
//
//
//    }

    class SearchResultAdapter extends BaseAdapter {

        private List<BLSearchResultCites> datas = new ArrayList<BLSearchResultCites>();
        private int layout;
        private LayoutInflater inflater;

        public SearchResultAdapter(List<BLSearchResultCites> data, int layout) {
            super();
            this.datas = data;
            this.layout = layout;
            this.inflater = LayoutInflater.from(BaseContext);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SearchResultHolder holder = null;
            if (null == view) {
                view = inflater.inflate(layout, null);
                holder = new SearchResultHolder(view);
                view.setTag(holder);
            } else {
                holder = (SearchResultHolder) view.getTag();
            }

            StrUtils.SetTxt(holder.tvSearchResultCityname, datas.get(i).getAreaname());

            return view;
        }


    }

    class SearchResultHolder {
        @Bind(R.id.tv_search_result_cityname)
        TextView tvSearchResultCityname;

        SearchResultHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


   public class HistorySearchAdapter extends BaseAdapter {

        private List<String> datas = new ArrayList<String>();
        private int layoutId;
        private LayoutInflater inflater;

        public HistorySearchAdapter(List<String> datas, int layoutId) {
            super();
            this.datas = datas;
            this.layoutId = layoutId;
            this.inflater = LayoutInflater.from(BaseContext);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

       public void removeData(){
           for(int i =0 ;i < datas.size();i++){
               datas.remove(i);
           }
           notifyDataSetChanged();
       }

       public boolean isHaveData(){
           if(datas.size() == 0){
               return false;
           }else{
               return true;
           }
       }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HistoryHolder holder = null;

            if (null == view) {
                view = inflater.inflate(layoutId, null);
                holder = new HistoryHolder(view);
                view.setTag(holder);
            } else {
                holder = (HistoryHolder) view.getTag();
            }

            StrUtils.SetTxt(holder.tvSearchHistoryCityname, datas.get(i));
            final int position = i;
            holder.ivDeleteHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datas.remove(position);
                    notifyDataSetChanged();
                    if (datas.size() == 0) {
                        // historySearchLayout.setVisibility(View.GONE);
                        Spuit.Search_City_History_Save(BaseContext, datas);
                    }
                }
            });
            return view;
        }


    }

    class HistoryHolder {
        @Bind(R.id.tv_search_history_cityname)
        TextView tvSearchHistoryCityname;
        @Bind(R.id.iv_delete_history)
        ImageView ivDeleteHistory;

        HistoryHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
