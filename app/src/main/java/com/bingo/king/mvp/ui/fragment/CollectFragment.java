package com.bingo.king.mvp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.di.component.DaggerCollectComponent;
import com.bingo.king.di.module.CollectModule;
import com.bingo.king.mvp.contract.CollectContract;
import com.bingo.king.mvp.presenter.CollectPresenter;
import com.bingo.king.mvp.ui.adapter.CollectViewPagerAdapter;
import com.bingo.king.mvp.ui.widget.LoadingPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * <收藏父页面>
 * Created by wwb on 2017/9/20 16:13.
 */

public class CollectFragment extends BaseFragment<CollectPresenter> implements CollectContract.View
{
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.mainPager)
    ViewPager mainPager;
    private List<Fragment> mFragments;

    @Override
    public void initComponent()
    {
        DaggerCollectComponent.builder()
                .appComponent(getAppComponent())
                .collectModule(new CollectModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.fragment_collect;
    }


    @Override
    public void initData(Bundle savedInstanceState)
    {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(new MeiziFragment());
            mFragments.add(new ArticleFragment());
        }
        mainPager.setOffscreenPageLimit(mFragments.size());
        mainPager.setAdapter(new CollectViewPagerAdapter(getChildFragmentManager(),mFragments));
        tabs.setupWithViewPager(mainPager);

        setState(LoadingPage.STATE_SUCCESS);
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
    public void showMessage(String message)
    {
        showSnackbar(message);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mainPager = null;
        tabs = null;
    }

    @Override
    protected void retryRequestData()
    {

    }

}
