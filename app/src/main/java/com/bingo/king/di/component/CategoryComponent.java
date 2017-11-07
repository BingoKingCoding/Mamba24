package com.bingo.king.di.component;


import com.bingo.king.di.module.CategoryModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.fragment.CategoryFragment;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/21 15:53.
 */
@ActivityScope
@Component(modules = CategoryModule.class,dependencies = AppComponent.class)
public interface CategoryComponent
{
    void inject(CategoryFragment fragment);
}
