package com.bingo.king.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseActivity;
import com.bingo.king.di.component.DaggerMainComponent;
import com.bingo.king.di.module.MainModule;
import com.bingo.king.mvp.contract.MainContract;
import com.bingo.king.mvp.ui.fragment.CollectFragment;
import com.bingo.king.mvp.ui.fragment.HomeFragment;
import com.bingo.king.mvp.ui.fragment.MeFragment;
import com.bingo.king.mvp.ui.fragment.WelfareFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainContract.View
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
    public void endLoadMore()
    {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try
        {
            InputMethodManager.class.getDeclaredMethod("windowDismissed", IBinder.class).invoke(imm,
                    getWindow().getDecorView().getWindowToken());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    @Override
    public void setState(int state)
    {

    }
}
