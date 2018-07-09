package com.bingo.king.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bingo.king.app.App;
import com.bingo.king.di.component.AppComponent;
import com.bingo.king.mvp.model.http.rxerrorhandler.StatefulCallback;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.blankj.utilcode.util.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/6/26:19:32.
 * Email:634051075@qq.com
 */

public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements StatefulCallback {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected P mPresenter;

    public LoadingPage mLoadingPage;
    protected View contentView;
    private Unbinder bind;
    private BaseActivity mActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d(TAG, "onCreate");
        if (useEventBus()) {//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);
        }//注册到事件主线
        initComponent();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (mLoadingPage == null) {
            mLoadingPage = new LoadingPage(getContext()) {
                @Override
                protected void initView() {
                    BaseFragment.this.contentView = this.contentView;
                    bind = ButterKnife.bind(BaseFragment.this, contentView);
                }

                @Override
                protected int getContentLayoutId() {
                    return BaseFragment.this.getContentLayoutId();
                }

                @Override
                protected void retryRequestData() {
                    BaseFragment.this.retryRequestData();
                }
            };
        }
        return addTitleView(mLoadingPage);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }


    protected abstract void initData(Bundle savedInstanceState);

    private View addTitleView(View contentView) {
        View viewFinal = contentView;
        int resId = this.onCreateTitleViewResId();
        if (resId != 0) {
            View titleView = LayoutInflater.from(getActivity()).inflate(resId, (ViewGroup) contentView, false);
            LinearLayout linAll = new LinearLayout(getActivity());
            linAll.setOrientation(LinearLayout.VERTICAL);
            linAll.addView(titleView);
            linAll.addView(contentView);
            viewFinal = linAll;
        }
        return viewFinal;
    }

    protected int onCreateTitleViewResId() {
        return 0;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        this.mPresenter = null;

        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);//注册到事件主线
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     */

    protected boolean useEventBus() {
        return true;
    }


    public void setState(int state) {
        mLoadingPage.showPage(state);
    }


    /**
     * 获取Activity
     */
    protected BaseActivity getBaseActivity() {
        if (mActivity == null) {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }

    protected AppComponent getAppComponent() {
        return App.getApplication().getAppComponent();
    }


    public void showMessage(String message) {
        ToastUtils.showShort(message);
    }

    protected abstract void initComponent();


    public void showLoadingDialog() {
        getBaseActivity().showLoadingDialog();
    }

    public void showLoadingDialog(String msg) {
        getBaseActivity().showLoadingDialog(msg);
    }

    public void closeLoadingDialog() {
        getBaseActivity().closeLoadingDialog();
    }

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

}
