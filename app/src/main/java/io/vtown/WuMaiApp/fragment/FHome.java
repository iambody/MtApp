package io.vtown.WuMaiApp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.DimensionPixelUtil;
import io.vtown.WuMaiApp.module.BAqi;
import io.vtown.WuMaiApp.module.BHome;
import io.vtown.WuMaiApp.module.BMessage;
import io.vtown.WuMaiApp.view.custom.HomeScrollView;

/**
 * Created by datutu on 2017/1/16.
 */

public class FHome extends FLazy implements HomeScrollView.OnScrollListener {
    @Bind(R.id.fragment_home_homescrollview)
    HomeScrollView fragmentHomeHomescrollview;
    @Bind(R.id.fragment_hscrollview_lay)
    LinearLayout fragmentHscrollviewLay;
    @Bind(R.id.fragment_up_view_lay)
    RelativeLayout fragmentUpViewLay;

    @Override
    protected void create(Bundle Mybundle) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_weather;
    }

    @Override
    protected void initViewsAndEvents(View view) {

    }

    @Override
    protected void onFirstUserVisible() {
        IbaseView();
    }

    private void IbaseView() {
        fragmentHomeHomescrollview.setScrolListener(this);
        LinearLayout.LayoutParams inps = new LinearLayout.LayoutParams(screenWidth, screenHeight - DimensionPixelUtil.dip2px(FBaseActivity, 30));
        fragmentUpViewLay.setLayoutParams(inps);


    }

    public void FrashView(BHome home) {
        for (int i = 0; i < home.getList().size(); i++) {
            BAqi MyAqi = home.getList().get(i);
            View ItemView = LayoutInflater.from(FBaseActivity).inflate(R.layout.item_home_hscrollview, null);
            ImageView Itemiv = (ImageView) ItemView.findViewById(R.id.item_home_hscrollview_iv);
            TextView ItemTxt = (TextView) ItemView.findViewById(R.id.item_home_hscrollview_txt);
            Itemiv.setImageResource(R.mipmap.ic_launcher);
            ItemTxt.setText(MyAqi.getHour());
            fragmentHscrollviewLay.addView(ItemView);

        }
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void DetoryViewAndThing() {

    }

    @Override
    public void onScroll(int scrollY) {
        BMessage bMessage = new BMessage(BMessage.Tage_HomeScrollY);
        bMessage.setHomeScrollY(scrollY);
        EventBus.getDefault().post(bMessage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
