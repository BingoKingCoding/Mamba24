package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhiHuCommentContract;
import com.bingo.king.mvp.model.entity.zhihu.CommentBean;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class ZhiHuCommentModel extends BaseModel implements ZhiHuCommentContract.Model
{

    private Application mApplication;

    @Inject
    public ZhiHuCommentModel(IRepository repository, Application application)
    {
        super(repository);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<CommentBean> requestLongCommentInfo(int id)
    {
        return mRepository.requestLongCommentInfo(id);
    }

    @Override
    public Observable<CommentBean> requestShortCommentInfo(int id)
    {
        return mRepository.requestShortCommentInfo(id);
    }
}