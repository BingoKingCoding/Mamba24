package com.bingo.king.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.app.utils.CommonUtils;
import com.bingo.king.di.component.DaggerMeiziComponent;
import com.bingo.king.di.module.MeiziModule;
import com.bingo.king.mvp.contract.MeiziContract;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.presenter.MeiziPresenter;
import com.bingo.king.mvp.ui.activity.PhotoViewActivity;
import com.bingo.king.mvp.ui.adapter.MeiziAdapter;
import com.bingo.king.mvp.ui.widget.SpacesItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * <收藏页面福利>
 * Created by wwb on 2017/9/27 16:33.
 */

public class MeiziFragment extends BaseFragment<MeiziPresenter> implements MeiziContract.View, SwipeRefreshLayout.OnRefreshListener
{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MeiziAdapter mAdapter;


    @Override
    public void initComponent()
    {
        DaggerMeiziComponent.builder()
                .appComponent(getAppComponent())
                .meiziModule(new MeiziModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void initData(Bundle savedInstanceState)
    {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        CommonUtils.configRecycleView(mRecyclerView, new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new MeiziAdapter(null);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        TextView textView = new TextView(getContext());
        textView.setText("没有更多内容了");
        textView.setGravity(Gravity.CENTER);
        mAdapter.setEmptyView(textView);

        mAdapter.setOnItemClickListener((adapter, view, position) ->
        {
            Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
            // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取

            intent.putExtra(PhotoViewActivity.EXTRA_IMAGE_URLS, mPresenter.getImages());
            intent.putExtra(PhotoViewActivity.EXTRA_POSITION, position);
            getActivity().startActivity(intent);
        });

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onFragmentFirstVisible()
    {
        super.onFragmentFirstVisible();
        mPresenter.requestData(true);
    }

    @Override
    protected void retryRequestData()
    {
        mPresenter.requestData(true);
    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.layout_refresh_list;
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
                .subscribe(integer ->
                        mSwipeRefreshLayout.setRefreshing(true));
    }

    @Subscriber(tag = "meizi")
    private void updateAdapter(Object o)
    {
        mPresenter.requestData(true);
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
