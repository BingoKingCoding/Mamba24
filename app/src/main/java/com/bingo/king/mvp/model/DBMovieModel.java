package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.DBMovieContract;
import com.bingo.king.mvp.model.entity.douban.HotMovieBean;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class DBMovieModel extends BaseModel implements DBMovieContract.Model
{

    private Application mApplication;

    @Inject
    public DBMovieModel(IRepository repository, Application application)
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
    public Observable<HotMovieBean> requestHotMovie()
    {
        return mRepository.requestHotMovie();
    }
}