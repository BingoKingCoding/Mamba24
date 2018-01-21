package com.bingo.king.wxapi;

import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WxappPay
{
	private static WxappPay mInstance;
	private String mAppId;
	private boolean mIsRegister = false;

	public static WxappPay getInstance()
	{
		if (mInstance == null)
		{
			mInstance = new WxappPay();
		}
		return mInstance;
	}

	private WxappPay()
	{
		// String appId = AppRuntimeWorker.getWx_app_key();
		// setAppId(appId);
	}

	public String getAppId()
	{
		return this.mAppId;
	}

	public void setAppId(String appId)
	{
		this.mAppId = appId;
		register();
	}

	public void register()
	{
		if (!mIsRegister && !TextUtils.isEmpty(mAppId))
		{
			mIsRegister = getWXAPI().registerApp(mAppId);
		}
	}

	public void pay(PayReq request)
	{
		if (request != null)
		{
			getWXAPI().sendReq(request);
		}
	}

	public IWXAPI getWXAPI()
	{
		return WXAPIFactory.createWXAPI(ActivityUtils.getTopActivity(), mAppId);
	}

}
