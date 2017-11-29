package com.bingo.king.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.view.inputmethod.InputMethodManager;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseActivity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.bingo.king.app.EventBusTags.ACTIVITY_FRAGMENT_REPLACE;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View
{
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;


    private List<Integer> mTitles;
    private List<Integer> mNavIds;
    private int mReplace = 0;

    private OnTabSelectListener mOnTabSelectListener = new OnTabSelectListener()
    {
        @Override
        public void onTabSelected(@IdRes int tabId)
        {
            switch (tabId)
            {
                case R.id.tab_home:
                    getSDFragmentManager().toggle(R.id.main_frame, null, HomeFragment.class);
                    mReplace = 0;
                    break;
                case R.id.tab_dashboard:
                    getSDFragmentManager().toggle(R.id.main_frame, null, WelfareFragment.class);
                    mReplace = 1;
                    break;
                case R.id.tab_myfavourite:
                    getSDFragmentManager().toggle(R.id.main_frame, null, CollectFragment.class);
                    mReplace = 2;
                    break;
                case R.id.tab_mycenter:
                    getSDFragmentManager().toggle(R.id.main_frame, null, MeFragment.class);
                    mReplace = 3;
                    break;
            }
        }
    };

    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_main;
    }


    @Override
    protected void setStatusBar()
    {
        int mStatusBarColor = getResources().getColor(R.color.colorPrimary);
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
        if (mTitles == null)
        {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.title_home);
            mTitles.add(R.string.title_dashboard);
            mTitles.add(R.string.title_favourite);
            mTitles.add(R.string.title_mecenter);
        }
        if (mNavIds == null)
        {
            mNavIds = new ArrayList<>();
            mNavIds.add(R.id.tab_home);
            mNavIds.add(R.id.tab_dashboard);
            mNavIds.add(R.id.tab_myfavourite);
            mNavIds.add(R.id.tab_mycenter);
        }
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
        //保存当前Activity显示的Fragment索引
        outState.putInt(ACTIVITY_FRAGMENT_REPLACE, mReplace);
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
        this.mTitles = null;
        this.mNavIds = null;
    }


    @Override
    public void setState(int state)
    {

    }
}
