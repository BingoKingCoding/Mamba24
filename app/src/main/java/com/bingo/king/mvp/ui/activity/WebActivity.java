package com.bingo.king.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Preconditions;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bingo.king.R;
import com.bingo.king.app.utils.CommonUtils;
import com.bingo.king.mvp.ui.widget.X5WebView;
import com.bingo.king.mvp.ui.widget.web.AppJsHandler;
import com.bingo.king.mvp.ui.widget.web.FullscreenHolder;
import com.bingo.king.mvp.ui.widget.web.IWebPageView;
import com.bingo.king.mvp.ui.widget.web.MyWebChromeClient;
import com.bingo.king.mvp.ui.widget.web.MyWebViewClient;
import com.bingo.king.mvp.ui.widget.web.ShareUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import timber.log.Timber;

/**
 */

public class WebActivity extends RxAppCompatActivity implements IWebPageView
{
    private final String TAG = this.getClass().getSimpleName();
    //加载错误时候网页
    public static final String NO_NETWORK_URL = "file:///android_asset/error_network.html";

    // 进度条
    ProgressBar mProgressBar;
    public X5WebView webView;
    // 全屏时视频加载view
    FrameLayout videoFullView;
    Toolbar mToolbar;
    // 进度条是否加载到90%
    public boolean mProgress90;
    // 网页是否加载完成
    public boolean mPageFinish;
    // 加载视频相关
    private MyWebChromeClient mWebChromeClient;
    // title
    private String mTitle;
    // 网页链接
    private String mUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getIntentData();
        initTitle();
        initWebView();
        RemoveAD();
        loadUrl();
    }

    private void getIntentData()
    {
        if (getIntent() != null)
        {
            mTitle = getIntent().getStringExtra("mTitle");
            mUrl = getIntent().getStringExtra("mUrl");
        }
    }

    private void initWebView()
    {
        mProgressBar.setVisibility(View.VISIBLE);

        mWebChromeClient = new MyWebChromeClient(this);
        webView.setWebChromeClient(mWebChromeClient);
        // 与js交互(这里的"App"可根据公司前端协商定义)
        webView.addJavascriptInterface(new AppJsHandler(this), "App");
        webView.setWebViewClient(new MyWebViewClient(this));
    }

    private void initTitle()
    {
        mProgressBar = findViewById(R.id.pb_progress);
        webView = findViewById(R.id.webview_detail);
        videoFullView = findViewById(R.id.video_fullView);
        mToolbar = findViewById(R.id.title_tool_bar);
        mToolbar.setTitle(mTitle);
        //设置相关默认操作
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.inflateMenu(R.menu.webview_menu);
        //左边Navigation Button监听回调
        mToolbar.setNavigationOnClickListener(v ->
        {
            if (NO_NETWORK_URL.equals(webView.getUrl()))
            {
                //如果当前url是网络错误的url，返回就关闭页面
                finish();
                return;
            }
            if (goBack())
            {
                Timber.d(TAG, "go_back");
            } else
            {
                onBackPressed();
            }
        });
        //右边菜单item监听回调
        mToolbar.setOnMenuItemClickListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.actionbar_refresh:
                    loadUrl();
                    break;
                case R.id.actionbar_share:// 分享到
                    String shareText = mWebChromeClient.getTitle() + mUrl + "（分享自" + R.string.app_name + "）";
                    ShareUtils.share(WebActivity.this, shareText);
                    break;
                case R.id.actionbar_cope:// 复制链接
                    CommonUtils.copy(mUrl);
                    showSnackbar("复制成功",false);
                    break;
                case R.id.actionbar_open:// 打开链接
                    CommonUtils.openLink(WebActivity.this, mUrl);
                    break;
            }
            return false;
        });

    }


    public void loadUrl()
    {
        if (!TextUtils.isEmpty(mUrl))
        {
            webView.loadUrl(mUrl);
        }
    }

    public void setTitle(String mTitle)
    {
        mToolbar.setTitle(mTitle);
    }


    public boolean goBack()
    {
        boolean goback = false;
        if (webView != null && webView.canGoBack() && !webView.getUrl().equals(NO_NETWORK_URL))
        {
            webView.goBack();
            goback = true;
        }
        return goback;
    }

    @Override
    public void startProgress()
    {
        startProgress90();
    }

    @Override
    public void hindProgressBar()
    {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWebView()
    {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindWebView()
    {
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void progressChanged(int newProgress)
    {
        if (mProgress90)
        {
            int progress = newProgress * 100;
            if (progress > 900)
            {
                mProgressBar.setProgress(progress);
                if (progress == 1000)
                {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void fullViewAddView(View view)
    {
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        videoFullView = new FullscreenHolder(WebActivity.this);
        videoFullView.addView(view);
        decor.addView(videoFullView);
    }

    @Override
    public void showVideoFullView()
    {
        videoFullView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindVideoFullView()
    {
        videoFullView.setVisibility(View.GONE);
    }

    public FrameLayout getVideoFullView()
    {
        return videoFullView;
    }


    /**
     * 进度条 假装加载到90%
     */
    public void startProgress90()
    {
        for (int i = 0; i < 900; i++)
        {
            final int progress = i + 1;
            mProgressBar.postDelayed(() ->
            {
                mProgressBar.setProgress(progress);
                if (progress == 900)
                {
                    mProgress90 = true;
                    if (mPageFinish)
                    {
                        startProgress90to100();
                    }
                }
            }, (i + 1) * 2);
        }
    }

    /**
     * 进度条 加载到100%
     */
    public void startProgress90to100()
    {
        for (int i = 900; i <= 1000; i++)
        {
            final int progress = i + 1;
            mProgressBar.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    mProgressBar.setProgress(progress);
                    if (progress == 1000)
                    {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }, (i + 1) * 2);
        }
    }


    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView()
    {
        mWebChromeClient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 上传图片之后的回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == MyWebChromeClient.FILECHOOSER_RESULTCODE)
        {
            mWebChromeClient.mUploadMessage(intent, resultCode);
        } else if (requestCode == MyWebChromeClient.FILECHOOSER_RESULTCODE_FOR_ANDROID_5)
        {
            mWebChromeClient.mUploadMessageForAndroid5(intent, resultCode);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            //全屏播放退出全屏
            if (mWebChromeClient.inCustomView())
            {
                hideCustomView();
                return true;

                //返回网页上一页
            } else if (webView.canGoBack())
            {
                webView.goBack();
                return true;

                //退出网页
            } else
            {
                webView.loadUrl("about:blank");
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        webView.onResume();
        // 支付宝网页版在打开文章详情之后,无法点击按钮下一步
        webView.resumeTimers();
        // 设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        videoFullView.removeAllViews();
        if (webView != null)
        {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null)
            {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
    }


    /**
     * 打开网页:
     *
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   title
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle)
    {
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra("mUrl", mUrl);
        intent.putExtra("mTitle", mTitle);
        mContext.startActivity(intent);
    }

    /**
     * 去除腾讯浏览器广告
     */
    private void RemoveAD()
    {
        getWindow().getDecorView().addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) ->
        {
            ArrayList<View> outView = new ArrayList<>();
            getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
            int size = outView.size();
            if (outView != null && outView.size() > 0)
            {
                outView.get(0).setVisibility(View.GONE);
            }
        });
    }


    /**
     * 使用snackbar显示内容
     *
     */
    private void showSnackbar(String message, boolean isLong)
    {
        Preconditions.checkNotNull(message);
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }

}
