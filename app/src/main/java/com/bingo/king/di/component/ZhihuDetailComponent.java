package com.bingo.king.di.component;

import dagger.Component;

import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.di.module.ZhihuDetailModule;

import com.bingo.king.mvp.ui.activity.ZhihuDetailActivity;

@ActivityScope
@Component(modules = ZhihuDetailModule.class, dependencies = AppComponent.class)
public interface ZhihuDetailComponent
{
    void inject(ZhihuDetailActivity activity);
}