package com.bingo.king.di.module;


import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.CategoryContract;
import com.bingo.king.mvp.model.CategoryModel;

import dagger.Module;
import dagger.Provides;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/21 15:49.
 */
@Module
public class CategoryModule
{
    private CategoryContract.View mView;

    public CategoryModule(CategoryContract.View view)
    {
        this.mView = view;
    }

    @Provides
    @ActivityScope
    CategoryContract.View provideCategoryView()
    {
        return mView;
    }

    @Provides
    @ActivityScope
    CategoryContract.Model provideCategoryModel(CategoryModel model)
    {
        return model;
    }


}
