package io.vtown.WuMaiApp.view.custom;

import android.app.Activity;
import android.content.Context;
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
import io.vtown.WuMaiApp.Utilss.StrUtils;
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
                message.setmCity(item);
                EventBus.getDefault().post(message);
                if (mListener != null) {
                    mListener.onClickResultItem(item);
                }
            }
        });

        city_lv_history_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String txt = city_lv_history_search.getAdapter().getItem(i).toString();
                etInput.setText(txt);
                etInput.setSelection(txt.length());
                lvTips.setVisibility(View.GONE);
                history_search_layout.setVisibility(View.GONE);
                notifyStartSearching(txt);
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
            if (!"".equals(charSequence.toString())) {
                history_search_layout.setVisibility(View.GONE);
                ivDelete.setVisibility(VISIBLE);
                lvTips.setVisibility(VISIBLE);
                city_lv_search_results.setVisibility(View.GONE);
                if (mAutoCompleteAdapter != null && lvTips.getAdapter() != mAutoCompleteAdapter) {
                    lvTips.setAdapter(mAutoCompleteAdapter);
                }
                //更新autoComplete数据
                if (mListener != null) {
                    mListener.onRefreshAutoComplete(charSequence + "");
                }
            } else {
                ivDelete.setVisibility(GONE);
                city_lv_search_results.setVisibility(View.GONE);
                if(mHintAdapter.isHaveData()){
                    history_search_layout.setVisibility(View.VISIBLE);
                }else{
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
                    if(mHintAdapter.isHaveData()){
                        history_search_layout.setVisibility(View.VISIBLE);
                    }else{
                        history_search_layout.setVisibility(View.GONE);
                    }

                    city_lv_search_results.setVisibility(View.GONE);
                } else {
                    history_search_layout.setVisibility(View.GONE);
                    lvTips.setVisibility(VISIBLE);
                }

                break;
            case R.id.search_iv_delete:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
                if(mHintAdapter.isHaveData()){
                    history_search_layout.setVisibility(View.VISIBLE);
                }else{
                    history_search_layout.setVisibility(View.GONE);
                }
                city_lv_search_results.setVisibility(View.GONE);
                break;
            case R.id.search_btn_back:
                ((Activity) mContext).finish();
                break;

            case R.id.delete_all_history:
                mHintAdapter.removeData();
                Spuit.Search_City_History_Save(mContext, new ArrayList<String>());
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
