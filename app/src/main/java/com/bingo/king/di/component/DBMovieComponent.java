package com.bingo.king.di.component;

import dagger.Component;

import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.di.module.DBMovieModule;

import com.bingo.king.mvp.ui.fragment.DBMovieFragment;

@ActivityScope
@Component(modules = DBMovieModule.class, dependencies = AppComponent.class)
public interface DBMovieComponent
{
    void inject(DBMovieFragment fragment);
}