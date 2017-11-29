package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MeiziContract;

import java.util.ArrayList;

import javax.inject.Inject;



@ActivityScope
public class MeiziPresenter extends BasePresenter<MeiziContract.Model, MeiziContract.View>
{
    private Application mApplication;

    @Inject
    public MeiziPresenter(MeiziContract.Model model, MeiziContract.View rootView
            , Application application)
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

    public void requestData(boolean b) {
        mRootView.setAdapter(mModel.getEntity());
        mRootView.hidePullLoading();
    }

    public ArrayList<String> getImages(){
        return mModel.getImages();
    }

}
