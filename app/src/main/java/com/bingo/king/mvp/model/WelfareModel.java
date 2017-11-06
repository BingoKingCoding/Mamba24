package com.bingo.king.mvp.model;

import android.os.Message;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.app.greendao.GreenDaoHelper;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.WelfareContract;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.IRepository;
import com.king.king.app.greendao.DaoGankEntityDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/22 17:04.
 */
@ActivityScope
public class WelfareModel extends BaseModel implements WelfareContract.Model
{
    @Inject
    public WelfareModel(IRepository repository)
    {
        super(repository);
    }

    @Override
    public Observable<GankEntity> getRandomGirl()
    {
        return mRepository.getRandomGirl();
    }

    @Override
    public Message addGankEntity(DaoGankEntity daoGankEntity)
    {
        Message message = new Message();
        List<DaoGankEntity> list = GreenDaoHelper.getInstance().getDaoSession().getDaoGankEntityDao()
                .queryBuilder()
                .where(DaoGankEntityDao.Properties._id.eq(daoGankEntity._id))
                .list();
        if (list.size() > 0){
            message.what =  101;
        }else {
            long insert = GreenDaoHelper.getInstance().getDaoSession().getDaoGankEntityDao().insert(daoGankEntity);
            if (insert > 0){
                message.what =  102;
            }else {
                message.what =  103;
            }
        }
        return message;
    }
}
