package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.app.greendao.DaoGankEntityDao;
import com.bingo.king.app.greendao.GreenDaoHelper;
import com.bingo.king.app.utils.CategoryType;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MeiziContract;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.model.http.IRepository;
import com.blankj.utilcode.util.EmptyUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class MeiziModel extends BaseModel implements MeiziContract.Model
{
    private Application mApplication;
    private List<DaoGankEntity> mDaoGankEntityList;
    private ArrayList<String> mImages;
    @Inject
    public MeiziModel(IRepository repository,Application application)
    {
        super(repository);
        this.mApplication = application;
        mImages = new ArrayList<>();
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
        mDaoGankEntityList = GreenDaoHelper.getInstance().getDaoSession().getDaoGankEntityDao()
                .queryBuilder()
                .where(DaoGankEntityDao.Properties.Type.eq(CategoryType.GIRLS_STR))
                .orderDesc(DaoGankEntityDao.Properties.Addtime)
                .list();
        return mDaoGankEntityList;
    }

    @Override
    public ArrayList<String> getImages()
    {
        if (EmptyUtils.isNotEmpty(mDaoGankEntityList))
        {
            mImages.clear();
            for (DaoGankEntity daoGankEntity : mDaoGankEntityList)
            {
                mImages.add(daoGankEntity.getUrl());
            }
            return mImages;
        }
        return new ArrayList<>();
    }
}