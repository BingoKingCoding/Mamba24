package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.CategoryContract;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;
import com.bingo.king.mvp.model.http.rxerrorhandler.StatefulCallback;
import com.bingo.king.mvp.ui.adapter.CategoryAdapter;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

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
            mRootView.setAdapter(mAdapter);
        }
        if (pullToRefresh)
        {
            lastUserId = 1;//上拉刷新默认只请求第一页
        } else
        {
            if (lastUserId == 5)//这边设置只加载5页
            {
                mAdapter.loadMoreEnd();
                return;
            }
            if (NetworkUtils.isAvailableByPing())
            {
                lastUserId++;
            }
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
                //判断是否有数据，如果有数据就让列表底部提示，如果没数据就显示重新加载
                if (mAdapter.getData().size() > 0)
                {
                    mAdapter.loadMoreFail();
                } else
                {
                    if (mRootView instanceof StatefulCallback)
                    {
                        ((StatefulCallback) mRootView).setState(LoadingPage.STATE_ERROR);
                    }
                }
                ToastUtils.showShort("获取数据有误，请检查网络是否连接");
            }

        });
    }


}
