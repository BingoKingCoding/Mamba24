package com.bingo.king.di.module;

import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhiHuContract;
import com.bingo.king.mvp.model.ZhiHuModel;

import dagger.Module;
import dagger.Provides;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/1 17:41.
 */
@Module
public class ZhiHuModule
{
    private ZhiHuContract.View mView;

    public ZhiHuModule(ZhiHuContract.View mView){
        this.mView = mView;
    }

    @Provides
    @ActivityScope
    ZhiHuContract.View provideZhiHuView(){
        return this.mView;
    }

    @ActivityScope
    @Provides
    ZhiHuContract.Model provideZhiHuModel(ZhiHuModel model) {
        return model;
    }
}
