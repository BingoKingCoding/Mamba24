package com.bingo.king.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bingo.king.R;
import com.bingo.king.app.ARouterPaths;
import com.bingo.king.app.EventBusTags;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.app.utils.CommonUtils;
import com.bingo.king.app.utils.ResourcesUtil;
import com.bingo.king.di.component.DaggerArticleComponent;
import com.bingo.king.di.module.ArticleModule;
import com.bingo.king.mvp.contract.ArticleContract;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.presenter.ArticlePresenter;
import com.bingo.king.mvp.ui.adapter.ArticleAdapter;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;


/**
 * <文章收藏页面>
 * Created by wwb on 2017/9/27 17:08.
 */

public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View,OnRefreshListener
{
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    private ArticleAdapter mAdapter;
    private GankEntity.ResultsBean intentArticle;


    @Override
    public void initComponent()
    {
        DaggerArticleComponent.builder()
                .appComponent(getAppComponent())
                .articleModule(new ArticleModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void initData(Bundle savedInstanceState)
    {
        setState(LoadingPage.STATE_SUCCESS);
        mRefreshLayout.setOnRefreshListener(this);
        CommonUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getActivity()));

        mAdapter = new ArticleAdapter(null);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
        {
            DaoGankEntity bean = (DaoGankEntity) adapter.getItem(position);
            if (intentArticle == null)
                intentArticle = new GankEntity.ResultsBean();
            intentArticle._id = bean._id;
            intentArticle.createdAt = bean.createdAt;
            intentArticle.desc = bean.desc;
            intentArticle.images = bean.images;
            intentArticle.publishedAt = bean.publishedAt;
            intentArticle.source = bean.source;
            intentArticle.type = bean.type;
            intentArticle.url = bean.url;
            intentArticle.used = bean.used;
            intentArticle.who = bean.who;
            ARouter.getInstance().build(ARouterPaths.MAIN_DETAIL)
                    .withSerializable(EventBusTags.EXTRA_DETAIL, intentArticle)
                    .navigation();
        });
        TextView textView = new TextView(getContext());
        textView.setText("没有更多内容了");
        textView.setTextColor(ResourcesUtil.getColor(R.color.C6));
        textView.setGravity(Gravity.CENTER);
        mAdapter.setEmptyView(R.layout.loadingpage_state_empty,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onFragmentFirstVisible()
    {
        super.onFragmentFirstVisible();
//        mPresenter.requestData(true);
    }

    @Override
    protected void retryRequestData()
    {
        mPresenter.requestData(true);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mPresenter.requestData(true);
    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.layout_refresh_list;
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
    public void onRefresh(RefreshLayout refreshLayout)
    {
        mPresenter.requestData(true);
    }

    @Override
    public void setAdapter(List<DaoGankEntity> entity)
    {
        mAdapter.setNewData(entity);
    }


}
