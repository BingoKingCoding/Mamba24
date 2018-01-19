package com.bingo.king.app.umeng;

import android.app.Application;
import android.text.TextUtils;

import com.bingo.king.R;
import com.bingo.king.mvp.model.entity.InitActBean;
import com.bingo.king.mvp.model.entity.dao.InitActBeanDao;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

/**
 * <友盟分享管理类>
 */

public class UmengSocialManager
{
    public static final int PLATFORM_NONE = 0;
    public static final int PLATFORM_WX = 1;
    public static final int PLATFORM_WX_CIRCLE = 2;
    public static final int PLATFORM_QQ = 3;
    public static final int PLATFORM_QZONE = 4;
    public static final int PLATFORM_SINA = 5;

    public static void init(Application app)
    {
        UMShareConfig config = new UMShareConfig();
        config.isOpenShareEditActivity(true);
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO); //如果有安装客户端优先客户端授权登录
        config.isNeedAuthOnGetUserInfo(true); //每次登录都重新授权
        UMShareAPI.get(app).setShareConfig(config);


        String wxAppKey = app.getResources().getString(R.string.wx_app_id);
        String wxAppSecret = app.getResources().getString(R.string.wx_app_secret);

        String qqAppKey = app.getResources().getString(R.string.qq_app_id);
        String qqAppSecret = app.getResources().getString(R.string.qq_app_key);

        String sinaAppKey = app.getResources().getString(R.string.sina_app_key);
        String sinaAppSecret = app.getResources().getString(R.string.sina_app_secret);

        InitActBean model = InitActBeanDao.query();
        if (model != null)
        {
            if (!TextUtils.isEmpty(model.getWx_app_key()) && !TextUtils.isEmpty(model.getWx_app_secret()))
            {
                wxAppKey = model.getWx_app_key();
                wxAppSecret = model.getWx_app_secret();
            }
            if (!TextUtils.isEmpty(model.getQq_app_key()) && !TextUtils.isEmpty(model.getQq_app_secret()))
            {
                qqAppKey = model.getQq_app_key();
                qqAppSecret = model.getQq_app_secret();

            }
            if (!TextUtils.isEmpty(model.getSina_app_key()) && !TextUtils.isEmpty(model.getSina_app_secret()))
            {
                sinaAppKey = model.getSina_app_key();
                sinaAppSecret = model.getSina_app_secret();
            }
        }

        PlatformConfig.setWeixin(wxAppKey, wxAppSecret);
        PlatformConfig.setQQZone(qqAppKey, qqAppSecret);
        PlatformConfig.setSinaWeibo(sinaAppKey, sinaAppSecret, "http://sns.whalecloud.com");
    }





}
