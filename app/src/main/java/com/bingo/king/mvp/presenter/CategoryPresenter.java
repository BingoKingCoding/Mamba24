package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.CategoryContract;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;
import com.bingo.king.mvp.model.http.rxerrorhandler.Stateful;
import com.bingo.king.mvp.ui.adapter.CategoryAdapter;
import com.bingo.king.mvp.ui.widget.EasyLoadMoreView;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 18:00.
 */
@ActivityScope
public class CategoryPresenter extends BasePresenter<CategoryContract.Model, CategoryContract.View>
{
    private Application mApplication;
    private int lastUserId = 1;
    private CategoryAdapter mAdapter;
    private int preEndIndex;
    private List<GankEntity.ResultsBean> mData = new ArrayList<>();


    @Inject
    public CategoryPresenter(CategoryContract.Model model, CategoryContract.View rootView
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

    public void requestData(String type, boolean pullToRefresh)
    {

        if (mAdapter == null)
        {
            mAdapter = new CategoryAdapter(mData);
            EasyLoadMoreView easyLoadMoreView = new EasyLoadMoreView();
            mAdapter.setLoadMoreView(easyLoadMoreView);
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            mAdapter.isFirstOnly(false);
            mRootView.setAdapter(mAdapter);
        }
        if (lastUserId == 10)
        {
            mAdapter.loadMoreEnd();
            return;
        }
        if (pullToRefresh)
        {
            lastUserId = 1;//上拉刷新默认只请求第一页
        } else
        {
            lastUserId++;
        }

        requestDataOnPullToRefresh(pullToRefresh, mModel.gank(type, String.valueOf(lastUserId)), new HttpCallback<GankEntity>()
        {
            @Override
            public void onSuccess(GankEntity gankEntity)
            {
                preEndIndex = mData.size();
                if (pullToRefresh)
                {
                    mData.clear();
                }
                mData.addAll(gankEntity.results);
                if (pullToRefresh)
                {
                    mAdapter.notifyDataSetChanged();
                } else
                {
                    mAdapter.notifyItemRangeInserted(preEndIndex, gankEntity.results.size());
                }
                mAdapter.loadMoreComplete();
            }

            @Override
            public void onError(@NonNull Throwable e)
            {
                e.printStackTrace();
                if (mAdapter.getData().size() > 0)
                {
                    mAdapter.loadMoreFail();
                } else
                {
                    if (mRootView instanceof Stateful)
                    {
                        ((Stateful) mRootView).setState(LoadingPage.STATE_ERROR);
                    }
                }
                ToastUtils.showShort("网络异常，请检查网络连接");
            }

        });
    }


}
