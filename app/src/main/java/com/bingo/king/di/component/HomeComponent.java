package com.bingo.king.di.component;

import com.bingo.king.di.module.HomeModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/6:20:37.
 *
 * @Email:634051075@qq.com
 */
@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent
{
    void inject(HomeFragment homeFragment);
}
