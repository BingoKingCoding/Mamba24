package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MainContract;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.IRepository;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model
{
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MainModel(IRepository repository, Gson gson, Application application)
    {
        super(repository);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<GankEntity> getRandomGirl()
    {
        return mRepository.getRandomGirl();
    }
}
