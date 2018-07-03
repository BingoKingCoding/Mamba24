package com.bingo.king.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bingo.king.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingGoodsActivity extends AppCompatActivity {


    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rl_header)
    RelativeLayout mRlHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_goods);
        ButterKnife.bind(this);
        initToolBar();

    }

    private void initToolBar() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, int state) {
                if (state == State.EXPANDED) {
                    // 展开状态
                    mTvTitle.setText("");
                    mRlHeader.setVisibility(View.VISIBLE);
                } else if (state == State.COLLAPSED) {
                    // 折叠状态
                    mTvTitle.setText("芭比馒头");
                    mRlHeader.setVisibility(View.GONE);
                } else {
                    mTvTitle.setText("");
                    mRlHeader.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onStateChanged(int i) {
                float height = mRlHeader.getHeight();
                float alpha = i / height;
                Logger.d("透明度" + (1 - Math.abs(alpha)));
                mRlHeader.setAlpha(1 - Math.abs(alpha));
            }
        });
    }
}
