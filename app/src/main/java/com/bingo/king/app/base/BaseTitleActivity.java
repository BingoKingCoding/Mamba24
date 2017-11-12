package com.bingo.king.app.base;

import android.os.Bundle;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/12:19:46.
 *
 * @Email:634051075@qq.com
 */

public abstract class BaseTitleActivity<P extends IPresenter> extends LoadingBaseActivity<P>
{
    @Override
    public int initView(Bundle savedInstanceState)
    {
        return 0;
    }


    @Override
    public int getBaseFrameLayoutId()
    {
        return 0;
    }

    @Override
    protected void loadData(Bundle savedInstanceState)
    {

    }

}
