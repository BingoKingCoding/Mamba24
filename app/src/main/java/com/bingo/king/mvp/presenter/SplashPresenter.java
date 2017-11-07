package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.SplashContract;

import javax.inject.Inject;


@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View>
{
    private Application mApplication;
    @Inject
    public SplashPresenter(SplashContract.Model model, SplashContract.View rootView
            , Application application)
    {
        super(model, rootView);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mApplication = null;
    }

//    public void getADImages(){
//        mRootView.delaySplash(mModel.getAllAD());
//    }

//    public void requestSplash(String deviceId){
//        mModel.requestSplash(deviceId)
//                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3,2))
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ErrorHandleSubscriber<SplashEntity>(mErrorHandler)
//                {
//                    @Override
//                    public void onNext(@NonNull SplashEntity splashEntity)
//                    {
//                        if (NetworkUtils.isWifiConnected())
//                        {
//                            if (splashEntity != null)
//                            {
//                                List<String> imgs = splashEntity.getImages();
//                                for (String url : imgs)
//                                {
//                                    OkHttpDownloader.downloadADImage(url);
//                                }
//                            }
//                        } else
//                        {
//                            Timber.i("不是WIFI环境,就不去下载图片了");
//                        }
//                    }
//                });
//    }

}
