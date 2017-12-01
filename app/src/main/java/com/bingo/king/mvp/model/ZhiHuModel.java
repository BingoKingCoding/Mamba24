package com.bingo.king.mvp.model;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhiHuContract;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/1 17:15.
 */
@ActivityScope
public class ZhiHuModel extends BaseModel implements ZhiHuContract.Model
{
    @Inject
    public ZhiHuModel(IRepository repository)
    {
        super(repository);
    }
}
