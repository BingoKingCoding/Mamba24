package com.bingo.king.mvp.presenter;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhiHuContract;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/1 17:16.
 */
@ActivityScope
public class ZhiHuPresenter extends BasePresenter<ZhiHuContract.Model,ZhiHuContract.View>
{
    @Inject
    public ZhiHuPresenter(ZhiHuContract.Model model, ZhiHuContract.View rootView)
    {
        super(model, rootView);
    }


    public void requestData(){

    }




}
