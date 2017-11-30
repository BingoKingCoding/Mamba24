package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.SettingContract;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/30 16:30.
 */
@ActivityScope
public class SettingModel extends BaseModel implements SettingContract.Model
{
    private Application mApplication;

    @Inject
    public SettingModel(IRepository repository, Application application)
    {
        super(repository);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mApplication = null;
    }
}
