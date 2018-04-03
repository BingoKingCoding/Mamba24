package com.bingo.king.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.LoadingBaseActivity;
import com.bingo.king.app.utils.GlideUtils;
import com.bingo.king.app.utils.StringFormatUtil;
import com.bingo.king.di.component.DaggerMovieTopDetailComponent;
import com.bingo.king.di.module.MovieTopDetailModule;
import com.bingo.king.mvp.contract.MovieTopDetailContract;
import com.bingo.king.mvp.model.entity.douban.HotMovieBean;
import com.bingo.king.mvp.model.entity.douban.MovieDetailBean;
import com.bingo.king.mvp.model.entity.douban.PersonBean;
import com.bingo.king.mvp.presenter.MovieTopDetailPresenter;
import com.bingo.king.mvp.ui.widget.HorizontalScrollView;
import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class MovieTopDetailActivity extends LoadingBaseActivity<MovieTopDetailPresenter> implements MovieTopDetailContract.View
{
    private ImageView iv_bg;
    private Toolbar mToolbar;
    private TextView tvOneRatingNumber;
    private TextView tvOneCity;
    private String id;


    NestedScrollView nsvMovieTopDetail;

    HorizontalScrollView hsFilm;

    TextView tvMovieTopDetail;

    private String mMoreUrl;
    private String mMovieName;
    private TextView tvFormerly;

    @Override
    public int onCreateContentView(Bundle savedInstanceState)
    {
        return R.layout.activity_movie_top_detail;
    }

    @Override
    protected void setStatusBar()
    {
    }

    @Override
    public void setupActivityComponent()
    {
        DaggerMovieTopDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .movieTopDetailModule(new MovieTopDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int getContentLayoutId()
    {
        return R.layout.activity_movie_top;
    }

    @Override
    public int getBaseFrameLayoutId()
    {
        return R.id.fl_douban_detail_content;
    }

    @Override
    public void loadData(Bundle savedInstanceState)
    {
        //setState(LoadingPage.STATE_SUCCESS);//如果不需要网络请求的话可以去掉注释 直接设置成功状态
        init();//可以在此进行初始化
        requestData();
    }

    private void init()
    {
        nsvMovieTopDetail = findViewById(R.id.nsv_movie_top_detail);
        hsFilm = findViewById(R.id.hs_film);
        tvMovieTopDetail = findViewById(R.id.tv_movie_top_detail);

        final AppBarLayout appbarMovieTopChild = findViewById(R.id.appbar_movie_top_child);
        iv_bg = findViewById(R.id.iv_bg);
        ImageView iv_one_photo = findViewById(R.id.iv_one_photo);
        TextView tvOneRatingRate = findViewById(R.id.tv_one_rating_rate);
        tvOneRatingNumber = findViewById(R.id.tv_one_rating_number);
        TextView tvOneGenres = findViewById(R.id.tv_one_genres);
        TextView tvOneDay = findViewById(R.id.tv_one_day);
        tvOneCity = findViewById(R.id.tv_one_city);
        tvFormerly = findViewById(R.id.tv_formerly);
        mToolbar = findViewById(R.id.toolbar_douban_detail);
        initToolBar(mToolbar, "");
        appbarMovieTopChild.addOnOffsetChangedListener((appBarLayout, verticalOffset) ->
        {
            if (appbarMovieTopChild.getBottom() > mToolbar.getBottom())
            {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
            } else
            {
                mToolbar.setBackgroundResource(R.color.colorPrimary);
            }
        });

        HotMovieBean.SubjectsBean subjectsBean = (HotMovieBean.SubjectsBean) getIntent().getSerializableExtra("bean");

        if (subjectsBean != null)
        {
            mToolbar.setTitle(subjectsBean.getTitle());
            mToolbar.setSubtitleTextColor(Color.WHITE);
            setImgHeaderBg(subjectsBean.getImages().getMedium());
            GlideUtils.getInstance().loadMovieTopImg(iv_one_photo, subjectsBean.getImages().getLarge());
            tvOneRatingRate.setText("评分：" + subjectsBean.getRating().getAverage());
            tvOneGenres.setText("类型：" + StringFormatUtil.formatGenres(subjectsBean.getGenres()));
            tvOneDay.setText("上映日期：" + subjectsBean.getYear());
            //电影详情的id
            id = subjectsBean.getId();
        }
    }


    private void requestData()
    {
        if (!TextUtils.isEmpty(id))
            mPresenter.requestMovieDetail(id);
    }

    @Override
    protected void retryRequestData()
    {
        requestData();
    }


    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void closeLoadingDialog() {

    }

    @Override
    public void hidePullLoading()
    {

    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }


    private void initToolBar(Toolbar toolbarDoubanDetail, String title)
    {
        setSupportActionBar(toolbarDoubanDetail);
        toolbarDoubanDetail.setTitle(title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
        toolbarDoubanDetail.setTitleTextColor(Color.WHITE);
        toolbarDoubanDetail.setNavigationOnClickListener(view -> onBackPressed());
        toolbarDoubanDetail.setTitleTextAppearance(this, R.style.ToolBar_Title);
        toolbarDoubanDetail.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);
        toolbarDoubanDetail.inflateMenu(R.menu.base_header_menu);
        toolbarDoubanDetail.setOverflowIcon(getResources().getDrawable(R.mipmap.actionbar_more));
        toolbarDoubanDetail.setOnMenuItemClickListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.actionbar_more:// 更多信息
                    setTitleClickMore();
                    break;
            }
            return true;
        });
    }

    private void setTitleClickMore()
    {
        WebActivity.loadUrl(MovieTopDetailActivity.this, mMoreUrl, mMovieName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.base_header_menu, menu);
        return true;
    }

    /**
     * 加载titlebar背景
     */
    private void setImgHeaderBg(String imgUrl)
    {
        if (!TextUtils.isEmpty(imgUrl))
        {
            // 高斯模糊背景  参数：12,5
            Glide.with(this).load(imgUrl)
                    .error(R.mipmap.stackblur_default)
                    .bitmapTransform(new BlurTransformation(this, 12, 5)).into(iv_bg);
        }
    }


    @Override
    public void refreshView(MovieDetailBean data)
    {
        mMoreUrl = data.getAlt();
        mMovieName = data.getTitle();
        tvFormerly.setText("原名：" + data.getOriginal_title());
        tvOneRatingNumber.setText(data.getRatings_count() + "人评分");
        tvOneCity.setText("制作国家/地区：" + data.getCountries() + "");

        List<PersonBean> castsList = data.getCasts();
        for (final PersonBean personBean : castsList)
        {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ConvertUtils.dp2px(120), ConvertUtils.dp2px(200)));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            GlideUtils.getInstance().loadMovieTopImg(imageView, personBean.getAvatars().getLarge());
            hsFilm.addView(imageView);
            imageView.setOnClickListener(v -> WebActivity.loadUrl(MovieTopDetailActivity.this, personBean.getAlt(), "加载中。。。"));
        }
        tvMovieTopDetail.setText(data.getSummary());
    }
}
