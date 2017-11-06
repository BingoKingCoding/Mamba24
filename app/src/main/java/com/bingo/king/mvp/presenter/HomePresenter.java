package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.HomeContract;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/6:20:33.
 *
 * @Email:634051075@qq.com
 */
@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View>
{
    private Application mApplication;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView, Application application)
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
