package io.vtown.WuMaiApp.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import io.vtown.WuMaiApp.R;

/**
 * Created by datutu on 16/10/20.
 */

public class FBase extends Fragment {
    /**
     * 屏幕宽度
     */
    protected int screenWidth;
    /**
     * 屏幕高度
     */
    protected int screenHeight;


    public void SetLevelIv(int Level, ImageView IV) {
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

    public void SetLevelIv(int Level, LinearLayout IV) {
        switch (Level) {
            case 0:
                IV.setBackgroundResource(R.mipmap.home_bg1);
                break;
            case 1:
                IV.setBackgroundResource(R.mipmap.home_bg2);
                break;
            case 2:
                IV.setBackgroundResource(R.mipmap.home_bg3);
                break;
            case 3:
                IV.setBackgroundResource(R.mipmap.home_bg4);
                break;
            case 4:
                IV.setBackgroundResource(R.mipmap.home_bg5);
                break;
            case 5:
                IV.setBackgroundResource(R.mipmap.home_bg6);
                break;
            case 6:
                IV.setBackgroundResource(R.mipmap.home_bg7);
                break;
        }
    }

}
