package com.bingo.king.di.component;


import com.bingo.king.di.module.WelfareModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.fragment.WelfareFragment;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/25 16:02.
 */
@ActivityScope
@Component(modules = WelfareModule.class,dependencies = AppComponent.class)
public interface WelfareComponent
{
    void inject(WelfareFragment fragment);
}
