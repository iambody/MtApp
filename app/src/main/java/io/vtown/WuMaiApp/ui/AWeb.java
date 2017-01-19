package io.vtown.WuMaiApp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebViewClient;

import io.vtown.WuMaiApp.R;
import io.vtown.WuMaiApp.view.ProgressWebView;


/**
 * @author 作者 大兔兔 wangyongkui@v-town.cc
 * @version 创建时间：2016-4-26 下午4:03:16
 */
public class AWeb extends ABase {

	private ProgressWebView aweb_web;

	/**
	 * 传进来的bean的key
	 */
	public final static String Key_Bean = "beankey";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
//		getWindow().requestFeature(Window.FEATURE_PROGRESS);

		aweb_web = (ProgressWebView) findViewById(R.id.aweb_web);

		try {
			aweb_web.getSettings().setDisplayZoomControls(true);
			aweb_web.getSettings().setJavaScriptEnabled(true);
			aweb_web.setWebViewClient(new WebViewClient());
			aweb_web.setDownloadListener(new DownloadListener() {
				@Override
				public void onDownloadStart(String url, String userAgent,
											String contentDisposition, String mimetype,
											long contentLength) {
					if (url != null && url.startsWith("http://"))
						startActivity(new Intent(Intent.ACTION_VIEW, Uri
								.parse(url)));
				}
			});
			aweb_web.loadUrl("http://www.v-town.cc/maitai.html");
			// 开启DOM 存储
			aweb_web.getSettings().setDomStorageEnabled(true);
			// aweb_web.addJavascriptInterface(this, "login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && aweb_web.canGoBack()) {
			aweb_web.goBack();// 返回前一个页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
