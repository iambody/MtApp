package io.vtown.WuMaiApp.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.NetUtil;
import io.vtown.WuMaiApp.constant.PromptManager;
import io.vtown.WuMaiApp.interf.IDialogResult;
import io.vtown.WuMaiApp.view.dialog.CustomDialog;

/**
 * Created by datutu on 2017/1/10.
 */

public class ABase extends AppCompatActivity {
    /**
     * 基上下文
     */
    protected Context BaseContext;
    protected Activity BaseActiviy;
    /**
     * 屏幕宽度
     */
    protected int screenWidth;
    /**
     * 屏幕高度
     */
    protected int screenHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigBase();
    }

    private void ConfigBase() {
        this.BaseContext = this;
        this.BaseActiviy = this;
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
    }

    /**
     * 部分点击前检查网络
     */
    public boolean CheckNet(Context mContext) {
        if (!NetUtil.isConnected(mContext)) {
            PromptManager.ShowCustomToast(mContext,
                    mContext.getString(R.string.check_net));
            return true;
        } else
            return false;

    }




    /**
     * 左右选择弹出框的封装
     */

    public void ShowCustomDialog(String title, String Left, String Right,
                                 final IDialogResult mDialogResult) {
        final CustomDialog dialog = new CustomDialog(BaseContext,
                R.style.mystyle, R.layout.dialog_purchase_cancel, 1, Left,
                Right);
        dialog.show();
        dialog.setTitleText(title);
        dialog.HindTitle2();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setcancelListener(new CustomDialog.oncancelClick() {

            @Override
            public void oncancelClick(View v) {
                dialog.dismiss();
                mDialogResult.LeftResult();
            }
        });

        dialog.setConfirmListener(new CustomDialog.onConfirmClick() {
            @Override
            public void onConfirmCLick(View v) {
                dialog.dismiss();
                mDialogResult.RightResult();
            }
        });

    }

    public void SetLevelIv(int Level, RelativeLayout IV) {
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

    /**
     * 根据level获取对应的色值
     */
    public int GetLevelColeo(int level,Context cn) {
        switch (level) {
            case 0:
                return cn.getResources().getColor(R.color.level1);

            case 1:
                return cn.getResources().getColor(R.color.level2);

            case 2:
                return cn.getResources().getColor(R.color.level3);

            case 3:
                return cn.getResources().getColor(R.color.level4);

            case 4:
                return cn.getResources().getColor(R.color.level5);

            case 5:
                return cn.getResources().getColor(R.color.level6);

            case 6:
                return cn.getResources().getColor(R.color.level7);

            default:
                return cn.getResources().getColor(R.color.level2);


        }
    }
}
