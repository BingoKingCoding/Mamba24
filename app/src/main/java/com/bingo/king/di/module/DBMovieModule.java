package com.bingo.king.di.module;


import dagger.Module;
import dagger.Provides;

import com.bingo.king.di.scope.ActivityScope;

import com.bingo.king.mvp.contract.DBMovieContract;
import com.bingo.king.mvp.model.DBMovieModel;


@Module
public class DBMovieModule
{
    private DBMovieContract.View view;

    /**
     * 构建DBMovieModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DBMovieModule(DBMovieContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DBMovieContract.View provideDBMovieView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    DBMovieContract.Model provideDBMovieModel(DBMovieModel model)
    {
        return model;
    }
}