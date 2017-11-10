package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.app.greendao.DaoGankEntityDao;
import com.bingo.king.app.greendao.GreenDaoHelper;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.DetailContract;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.IRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class DetailModel extends BaseModel implements DetailContract.Model
{
    private Application mApplication;

    @Inject
    public DetailModel(IRepository repository,Application application)
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
    public Observable<GankEntity> getRandomGirl()
    {
        return mRepository.getRandomGirl();
    }

    @Override
    public List<DaoGankEntity> queryById(String id)
    {
        return GreenDaoHelper
                .getInstance()
                .getDaoSession()
                .getDaoGankEntityDao()
                .queryBuilder()
                .where(DaoGankEntityDao.Properties._id.eq(id))
                .list();
    }

    @Override
    public void removeByid(String id)
    {
            GreenDaoHelper.getInstance().getDaoSession().getDaoGankEntityDao()
                    .queryBuilder().where(DaoGankEntityDao.Properties._id.eq(id))
                    .buildDelete().executeDeleteWithoutDetachingEntities();
    }

    @Override
    public void addGankEntity(DaoGankEntity entity)
    {
            GreenDaoHelper.getInstance().getDaoSession().getDaoGankEntityDao().insert(entity);
    }
}