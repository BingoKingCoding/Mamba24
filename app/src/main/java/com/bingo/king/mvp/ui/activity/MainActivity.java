package com.bingo.king.mvp.ui.activity;

import android.os.Bundle;

import com.bingo.king.R;
import com.bingo.king.app.base.BasePresenterActivity;
import com.bingo.king.di.component.DaggerMainComponent;
import com.bingo.king.di.module.MainModule;
import com.bingo.king.mvp.contract.MainContract;
import com.bingo.king.mvp.presenter.MainPresenter;
import com.bingo.king.mvp.ui.fragment.CollectFragment;
import com.bingo.king.mvp.ui.fragment.HomeFragment;
import com.bingo.king.mvp.ui.fragment.MeFragment;
import com.bingo.king.mvp.ui.fragment.WelfareFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

public class MainActivity extends BasePresenterActivity<MainPresenter> implements MainContract.View
{
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private OnTabSelectListener mOnTabSelectListener = tabId ->
    {
        switch (tabId)
        {
            case R.id.tab_home:
                getSDFragmentManager().toggle(R.id.main_frame, null, HomeFragment.class);
                break;
            case R.id.tab_dashboard:
                getSDFragmentManager().toggle(R.id.main_frame, null, WelfareFragment.class);
                break;
            case R.id.tab_myfavourite:
                getSDFragmentManager().toggle(R.id.main_frame, null, CollectFragment.class);
                break;
            case R.id.tab_mycenter:
                getSDFragmentManager().toggle(R.id.main_frame, null, MeFragment.class);
                break;
        }
    };

    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_main;
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
    public void initData(Bundle savedInstanceState)
    {
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
        mBottomBar.selectTabAtPosition(0);
    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }


    @Override
    public void hidePullLoading()
    {

    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void setState(int state)
    {

    }
}
