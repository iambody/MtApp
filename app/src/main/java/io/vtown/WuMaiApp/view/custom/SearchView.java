package io.vtown.WuMaiApp.view.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.NetUtil;
import io.vtown.WuMaiApp.Utilss.StrUtils;
import io.vtown.WuMaiApp.constant.Constans;
import io.vtown.WuMaiApp.constant.PromptManager;
import io.vtown.WuMaiApp.constant.Spuit;
import io.vtown.WuMaiApp.module.BMessage;
import io.vtown.WuMaiApp.module.cites.BLSearchResultCites;
import io.vtown.WuMaiApp.ui.ui.AAddCity;

/**
 * Created by Yihuihua on 2017/1/16.
 */

public class SearchView extends LinearLayout implements View.OnClickListener {

    /**
     * 输入框
     */
    private EditText etInput;

    /**
     * 删除键
     */
    private ImageView ivDelete;

    /**
     * 返回按钮
     */
    private ImageView btnBack;

    /**
     * 上下文对象
     */
    private Context mContext;

    /**
     * 弹出列表
     */
    private ListView lvTips;

    /**
     * 提示adapter （推荐adapter）
     */
    private AAddCity.HistorySearchAdapter mHintAdapter;

    private BaseAdapter mResultAdapter;

    /**
     * 自动补全adapter 只显示名字
     */
    private ArrayAdapter<String> mAutoCompleteAdapter;

    /**
     * 搜索回调接口
     */
    private SearchViewListener mListener;
    private ImageView iv_search;
    private LinearLayout history_search_layout;
    private ListView city_lv_history_search;
    private LinearLayout delete_all_history;
    private ListView city_lv_search_results;


    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        initViews();
    }

    private void initViews() {
        etInput = (EditText) findViewById(R.id.search_et_input);
        ivDelete = (ImageView) findViewById(R.id.search_iv_delete);
        btnBack = (ImageView) findViewById(R.id.search_btn_back);
        lvTips = (ListView) findViewById(R.id.search_lv_tips);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        city_lv_search_results = (ListView) findViewById(R.id.city_lv_search_results);

        history_search_layout = (LinearLayout) findViewById(R.id.history_search_layout);
        city_lv_history_search = (ListView) findViewById(R.id.city_lv_history_search);
        delete_all_history = (LinearLayout) findViewById(R.id.delete_all_history);

        delete_all_history.setOnClickListener(this);


        city_lv_search_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BLSearchResultCites item = (BLSearchResultCites) city_lv_search_results.getAdapter().getItem(i);
                BMessage message = new BMessage(BMessage.Tage_Select_City);
                EventBus.getDefault().post(message);
                List<BLSearchResultCites> history_city = Spuit.Search_City_History_Get(mContext);
                checkHistoryCity(history_city, item);
                List<BLSearchResultCites> Cites = Spuit.Location_City_Get(mContext);
                BLSearchResultCites fristcity = Spuit.BaiDuMap_Location_Get(mContext);
                checkCity(Cites, item, fristcity);
                if (mListener != null) {
                    mListener.onClickResultItem(item);
                }

            }


        });

        city_lv_history_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String txt = city_lv_history_search.getAdapter().getItem(i).toString();
                if (!NetUtil.isConnected(mContext)) {
                    PromptManager.ShowCustomToast(mContext,
                            mContext.getString(R.string.check_net));
                    return;
                }
                BLSearchResultCites item = (BLSearchResultCites) city_lv_history_search.getAdapter().getItem(i);
                BMessage message = new BMessage(BMessage.Tage_Select_City);
                EventBus.getDefault().post(message);
                List<BLSearchResultCites> Cites = Spuit.Location_City_Get(mContext);
                BLSearchResultCites fristcity = Spuit.BaiDuMap_Location_Get(mContext);
                checkCity(Cites, item, fristcity);
                if (mListener != null) {
                    mListener.onClickResultItem(item);
                }
                lvTips.setVisibility(View.GONE);
                //history_search_layout.setVisibility(View.GONE);

            }
        });

        lvTips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = lvTips.getAdapter().getItem(i).toString();
                etInput.setText(text);
                etInput.setSelection(text.length());
                lvTips.setVisibility(View.GONE);
                history_search_layout.setVisibility(View.GONE);
                notifyStartSearching(text);
            }
        });

        ivDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        etInput.addTextChangedListener(new EditChangedListener());
        // etInput.setOnClickListener(this);
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    lvTips.setVisibility(GONE);
                    history_search_layout.setVisibility(View.GONE);
                    notifyStartSearching(etInput.getText().toString());
                }
                return true;
            }
        });
        //搜索图标
        iv_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetUtil.isConnected(mContext)) {
                    PromptManager.ShowCustomToast(mContext,
                            mContext.getString(R.string.check_net));
                    return;
                }
                String txt = etInput.getText().toString().trim();
                if (StrUtils.isEmpty(txt)) {
                    Toast.makeText(mContext, "请输入你要定位的城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                lvTips.setVisibility(GONE);
                history_search_layout.setVisibility(View.GONE);
                notifyStartSearching(txt);
            }
        });
    }


    private void checkHistoryCity(List<BLSearchResultCites> history_city, BLSearchResultCites item) {
        if (history_city.size() > 0) {
            boolean flag = false;
            for (int i = 0; i < history_city.size(); i++) {
                if (!history_city.get(i).getAreaid().equals(item.getAreaid())) {
                    flag = false;
                } else {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (history_city.size() == Constans.History_City_Count) {
                    history_city.remove(0);
                }
                history_city.add(item);
            }
        } else {
            history_city.add(item);
        }
        Spuit.Search_City_History_Save(mContext, history_city);
    }


    private void checkCity(List<BLSearchResultCites> mCites, BLSearchResultCites blSearchResultCites, BLSearchResultCites fristcity) {
        if (mCites.size() > 0) {
            boolean flag = false;
            for (int i = 0; i < mCites.size(); i++) {
                if (!mCites.get(i).getAreaid().equals(blSearchResultCites.getAreaid()) && !fristcity.getAreaid().equals(blSearchResultCites.getAreaid())) {
                    flag = false;
                } else {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (mCites.size() == Constans.City_Count) {//如果保存已达到上限，删除第一个再添加
                    mCites.remove(0);
                }
                mCites.add(blSearchResultCites);
            }

        } else {
            if (!fristcity.getAreaid().equals(blSearchResultCites.getAreaid())) {
                mCites.add(blSearchResultCites);
            }
        }
        Spuit.Location_City_Save(mContext, mCites);
    }

    /**
     * 通知监听者 进行搜索操作
     *
     * @param text
     */
    private void notifyStartSearching(String text) {
        if (mListener != null) {
            mListener.onSearch(text);
        }

        city_lv_search_results.setVisibility(View.VISIBLE);
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置热搜版提示 adapter
     */
    public void setTipsHintAdapter(AAddCity.HistorySearchAdapter adapter) {
        this.mHintAdapter = adapter;
        if (city_lv_history_search.getAdapter() == null) {
            city_lv_history_search.setAdapter(mHintAdapter);
        }
    }

    public void setHistoryShow(boolean isshow) {
        if (isshow) {
            history_search_layout.setVisibility(View.VISIBLE);
        } else {
            history_search_layout.setVisibility(View.GONE);
        }
    }

    public void setResultAdapter(BaseAdapter adapter) {
        this.mResultAdapter = adapter;
        if (city_lv_search_results.getAdapter() == null) {
            city_lv_search_results.setAdapter(mResultAdapter);
        }
    }

    /**
     * 设置自动补全adapter
     */
    public void setAutoCompleteAdapter(ArrayAdapter<String> adapter) {
        this.mAutoCompleteAdapter = adapter;
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!NetUtil.isConnected(mContext)) {
                PromptManager.ShowCustomToast(mContext,
                        mContext.getString(R.string.check_net));
                return;
            }
            if (!"".equals(charSequence.toString())) {
                history_search_layout.setVisibility(View.GONE);
                ivDelete.setVisibility(VISIBLE);
                lvTips.setVisibility(GONE);
                city_lv_search_results.setVisibility(View.VISIBLE);
//                if (mAutoCompleteAdapter != null && lvTips.getAdapter() != mAutoCompleteAdapter) {
//                    lvTips.setAdapter(mAutoCompleteAdapter);
//                }
                //更新autoComplete数据
                if (mListener != null) {
                    mListener.onRefreshAutoComplete(charSequence + "");
                }
            } else {
                ivDelete.setVisibility(GONE);
                city_lv_search_results.setVisibility(View.GONE);
                if (mHintAdapter.isHaveData()) {
                    history_search_layout.setVisibility(View.VISIBLE);
                } else {
                    history_search_layout.setVisibility(View.GONE);
                }

                lvTips.setVisibility(GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_et_input:
                String input = etInput.getText().toString().trim();
                if (StrUtils.isEmpty(input)) {
                    lvTips.setVisibility(GONE);
                    if (mHintAdapter.isHaveData()) {
                        history_search_layout.setVisibility(View.VISIBLE);
                    } else {
                        history_search_layout.setVisibility(View.GONE);
                    }

                    city_lv_search_results.setVisibility(View.GONE);
                } else {
                    history_search_layout.setVisibility(View.GONE);
                    lvTips.setVisibility(GONE);
                }

                break;
            case R.id.search_iv_delete:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
                if (mHintAdapter.isHaveData()) {
                    history_search_layout.setVisibility(View.VISIBLE);
                } else {
                    history_search_layout.setVisibility(View.GONE);
                }
                city_lv_search_results.setVisibility(View.GONE);
                break;
            case R.id.search_btn_back:
                ((Activity) mContext).finish();
                break;

            case R.id.delete_all_history:
                mHintAdapter.removeData();
                Spuit.Search_City_History_Save(mContext, new ArrayList<BLSearchResultCites>());
                history_search_layout.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * search view回调方法
     */
    public interface SearchViewListener {

        /**
         * 更新自动补全内容
         *
         * @param text 传入补全后的文本
         */
        void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);


        void onClickResultItem(BLSearchResultCites item);

        //        /**
        //         * 提示列表项点击时回调方法 (提示/自动补全)
        //         */
        //        void onTipsItemClick(String text);
    }

}
