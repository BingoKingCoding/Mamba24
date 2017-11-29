package com.bingo.king.mvp.ui.fragment;

import android.os.Bundle;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.di.component.DaggerMeComponent;
import com.bingo.king.di.module.MeModule;
import com.bingo.king.mvp.contract.MeContract;
import com.bingo.king.mvp.presenter.MePresenter;
import com.bingo.king.mvp.ui.widget.LoadingPage;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/29 11:23.
 */

public class MeFragment extends BaseFragment<MePresenter> implements MeContract.View
{


    @Override
    protected int getContentLayoutId()
    {
        return R.layout.fragment_me;
    }

    @Override
    protected void initComponent()
    {
        DaggerMeComponent.builder()
                .appComponent(getAppComponent())
                .meModule(new MeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {
        setState(LoadingPage.STATE_SUCCESS);

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
    public void showMessage(String message)
    {
        showSnackbar(message);
    }

    @Override
    protected void retryRequestData()
    {

    }

}
