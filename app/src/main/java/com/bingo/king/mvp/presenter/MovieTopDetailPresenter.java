package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MovieTopDetailContract;
import com.bingo.king.mvp.model.entity.douban.MovieDetailBean;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;

import javax.inject.Inject;


@ActivityScope
public class MovieTopDetailPresenter extends BasePresenter<MovieTopDetailContract.Model, MovieTopDetailContract.View>
{
    private Application mApplication;
    @Inject
    public MovieTopDetailPresenter(MovieTopDetailContract.Model model, MovieTopDetailContract.View rootView, Application application)
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

    public void requestMovieDetail(String id)
    {
        requestInitializeData(mModel.requestMovieDetail(id), new HttpCallback<MovieDetailBean>()
        {
            @Override
            public void onSuccess(MovieDetailBean data)
            {
                mRootView.refreshView(data);
            }
        });
    }
}
