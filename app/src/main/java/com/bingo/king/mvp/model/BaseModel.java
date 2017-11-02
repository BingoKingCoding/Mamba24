package com.bingo.king.mvp.model;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/2:22:17.
 */

public class BaseModel implements IModel
{

    protected IRepository mRepository;//用于管理网络请求层,以及数据缓存层

    public BaseModel(IRepository repository)
    {
        this.mRepository = mRepository;
    }

    @Override
    public void onDestroy()
    {
        mRepository = null;
    }
}
