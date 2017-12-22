package com.bingo.king.mvp.ui.fragment;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.di.component.DaggerDBMovieComponent;
import com.bingo.king.di.module.DBMovieModule;
import com.bingo.king.mvp.contract.DBMovieContract;
import com.bingo.king.mvp.model.entity.douban.HotMovieBean;
import com.bingo.king.mvp.presenter.DBMoviePresenter;
import com.bingo.king.mvp.ui.activity.MovieTopDetailActivity;
import com.bingo.king.mvp.ui.adapter.MovieLatestAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;


public class DBMovieFragment extends BaseFragment<DBMoviePresenter> implements DBMovieContract.View
{
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MovieLatestAdapter mAdapter;
    private List<HotMovieBean.SubjectsBean> subjectsBeanList;


    public static DBMovieFragment newInstance()
    {
        DBMovieFragment fragment = new DBMovieFragment();
        return fragment;
    }

    @Override
    public void initComponent()
    {
        DaggerDBMovieComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .dBMovieModule(new DBMovieModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.fragment_dbmovie;
    }


    @Override
    public void initData(Bundle savedInstanceState)
    {
        //setState(LoadingPage.STATE_SUCCESS);//如果不需要网络请求的话可以去掉注释 直接设置成功状态
        initUI();
        mPresenter.requestHotMovie();
    }

    private void initUI()
    {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MovieLatestAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(view -> new Animator[]{});
        mAdapter.setOnItemClickListener(this::startMovieTopDetailActivity);
    }


    @Override
    public void refreshView(HotMovieBean data)
    {
        subjectsBeanList = data.getSubjects();
        mAdapter.addData(subjectsBeanList);
    }


    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    public void setData(Object data)
    {

    }

    @Override
    protected void retryRequestData()
    {

    }


    @Override
    public void hidePullLoading()
    {

    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }


    private void startMovieTopDetailActivity(HotMovieBean.SubjectsBean positionData, View view)
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MovieTopDetailActivity.class);
        intent.putExtra("bean", positionData);
        /**
         * 用这个ActivityOptionsCompat比用ActivityOptions兼容性更好，前者是V4下的兼容到16后者到21.
         * ActivityOptionsCompat.makeSceneTransitionAnimation(）的第三个参数则是跳转后图片显示的transitionName的值
         *     <android.support.design.widget.AppBarLayout
         android:transitionName="zhihu_detail_title"
         android:fitsSystemWindows="true">
         跳转到目标ImageView不能是addview进来的
         */
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                view, getActivity().getResources().getString(R.string.transition_image));
        getActivity().startActivity(intent, options.toBundle());
    }

}
