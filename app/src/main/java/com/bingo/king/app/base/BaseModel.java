package com.bingo.king.app.base;

import com.bingo.king.mvp.model.http.IRepository;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/2:22:17.
 */

public class BaseModel implements IModel
{
    protected IRepository mRepository;//用于管理网络请求层,以及数据缓存层

    public BaseModel(IRepository repository)
    {
        this.mRepository = repository;
    }

    @Override
    public void onDestroy()
    {
        mRepository = null;
    }
}
