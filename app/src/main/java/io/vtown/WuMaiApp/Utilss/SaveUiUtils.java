package io.vtown.WuMaiApp.Utilss;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Environment;
import android.view.View;
import android.widget.ScrollView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.view.custom.HomeScrollView;

/**
 * Created by datutu on 2017/1/18.
 */

public class SaveUiUtils {
    private static File sdCards = Environment.getExternalStorageDirectory();
    private static final File SaveFile = new File(sdCards + "/mtshare");

    public static Bitmap snapShotWithoutStatusBar(Activity activity) {

        View view = activity.getWindow().getDecorView();

        view.setDrawingCacheEnabled(true);

        view.buildDrawingCache();

        Bitmap bmp = view.getDrawingCache();

        Rect frame = new Rect();

        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        int statusBarHeight = frame.top;

        int width = ScreenUtils.getScreenWidth(activity);

        int height = ScreenUtils.getScreenHeight(activity);

        Bitmap bp = null;

        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height

                - statusBarHeight);

        view.destroyDrawingCache();

        return bp;

    }

    public static void SaveScreen(Activity activity) {
        savePic(snapShotWithoutStatusBar(activity));
    }

    public static void savePic(Bitmap bitmap) {

//时间格式​

        final String FORMAT_PICTURE_NAME = "yyyyMMdd_HHmmss";


// 将时间作为pic的Name

        String picName = "share.jpg";
        if (SaveFile.exists()) {
            RecursionDeleteFile(SaveFile);
        } else {
            SaveFile.mkdir();
        }
// 使用此流读取

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

// 第二个参数影响的是图片的质量，但是图片的宽度与高度是不会受影响滴

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

// 这个函数能够设定图片的宽度与高度

// Bitmap map = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

// 把数据转为为字节数组

        byte[] byteArray = baos.toByteArray();

        try {

// 获取输入路径

            FileOutputStream fos = new FileOutputStream(SaveFile + File.separator + picName);

            BufferedOutputStream bos = new BufferedOutputStream(fos);

//向file中写数据​

            bos.write(byteArray);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                baos.close();

            } catch (IOException e) {

// TODO Auto-generated catch block

                e.printStackTrace();

            }

        }

    }

    public static void RecursionDeleteFile(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    RecursionDeleteFile(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        RecursionDeleteFile(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
//***********************************************************************************************************************

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getScrollViewBitmap(ScrollView scrollView, String picpath, boolean isset,int Colo,Context xxx) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
          if(isset)  scrollView.getChildAt(i).setBackgroundColor(
                    Colo);
//                    Color.parseColor("#00bfff"));
        }
//        if (isset) scrollView.setBackgroundResource(R.mipmap.home_bg2);
//        Log.d(TAG, "实际高度:" + h);
//        Log.d(TAG, " 高度:" + scrollView.getHeight());
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        return bitmap;
    }


    // 程序入口 截取ScrollView
    public static void SaveScrollView(ScrollView scrollView, boolean IsSet, int colore, Context xx) {
        ScrollView scrollView1s=new HomeScrollView(xx);
        scrollView1s=scrollView;
        String picName = "share.jpg";
        if (SaveFile.exists()) {
            RecursionDeleteFile(SaveFile);
        } else {
            SaveFile.mkdir();
        }

        savePicss(getScrollViewBitmap(scrollView1s, SaveFile + File.separator + picName,IsSet,colore ,xx), SaveFile + File.separator + picName);
    }

    // 保存到sdcard
    public static void savePicss(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
