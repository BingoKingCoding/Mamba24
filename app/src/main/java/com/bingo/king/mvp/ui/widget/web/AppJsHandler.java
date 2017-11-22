package com.bingo.king.mvp.ui.widget.web;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.webkit.JavascriptInterface;

import com.bingo.king.app.utils.CommonUtils;
import com.bingo.king.app.utils.DDHandlerManager;
import com.bingo.king.mvp.ui.activity.WebActivity;


/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/7/4 17:55.
 */

public class AppJsHandler extends BaseJsHandler
{
    private static final String DEFAULT_NAME = "App";
    private String url;
    private static final int CODE= 1;
    public AppJsHandler(String name, Activity activity)
    {
        super(name, activity);
    }

    public AppJsHandler(Activity activity)
    {
        this(DEFAULT_NAME, activity);
    }

    /**
     * @Description 检查网络
     */
    @JavascriptInterface
    public void check_network()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("网络设置提示");
        builder.setMessage("网络连接不可用，是否进行设置?");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", (dialog, which) -> CommonUtils.openWirelessSettings(getActivity()));
        builder.show();
    }

    /**
     * @Description 重新加载
     */
    @JavascriptInterface
    public void refresh_reload()
    {
        DDHandlerManager.getMainHandler().post(() -> ((WebActivity)getActivity()).loadUrl());
    }

}
