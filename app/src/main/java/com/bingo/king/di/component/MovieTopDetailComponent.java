package com.bingo.king.di.component;

import com.bingo.king.di.module.MovieTopDetailModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.activity.MovieTopDetailActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MovieTopDetailModule.class, dependencies = AppComponent.class)
public interface MovieTopDetailComponent
{
    void inject(MovieTopDetailActivity activity);
}