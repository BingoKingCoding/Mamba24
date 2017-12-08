package com.bingo.king.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
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
import com.bingo.king.mvp.ui.widget.X5WebView;

import butterknife.BindView;
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
    X5WebView wvDetailContent;


    private int id;



    boolean isBottomShow = true;//是否显示

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
        super.loadData(savedInstanceState);
        //setState(LoadingPage.STATE_SUCCESS);//如果不需要网络请求的话可以去掉注释 直接设置成功状态
        init();//可以在此进行初始化
    }

    private void init()
    {
        getIntentData();
        initScrollView();
        mPresenter.requestDetailInfo(id);
    }

    private void getIntentData()
    {
        id = getIntent().getIntExtra(EXTRA_ID,0);
    }

    private void initScrollView()
    {
        nsvZhihuDetail.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                Timber.d(TAG,"scrollY = " +scrollY +"oldScrollY = " +oldScrollY);
                if (scrollY - oldScrollY >0 && isBottomShow)//下移隐藏
                {
                    isBottomShow = false;
                    flDetailBottom.animate().translationY(flDetailBottom.getHeight());

                }else if(scrollY - oldScrollY < 0 && !isBottomShow){//上移出现
                    isBottomShow = true;
                    flDetailBottom.animate().translationY(0);

                }
            }
        });
    }

    @Override
    protected void retryRequestData()
    {
        mPresenter.requestDetailInfo(id);
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
    public static void start(Activity oldActivity,int id){
        Intent intent = new Intent();
        intent.setClass(oldActivity, ZhihuDetailActivity.class);
        intent.putExtra(EXTRA_ID, id);
    }

    @Override
    public void showDetailInfo(ZhihuDetailBean zhihuDetailBean)
    {
        String shareUrl = zhihuDetailBean.getShare_url();
        loadImage(zhihuDetailBean.getImage());
        tv_copyright.setText(zhihuDetailBean.getImage_source());

        String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(), zhihuDetailBean.getCss(), zhihuDetailBean.getJs());
        wvDetailContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showExtraInfo(DetailExtraBean detailExtraBean)
    {

    }
}
