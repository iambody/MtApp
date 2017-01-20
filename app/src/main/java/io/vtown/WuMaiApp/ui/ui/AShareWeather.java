package io.vtown.WuMaiApp.ui.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.Utilss.SaveUiUtils;
import io.vtown.WuMaiApp.constant.PromptManager;
import io.vtown.WuMaiApp.ui.ABase;

/**
 * Created by datutu on 2017/1/18.
 */

public class AShareWeather extends ABase {
    private static File sdCards = Environment.getExternalStorageDirectory();
    private static final File SaveFile = new File(sdCards + "/mtshare/share.jpg");
    @Bind(R.id.share_share_Iv)
    ImageView shareShareIv;
    @Bind(R.id.share_weixin_lay)
    LinearLayout shareWeixinLay;
    @Bind(R.id.share_quan_lay)
    LinearLayout shareQuanLay;
    @Bind(R.id.share_down_lay)
    LinearLayout shareDownLay;
    @Bind(R.id.shareweather_scrollview)
    ScrollView shareweatherScrollview;
    private int Levell = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareweather);
        ButterKnife.bind(this);
        IBund();
        IBaseVV();
    }

    private void IBund() {
        if (getIntent().getExtras().containsKey("level"))
            Levell = getIntent().getIntExtra("level", 2);
    }

    private void IBaseVV() {
        if (SaveFile.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(SaveFile.getPath());
            //将图片显示到ImageView中
            shareShareIv.setImageBitmap(bm);
        }

//

    }

    @Override
    protected void onPause() {
        super.onPause();
        PromptManager.closeLoading();
    }

    @OnClick({R.id.share_weixin_lay, R.id.share_quan_lay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_weixin_lay:
//                shareDownLay.setVisibility(View.GONE);
                PromptManager.showLoading(BaseContext);
                SaveUiUtils.SaveScrollView(shareweatherScrollview, true,GetLevelColeo(Levell,BaseActiviy),BaseActiviy);
                Share(true);
                break;
            case R.id.share_quan_lay:
//                shareDownLay.setVisibility(View.GONE);
                PromptManager.showLoading(BaseContext);
                SaveUiUtils.SaveScrollView(shareweatherScrollview, true,GetLevelColeo(Levell,BaseActiviy),BaseActiviy);
                Share(false);
                break;
        }
    }

    private void Share(boolean haoyou) {
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
//    private void ShareWeather(int Type) {
//        ShareSDK.initSDK(BaseContext);
//        if (!ViewUtils.isWeixinAvilible(BaseContext)) {
//            PromptManager.ShowCustomToast(BaseContext, "请先安装手机微信");
//            return;
//        }
//        Platform platform = null;
//        Platform.ShareParams sp = new Platform.ShareParams();
//        switch (Type) {
//            case 1:// 好友分享
//                platform = ShareSDK.getPlatform(BaseContext, Wechat.NAME);
//
//                sp.setShareType(Platform.SHARE_IMAGE);
//
//
//                sp.setText("测试ss");
//                sp.setImageUrl(SaveFile.getPath());
//                sp.setImagePath(SaveFile.getPath());
//                sp.setTitle("测试");//
////                sp.setUrl(SaveFile.getPath());
//                break;
//            case 2:// 朋友圈分享
//                platform = ShareSDK.getPlatform(BaseContext, WechatMoments.NAME);
//
//                sp.setShareType(Platform.SHARE_IMAGE);
//
//                // sp.setText("大兔兔的测试数据");
//                // sp.setImageUrl("http://static.freepik.com/free-photo/letter-a-underlined_318-8682.jpg");
//                // sp.setTitle("大兔兔的测试数据");//
//                // sp.setUrl("www.baidu.com");
//                sp.setText("测试ss");
//                sp.setImageUrl(SaveFile.getPath());
//                sp.setImagePath(SaveFile.getPath());
//                sp.setTitle("测试");//
////                sp.setUrl(SaveFile.getPath());
//                break;
//            default:
//                break;
//        }
//        platform.setPlatformActionListener(new PlatformActionListener() {
//
//            @Override
//            public void onError(Platform arg0, int arg1, Throwable arg2) {
//                PromptManager.ShowCustomToast(BaseContext, "失败"+arg1);
//            }
//
//            @Override
//            public void onComplete(Platform arg0, int arg1,
//                                   HashMap<String, Object> arg2) {
//                PromptManager.ShowCustomToast(BaseContext, "成功");
//            }
//
//            @Override
//            public void onCancel(Platform arg0, int arg1) {
//                PromptManager.ShowCustomToast(BaseContext, "取消"+arg1);
//            }
//        });
//        platform.share(sp);
//    }
}
