package com.bingo.king.mvp.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseLazyFragment;
import com.bingo.king.di.component.DaggerZhiHuCommentComponent;
import com.bingo.king.di.module.ZhiHuCommentModule;
import com.bingo.king.mvp.contract.ZhiHuCommentContract;
import com.bingo.king.mvp.model.entity.zhihu.CommentBean;
import com.bingo.king.mvp.presenter.ZhiHuCommentPresenter;
import com.bingo.king.mvp.ui.activity.ZhiHuCommentActivity;
import com.bingo.king.mvp.ui.adapter.ZhiHuCommentAdapter;

import java.util.List;

import butterknife.BindView;


public class ZhiHuCommentFragment extends BaseLazyFragment<ZhiHuCommentPresenter> implements ZhiHuCommentContract.View
{

    @BindView(R.id.rv_zhihu_comment)
    RecyclerView mRecyclerView;

    ZhiHuCommentAdapter mAdapter;

    private boolean isShort;

    public static ZhiHuCommentFragment newInstance(boolean isShort)
    {
        ZhiHuCommentFragment fragment = new ZhiHuCommentFragment();
        fragment.isShort = isShort;
        return fragment;
    }

    @Override
    public void initComponent()
    {
        DaggerZhiHuCommentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .zhiHuCommentModule(new ZhiHuCommentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.fragment_zhi_hu_comment;
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

    public void requestData(){
        ZhiHuCommentActivity mZhiHuCommentActivity = (ZhiHuCommentActivity) getActivity();
        int id = mZhiHuCommentActivity.getId();
        if (isShort) {//懒加载在可见的时候加载，会让非静态变量最终都是同一个值所以只能用静态变量。
            mPresenter.requestShortCommentInfo(id);
        } else {
            mPresenter.requestLongCommentInfo(id);
        }
    }


    @Override
    protected void retryRequestData()
    {
        requestData();
    }



    @Override
    public void refreshView(List<CommentBean.CommentsBean> list)
    {
        mAdapter.setNewData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void fetchData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ZhiHuCommentAdapter(null);
        mAdapter.setDefaultEmptyView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        requestData();
    }
}
