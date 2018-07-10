package com.bingo.king.mvp.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.bingo.king.R;
import com.bingo.king.app.base.BasePresenterActivity;
import com.bingo.king.app.umeng.UmengSocialManager;
import com.bingo.king.app.utils.ResourcesUtil;
import com.bingo.king.di.component.DaggerSplashComponent;
import com.bingo.king.di.module.SplashModule;
import com.bingo.king.mvp.contract.SplashContract;
import com.bingo.king.mvp.model.entity.InitActBean;
import com.bingo.king.mvp.model.entity.UserBean;
import com.bingo.king.mvp.model.entity.dao.InitActBeanDao;
import com.bingo.king.mvp.model.entity.dao.UserBeanDao;
import com.bingo.king.mvp.presenter.SplashPresenter;
import com.bingo.king.mvp.ui.widget.FixedImageView;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class SplashActivity extends BasePresenterActivity<SplashPresenter> implements SplashContract.View, EasyPermissions.PermissionCallbacks
{

    @BindView(R.id.splash_img)
    FixedImageView splashImg;
    private static final int PERMISSON_REQUESTCODE = 1;
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    public void setupActivityComponent()
    {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int onCreateContentView(Bundle savedInstanceState)
    {
        return R.layout.activity_splash; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    protected void setStatusBar()
    {

    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        UmengSocialManager.init(getApplication());
    }



    @Override
    protected void onStart()
    {
        super.onStart();
        requestPermissions();
    }


    public void requestPermissions()
    {
        if (!EasyPermissions.hasPermissions(this, needPermissions))
        {
            EasyPermissions.requestPermissions(this, "应用需要申请权限", PERMISSON_REQUESTCODE, needPermissions);
        } else
        {
            splashImg.setImageDrawable(ResourcesUtil.getDrawable(R.drawable.window_static_bg_normal_mid));
            animWelcomeImage();
        }
    }


    @AfterPermissionGranted(PERMISSON_REQUESTCODE)
    private void afterGet()
    {
//        Toast.makeText(this, "已获取权限，让我们干爱干的事吧！", Toast.LENGTH_SHORT).show();
        animWelcomeImage();
    }

    public void showMissingPermissionDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                (dialog, which) -> finish());
        builder.setPositiveButton("设置",
                (dialog, which) -> startAppSettings());
        builder.setCancelable(false);
        builder.show();

    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings()
    {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void animWelcomeImage()
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(splashImg, "translationX", -100F);
        animator.setDuration(2000L).start();
        animator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                requestInit();
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms)
    {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms)
    {
        showMissingPermissionDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void startMainOrLogin()
    {
        UserBean user = UserBeanDao.query();
        if (user != null)
        {
            startMainActivity();
        } else
        {
            startLoginActivity();
        }
    }

    private void startMainActivity()
    {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * 请求初始化接口获取APP的初始化数据
     */
    private void requestInit()
    {
        //为了方便此处就直接本地初始化，项目中需求通过请求接口获取

        InitActBean initActBean = new InitActBean();
        initActBean.setHas_mobile_login(1);
        initActBean.setHas_qq_login(1);
        initActBean.setHas_wx_login(1);
        initActBean.setHas_sina_login(1);
        initActBean.setHas_visitors_login(1);
        initActBean.setQq_app_key("");
        initActBean.setQq_app_secret("");
        initActBean.setSina_app_key("");
        initActBean.setSina_app_secret("");
        initActBean.setWx_app_key("");
        initActBean.setWx_app_secret("");
        initActBean.setPrivacy_title("用户隐私政策");
        initActBean.setPrivacy_link("https://www.jianshu.com/u/243819ed1836");

        InitActBeanDao.insertOrUpdate(initActBean);


        //初始化接口成功后调用
        startMainOrLogin();

    }

    private void startLoginActivity()
    {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
