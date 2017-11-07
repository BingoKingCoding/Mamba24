package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ArticleContract;

import javax.inject.Inject;



@ActivityScope
public class ArticlePresenter extends BasePresenter<ArticleContract.Model, ArticleContract.View>
{
    private Application mApplication;

    @Inject
    public ArticlePresenter(ArticleContract.Model model, ArticleContract.View rootView
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

    public void requestData(boolean pullToRefresh) {
        mRootView.setAdapter(mModel.getEntity());
        mRootView.endLoadMore();
    }

}
