package com.bingo.king.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BasePresenterActivity;
import com.bingo.king.di.component.DaggerPhotoViewComponent;
import com.bingo.king.di.module.PhotoViewModule;
import com.bingo.king.mvp.contract.PhotoViewContract;
import com.bingo.king.mvp.presenter.PhotoViewPresenter;
import com.bingo.king.mvp.ui.adapter.PhotoViewPageAdapter;
import com.bingo.king.mvp.ui.widget.PhotoViewPager;

import java.util.ArrayList;

import butterknife.BindView;


public class PhotoViewActivity extends BasePresenterActivity<PhotoViewPresenter> implements PhotoViewContract.View, View.OnClickListener
{

    /**
     * 获取图片url集合(list)
     */
    public static final String EXTRA_IMAGE_URLS = "extra_image_urls";

    /**
     * 获取图片当前位置(int)
     */
    public static final String EXTRA_POSITION = "extra_position";


    @BindView(R.id.viewPager)
    PhotoViewPager mViewPager;
    @BindView(R.id.tv_page)
    TextView tv_page;//当前图片位置
    @BindView(R.id.iv_down_load)
    ImageView iv_down_load;//下载
    /**
     * 获取前一个activity传过来的position
     */
    private int selectPosition;
    /**
     * 存储图片的url集合
     */
    private ArrayList<String> listImageUrl;
    private PhotoViewPageAdapter mAdapter;


    @Override
    protected void setStatusBar()
    {
    }

    @Override
    public void setupActivityComponent()
    {
        DaggerPhotoViewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .photoViewModule(new PhotoViewModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int onCreateContentView(Bundle savedInstanceState)
    {
        return R.layout.activity_photo_view; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        selectPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
        listImageUrl = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);

        mViewPager.addOnPageChangeListener(pageChangeListener);

        mAdapter = new PhotoViewPageAdapter(listImageUrl, this);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dp_10));
        mViewPager.setCurrentItem(selectPosition);

        updateSelected();
        iv_down_load.setOnClickListener(this);
    }


    @Override
    public void hidePullLoading()
    {

    }

    @Override
    public void showMessage(@NonNull String message)
    {
        showSnackbar(message);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_down_load:
                mPresenter.savePic(listImageUrl, selectPosition);
                break;
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            selectPosition = position;
            updateSelected();
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
        }
    };

    private void updateSelected()
    {
        tv_page.setText((selectPosition + 1) + "/" + listImageUrl.size());
    }

    @Override
    public void setState(int state)
    {

    }
}
