package com.bingo.king.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseCoordinatorActivity;
import com.bingo.king.app.utils.HtmlUtil;
import com.bingo.king.di.component.DaggerZhihuDetailComponent;
import com.bingo.king.di.module.ZhihuDetailModule;
import com.bingo.king.mvp.contract.ZhihuDetailContract;
import com.bingo.king.mvp.model.entity.zhihu.DetailExtraBean;
import com.bingo.king.mvp.model.entity.zhihu.ZhihuDetailBean;
import com.bingo.king.mvp.presenter.ZhihuDetailPresenter;
import com.bingo.king.mvp.ui.widget.web.ShareUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;


public class ZhihuDetailActivity extends BaseCoordinatorActivity<ZhihuDetailPresenter> implements ZhihuDetailContract.View
{
    @BindView(R.id.tv_detail_bottom_like)
    TextView tvDetailBottomLike;
    @BindView(R.id.tv_detail_bottom_comment)
    TextView tvDetailBottomComment;
    @BindView(R.id.fl_detail_bottom)
    FrameLayout flDetailBottom;
    @BindView(R.id.nsv_zhihu_detail)
    NestedScrollView nsvZhihuDetail;
    @BindView(R.id.wv_detail_content)
    WebView wvDetailContent;

    private int id;

    boolean isBottomShow = true;//是否显示
    int allNum = 0;
    int shortNum = 0;
    int longNum = 0;
    private String mShareUrl;

    @Override
    public void setupActivityComponent()
    {
        DaggerZhihuDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .zhihuDetailModule(new ZhihuDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int getContentLayoutId()
    {
        return R.layout.activity_zhihu_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void loadData(Bundle savedInstanceState)
    {
        //setState(LoadingPage.STATE_SUCCESS);//如果不需要网络请求的话可以去掉注释 直接设置成功状态
        init();//可以在此进行初始化
    }

    private void init()
    {
        if (toolbar != null)
        {
            initToolBar(toolbar, true, "加载中");
        }
        getIntentData();
        initScrollView();

        mPresenter.requestDetailInfo(id);
        mPresenter.requestDetailExtraInfo(id);
        fab.setOnClickListener(v ->
                ShareUtils.share(ZhihuDetailActivity.this, mShareUrl));
    }

    private void getIntentData()
    {
        id = getIntent().getIntExtra(EXTRA_ID, 0);
    }

    private void initScrollView()
    {
        nsvZhihuDetail.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
        {
            Timber.d(TAG, "scrollY = " + scrollY + "oldScrollY = " + oldScrollY);
            if (scrollY - oldScrollY > 0 && isBottomShow)//下移隐藏
            {
                isBottomShow = false;
                flDetailBottom.animate().translationY(flDetailBottom.getHeight());

            } else if (scrollY - oldScrollY < 0 && !isBottomShow)
            {//上移出现
                isBottomShow = true;
                flDetailBottom.animate().translationY(0);

            }
        });
    }

    @Override
    protected void retryRequestData()
    {
        mPresenter.requestDetailInfo(id);
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

    public static final String EXTRA_ID = "id";

    public static void start(Activity oldActivity, int id)
    {
        Intent intent = new Intent();
        intent.setClass(oldActivity, ZhihuDetailActivity.class);
        intent.putExtra(EXTRA_ID, id);
    }

    @Override
    public void showDetailInfo(ZhihuDetailBean zhihuDetailBean)
    {
        mShareUrl = zhihuDetailBean.getShare_url();
        loadImage(zhihuDetailBean.getImage());

        ctl_toolbar.setTitle(zhihuDetailBean.getTitle());
        tv_copyright.setText(zhihuDetailBean.getImage_source());

        String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(), zhihuDetailBean.getCss(), zhihuDetailBean.getJs());
        wvDetailContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showExtraInfo(DetailExtraBean detailExtraBean)
    {
        tvDetailBottomLike.setText(String.format(Locale.getDefault(), getResources().getString(R.string.zh_like), detailExtraBean.getPopularity()));
        tvDetailBottomComment.setText(String.format(Locale.getDefault(), getResources().getString(R.string.zh_comment), detailExtraBean.getComments()));
        allNum = detailExtraBean.getComments();
        shortNum = detailExtraBean.getShort_comments();
        longNum = detailExtraBean.getLong_comments();
    }

    @OnClick(R.id.tv_detail_bottom_share)
    void shareUrl()
    {
        ShareUtils.share(this, mShareUrl);
    }


    @OnClick(R.id.tv_detail_bottom_comment)
    void gotoComment()
    {
        Intent intent = new Intent();
        intent.setClass(this, ZhiHuCommentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("allNum", allNum);
        intent.putExtra("shortNum", shortNum);
        intent.putExtra("longNum", longNum);
        startActivity(intent);
    }


}
