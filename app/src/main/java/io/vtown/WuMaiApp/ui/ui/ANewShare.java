package io.vtown.WuMaiApp.ui.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vtown.WuMaiApp.MyApplication;
import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.SaveUiUtils;
import io.vtown.WuMaiApp.Utilss.StrUtils;
import io.vtown.WuMaiApp.constant.PromptManager;
import io.vtown.WuMaiApp.module.BAqi;
import io.vtown.WuMaiApp.module.BDetail;
import io.vtown.WuMaiApp.module.BHome;
import io.vtown.WuMaiApp.ui.ABase;
import io.vtown.WuMaiApp.view.custom.CompleteListView;

/**
 * Created by datutu on 2017/1/22.
 */

public class ANewShare extends ABase {
    private static File sdCards = Environment.getExternalStorageDirectory();
    private static final File SaveFile = new File(sdCards + "/mtshare/share.jpg");

    @Bind(R.id.fragment_up_city_namea)
    TextView fragmentUpCityNamea;
    @Bind(R.id.fragment_up_city_levela)
    TextView fragmentUpCityLevela;
    @Bind(R.id.fragment_up_city_level_desca)
    TextView fragmentUpCityLevelDesca;
    @Bind(R.id.fragment_hscrollview_laya)
    LinearLayout fragmentHscrollviewLaya;
    @Bind(R.id.fragment_up_view_laya)
    LinearLayout fragmentUpViewLaya;
    @Bind(R.id.fragment_down_detail_lsa)
    CompleteListView fragmentDownDetailLsa;
    @Bind(R.id.fragment_home_homescrollviewa)
    ScrollView fragmentHomeHomescrollviewa;
    @Bind(R.id.share_weixin_laya)
    LinearLayout shareWeixinLaya;
    @Bind(R.id.share_quan_laya)
    LinearLayout shareQuanLaya;
    @Bind(R.id.share_down_laya)
    LinearLayout shareDownLaya;

    private FragHomeAp myFragHomeAp;
    private BHome myhome;
    private MyApplication BaMyApplication;
private String CityName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newshare);
        BaMyApplication = (MyApplication) getApplication();
        myhome = BaMyApplication.getMyhomedata();
        ButterKnife.bind(this);
        IBUD();
        IBasev();
    }

    private void IBUD() {
        CityName=getIntent().getExtras().getString("city","霾太");
    }


    private void IBasev() {
        myFragHomeAp = new FragHomeAp();
        fragmentDownDetailLsa.setAdapter(myFragHomeAp);
        FrashData(myhome);
    }

    /**
     * 刷新数据
     *
     * @param bHome
     */
    private void FrashData(BHome bHome) {
//        fragmentUpCityName.setText(CityName);
        StrUtils.SetTxt(fragmentUpCityNamea, CityName);
        fragmentUpCityLevela.setText(bHome.getAqi() + "");
        fragmentUpCityLevelDesca.setText(bHome.getAqi_detail());
        FrashHSView(bHome);
        int Level = bHome.getAqi_level();
        SetLevelIv(bHome.getAqi_level(), fragmentHomeHomescrollviewa);
        myFragHomeAp.FrashData(bHome.getSeven_list());


//        SaveBitMapUtiils.SaveBitMap(fragmentUpViewLay);
    }
    public void SetLevelIv(int Level, ScrollView IV) {
        switch (Level) {
            case 0:
                IV.setBackgroundColor(getResources().getColor(R.color.level1));
                break;
            case 1:
                IV.setBackgroundColor(getResources().getColor(R.color.level2));
                break;
            case 2:
                IV.setBackgroundColor(getResources().getColor(R.color.level3));
                break;
            case 3:
                IV.setBackgroundColor(getResources().getColor(R.color.level4));
                break;
            case 4:
                IV.setBackgroundColor(getResources().getColor(R.color.level5));
                break;
            case 5:
                IV.setBackgroundColor(getResources().getColor(R.color.level6));
                break;
            case 6:
                IV.setBackgroundColor(getResources().getColor(R.color.level7));
                break;
        }
    }

    public void FrashHSView(BHome home) {


        for (int i = 0; i < home.getList().size(); i++) {
            BAqi MyAqi = home.getList().get(i);
            View ItemView = LayoutInflater.from(BaseActiviy).inflate(R.layout.item_home_hscrollview, null);
            ImageView Itemiv = (ImageView) ItemView.findViewById(R.id.item_home_hscrollview_iv);
            TextView ItemTxt = (TextView) ItemView.findViewById(R.id.item_home_hscrollview_txt);
            TextView Itemleel = (TextView) ItemView.findViewById(R.id.item_home_hscrollview_level);
            SetLevelIv1ss(MyAqi.getAqi_level(), Itemiv);
            ItemTxt.setText(MyAqi.getHour());
            Itemleel.setText(MyAqi.getAqi() + "");
            fragmentHscrollviewLaya.addView(ItemView);

        }
    }

    private void SetLevelIv1ss(int Level, ImageView IV) {
        {
            switch (Level) {
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

    private class FragHomeAp extends BaseAdapter {
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
                convertView = LayoutInflater.from(BaseActiviy).inflate(R.layout.item_fragment_detail, null);
                myItem = new FMyItem();
                myItem.item_fragment_detail_up_txt = (TextView) convertView.findViewById(R.id.item_fragment_detail_up_txt);
                myItem.item_fragment_detail_v1 = convertView.findViewById(R.id.item_fragment_detail_v1);
                myItem.item_fragment_detail_v2 = convertView.findViewById(R.id.item_fragment_detail_v2);
                myItem.item_fragment_detail_v3 = convertView.findViewById(R.id.item_fragment_detail_v3);
                myItem.item_fragment_detail_v4 = convertView.findViewById(R.id.item_fragment_detail_v4);
                myItem.item_fragment_detail_v5 = convertView.findViewById(R.id.item_fragment_detail_v5);
                myItem.item_fragment_detail_v6 = convertView.findViewById(R.id.item_fragment_detail_v6);
                myItem.item_fragment_detail_v7 = convertView.findViewById(R.id.item_fragment_detail_v7);
                myItem.item_fragment_detail_v8 = convertView.findViewById(R.id.item_fragment_detail_va);
                myItem.item_fragment_detail_vline= (ImageView) convertView.findViewById(R.id.item_fragment_detail_vline);
                convertView.setTag(myItem);
            } else {
                myItem = (FMyItem) convertView.getTag();
            }
            BDetail ItemData = DownDatas.get(position);
            StrUtils.SetTxt(myItem.item_fragment_detail_up_txt, ItemData.getDate());
            ItemEightData(ItemData, 0, myItem.item_fragment_detail_v1);
            ItemEightData(ItemData, 1, myItem.item_fragment_detail_v2);
            ItemEightData(ItemData, 2, myItem.item_fragment_detail_v3);
            ItemEightData(ItemData, 3, myItem.item_fragment_detail_v4);
            ItemEightData(ItemData, 4, myItem.item_fragment_detail_v5);
            ItemEightData(ItemData, 5, myItem.item_fragment_detail_v6);
            ItemEightData(ItemData, 6, myItem.item_fragment_detail_v7);
//            ItemEightData(ItemData, 7, myItem.item_fragment_detail_v8);

            ItemEightData(ItemData, ItemData.getList().size()-1, myItem.item_fragment_detail_v8);

            if (ItemData.getList().size() == 7) {
                myItem.item_fragment_detail_v8.setVisibility(View.GONE);
                myItem.item_fragment_detail_vline.setVisibility(View.GONE);
            }else{
                myItem.item_fragment_detail_v8.setVisibility(View.VISIBLE);
                myItem.item_fragment_detail_vline.setVisibility(View.VISIBLE);
            } ;//
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
            ImageView item_fragment_detail_vline;
        }
    }


    @OnClick({R.id.share_weixin_laya, R.id.share_quan_laya})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_weixin_laya:
//                PromptManager.showtextLoading(BaseActiviy,"分享中");
                SaveUiUtils.SaveScrollView(fragmentHomeHomescrollviewa, true,GetLevelColeo(myhome.getAqi_level(),BaseActiviy),BaseActiviy);
                Share(true);
                break;
            case R.id.share_quan_laya:
//                PromptManager.showtextLoading(BaseActiviy,"分享中");
                SaveUiUtils.SaveScrollView(fragmentHomeHomescrollviewa, true,GetLevelColeo(myhome.getAqi_level(),BaseActiviy),BaseActiviy);

                Share(false);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PromptManager.closeLoading();
    }

    private void Share(boolean haoyou) {
        PromptManager.showLoading(BaseActiviy);
        Uri uriToImage = Uri.fromFile(SaveFile);
        Intent shareIntent = new Intent();
        //发送图片到朋友圈
        //ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        //发送图片给好友。
        ComponentName comp = new ComponentName("com.tencent.mm", haoyou ? "com.tencent.mm.ui.tools.ShareImgUI" : "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "分享图片"));
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
            case 7:
                IV.setImageResource(R.mipmap.point_level7);
                break;

        }

    }
}
