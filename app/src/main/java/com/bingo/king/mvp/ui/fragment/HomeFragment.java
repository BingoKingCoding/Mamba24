package com.bingo.king.mvp.ui.fragment;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.di.component.DaggerHomeComponent;
import com.bingo.king.di.module.HomeModule;
import com.bingo.king.mvp.contract.HomeContract;
import com.bingo.king.mvp.presenter.HomePresenter;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/6:20:26.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View
{


    @Override
    protected int getContentLayoutId()
    {
        return R.layout.fragment_home;
    }

    @Override
    public void setupComponent()
    {
        DaggerHomeComponent.builder()
                .appComponent(getAppComponent())
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void initData()
    {

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

}
