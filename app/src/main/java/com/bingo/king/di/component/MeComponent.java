package com.bingo.king.di.component;

import com.bingo.king.di.module.MeModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.fragment.MeFragment;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/29 11:54.
 */
@ActivityScope
@Component(modules = MeModule.class, dependencies = AppComponent.class)
public interface MeComponent
{
    void inject(MeFragment fragment);
}
