package com.bingo.king.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bingo.king.R;
import com.bingo.king.app.ARouterPaths;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.app.utils.CommonUtils;
import com.bingo.king.di.component.DaggerCategoryComponent;
import com.bingo.king.di.module.CategoryModule;
import com.bingo.king.mvp.contract.CategoryContract;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.presenter.CategoryPresenter;
import com.bingo.king.mvp.ui.adapter.CategoryAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

import static com.bingo.king.app.EventBusTags.EXTRA_DETAIL;


/**
 * <首页文章列表页>
 * Created by wwb on 2017/9/20 17:19.
 */

public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.View,BaseQuickAdapter.RequestLoadMoreListener,OnRefreshListener
{
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    private String type;

    private CategoryAdapter mAdapter;

    public static CategoryFragment newInstance(String type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initComponent()
    {
        DaggerCategoryComponent.builder()
                .appComponent(getAppComponent())
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        type = getArguments().getString("type");
        mRefreshLayout.setOnRefreshListener(this);
        CommonUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getActivity()));
    }


    @Override
    protected void onFragmentFirstVisible()
    {
        super.onFragmentFirstVisible();
        mPresenter.requestData(type,true);
    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.layout_refresh_list;
    }


    @Override
    protected void retryRequestData()
    {
        mPresenter.requestData(type,true);
    }

    @Override
    public void setAdapter(CategoryAdapter mAdapter)
    {
        this.mAdapter = mAdapter;
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
        {
            GankEntity.ResultsBean bean = (GankEntity.ResultsBean) adapter.getItem(position);
            ARouter.getInstance().build(ARouterPaths.MAIN_DETAIL)
                    .withSerializable(EXTRA_DETAIL,bean)
                    .navigation();
//            WebActivity.loadUrl(getActivity(),bean.url,"加载中");
        });
    }


    @Override
    public void hidePullLoading()
    {
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }

    @Override
    public void onLoadMoreRequested()
    {
        mPresenter.requestData(type,false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout)
    {
        mPresenter.requestData(type,true);
    }
}
