package com.bingo.king.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.app.utils.CommonUtils;
import com.bingo.king.di.component.DaggerCategoryComponent;
import com.bingo.king.di.module.CategoryModule;
import com.bingo.king.mvp.contract.CategoryContract;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.presenter.CategoryPresenter;
import com.bingo.king.mvp.ui.adapter.CategoryAdapter;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * <首页文章列表页>
 * Created by wwb on 2017/9/20 17:19.
 */

public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.View,SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isLoadingMore;
//    private Paginate mPaginate;
    private String type;

    public static CategoryFragment newInstance(String type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupComponent()
    {
        DaggerCategoryComponent.builder()
                .appComponent(getAppComponent())
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData()
    {
        type = getArguments().getString("type");
        mSwipeRefreshLayout.setOnRefreshListener(this);
        CommonUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getActivity()));


        mPresenter.requestData(type,true);
    }



    @Override
    protected int getContentLayoutId()
    {
        return R.layout.layout_refresh_list;
    }

    @Override
    protected void initView()
    {

    }

    @Override
    public void startLoadMore()
    {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore()
    {
        isLoadingMore = false;
    }

    @Override
    protected void retryRequestData()
    {
        mPresenter.requestData(type,true);
    }

    @Override
    public void setAdapter(CategoryAdapter mAdapter)
    {
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
        {
            GankEntity.ResultsBean bean = (GankEntity.ResultsBean) adapter.getItem(position);
//            ARouter.getInstance().build(MAIN_DETAIL)
//                    .withSerializable(EXTRA_DETAIL,bean)
//                    .navigation();
        });
        initPaginate();
    }

    private void initPaginate()
    {
//        if (mPaginate == null)
//        {
//            Paginate.Callbacks callback = new Paginate.Callbacks()
//            {
//                @Override
//                public void onLoadMore()
//                {
//                    mPresenter.requestData(type,false);
//                }
//
//                @Override
//                public boolean isLoading()
//                {
//                    return isLoadingMore;
//                }
//
//                @Override
//                public boolean hasLoadedAllItems()
//                {
//                    return false;
//                }
//            };
//            mPaginate = Paginate.with(mRecyclerView,callback)
//                    .setLoadingTriggerThreshold(0)
//                    .build();
//            mPaginate.setHasMoreDataToLoad(false);
//        }
    }

    @Override
    public void showLoading()
    {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer ->
                        mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading()
    {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }

    @Override
    public void refreshView(Object data)
    {

    }

    @Override
    public void onRefresh()
    {
        mPresenter.requestData(type,true);
    }

}
