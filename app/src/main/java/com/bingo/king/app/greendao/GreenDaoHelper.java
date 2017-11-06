package com.bingo.king.app.greendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

/**
 * <请描述这个类是干什么的>
 * 可参考http://blog.csdn.net/wjk343977868/article/details/53943135
 * Created by wwb on 2017/9/21 15:16.
 */

public class GreenDaoHelper
{
    private volatile static GreenDaoHelper singleton;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    private GreenDaoHelper()
    {
    }

    public static GreenDaoHelper getInstance()
    {
        if (singleton == null)
        {
            synchronized (Singleton.class)
            {
                if (singleton == null)
                {
                    singleton = new GreenDaoHelper();
                }
            }
        }
        return singleton;
    }


    /**
     * 初始化greenDao，这个操作建议在Application初始化的时候添加；
     *
     * @param application
     */
    public void initDatabase(Application application)
    {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(application, "learn-db", null);
        db = mHelper.getWritableDatabase();
//        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster.DevOpenHelper getHelper()
    {
        return mHelper;
    }

    public DaoMaster getDaoMaster()
    {
        return mDaoMaster;
    }

    public DaoSession getDaoSession()
    {
        return mDaoSession;
    }

    public SQLiteDatabase getDb()
    {
        return db;
    }
}
