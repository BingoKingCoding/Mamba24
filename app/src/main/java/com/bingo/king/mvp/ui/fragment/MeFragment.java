package com.bingo.king.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.app.utils.GlideUtils;
import com.bingo.king.app.utils.ViewBinder;
import com.bingo.king.di.component.DaggerMeComponent;
import com.bingo.king.di.module.MeModule;
import com.bingo.king.mvp.contract.MeContract;
import com.bingo.king.mvp.model.entity.UserBean;
import com.bingo.king.mvp.model.entity.dao.UserBeanDao;
import com.bingo.king.mvp.presenter.MePresenter;
import com.bingo.king.mvp.ui.activity.SettingActivity;
import com.bingo.king.mvp.ui.widget.CircleImageView;
import com.bingo.king.mvp.ui.widget.LoadingPage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <个人中心页面>
 * Created by wwb on 2017/11/29 11:23.
 */

public class MeFragment extends BaseFragment<MePresenter> implements MeContract.View {
    @BindView(R.id.civ_head)
    CircleImageView civ_head;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.iv_personinfo_bg)
    ImageView iv_personinfo_bg;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initComponent() {
        DaggerMeComponent.builder()
                .appComponent(getAppComponent())
                .meModule(new MeModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_title_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setState(LoadingPage.STATE_SUCCESS);
        UserBean user = UserBeanDao.query();
        ViewBinder.setTextView(tv_name, user.getNick_name() + user.getUser_id());
        ViewBinder.setTextView(tv_id, "ID:" + user.getUser_id());
        GlideUtils.getInstance().loadImage(user.getHead_image(), civ_head);
//        GlideUtils.getInstance().loadImage(user.getHead_image(), iv_personinfo_bg, new RequestOptions().transform(new BlurTransformation()));
    }
}
