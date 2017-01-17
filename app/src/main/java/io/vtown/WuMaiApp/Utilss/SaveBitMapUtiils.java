package io.vtown.WuMaiApp.Utilss;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by datutu on 2017/1/17.
 */

public class SaveBitMapUtiils {


    public static void SaveBitMap(View view) {
        File sdCards = Environment.getExternalStorageDirectory();
        final File test = new File(sdCards + "/mtshare");

        final Bitmap bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bmp));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFormat.format(new Date());


        if (!test.exists()) {
            test.mkdirs();
        }

        final String photoUrl = test + File.separator + time + ".png";//换成自己的图片保存路径
        final File file = new File(photoUrl);

        new Thread() {
            @Override
            public void run() {
                try {
                    boolean bitMapOk = bmp.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
                    if (bitMapOk) {
//                        myApp.photoFile = file;
//                        myApp.photoUrl = photoUrl;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }.start();


    }
}