package com.bingo.king.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseActivity;
import com.bingo.king.app.utils.DDViewUtil;
import com.bingo.king.di.component.DaggerMainComponent;
import com.bingo.king.di.module.MainModule;
import com.bingo.king.mvp.contract.MainContract;
import com.bingo.king.mvp.presenter.MainPresenter;
import com.bingo.king.mvp.ui.fragment.CollectFragment;
import com.bingo.king.mvp.ui.fragment.HomeFragment;
import com.bingo.king.mvp.ui.fragment.WelfareFragment;
import com.blankj.utilcode.util.FragmentUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bingo.king.app.EventBusTags.ACTIVITY_FRAGMENT_REPLACE;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View
{
    @BindView(R.id.toolbar)Toolbar mToolbar;
    @BindView(R.id.toolbar_title)TextView mToolbarTitle;
    @BindView(R.id.bottomBar)BottomBar mBottomBar;
    @BindView(R.id.dl_layout)DrawerLayout dlLayout;


    private List<Integer> mTitles;
    private List<Fragment> mFragments;
    private List<Integer> mNavIds;
    private int mReplace = 0;


    @OnClick(R.id.fl_title_menu)
    public void flTitleMenu() {
        dlLayout.openDrawer(GravityCompat.START);
    }

    private OnTabSelectListener mOnTabSelectListener = tabId ->
    {
        DDViewUtil.setVisible(mToolbar);
        switch (tabId)
        {
            case R.id.tab_home:
                mReplace = 0;
                break;
            case R.id.tab_dashboard:
                mReplace = 1;
                break;
            case R.id.tab_myfavourite:
                mReplace = 2;
                break;
//            case R.id.tab_mycenter:
//                mReplace = 3;
//                DDViewUtil.setGone(mToolbar);
//                break;
        }
        mToolbarTitle.setText(mTitles.get(mReplace));
        FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));

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
        mIsExitApp = true;
        if (mTitles == null)
        {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.title_home);
            mTitles.add(R.string.title_dashboard);
            mTitles.add(R.string.title_favourite);
//            mTitles.add(R.string.title_mecenter);
        }
//
        if (mNavIds == null)
        {
            mNavIds = new ArrayList<>();
            mNavIds.add(R.id.tab_home);
            mNavIds.add(R.id.tab_dashboard);
            mNavIds.add(R.id.tab_myfavourite);
//            mNavIds.add(R.id.tab_mycenter);
        }
        HomeFragment homeFragment;
        WelfareFragment welfareFragment;
        CollectFragment collectFragment;
//        UserCenterFragment userCenterFragment;

        if (savedInstanceState == null)
        {
            homeFragment = HomeFragment.newInstance();
            welfareFragment = WelfareFragment.newInstance();
            collectFragment = CollectFragment.newInstance();
//            userCenterFragment = UserCenterFragment.newInstance();
        }else {
            mReplace = savedInstanceState.getInt(ACTIVITY_FRAGMENT_REPLACE);
            FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm,HomeFragment.class);
            welfareFragment = (WelfareFragment) FragmentUtils.findFragment(fm,WelfareFragment.class);
            collectFragment = (CollectFragment) FragmentUtils.findFragment(fm,CollectFragment.class);
//            userCenterFragment = (UserCenterFragment) FragmentUtils.findFragment(fm,UserCenterFragment.class);
        }
//
        if (mFragments == null)
        {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(welfareFragment);
            mFragments.add(collectFragment);
//            mFragments.add(userCenterFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(),mFragments,R.id.main_frame,0);
//        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
    }


    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }

    @Override
    public void refreshView(Object data)
    {

    }

    @Override
    public void showLoading()
    {

    }

    @Override
    public void hideLoading()
    {

    }

    @Override
    public void startLoadMore()
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
        try {
            InputMethodManager.class.getDeclaredMethod("windowDismissed", IBinder.class).invoke(imm,
                    getWindow().getDecorView().getWindowToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
        this.mTitles = null;
        this.mFragments = null;
        this.mNavIds = null;
    }

    @Override
    public void onBackPressed() {
        if (dlLayout.isDrawerOpen(GravityCompat.START)) {
            dlLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

}
