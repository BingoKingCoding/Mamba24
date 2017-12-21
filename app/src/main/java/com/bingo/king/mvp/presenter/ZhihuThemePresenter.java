package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhihuThemeContract;
import com.bingo.king.mvp.model.entity.zhihu.SectionChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeChildListBean;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;

import javax.inject.Inject;


@ActivityScope
public class ZhihuThemePresenter extends BasePresenter<ZhihuThemeContract.Model, ZhihuThemeContract.View>
{

    private Application mApplication;

    @Inject
    public ZhihuThemePresenter(ZhihuThemeContract.Model model, ZhihuThemeContract.View rootView, Application application)
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

    public void requestSectionChildList(int sectionId)
    {
        requestInitializeData(mModel.requestSectionChildList(sectionId), new HttpCallback<SectionChildListBean>()
        {
            @Override
            public void onSuccess(SectionChildListBean data)
            {
                mRootView.refreshSectionData(data);
            }
        });
    }

    public void requestThemeChildList(int id)
    {
        requestInitializeData(mModel.requestThemeChildList(id), new HttpCallback<ThemeChildListBean>()
        {
            @Override
            public void onSuccess(ThemeChildListBean data)
            {
                mRootView.refreshView(data);
            }
        });
    }
}
