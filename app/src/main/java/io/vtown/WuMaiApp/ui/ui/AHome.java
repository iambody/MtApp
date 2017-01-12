package io.vtown.WuMaiApp.ui.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.ui.ABase;
import io.vtown.WuMaiApp.view.homeslid.MyScrollView;
import io.vtown.WuMaiApp.view.homeslid.SlidingDetailsLayout;

/**
 * Created by datutu on 2017/1/12.
 */

public class AHome extends ABase {
    @Bind(R.id.test)
    Button test;
    @Bind(R.id.home_up_scrollview)
    MyScrollView homeUpScrollview;
    @Bind(R.id.home_scroll_note)
    TextView homeScrollNote;
    @Bind(R.id.home_down_scrollview)
    MyScrollView homeDownScrollview;
    @Bind(R.id.home_slidinghomeLayout)
    SlidingDetailsLayout homeSlidinghomeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        IBaseView();
    }


    private void IBaseView() {

        homeSlidinghomeLayout.setPositionChangListener(new SlidingDetailsLayout.PositionChangListener() {
            @Override
            public void position(int positon) {
                if (positon == 0) {
                    homeScrollNote.setText("上拉查看详情");

                } else {
                    homeScrollNote.setText("下拉返回主页");

                }
            }

            @Override
            public void onBottom() {
                homeScrollNote.setText("松开查看详情");
            }

            @Override
            public void backBottom() {
                homeScrollNote.setText("上拉查看详情");
            }

            @Override
            public void onTop() {
                homeScrollNote.setText("松开返回主页");
            }

            @Override
            public void backTop() {
                {
                    homeScrollNote.setText("下拉返回主页");
                }
            }

        });
    }

}
