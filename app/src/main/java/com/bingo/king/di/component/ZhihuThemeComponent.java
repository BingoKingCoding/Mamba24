package com.bingo.king.di.component;

import dagger.Component;

import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.di.module.ZhihuThemeModule;

import com.bingo.king.mvp.ui.activity.ZhihuThemeActivity;

@ActivityScope
@Component(modules = ZhihuThemeModule.class, dependencies = AppComponent.class)
public interface ZhihuThemeComponent
{
    void inject(ZhihuThemeActivity activity);
}