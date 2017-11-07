package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.SplashContract;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;


/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/9/21:22:19.
 *
 * @Email:634051075@qq.com
 */
@ActivityScope
public class SplashModel extends BaseModel implements SplashContract.Model
{
    private Application mApplication;

    @Inject
    public SplashModel(IRepository repository, Application application)
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

//    @Override
//    public Observable<SplashEntity> requestSplash(String deviceId)
//    {
//        String client = "android";
//        String version = "1.3.0";
//        Long time = System.currentTimeMillis() / 1000;
//        RetrofitUrlManager.getInstance().putDomain("adimages", Api.ADURL);
//        return mRepositoryManager.obtainRetrofitService(CommonService.class)
//                .requestSplash(client,version,time,deviceId);
//    }
//
//    @Override
//    public List<String> getAllAD()
//    {
//        return DDFileUtil.getAllAD();
//    }
}
