package com.bingo.king.mvp.model.entity.dao;

import com.bingo.king.BuildConfig;
import com.bingo.king.mvp.model.entity.UserBean;
import com.bingo.king.mvp.model.entity.event.EUpdateUserInfo;
import com.fanwe.lib.cache.SDDisk;

import org.simple.eventbus.EventBus;

import timber.log.Timber;

/**
 * <请描述这个类是干什么的>
 */

public class UserBeanDao
{
    public static boolean insertOrUpdate(UserBean model)
    {
        long start = System.currentTimeMillis();

        boolean result = SDDisk.openInternal().setMemorySupport(true).putObject(model);

        if (BuildConfig.DEBUG && result)
        {
            Timber.i("insertOrUpdate time:" + (System.currentTimeMillis() - start));
        }

        //更新用户最新信息
        EUpdateUserInfo event = new EUpdateUserInfo();
        event.user = model;
        EventBus.getDefault().post(event);

        return result;
    }

    public static UserBean query()
    {
        long start = System.currentTimeMillis();

        UserBean model = SDDisk.openInternal().setMemorySupport(true).getObject(UserBean.class);

        if (BuildConfig.DEBUG && model != null)
        {
            Timber.i("query time:" + (System.currentTimeMillis() - start));
        }

        return model;
    }

    public static void delete()
    {
        SDDisk.openInternal().removeObject(UserBean.class);
    }

    /**
     * 获取用户id
     *
     * @return
     */
    public static String getUserId()
    {
        UserBean model = query();
        if (model == null)
        {
            return "";
        }
        return model.getUser_id();
    }

    /**
     * 获取用户头像路径
     *
     * @return
     */
    public static String getUserHeadImage()
    {
        UserBean model = query();
        if (model == null)
        {
            return "";
        }
        return model.getHead_image();
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public static String getUserNickName()
    {
        UserBean model = query();
        if (model == null)
        {
            return "";
        }
        return model.getNick_name();
    }
}
