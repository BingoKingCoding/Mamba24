package com.bingo.king.app.base;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.bingo.king.mvp.ui.widget.LoadingPage;

import butterknife.ButterKnife;

/**
 * <请描述这个类是干什么的>
 */

public abstract class LoadingBaseActivity<P extends IPresenter> extends BasePresenterActivity<P>
{

    protected LoadingPage mLoadingPage;

    protected FrameLayout flBaseContent;


    @Override
    public void initData(Bundle savedInstanceState)
    {
        flBaseContent = findViewById(getBaseFrameLayoutId());
        if (mLoadingPage == null)
        {
            mLoadingPage = new LoadingPage(this)
            {
                @Override
                protected int getContentLayoutId()
                {
                    return LoadingBaseActivity.this.getContentLayoutId();
                }

                @Override
                protected void initView()
                {
                    mUnbinder = ButterKnife.bind(LoadingBaseActivity.this, contentView);
                }

                @Override
                protected void retryRequestData()
                {
                    LoadingBaseActivity.this.retryRequestData();
                }
            };
        }
        flBaseContent.addView(mLoadingPage);
        initUI();
        loadData(savedInstanceState);
    }

    protected void initUI(){
        
    }

    public void setState(int state)
    {
        mLoadingPage.showPage(state);
    }


    protected abstract void loadData(Bundle savedInstanceState);


    /**
     * 1
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     * * 如果是静态页面不需要网络请求的在子类的loadData方法中添加以下2行即可
     * mLoadingPage.state = STATE_SUCCESS;
     * mLoadingPage.showPage();
     * 或者调用setState(AppConstants.STATE_SUCCESS)
     */
    protected abstract void retryRequestData();


    /**
     * 2
     * 网络请求成功在去加载布局
     *
     * @return
     */
    public abstract int getContentLayoutId();

    /**
     * 获取子类FrameLayout的Id是孙子类的容器
     *
     * @return
     */
    public abstract int getBaseFrameLayoutId();


}
