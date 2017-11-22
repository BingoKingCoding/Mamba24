package com.bingo.king.mvp.ui.widget.web;

import android.app.Activity;
import android.content.Intent;

/**
 * <JS调用原生>
 * Created by wwb on 2017/7/4 17:54.
 */

public class BaseJsHandler
{
    private String name;
    private Activity mActivity;

    public BaseJsHandler(String name, Activity activity)
    {
        super();
        this.name = name;
        this.mActivity = activity;
    }

    public Activity getActivity()
    {
        return mActivity;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    protected void startActivity(Intent intent)
    {
        if (intent != null && mActivity != null)
        {
            mActivity.startActivity(intent);
        }
    }

    protected void finish()
    {
        if (mActivity != null)
        {
            mActivity.finish();
        }
    }
}
