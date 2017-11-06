package com.bingo.king.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bingo.king.mvp.model.http.rxerrorhandler.Stateful;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/2:20:22.
 *
 * @Email:634051075@qq.com
 */

public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements IFragment,Stateful
{
    protected final String TAG = this.getClass().getSimpleName();


    public LoadingPage mLoadingPage;

    private boolean mIsVisible = false;     // fragment是否显示了

    private boolean isPrepared = false;

    private boolean isFirst = true; //只加载一次界面

    protected View contentView;
    private Unbinder bind;

    @Inject
    protected P mPresenter;
    private BaseActivity mActivity;

    public BaseFragment()
    {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (mLoadingPage == null)
        {
            mLoadingPage = new LoadingPage(getContext())
            {
                @Override
                protected int getContentLayoutId()
                {
                    return BaseFragment.this.getContentLayoutId();
                }

                @Override
                protected void initView()
                {
                    if (isFirst)
                    {
                        BaseFragment.this.contentView = this.contentView;
                        bind = ButterKnife.bind(BaseFragment.this, contentView);
                        BaseFragment.this.initView();
                        isFirst = false;
                    }
                }

                @Override
                protected void retryRequestData()
                {
                    BaseFragment.this.retryRequestData();
                }
            };
        }
        isPrepared = true;
        loadBaseData();
        return mLoadingPage;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint())
        {//fragment可见
            mIsVisible = true;
            onVisible();
        } else
        {//fragment不可见
            mIsVisible = false;
            onInvisible();
        }

    }

    @Override
    public void setState(int state) {
        mLoadingPage.state = state;
        mLoadingPage.showPage();
    }


    protected void onInvisible()
    {
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void onVisible()
    {
        if (isFirst)
        {
            initInject();
        }
        loadBaseData();//根据获取的数据来调用showView()切换界面
    }

    public void loadBaseData()
    {
        if (!mIsVisible || !isPrepared || !isFirst)
        {
            return;
        }
        loadData();
    }


    @Override
    public void onDetach()
    {
        super.onDetach();
        if (bind != null) {
            bind.unbind();
        }
        if (mPresenter != null)
        {
            mPresenter.onDestroy();
        }
        this.mPresenter = null;
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus()
    {
        return true;
    }


    /**
     * 获取Activity
     *
     * @return
     */
    public BaseActivity getBaseActivity()
    {
        if (mActivity == null)
        {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }

    /**
     * 获取当前Fragment状态
     *
     * @return true为正常 false为未加载或正在删除
     */
    private boolean getStatus()
    {
        return (isAdded() && !isRemoving());
    }

    protected abstract void loadData();

    /**
     * 1
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected abstract void retryRequestData();

    /**
     * 2
     * 网络请求成功在去加载布局
     *
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 3
     * 子类关于View的操作(如setAdapter)都必须在这里面，会因为页面状态不为成功，而binding还没创建就引用而导致空指针。
     * loadData()和initView只执行一次，如果有一些请求需要二次的不要放到loadData()里面。
     */
    protected abstract void initView();

    /**
     * dagger2注入
     */
    protected abstract void initInject();


}
