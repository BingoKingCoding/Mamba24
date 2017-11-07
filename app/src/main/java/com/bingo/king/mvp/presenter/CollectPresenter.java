package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.CollectContract;

import javax.inject.Inject;



@ActivityScope
public class CollectPresenter extends BasePresenter<CollectContract.Model, CollectContract.View>
{
    private Application mApplication;

    @Inject
    public CollectPresenter(CollectContract.Model model, CollectContract.View rootView
            ,Application application)
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
