package com.bingo.king.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BasePresenterActivity;
import com.bingo.king.app.common.CommonOpenLoginSDK;
import com.bingo.king.app.utils.DDViewUtil;
import com.bingo.king.app.utils.ViewBinder;
import com.bingo.king.di.component.DaggerLoginComponent;
import com.bingo.king.di.module.LoginModule;
import com.bingo.king.mvp.contract.LoginContract;
import com.bingo.king.mvp.model.entity.InitActBean;
import com.bingo.king.mvp.model.entity.UserBean;
import com.bingo.king.mvp.model.entity.dao.InitActBeanDao;
import com.bingo.king.mvp.model.entity.dao.UserBeanDao;
import com.bingo.king.mvp.presenter.LoginPresenter;
import com.blankj.utilcode.util.ToastUtils;
import com.fanwe.lib.blocker.SDDurationBlocker;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


public class LoginActivity extends BasePresenterActivity<LoginPresenter> implements LoginContract.View
{
    //微信
    private LinearLayout ll_weixin;
    private ImageView iv_weixin;

    //QQ
    private LinearLayout ll_qq;
    private ImageView iv_qq;

    //新浪
    private LinearLayout ll_sina;
    private ImageView iv_sina;

    //手机
    private LinearLayout ll_shouji;
    private ImageView iv_shouji;

    //游客
    private TextView tv_visitors;

    private TextView tv_agreement;
    private SDDurationBlocker blocker = new SDDurationBlocker(2000);

    @Override
    public int onCreateContentView(Bundle savedInstanceState)
    {
        return R.layout.activity_login;
    }

    @Override
    public void setupActivityComponent()
    {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        initView();
        initLoginIcon();
        bindDefaultData();
    }

    @Override
    protected void setStatusBar()
    {
    }

    @Override
    public void hidePullLoading()
    {

    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }


    @Override
    public void setState(int state)
    {

    }

    private void initView()
    {
        ll_weixin = findViewById(R.id.ll_weixin);
        iv_weixin = findViewById(R.id.iv_weixin);
        ll_qq = findViewById(R.id.ll_qq);
        iv_qq = findViewById(R.id.iv_qq);
        ll_sina = findViewById(R.id.ll_sina);
        iv_sina = findViewById(R.id.iv_sina);
        ll_shouji = findViewById(R.id.ll_shouji);
        iv_shouji = findViewById(R.id.iv_shouji);
        tv_visitors = findViewById(R.id.tv_visitors);
        tv_agreement = findViewById(R.id.tv_agreement);

        iv_qq.setOnClickListener(this);
        iv_sina.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        iv_shouji.setOnClickListener(this);
        tv_visitors.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);

        tv_visitors.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线

    }

    private void bindDefaultData()
    {
        InitActBean model = InitActBeanDao.query();
        if (model != null)
        {
            String privacy_title = model.getPrivacy_title();
            ViewBinder.setTextView(tv_agreement, privacy_title);
        }
    }

    private void initLoginIcon()
    {
        InitActBean model = InitActBeanDao.query();
        if (model != null)
        {
            //微信
            int has_wx_login = model.getHas_wx_login();
            if (has_wx_login == 1)
            {
                DDViewUtil.setVisible(ll_weixin);
            } else
            {
                DDViewUtil.setGone(ll_weixin);
            }

            //QQ
            int has_qq_login = model.getHas_qq_login();
            if (has_qq_login == 1)
            {
                DDViewUtil.setVisible(ll_qq);
            } else
            {
                DDViewUtil.setGone(ll_qq);
            }

            //新浪
            int has_sina_login = model.getHas_sina_login();
            if (has_sina_login == 1)
            {
                DDViewUtil.setVisible(ll_sina);
            } else
            {
                DDViewUtil.setGone(ll_sina);
            }

            //手机
            int has_mobile_login = model.getHas_mobile_login();
            if (has_mobile_login == 1)
            {
                DDViewUtil.setVisible(ll_shouji);
            } else
            {
                DDViewUtil.setGone(ll_shouji);
            }

            //游客
            int has_visitors_login = model.getHas_visitors_login();
            if (has_visitors_login == 1)
            {
                DDViewUtil.setVisible(tv_visitors);
            } else
            {
                DDViewUtil.setGone(tv_visitors);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        if (blocker.block())
        {
            return;
        }

        if (v == iv_weixin)
        {
            clickLoginWeiXing();
        } else if (v == iv_qq)
        {
            clickLoginQQ();
        } else if (v == iv_sina)
        {
            clickLoginSina();
        } else if (v == iv_shouji)
        {
            clickLoginShouJi();
        } else if (v == tv_visitors)
        {
            clickLoginVisitors();
        } else if (v == tv_agreement)
        {
            clickAgreement();
        }
    }

    private void clickAgreement()
    {
        InitActBean initActModel = InitActBeanDao.query();
        if (initActModel != null)
        {
            String privacy_link = initActModel.getPrivacy_link();
            if (!TextUtils.isEmpty(privacy_link))
            {
                WebActivity.loadUrl(this,privacy_link,"");
            }
        }
    }

    private void clickLoginVisitors()
    {
        //游客登录，项目中做请求接口进行用户初始化
        //本项目直接在本地进行初始化
        loginSuccess();
    }

    private void clickLoginShouJi()
    {
//        Intent intent = new Intent(this, LiveMobielRegisterActivity.class);
//        startActivity(intent);
    }

    private void clickLoginSina()
    {
        CommonOpenLoginSDK.umSinalogin(this, sinaListener);
    }

    /**
     * 新浪授权监听
     */
    private UMAuthListener sinaListener = new UMAuthListener()
    {
        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data)
        {
            ToastUtils.showShort("授权成功");
            String access_token = data.get("access_token");
            String uid = data.get("uid");
//            requestSinaLogin(access_token, uid);//提交给后台做请求处理
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t)
        {
            ToastUtils.showShort("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action)
        {
            ToastUtils.showShort("授权取消");
        }
    };



    private void clickLoginQQ()
    {
        CommonOpenLoginSDK.umQQlogin(this, qqListener);
    }

    /**
     * qq授权监听
     */
    private UMAuthListener qqListener = new UMAuthListener()
    {
        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data)
        {
            ToastUtils.showShort("授权成功");
            String openid = data.get("openid");
            String access_token = data.get("access_token");
//            requestQQ(openid, access_token);//提交给后台做请求处理
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t)
        {
            ToastUtils.showShort("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action)
        {
            ToastUtils.showShort("授权取消");
        }
    };

    private void clickLoginWeiXing()
    {
        CommonOpenLoginSDK.loginWx(this, wxListener);
    }
    /**
     * 微信授权监听
     */
    private UMAuthListener wxListener = new UMAuthListener()
    {

        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map)
        {
            ToastUtils.showShort("授权成功");
            String openid = map.get("openid");
            String access_token = map.get("access_token");
//            requestWeiXinLogin(openid, access_token);//提交给后台做请求处理
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable)
        {
            ToastUtils.showShort("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i)
        {
            ToastUtils.showShort("授权取消");
        }
    };

    private void enableClickLogin(boolean enable)
    {
        iv_weixin.setClickable(enable);
        iv_qq.setClickable(enable);
        iv_sina.setClickable(enable);
        iv_shouji.setClickable(enable);
        tv_visitors.setClickable(enable);
    }


    private void loginSuccess()
    {
        //此处做本地处理，项目中需要从接口获取
        UserBean user = new UserBean();
        user.setUser_id("25426598");
        user.setNick_name("余温");
        user.setSex(1);
        user.setHead_image("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2144884291,2915597647&fm=27&gp=0.jpg");
        user.setCity("厦门");
        user.setProvince("福建");
        UserBeanDao.insertOrUpdate(user);

        startMainActivity();
    }


    private void startMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
