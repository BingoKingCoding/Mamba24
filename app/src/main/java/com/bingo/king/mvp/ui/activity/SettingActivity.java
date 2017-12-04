package com.bingo.king.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseTitleActivity;
import com.bingo.king.di.component.DaggerSettingComponent;
import com.bingo.king.di.module.SettingModule;
import com.bingo.king.mvp.contract.SettingContract;
import com.bingo.king.mvp.presenter.SettingPresenter;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.blankj.utilcode.util.ToastUtils;

/*
 * Created by wwb on 2017/11/30 16:41.
 */

public class SettingActivity extends BaseTitleActivity<SettingPresenter> implements SettingContract.View
{
    @Override
    public int getContentLayoutId()
    {
        return R.layout.activity_setting;
    }

    @Override
    public void setupActivityComponent()
    {
        DaggerSettingComponent.builder()
                .appComponent(getAppComponent())
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void loadData(Bundle savedInstanceState)
    {
        super.loadData(savedInstanceState);
        setState(LoadingPage.STATE_SUCCESS);
//        setToolbarMiddleTitle("设置");
        setToolbarRightAction("保存");
        setMainTitle("设置");
    }

    @Override
    protected void callbackOnClickRightAction(View view)
    {
        super.callbackOnClickRightAction(view);
        ToastUtils.showShort("保存");
    }

    @Override
    protected void retryRequestData()
    {

    }


    @Override
    public void hidePullLoading()
    {

    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }
}
