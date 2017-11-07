package com.bingo.king.di.component;


import com.bingo.king.di.module.MeiziModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.fragment.MeiziFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MeiziModule.class, dependencies = AppComponent.class)
public interface MeiziComponent
{
    void inject(MeiziFragment fragment);
}