package com.bingo.king.mvp.presenter;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.SettingContract;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/30 16:35.
 */
@ActivityScope
public class SettingPresenter extends BasePresenter<SettingContract.Model, SettingContract.View>
{
    @Inject
    public SettingPresenter(SettingContract.Model model, SettingContract.View view)
    {
        super(model, view);
    }



}
