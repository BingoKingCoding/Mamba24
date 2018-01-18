package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.mvp.model.http.IRepository;
import com.bingo.king.app.base.BaseModel;

import com.bingo.king.di.scope.ActivityScope;

import javax.inject.Inject;

import com.bingo.king.mvp.contract.LoginContract;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model
{

    private Application mApplication;

    @Inject
    public LoginModel(IRepository repository, Application application)
    {
        super(repository);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mApplication = null;
    }

}