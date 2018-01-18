package com.bingo.king.app.common;

import android.app.Activity;

import com.bingo.king.app.App;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * <第三方登录>
 */

public class CommonOpenLoginSDK
{

    /**
     * 点击微信登录，先获取个人资料
     */
    public static void loginWx(final Activity activity, UMAuthListener authListener)
    {
        umLogin(activity, SHARE_MEDIA.WEIXIN, authListener);
    }

    public static void umSinalogin(Activity activity, UMAuthListener listener)
    {
        umLogin(activity, SHARE_MEDIA.SINA, listener);
    }

    public static void umQQlogin(Activity activity, UMAuthListener listener)
    {
        umLogin(activity, SHARE_MEDIA.QQ, listener);
    }

    public static void umLogin(Activity activity, SHARE_MEDIA platform, UMAuthListener listener)
    {
        if (activity == null || platform == null)
        {
            return;
        }

        if (platform == SHARE_MEDIA.WEIXIN)
        {
            if (!AppUtils.isInstallApp("com.tencent.mm"))
            {
                ToastUtils.showShort("您未安装微信客户端");
                return;
            }
        } else if (platform == SHARE_MEDIA.QQ)
        {
            if (!AppUtils.isInstallApp("com.tencent.mobileqq"))
            {
                ToastUtils.showShort("您未安装QQ客户端");
                return;
            }
        }

        UMShareAPI shareAPI = UMShareAPI.get(App.getApplication());
        if (shareAPI == null)
        {
            ToastUtils.showShort("UMShareAPI is null");
            return;
        }
        shareAPI.doOauthVerify(activity, platform, listener);
    }
}
