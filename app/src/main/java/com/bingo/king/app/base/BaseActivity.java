package com.bingo.king.app.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.bingo.king.R;
import com.bingo.king.app.App;
import com.bingo.king.app.EventBusTags;
import com.bingo.king.app.utils.SDFragmentManager;
import com.bingo.king.di.component.AppComponent;
import com.bingo.king.mvp.ui.widget.dialog.ProgressDialog;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import timber.log.Timber;

import static com.bingo.king.app.utils.ThirdViewUtil.convertAutoView;

/**
 * Created by wang on 2017/11/1 16:55.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements IView,View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();
    protected Unbinder mUnbinder;

    /**
     * 触摸返回键是否退出App
     */
    protected boolean mIsExitApp = false;
    protected long mExitTime = 0;


    protected ProgressDialog mProgressDialog;


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = onCreateContentView(savedInstanceState);
            if (layoutResID != 0) {//如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        setupActivityComponent();
        initData(savedInstanceState);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;

    }

    protected AppComponent getAppComponent() {
        return App.getApplication().getAppComponent();
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     */
    public boolean useEventBus() {
        return true;
    }


    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean enableBack, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        if (enableBack) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        }
    }

    public void initToolBar(Toolbar toolbar, boolean enableBack, int resTitle) {
        initToolBar(toolbar, enableBack, getString(resTitle));
    }

    @Override
    public void onBackPressed() {
        if (mIsExitApp) {
            exitApp();
        } else {
            super.onBackPressed();
        }
    }

    public void exitApp() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ToastUtils.showShort("再按一次返回桌面!");
        } else {
            //发送事件,参考下面的方法onExitAppReceive
//            EventBus.getDefault().post(new Message(), EventBusTags.EXITAPP_MESSAGE);
//            AppUtils.exitApp();

            //使App不退出，而是进入后台运行
            moveTaskToBack(false);
        }
        mExitTime = System.currentTimeMillis();
    }

    /**
     * 通过eventbus post事件,远程遥控执行对应方法
     */
    @Subscriber(tag = EventBusTags.EXITAPP_MESSAGE, mode = ThreadMode.MAIN)
    public void onExitAppReceive(Message message) {
        //可以在工具类或者其他类中做相应的退出逻辑
        Timber.d(TAG, "exit_app");
    }


    protected static final String MESSAGE_LOADING = "请稍候...";

    @Override
    public void setState(int state) {

    }

    public void showLoadingDialog() {
        showLoadingDialog(this, MESSAGE_LOADING, true);
    }

    public void showLoadingDialog(String msg) {
        showLoadingDialog(this, msg, true);
    }

    public void showLoadingDialog(boolean cancelable) {
        showLoadingDialog(this, MESSAGE_LOADING, cancelable);
    }

    public void showLoadingDialog(String msg, boolean cancelable) {
        showLoadingDialog(this, msg, cancelable);
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


    public Observable<Object> clicks(int viewId) {
        return clicks(findViewById(viewId));
    }

    public Observable<Object> clicks(View view) {
        return RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS).compose(bindToLifecycle());
    }

    private SDFragmentManager mFragmentManager;

    public SDFragmentManager getSDFragmentManager() {
        if (mFragmentManager == null) {
            mFragmentManager = new SDFragmentManager(getSupportFragmentManager());
        }
        return mFragmentManager;
    }

    /**
     * 初始化 View,
     */
    public abstract int onCreateContentView(Bundle savedInstanceState);

    public abstract void setupActivityComponent();

    /**
     * 初始化数据
     */
    public abstract void initData(Bundle savedInstanceState);

    @Override
    public void onClick(View v) {

    }
}
