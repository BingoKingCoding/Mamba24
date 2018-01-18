package com.bingo.king.mvp.model.entity.dao;


import com.bingo.king.BuildConfig;
import com.bingo.king.mvp.model.entity.InitActBean;
import com.fanwe.lib.cache.SDDisk;

import timber.log.Timber;

public class InitActBeanDao
{
    public static boolean insertOrUpdate(InitActBean model)
    {
        long start = System.currentTimeMillis();

        boolean result = SDDisk.openInternal().putObject(model);

        if (BuildConfig.LOG_DEBUG && result)
        {
            Timber.i("insertOrUpdate time" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public static InitActBean query()
    {
        long start = System.currentTimeMillis();

        InitActBean model = SDDisk.openInternal().getObject(InitActBean.class);

        if (BuildConfig.LOG_DEBUG && model != null)
        {
            Timber.i("query time:" + (System.currentTimeMillis() - start));
        }

        return model;
    }

    public static void delete()
    {
        SDDisk.openInternal().removeObject(InitActBean.class);
    }
}
