package com.bingo.king.di.component;

import dagger.Component;

import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.di.module.LoginModule;

import com.bingo.king.mvp.ui.activity.LoginActivity;

@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent
{
    void inject(LoginActivity activity);
}