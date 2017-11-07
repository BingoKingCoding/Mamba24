package com.bingo.king.di.component;


import com.bingo.king.di.module.SplashModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.activity.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent
{
    void inject(SplashActivity activity);
}