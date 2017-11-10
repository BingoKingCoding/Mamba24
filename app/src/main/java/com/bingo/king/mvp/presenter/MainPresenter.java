package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MainContract;

import javax.inject.Inject;


/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 15:54.
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View>
{
    private Application mApplication;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView
            , Application application)
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
