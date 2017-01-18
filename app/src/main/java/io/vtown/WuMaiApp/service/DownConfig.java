package io.vtown.WuMaiApp.service;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * @author 作者 大兔兔 wangyongkui@v-town.cc
 * @version 创建时间：2016-8-1 下午5:58:28
 * 
 */
public class DownConfig {
	private static Context mAppContext;

	public static void setAppContext(Context context) {
		mAppContext = context;
	}

	public static Context getAppContext() {
		return mAppContext;
	}

	/**
	 * 下载到SD卡地址
	 */
	public static String getUpgradePath() {
		String filePath = getAppRootPath() + "/upgradea/";
		File file = new File(filePath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		file = null;
		return filePath;
	}

	public static String getAppRootPath() {
		String filePath = "/maitai";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			filePath = Environment.getExternalStorageDirectory() + filePath;
		} else {
			filePath = getAppContext().getCacheDir() + filePath;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = null;
		File nomedia = new File(filePath + "/.nomedia");
		if (!nomedia.exists())
			try {
				nomedia.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return filePath;
	}
}
