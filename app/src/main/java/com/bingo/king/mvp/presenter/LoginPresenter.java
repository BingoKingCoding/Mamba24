package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.LoginContract;

import javax.inject.Inject;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View>
{
    private Application mApplication;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView, Application application)
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
