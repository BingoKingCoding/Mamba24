package com.bingo.king.app.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.bingo.king.app.App;
import com.bingo.king.app.EventBusTags;
import com.bingo.king.di.component.AppComponent;
import com.bingo.king.mvp.ui.widget.LoadingDialogListener;
import com.bingo.king.mvp.ui.widget.ProgressDialog;
import com.blankj.utilcode.util.AppUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

import static com.bingo.king.app.utils.ThirdViewUtil.convertAutoView;

/**
 * Created by wang on 2017/11/1 16:55.
 */

public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity implements IActivity, LoadingDialogListener
{
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;

    /**
     * 触摸返回键是否退出App
     */
    protected boolean mIsExitApp = false;
    protected long mExitTime = 0;

    @Inject
    protected P mPresenter;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs)
    {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            int layoutResID = initView(savedInstanceState);
            if (layoutResID != 0)
            {//如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (useEventBus())
        {
            EventBus.getDefault().register(this);
        }
        setupActivityComponent();
        initData(savedInstanceState);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (useEventBus())
        {
            EventBus.getDefault().unregister(this);
        }
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
        {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        if (mPresenter != null)
        {
            mPresenter.onDestroy();
        }//释放资源
        this.mPresenter = null;

        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;


    }

    protected AppComponent getAppComponent()
    {
        return App.getApplication().getAppComponent();
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
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    @Override
    public boolean useFragment()
    {
        return true;
    }


    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean enable, String title)
    {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        getSupportActionBar().setDisplayShowHomeEnabled(enable);//2.显示toolbar的返回按钮12要一起用
        getSupportActionBar().setDisplayShowTitleEnabled(enable);//显示toolbar的标题
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    public void initToolBar(Toolbar toolbar, boolean enable, int resTitle)
    {
        initToolBar(toolbar, enable, getString(resTitle));
    }

    @Override
    public void onBackPressed()
    {
        if (mIsExitApp)
        {
            exitApp();
        } else
        {
            super.onBackPressed();
        }
    }

    public void exitApp()
    {
        if (System.currentTimeMillis() - mExitTime > 2000)
        {
            showSnackbar("再按一次退出!");
        } else
        {
            //发送事件,参考下面的方法onExitAppReceive
            EventBus.getDefault().post(new Message(), EventBusTags.EXITAPP_MESSAGE);
            AppUtils.exitApp();
        }
        mExitTime = System.currentTimeMillis();
    }

    /**
     * 通过eventbus post事件,远程遥控执行对应方法
     */
    @Subscriber(tag = EventBusTags.EXITAPP_MESSAGE, mode = ThreadMode.MAIN)
    public void onExitAppReceive(Message message)
    {
        //可以在工具类或者其他类中做相应的退出逻辑
    }

    private ProgressDialog mProgressDialog;

    @Override
    public void showLoadingDialog()
    {
        showLoadingDialog(false, "正在加载中...");
    }

    public void showLoadingDialog(String msg)
    {
        showLoadingDialog(false, msg);
    }

    public void showLoadingDialog(boolean cancelAble, String msg)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(this);
        }
        if (!mProgressDialog.isShowing())
        {
//            mProgressDialog.setTextMsg(msg);
            mProgressDialog.setCancelable(cancelAble);
            mProgressDialog.show();
        }
        setLoadingState(true);
    }

    @Override
    public void closeLoadingDialog()
    {
        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();
        }
        setLoadingState(false);
    }

    private void setLoadingState(boolean isLoading)
    {
        if (mPresenter != null)
        {
            mPresenter.setLoadingDialog(isLoading);
        }
    }


    /**
     * 用snackbar显示
     *
     * @param message
     */
    protected void showSnackbar(String message)
    {
        showSnackbar(message, false);
    }

    /**
     * 用snackbar显示
     *
     * @param message
     */
    protected void showSnackbarWithLong(String message)
    {
        showSnackbar(message, true);
    }


    /**
     * 使用snackbar显示内容
     *
     * @param message
     * @param isLong
     */
    protected void showSnackbar(String message, boolean isLong)
    {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }


    public Observable<Object> clicks(int viewId)
    {
        return clicks(findViewById(viewId));
    }

    public Observable<Object> clicks(View view)
    {
        return RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS).compose(bindToLifecycle());
    }

}
