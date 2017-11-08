package com.bingo.king.mvp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.app.utils.CategoryType;
import com.bingo.king.di.component.DaggerHomeComponent;
import com.bingo.king.di.module.HomeModule;
import com.bingo.king.mvp.contract.HomeContract;
import com.bingo.king.mvp.presenter.HomePresenter;
import com.bingo.king.mvp.ui.adapter.MianViewPagerAdapter;
import com.bingo.king.mvp.ui.widget.LoadingPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/6:20:26.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View
{
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.mainPager)
    ViewPager mainPager;
    private List<Fragment> mFragments;


    @Override
    protected int getContentLayoutId()
    {
        return R.layout.fragment_home;
    }

    @Override
    public void initComponent()
    {
        DaggerHomeComponent.builder()
                .appComponent(getAppComponent())
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void initData(Bundle savedInstanceState)
    {
        if (mFragments == null)
        {
            mFragments = new ArrayList<>();
            mFragments.add(CategoryFragment.newInstance(CategoryType.ANDROID_STR));
            mFragments.add(CategoryFragment.newInstance(CategoryType.IOS_STR));
            mFragments.add(CategoryFragment.newInstance(CategoryType.QIAN_STR));
        }
        mainPager.setOffscreenPageLimit(mFragments.size());
        mainPager.setAdapter(new MianViewPagerAdapter(getChildFragmentManager(), mFragments));
        tabs.setupWithViewPager(mainPager);
    }

    @Override
    protected void initView()
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
    public void showMessage(String message)
    {
        showSnackbar(message);
    }

    @Override
    public void refreshView(Object data)
    {

    }

    @Override
    protected void retryRequestData()
    {

    }

    @Override
    protected void onFragmentFirstVisible()
    {
        setState(LoadingPage.STATE_SUCCESS);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mainPager = null;
        tabs = null;
    }

}
