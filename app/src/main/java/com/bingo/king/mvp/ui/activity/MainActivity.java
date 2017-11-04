package com.bingo.king.mvp.ui.activity;

import android.os.Bundle;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseActivity;
import com.bingo.king.di.component.DaggerMainComponent;
import com.bingo.king.di.module.MainModule;
import com.bingo.king.mvp.contract.MainContract;
import com.bingo.king.mvp.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void showLoading()
    {
        showLoadingDialog();
    }

    @Override
    public void hideLoading()
    {
        closeLoadingDialog();
    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }

    @Override
    public void setupActivityComponent()
    {
        DaggerMainComponent.builder()
                .appComponent(getAppComponent())
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        showLoading();
        mPresenter.requestData(true);
    }
}
