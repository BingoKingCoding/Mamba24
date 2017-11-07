package com.bingo.king.di.component;


import com.bingo.king.di.module.CollectModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.fragment.CollectFragment;

import dagger.Component;

@ActivityScope
@Component(modules = CollectModule.class, dependencies = AppComponent.class)
public interface CollectComponent
{
    void inject(CollectFragment fragment);
}