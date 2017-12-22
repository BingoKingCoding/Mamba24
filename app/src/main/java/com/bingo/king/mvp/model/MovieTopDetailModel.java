package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MovieTopDetailContract;
import com.bingo.king.mvp.model.entity.douban.MovieDetailBean;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class MovieTopDetailModel extends BaseModel implements MovieTopDetailContract.Model
{

    private Application mApplication;

    @Inject
    public MovieTopDetailModel(IRepository repository, Application application)
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

    @Override
    public Observable<MovieDetailBean> requestMovieDetail(String id)
    {
        return mRepository.requestMovieDetail(id);
    }
}