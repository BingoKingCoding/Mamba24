package com.bingo.king.mvp.ui.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bingo.king.R;
import com.bingo.king.app.ARouterPaths;
import com.bingo.king.app.base.CoordinatorBaseActivity;
import com.bingo.king.di.component.DaggerDetailComponent;
import com.bingo.king.di.module.DetailModule;
import com.bingo.king.mvp.contract.DetailContract;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.presenter.DetailPresenter;
import com.bingo.king.mvp.ui.widget.X5WebView;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

import static com.bingo.king.app.EventBusTags.EXTRA_DETAIL;

@Route(path = ARouterPaths.MAIN_DETAIL)
public class DetailActivity extends CoordinatorBaseActivity<DetailPresenter> implements DetailContract.View
{
    @BindView(R.id.webview)
    X5WebView webview;
    private GankEntity.ResultsBean entity;
    private boolean isFavorite;

    @Override
    public void setupActivityComponent()
    {
        DaggerDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .detailModule(new DetailModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void loadData(Bundle savedInstanceState)
    {
        super.loadData(savedInstanceState);
        entity = (GankEntity.ResultsBean) getIntent().getExtras().getSerializable(EXTRA_DETAIL);
        mPresenter.getGirl();
        mPresenter.getQuery(entity._id);
        if (toolbar != null) {
                initToolBar(toolbar,true,entity.desc);
        }
        fab.setImageResource(R.mipmap.ic_heart);
        fab.setOnClickListener(v ->
        {
            if (isFavorite)
            {
                ToastUtils.showShort("已移除收藏夹");
                mPresenter.removeByid(entity);
            }else {
                ToastUtils.showShort("已添加到收藏夹");
                mPresenter.addToFavorites(entity);
            }
        });

        initWebView();
    }


    @Override
    protected void retryRequestData()
    {
        mPresenter.getGirl();
        webview.loadUrl(entity.url);
    }

    @Override
    public int getContentLayoutId()
    {
        return R.layout.activity_detail;
    }

    private void initWebView() {
        webview.setWebViewClient(new WebViewClient(){
            /**
             * 防止加载网页时调起系统浏览器
             */
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.loadUrl(entity.url);
    }


    @Override
    public void hideLoading()
    {

    }

    @Override
    public void endLoadMore()
    {

    }

    @Override
    public void showMessage(@NonNull String message)
    {
        showSnackbar(message);
    }


    @Override
    public void setData(String url)
    {
        loadImage(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFavoriteChange(boolean isFavorite)
    {
        this.isFavorite = isFavorite;
        if (isFavorite)
        {
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        }else {
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.C4)));
        }

    }
}
