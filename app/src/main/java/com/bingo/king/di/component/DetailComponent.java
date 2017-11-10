package com.bingo.king.di.component;


import com.bingo.king.di.module.DetailModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.activity.DetailActivity;

import dagger.Component;

@ActivityScope
@Component(modules = DetailModule.class, dependencies = AppComponent.class)
public interface DetailComponent
{
    void inject(DetailActivity activity);
}