package com.bingo.king.mvp.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.app.utils.CommonUtils;
import com.bingo.king.di.component.DaggerArticleComponent;
import com.bingo.king.di.module.ArticleModule;
import com.bingo.king.mvp.contract.ArticleContract;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.presenter.ArticlePresenter;
import com.bingo.king.mvp.ui.adapter.ArticleAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * <文章收藏页面>
 * Created by wwb on 2017/9/27 17:08.
 */

public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View, SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArticleAdapter mAdapter;
    private GankEntity.ResultsBean intentArticle;

    public static ArticleFragment newInstance()
    {
        ArticleFragment fragment = new ArticleFragment();
        return fragment;
    }

    @Override
    public void setupComponent()
    {
        DaggerArticleComponent.builder()
                .appComponent(getAppComponent())
                .articleModule(new ArticleModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void initData()
    {
        mSwipeRefreshLayout.setOnRefreshListener(this);
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
//            ARouter.getInstance().build(ARouterPaths.MAIN_DETAIL)
//                    .withSerializable(EventBusTags.EXTRA_DETAIL, intentArticle)
//                    .navigation();
        });
        TextView textView = new TextView(getContext());
        textView.setText("没有更多内容了");
        textView.setGravity(Gravity.CENTER);
        mAdapter.setEmptyView(textView);
        mRecyclerView.setAdapter(mAdapter);



        mPresenter.requestData(true);
    }


    @Override
    protected void retryRequestData()
    {

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
    public void showLoading()
    {

    }

    @Override
    public void hideLoading()
    {

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
        mPresenter.requestData(true);
    }

    @Override
    public void startLoadMore()
    {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void endLoadMore()
    {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setAdapter(List<DaoGankEntity> entity)
    {
        mAdapter.setNewData(entity);
    }


}
