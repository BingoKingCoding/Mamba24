package com.bingo.king.di.component;


import com.bingo.king.di.module.MainModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by wwb on 2017/9/20 16:54.
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent
{
    void inject(MainActivity activity);
}
