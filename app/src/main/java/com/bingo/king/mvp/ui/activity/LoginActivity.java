package com.bingo.king.mvp.ui.activity;

import android.os.Bundle;


import com.bingo.king.app.base.BasePresenterActivity;

import com.bingo.king.di.component.DaggerLoginComponent;
import com.bingo.king.di.module.LoginModule;
import com.bingo.king.mvp.contract.LoginContract;
import com.bingo.king.mvp.presenter.LoginPresenter;

import com.bingo.king.R;


public class LoginActivity extends BasePresenterActivity<LoginPresenter> implements LoginContract.View
{

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
}
