package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.app.utils.CollectionUtil;
import com.bingo.king.app.utils.OkHttpDownloader;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.PhotoViewContract;
import com.blankj.utilcode.util.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;



@ActivityScope
public class PhotoViewPresenter extends BasePresenter<PhotoViewContract.Model, PhotoViewContract.View>
{
    private Application mApplication;

    @Inject
    public PhotoViewPresenter(PhotoViewContract.Model model, PhotoViewContract.View rootView
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

    public void savePic(ArrayList<String> listImageUrl, int selectPosition)
    {
        String url = CollectionUtil.get(listImageUrl, selectPosition);
        mModel.savePic(url, new OkHttpDownloader.DownloadListener()
        {
            @Override
            public void onSuccess()
            {
                ToastUtils.showShort("图片保存成功");
            }

            @Override
            public void onFailure(IOException e)
            {
                ToastUtils.showShort("图片保存失败");
            }
        });
    }

}
