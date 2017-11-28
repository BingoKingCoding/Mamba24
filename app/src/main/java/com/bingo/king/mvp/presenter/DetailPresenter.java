package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.DetailContract;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;
import com.blankj.utilcode.util.TimeUtils;

import javax.inject.Inject;


@ActivityScope
public class DetailPresenter extends BasePresenter<DetailContract.Model, DetailContract.View>
{
    private Application mApplication;
    private DaoGankEntity daoGankEntity;
    @Inject
    public DetailPresenter(DetailContract.Model model, DetailContract.View rootView, Application application)
    {
        super(model, rootView);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mApplication = null;
    }

    public void getGirl(){
        initializeRequestData(mModel.getRandomGirl(), new HttpCallback<GankEntity>()
        {
            @Override
            public void onSuccess(GankEntity gankEntity)
            {
                mRootView.setData(gankEntity.results.get(0).url);
            }
        });
    }

    public void getQuery(String id){
        mRootView.onFavoriteChange(mModel.queryById(id).size()>0);
    }

    public void removeByid(GankEntity.ResultsBean entity){
        DaoGankEntity daoGankEntity = entityToDao(entity);
        mModel.removeByid(daoGankEntity._id);
        mRootView.onFavoriteChange(false);
    }

    public void addToFavorites(GankEntity.ResultsBean entity) {
        DaoGankEntity daoGankEntity = entityToDao(entity);
        mModel.addGankEntity(daoGankEntity);
        mRootView.onFavoriteChange(true);
    }

    private DaoGankEntity entityToDao(GankEntity.ResultsBean entity) {
        String str = TimeUtils.getNowString();
        if (daoGankEntity == null){
            daoGankEntity = new DaoGankEntity();
        }
        daoGankEntity._id = entity._id;
        daoGankEntity.createdAt = entity.createdAt;
        daoGankEntity.desc = entity.desc;
        daoGankEntity.publishedAt = entity.publishedAt;
        daoGankEntity.source = entity.source;
        daoGankEntity.type = entity.type;
        daoGankEntity.url = entity.url;
        daoGankEntity.used = entity.used;
        daoGankEntity.who = entity.who;
        daoGankEntity.addtime =str;
        return daoGankEntity;
    }


}
