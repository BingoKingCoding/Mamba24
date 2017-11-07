package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.CategoryContract;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 18:02.
 */
@ActivityScope
public class CategoryModel extends BaseModel implements CategoryContract.Model
{
    private Application mApplication;
    public static final int USERS_PER_PAGESIZE = 10;
    @Inject
    public CategoryModel(IRepository repository,Application application)
    {
        super(repository);
        this.mApplication = application;
    }

    @Override
    public Observable<GankEntity> gank(String type, String page)
    {
        return mRepository.gank(type, USERS_PER_PAGESIZE, page);
    }
}
