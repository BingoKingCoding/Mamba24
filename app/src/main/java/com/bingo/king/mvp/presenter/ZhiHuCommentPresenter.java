package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhiHuCommentContract;

import javax.inject.Inject;


@ActivityScope
public class ZhiHuCommentPresenter extends BasePresenter<ZhiHuCommentContract.Model, ZhiHuCommentContract.View>
{

    private Application mApplication;

    @Inject
    public ZhiHuCommentPresenter(ZhiHuCommentContract.Model model, ZhiHuCommentContract.View rootView, Application application)
    {
        super(model, rootView);

        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        this.mApplication = null;

    }

}
