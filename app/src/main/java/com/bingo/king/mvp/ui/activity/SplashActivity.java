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
import com.bingo.king.app.base.BaseActivity;
import com.bingo.king.app.utils.ResourcesUtil;
import com.bingo.king.di.component.DaggerSplashComponent;
import com.bingo.king.di.module.SplashModule;
import com.bingo.king.mvp.contract.SplashContract;
import com.bingo.king.mvp.presenter.SplashPresenter;
import com.bingo.king.mvp.ui.widget.FixedImageView;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View, EasyPermissions.PermissionCallbacks
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
    public int initView(Bundle savedInstanceState)
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
    }


    @Override
    public void hideLoading()
    {

    }

    @Override
    public void endLoadMore()
    {

    }

    @Override
    public void showMessage(@NonNull String message)
    {
        showSnackbar(message);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        requestPermissions();
    }

    @AfterPermissionGranted(PERMISSON_REQUESTCODE)
    public void requestPermissions()
    {
        if (!EasyPermissions.hasPermissions(this, needPermissions))
        {
            EasyPermissions.requestPermissions(this, "应用需要这些权限", PERMISSON_REQUESTCODE, needPermissions);
        } else
        {
            splashImg.setImageDrawable(ResourcesUtil.getDrawable(R.drawable.window_static_bg_normal_mid));
            animWelcomeImage();
        }
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

//    @Override
//    public void delaySplash(List<String> picList)
//    {
//        if (picList.size() > 0) {
//            Random random = new Random();
//            int index = random.nextInt(picList.size());
//            int imgIndex = SPUtils.getInstance().getInt("splash_img_index", 0);
//            Timber.i("当前的imgIndex=" + imgIndex);
//            if (index == imgIndex) {
//                if (index >= picList.size()) {
//                    index--;
//                } else if (imgIndex == 0) {
//                    if (index + 1 < picList.size()) {
//                        index++;
//                    }
//                }
//            }
//            SPUtils.getInstance().put("splash_img_index", index);
//            Timber.i("当前的picList.size=" + picList.size() + ",index = " + index);
//            File file = new File(picList.get(index));
//            try {
//                InputStream fis = new FileInputStream(file);
//                splashImg.setImageDrawable(InputStream2Drawable(fis));
//                animWelcomeImage();
//                fis.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//
//            }
//        } else {
//            try {
//                AssetManager assetManager = this.getAssets();
//                InputStream in = assetManager.open("welcome_default.jpg");
//                splashImg.setImageDrawable(InputStream2Drawable(in));
//                animWelcomeImage();
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


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
                startMainOrLogin();
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
        recreate();
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
//        UserEntity user = UserModelDao.query();
//        if (user != null)
//        {
        startMainActivity();
//        } else
//        {
//            startLoginActivity();
//        }
    }

    private void startMainActivity()
    {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setState(int state)
    {

    }

//    private void startLoginActivity(){
//        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }
}
