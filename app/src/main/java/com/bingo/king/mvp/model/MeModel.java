package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MeContract;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/29 11:01.
 */
@ActivityScope
public class MeModel extends BaseModel implements MeContract.Model
{
    private Application mApplication;

    @Inject
    public MeModel(IRepository repository, Application application)
    {
        super(repository);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        mApplication = null;
    }
}
