package com.bingo.king.di.module;


import dagger.Module;
import dagger.Provides;

import com.bingo.king.di.scope.ActivityScope;

import com.bingo.king.mvp.contract.MovieTopDetailContract;
import com.bingo.king.mvp.model.MovieTopDetailModel;


@Module
public class MovieTopDetailModule
{
    private MovieTopDetailContract.View view;

    /**
     * 构建MovieTopDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MovieTopDetailModule(MovieTopDetailContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MovieTopDetailContract.View provideMovieTopDetailView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    MovieTopDetailContract.Model provideMovieTopDetailModel(MovieTopDetailModel model)
    {
        return model;
    }
}