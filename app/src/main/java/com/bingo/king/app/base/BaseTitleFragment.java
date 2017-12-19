package com.bingo.king.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.utils.CommonUtils;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/19 17:16.
 */

public abstract class BaseTitleFragment<P extends IPresenter> extends BaseFragment<P>
{
    protected Toolbar toolbar;
    protected TextView tv_title;
    protected TextView toolbar_right_action;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initControlViews();
        initTitle();
    }


    protected void initTitle(){

    }

    @Override
    protected int onCreateTitleViewResId()
    {
        return R.layout.layout_toolbar;
    }

    /**
     * 控件初始化操作
     */
    private void initControlViews()
    {
        toolbar = getActivity().findViewById(R.id.toolbar);
        tv_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_right_action = getActivity().findViewById(R.id.toolbar_right_action);
        //设置相关默认操作
        setTitleNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setTitleBgColor(R.color.base_title_color);

        setInflateMenu();

        //左边Navigation Button监听回调
        toolbar.setNavigationOnClickListener(this::callbackOnClickNavigationAction);
        //右边菜单item监听回调
        toolbar.setOnMenuItemClickListener(this::callbackOnMenuAction);

        toolbar_right_action.setOnClickListener(v -> callbackOnClickRightAction(toolbar_right_action));
    }


    /**
     * 为toolbar设置menu项
     */
    private void setInflateMenu()
    {
        if (getMenuLayoutId() > 0)
            toolbar.inflateMenu(getMenuLayoutId());
    }

    /**
     * 获取菜单资源id，默认无，子类可重写
     */
    protected int getMenuLayoutId()
    {
        return 0;
    }

    /**
     * 设置主标题
     */
    public void setToolBarTitle(Object object)
    {
        toolbar.setTitle(CommonUtils.getResultString(object));
    }

    /**
     * 设置子类标题
     */
    public void setSubTitle(Object object)
    {
        toolbar.setSubtitle(CommonUtils.getResultString(object));
    }

    /**
     * 设置主标题字体颜色
     */
    public void setToorBarTitleColor(Object object)
    {
        toolbar.setTitleTextColor(CommonUtils.getResultColor(object));
    }

    /**
     * 设置子标题字体颜色
     */
    public void setSubTitleColor(Object object)
    {
        toolbar.setSubtitleTextColor(CommonUtils.getResultColor(object));
    }

    /**
     * 设置logoIcon
     */

    public void setLogoIcon(int resId)
    {
        toolbar.setLogo(resId);
    }

    /**
     * 设置中间标题
     */
    public void setToolbarMiddleTitle(Object object)
    {
        tv_title.setText(CommonUtils.getResultString(object));
    }

    /**
     * 设置右边单个按钮
     */
    public void setToolbarRightAction(Object object)
    {
        toolbar_right_action.setText(CommonUtils.getResultString(object));
    }

    /**
     * 设置标题栏背景颜色
     */
    protected void setTitleBgColor(int color)
    {
        toolbar.setBackgroundColor(CommonUtils.getResultColor(color));
    }


    /**
     * 设置左边标题图标
     */
    public void setTitleNavigationIcon(int iconRes)
    {
        toolbar.setNavigationIcon(iconRes);
    }


    /**
     * 隐藏标题栏
     */
    protected void hideToolbar()
    {
        if (toolbar.getVisibility() == View.VISIBLE)
            toolbar.setVisibility(View.GONE);
    }

    /**
     * 不显示 NavigationButton
     */
    public void hideTitleNavigationButton()
    {
        toolbar.setNavigationIcon(null);
    }


    /**
     * Navigation Button点击回调，默认回退销毁页面，其他操作子类可重写
     */
    protected void callbackOnClickNavigationAction(View view)
    {
        getActivity().onBackPressed();
    }

    /**
     * 右边操作点击回调，其他操作子类可重写
     */
    protected void callbackOnClickRightAction(View view)
    {

    }


    /**
     * menu点击回调，默认无，子类可重写
     */
    protected boolean callbackOnMenuAction(MenuItem item)
    {
        return false;
    }


}
