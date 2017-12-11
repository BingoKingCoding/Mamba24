package com.bingo.king.di.component;

import com.bingo.king.di.module.ZhiHuCommentModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.fragment.ZhiHuCommentFragment;

import dagger.Component;

@ActivityScope
@Component(modules = ZhiHuCommentModule.class, dependencies = AppComponent.class)
public interface ZhiHuCommentComponent
{
    void inject(ZhiHuCommentFragment fragment);
}