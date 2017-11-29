package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MeContract;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/29 11:06.
 */
@ActivityScope
public class MePresenter extends BasePresenter<MeContract.Model, MeContract.View>
{
    private Application mApplication;

    @Inject
    public MePresenter(MeContract.Model model, MeContract.View view, Application application)
    {
        super(model, view);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mApplication = null;
    }






}
