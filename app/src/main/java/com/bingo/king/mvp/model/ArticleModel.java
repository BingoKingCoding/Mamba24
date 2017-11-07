package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.app.greendao.DaoGankEntityDao;
import com.bingo.king.app.greendao.GreenDaoHelper;
import com.bingo.king.app.utils.CategoryType;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ArticleContract;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.model.http.IRepository;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class ArticleModel extends BaseModel implements ArticleContract.Model
{
    private Application mApplication;

    @Inject
    public ArticleModel(IRepository repository,Application application)
    {
        super(repository);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public List<DaoGankEntity> getEntity()
    {
        return GreenDaoHelper.getInstance().getDaoSession().getDaoGankEntityDao()
                .queryBuilder()
                .where(DaoGankEntityDao.Properties.Type.notEq(CategoryType.GIRLS_STR))
                .orderDesc(DaoGankEntityDao.Properties.Addtime)
                .list();
    }
}