package com.bingo.king.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Preconditions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bingo.king.app.App;
import com.bingo.king.di.component.AppComponent;
import com.bingo.king.mvp.model.http.rxerrorhandler.StatefulCallback;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/2:20:22.
 *
 * @Email:634051075@qq.com
 */

public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements StatefulCallback
{
    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected P mPresenter;

    public LoadingPage mLoadingPage;

    private boolean isFragmentVisible = false;     // fragment是否显示了

    private boolean isReuseView;

    private boolean isFirstVisible = true; //只加载一次界面

    protected View contentView;
    private Unbinder bind;

    private View rootView;
    private BaseActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Timber.d(TAG, "onCreateView");
        if (mLoadingPage == null)
        {
            mLoadingPage = new LoadingPage(getContext())
            {
                @Override
                protected void initView()
                {
                    BaseFragment.this.contentView = this.contentView;
                    bind = ButterKnife.bind(BaseFragment.this, contentView);
                }

                @Override
                protected int getContentLayoutId()
                {
                    return BaseFragment.this.getContentLayoutId();
                }

                @Override
                protected void retryRequestData()
                {
                    BaseFragment.this.retryRequestData();
                }
            };
        }
        return addTitleView(mLoadingPage);
    }

    private View addTitleView(View contentView) {
        View viewFinal = contentView;
        int resId = this.onCreateTitleViewResId();
        if(resId != 0) {
            View titleView = LayoutInflater.from(getActivity()).inflate(resId, (ViewGroup)contentView, false);
            LinearLayout linAll = new LinearLayout(getActivity());
            linAll.setOrientation(LinearLayout.VERTICAL);
            linAll.addView(titleView);
            linAll.addView(contentView);
            viewFinal = linAll;
        }
        return viewFinal;
    }

    protected int onCreateTitleViewResId()
    {
        return 0;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d(TAG, "onCreate");
        if (useEventBus())
        {//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);
        }//注册到事件主线
        initVariable();
        initComponent();

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (rootView == null)
        {
            rootView = view;
        }
        Timber.d(TAG, "onViewCreated");
        super.onViewCreated(isReuseView ? rootView : view, savedInstanceState);

        initData(savedInstanceState);
    }


    @Override
    public void onStart()
    {
        super.onStart();
        Timber.d(TAG, "onStart");
        if (getUserVisibleHint())
        {
            if (isFirstVisible)
            {
                onFragmentFirstVisible();
                isFirstVisible = false;
            }
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }


    private void initVariable()
    {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        Timber.d(TAG, "setUserVisibleHint");
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null)
        {
            return;
        }
        if (isFirstVisible && isVisibleToUser)
        {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser)
        {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible)
        {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }

    }


    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse)
    {
        isReuseView = isReuse;
    }


    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible)
    {
        Timber.d(TAG, "onFragmentVisibleChange");
    }


    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     */
    protected void onFragmentFirstVisible()
    {
        Timber.d(TAG, "onFragmentFirstVisible");
    }


    protected boolean isFragmentVisible()
    {
        return isFragmentVisible;
    }


    public void setState(int state)
    {
        mLoadingPage.state = state;
        mLoadingPage.showPage();
    }

    public void showLoadingDialog(String msg)
    {
        getBaseActivity().showLoadingDialog(msg);
    }

    public void closeLoadingDialog()
    {
        getBaseActivity().closeLoadingDialog();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Timber.d(TAG, "onDestroy");
        initVariable();
        if (bind != null)
        {
            bind.unbind();
        }
        if (mPresenter != null)
        {
            mPresenter.onDestroy();
        }
        this.mPresenter = null;

        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);//注册到事件主线
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     */

    protected boolean useEventBus()
    {
        return true;
    }


    /**
     * 获取Activity
     */
    protected BaseActivity getBaseActivity()
    {
        if (mActivity == null)
        {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }

    protected AppComponent getAppComponent()
    {
        return App.getApplication().getAppComponent();
    }


    /**
     * 用snackbar显示
     */
    protected void showSnackbar(String message)
    {
        showSnackbar(message, false);
    }

    /**
     * 用snackbar显示
     */
    protected void showSnackbarWithLong(String message)
    {
        showSnackbar(message, true);
    }


    /**
     * 使用snackbar显示内容
     */
    protected void showSnackbar(String message, boolean isLong)
    {
        View view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }


    /**
     * 获取当前Fragment状态
     *
     * @return true为正常 false为未加载或正在删除
     */
    protected boolean getStatus()
    {
        return (isAdded() && !isRemoving());
    }


    protected abstract void initComponent();


    protected abstract void initData(Bundle savedInstanceState);


    /**
     * 1
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected abstract void retryRequestData();

    /**
     * 2
     * 网络请求成功去加载布局
     */
    protected abstract int getContentLayoutId();


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        Timber.d(TAG, "onAttach");
    }


    @Override
    public void onResume()
    {
        super.onResume();
        Timber.d(TAG, "onResume");
    }


}
