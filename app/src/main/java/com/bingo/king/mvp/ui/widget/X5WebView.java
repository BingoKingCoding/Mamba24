package com.bingo.king.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView {
	TextView title;
	private WebViewClient client = new WebViewClient() {
		/**
		 * 防止加载网页时调起系统浏览器
		 */
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	};

	public X5WebView(Context arg0) {
		super(arg0);
		setBackgroundColor(85621);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public X5WebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
//		this.setWebViewClient(client);
		// this.setWebChromeClient(chromeClient);
		// WebStorage webStorage = WebStorage.getInstance();
		initWebViewSettings();
		this.getView().setClickable(true);
	}

	private void initWebViewSettings() {
		WebSettings webSetting = this.getSettings();

		// 网页内容的宽度是否可大于WebView控件的宽度
		webSetting.setLoadWithOverviewMode(false);
		// 保存表单数据
		webSetting.setSaveFormData(true);
		// 是否应该支持使用其屏幕缩放控件和手势缩放
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setDisplayZoomControls(false);
		// 启动应用缓存
		webSetting.setAppCacheEnabled(true);
		// 设置缓存模式
		webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
		// setDefaultZoom  api19被弃用
		// 设置此属性，可任意比例缩放。
		webSetting.setUseWideViewPort(true);
		// 缩放比例 1
		this.setInitialScale(1);
		//  页面加载好以后，再放开图片
		webSetting.setBlockNetworkImage(false);
		// 使用localStorage则必须打开
		webSetting.setDomStorageEnabled(true);
		// 排版适应屏幕
		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		// WebView是否支持多个窗口。
		webSetting.setSupportMultipleWindows(true);
		// 告诉WebView启用JavaScript执行。默认的是false。
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setAllowFileAccess(true);
//		webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
//		webSetting.setUseWideViewPort(true);
		// webSetting.setDatabaseEnabled(true);
//		webSetting.setDomStorageEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		// webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
//		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

		// this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
		// settings 的设计

		// webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
//			webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
		/** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
		webSetting.setTextZoom(100);
	}


	/**
	 * 调试用，左上角出现的红色字体，发布前请注释掉
	 */
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		boolean ret = super.drawChild(canvas, child, drawingTime);
		canvas.save();
		Paint paint = new Paint();
		paint.setColor(0x7fff0000);
		paint.setTextSize(24.f);
		paint.setAntiAlias(true);
		if (getX5WebViewExtension() != null) {
			canvas.drawText(this.getContext().getPackageName() + "-pid:"
					+ android.os.Process.myPid(), 10, 50, paint);
			canvas.drawText(
					"X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
					100, paint);
		} else {
			canvas.drawText(this.getContext().getPackageName() + "-pid:"
					+ android.os.Process.myPid(), 10, 50, paint);
			canvas.drawText("Sys Core", 10, 100, paint);
		}
		canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
		canvas.drawText(Build.MODEL, 10, 200, paint);
		canvas.restore();
		return ret;
	}


}
