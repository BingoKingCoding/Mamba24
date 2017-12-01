package com.bingo.king.di.component;

import com.bingo.king.di.module.ZhiHuModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.fragment.ZhiHuFragment;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/1 17:42.
 */
@ActivityScope
@Component(modules = ZhiHuModule.class, dependencies = AppComponent.class)
public interface ZhiHuComponent
{
    void inject(ZhiHuFragment zhiHuFragment);
}
