package com.bingo.king.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.bingo.king.R;
import com.bingo.king.app.base.BasePresenterActivity;
import com.bingo.king.di.component.DaggerMainComponent;
import com.bingo.king.di.module.MainModule;
import com.bingo.king.mvp.contract.MainContract;
import com.bingo.king.mvp.presenter.MainPresenter;
import com.bingo.king.mvp.ui.fragment.CollectFragment;
import com.bingo.king.mvp.ui.fragment.HomeFragment;
import com.bingo.king.mvp.ui.fragment.MeFragment;
import com.bingo.king.mvp.ui.fragment.WelfareFragment;
import com.bingo.king.mvp.ui.widget.BottomNavigationView;
import com.bingo.king.mvp.ui.widget.dialog.ADFragmentDialog;

import butterknife.BindView;

public class MainActivity extends BasePresenterActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.bottomBar)
    BottomNavigationView mBottomBar;

    private ADFragmentDialog mADFragmentDialog;

    @Override
    public int onCreateContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void setupActivityComponent() {
        DaggerMainComponent.builder()
                .appComponent(getAppComponent())
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        mIsExitApp = true;
        initTab();
        showFragmentDialog();
    }


    private void showFragmentDialog() {
        if (mADFragmentDialog != null && mADFragmentDialog.isShowing()) {
            return;
        }
        mADFragmentDialog = new ADFragmentDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mADFragmentDialog.showDialog(MainActivity.this);
            }
        },1000);

    }

    private void initTab() {
        mBottomBar.setCallback(new BottomNavigationView.Callback() {
            @Override
            public void onTabSelected(int index) {
                switch (index) {
                    case BottomNavigationView.INDEX_HOME:
                        getSDFragmentManager().toggle(R.id.main_frame, null, HomeFragment.class);
                        break;
                    case BottomNavigationView.INDEX_DASHBOARD:
                        getSDFragmentManager().toggle(R.id.main_frame, null, WelfareFragment.class);
                        break;
                    case BottomNavigationView.INDEX_FAVOURITE:
                        getSDFragmentManager().toggle(R.id.main_frame, null, CollectFragment.class);
                        break;
                    case BottomNavigationView.INDEX_ME:
                        getSDFragmentManager().toggle(R.id.main_frame, null, MeFragment.class);
                        break;
                }
            }

            @Override
            public void onTabReselected(int index) {

            }
        });
        mBottomBar.selectTab(BottomNavigationView.INDEX_HOME);
    }

    @Override
    public void showMessage(String message) {
        showSnackbar(message);
    }


    @Override
    public void hidePullLoading() {

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void setState(int state) {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void closeLoadingDialog() {

    }
}
