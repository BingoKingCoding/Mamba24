package com.bingo.king.di.component;


import com.bingo.king.di.module.ArticleModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.fragment.ArticleFragment;

import dagger.Component;

@ActivityScope
@Component(modules = ArticleModule.class, dependencies = AppComponent.class)
public interface ArticleComponent
{
    void inject(ArticleFragment fragment);
}