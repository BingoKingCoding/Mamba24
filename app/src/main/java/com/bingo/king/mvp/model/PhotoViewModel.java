package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.app.utils.FileUtil;
import com.bingo.king.app.utils.OkHttpDownloader;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.PhotoViewContract;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;


@ActivityScope
public class PhotoViewModel extends BaseModel implements PhotoViewContract.Model
{
    private Application mApplication;

    @Inject
    public PhotoViewModel(IRepository repository,Application application)
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
    public void savePic(String url,OkHttpDownloader.DownloadListener listener)
    {
        OkHttpDownloader.downloadImage(url, FileUtil.IMAGE_CACHE_DIR,listener);
    }
}