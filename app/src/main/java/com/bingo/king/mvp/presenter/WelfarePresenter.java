package com.bingo.king.mvp.presenter;

import android.app.Application;
import android.os.Message;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.WelfareContract;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/22 17:08.
 */
@ActivityScope
public class WelfarePresenter extends BasePresenter<WelfareContract.Model, WelfareContract.View>
{
    private Application mApplication;
    private DaoGankEntity daoGankEntity;

    @Inject
    public WelfarePresenter(WelfareContract.Model model, WelfareContract.View rootView
            , Application application)
    {
        super(model, rootView);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mApplication = null;
    }


    public void requestData(boolean pullToRefresh)
    {
        requestDataOnPullToRefresh(pullToRefresh, mModel.getRandomGirl(), new HttpCallback<GankEntity>()
        {
            @Override
            public void onSuccess(GankEntity gankEntity)
            {
                if (pullToRefresh)
                {
                    mRootView.setNewData(gankEntity.results);
                } else
                {
                    mRootView.setAddData(gankEntity.results);
                }
            }
        });

    }

    public void addToFavorites(GankEntity.ResultsBean entity)
    {
        DaoGankEntity daoGankEntity = entityToDao(entity);
        Message message = mModel.addGankEntity(daoGankEntity);
        String str = "";
        switch (message.what)
        {
            case 101:
                str = "该图片已经收藏过了";
                break;
            case 102:
                str = "收藏成功";
                EventBus.getDefault().post(new Object(), "meizi");
                break;
            case 103:
                str = "收藏失败";
                break;
        }
        ToastUtils.showShort(str);
    }

    private DaoGankEntity entityToDao(GankEntity.ResultsBean entity)
    {
        String str = TimeUtils.getNowString();//获取当前时间
        if (daoGankEntity == null)
        {
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
        daoGankEntity.addtime = str;
        return daoGankEntity;
    }


}
