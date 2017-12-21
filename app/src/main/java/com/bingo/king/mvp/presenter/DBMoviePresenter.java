package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.DBMovieContract;
import com.bingo.king.mvp.model.entity.douban.HotMovieBean;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;

import javax.inject.Inject;


@ActivityScope
public class DBMoviePresenter extends BasePresenter<DBMovieContract.Model, DBMovieContract.View>
{

    private Application mApplication;

    @Inject
    public DBMoviePresenter(DBMovieContract.Model model, DBMovieContract.View rootView, Application application)
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

    public void requestHotMovie()
    {
        requestInitializeData(mModel.requestHotMovie(), new HttpCallback<HotMovieBean>()
        {
            @Override
            public void onSuccess(HotMovieBean data)
            {
                mRootView.refreshView(data);
            }
        });
    }
}
