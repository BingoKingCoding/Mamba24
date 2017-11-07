package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.CollectContract;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;


@ActivityScope
public class CollectModel extends BaseModel implements CollectContract.Model
{
    private Application mApplication;

    @Inject
    public CollectModel(IRepository repository, Application application)
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