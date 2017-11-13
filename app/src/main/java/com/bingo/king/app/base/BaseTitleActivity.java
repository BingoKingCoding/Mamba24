package com.bingo.king.app.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.utils.CommonUtils;

/**
 *
 */

public abstract class BaseTitleActivity<P extends IPresenter> extends LoadingBaseActivity<P>
{
    protected Toolbar toolbar;
    protected TextView tv_title;

    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_base_toolbar;
    }

    @Override
    public int getBaseFrameLayoutId()
    {
        return R.id.base_fl_content;
    }

    @Override
    protected void loadData(Bundle savedInstanceState)
    {
        initControlViews();
    }

    /**
     * 控件初始化操作
     */
    private void initControlViews()
    {
        toolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.toolbar_title);

        //设置相关默认操作
        setTitleNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setTitleBgColor(R.color.base_title_color);

        setInflateMenu();

        //左边Navigation Button监听回调
        toolbar.setNavigationOnClickListener(v -> callbackOnClickNavigationAction(v));
        //右边菜单item监听回调
        toolbar.setOnMenuItemClickListener(item -> callbackOnMenuAction(item));

    }

    /**
     * 为toolbar设置menu项
     */
    private void setInflateMenu() {
        if (getMenuLayoutId() > 0)
            toolbar.inflateMenu(getMenuLayoutId());
    }

    /**
     * 获取菜单资源id，默认无，子类可重写
     *
     * @return
     */
    protected int getMenuLayoutId() {
        return 0;
    }

    /**
     * 设置主标题
     *
     * @param object
     */
    public void setMainTitle(Object object) {
        toolbar.setTitle(CommonUtils.getResultString(object));
    }

    /**
     * 设置子类标题
     *
     * @param object
     */
    public void setSubTitle(Object object) {
        toolbar.setSubtitle(CommonUtils.getResultString(object));
    }

    /**
     * 设置主标题字体颜色
     *
     * @param object
     */
    public void setMainTitleColor(Object object) {
        toolbar.setTitleTextColor(CommonUtils.getResultColor(object));
    }

    /**
     * 设置子标题字体颜色
     *
     * @param object
     */
    public void setSubTitleColor(Object object) {
        toolbar.setSubtitleTextColor(CommonUtils.getResultColor(object));
    }

    /**
     * 设置logoIcon
     *
     * @param resId
     */

    public void setLogoIcon(int resId) {
        toolbar.setLogo(resId);
    }

    /**
     * 设置中间标题
     *
     * @param object
     */
    public void setToolbarTitleTv(Object object) {
        tv_title.setText(CommonUtils.getResultString(object));
    }

    /**
     * 设置标题栏背景颜色
     *
     * @param color
     */
    protected void setTitleBgColor(int color) {
        toolbar.setBackgroundColor(CommonUtils.getResultColor(color));
    }


    /**
     * 设置左边标题图标
     *
     * @param iconRes
     */
    public void setTitleNavigationIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }


    /**
     * 隐藏标题栏
     */
    protected void hideToolbar() {
        if (toolbar.getVisibility() == View.VISIBLE)
            toolbar.setVisibility(View.GONE);
    }

    /**
     * 不显示 NavigationButton
     */
    public void hideTitleNavigationButton() {
        toolbar.setNavigationIcon(null);
    }


    /**
     * Navigation Button点击回调，默认回退销毁页面，其他操作子类可重写
     *
     * @param view
     */
    protected void callbackOnClickNavigationAction(View view) {
        onBackPressed();
    }


    /**
     * menu点击回调，默认无，子类可重写
     *
     * @param item
     * @return
     */
    protected boolean callbackOnMenuAction(MenuItem item) {
        return false;
    }




}
