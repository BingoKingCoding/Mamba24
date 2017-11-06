package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.HomeContract;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/6:20:28.
 *
 * @Email:634051075@qq.com
 */
@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model
{
    private Application mApplication;

    @Inject
    public HomeModel(IRepository repository, Application application)
    {
        super(repository);
        this.mApplication = application;
    }


    @Override
    public void onDestroy()
    {
        this.mApplication = null;
    }
}
