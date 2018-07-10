package com.bingo.king.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bingo.king.app.App;
import com.bingo.king.di.component.AppComponent;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.bingo.king.mvp.ui.widget.ProgressDialog;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/6/26:19:32.
 * Email:634051075@qq.com
 */

public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements IView{

    @Inject
    protected P mPresenter;

    public LoadingPage mLoadingPage;
    protected ProgressDialog mProgressDialog;
    protected View contentView;
    private Unbinder bind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
        if (useEventBus()) {//如果要使用eventBus请将此方法返回true
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
                    BaseFragment.this.initView(this.contentView);
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

    protected void initView(View view) {
    }

    protected void initData(Bundle savedInstanceState) {
    }

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

        if (useEventBus())//如果要使用eventBus请将此方法返回true
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


    protected static final String MESSAGE_LOADING = "请稍候...";

    public void showLoadingDialog() {
        showLoadingDialog(getActivity(), MESSAGE_LOADING, true);
    }

    public void showLoadingDialog(String msg) {
        showLoadingDialog(getActivity(), msg, true);
    }

    public void showLoadingDialog(boolean cancelable) {
        showLoadingDialog(getActivity(), MESSAGE_LOADING, cancelable);
    }

    public void showLoadingDialog(String msg, boolean cancelable) {
        showLoadingDialog(getActivity(), msg, cancelable);
    }

    public void showLoadingDialog(Activity activity, String msg, boolean cancelable) {
        closeLoadingDialog();
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(activity);
        }
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setText(TextUtils.isEmpty(msg) ? MESSAGE_LOADING : msg);
        mProgressDialog.show();
    }

    public void closeLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void hidePullLoading() {

    }

    protected AppComponent getAppComponent() {
        return App.getApplication().getAppComponent();
    }

    protected void initComponent() {
    }

    /**
     * 1
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected void retryRequestData() {
    }

    /**
     * 2
     * 网络请求成功去加载布局
     */
    protected abstract int getContentLayoutId();

}
