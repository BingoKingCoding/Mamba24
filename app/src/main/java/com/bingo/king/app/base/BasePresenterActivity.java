package com.bingo.king.app.base;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/1 16:38.
 */

public abstract class BasePresenterActivity<P extends IPresenter> extends BaseActivity
{

    @Inject
    protected P mPresenter;

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mPresenter != null)
        {
            mPresenter.onDestroy();
        }//释放资源
        this.mPresenter = null;
    }
}
