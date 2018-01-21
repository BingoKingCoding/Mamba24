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

import butterknife.OnClick;

/**
 * <个人中心页面>
 * Created by wwb on 2017/11/29 11:23.
 */

public class MeFragment extends BaseFragment<MePresenter> implements MeContract.View
{
    private CircleImageView civ_head;
    private TextView tv_name;
    private TextView tv_id;
    private ImageView iv_personinfo_bg;

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.fragment_me;
    }

    @Override
    protected void initComponent()
    {
        DaggerMeComponent.builder()
                .appComponent(getAppComponent())
                .meModule(new MeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {
        setState(LoadingPage.STATE_SUCCESS);
        initView();
        initData();
    }

    private void initView()
    {
        civ_head = getActivity().findViewById(R.id.civ_head);
        tv_name = getActivity().findViewById(R.id.tv_name);
        tv_id = getActivity().findViewById(R.id.tv_id);
        iv_personinfo_bg = getActivity().findViewById(R.id.iv_personinfo_bg);

    }

    private void initData()
    {
        UserBean user = UserBeanDao.query();
        ViewBinder.setTextView(tv_name, user.getNick_name() + user.getUser_id());
        ViewBinder.setTextView(tv_id, "ID:" + user.getUser_id());
        GlideUtils.getInstance().load(user.getHead_image()).into(civ_head);
//        GlideUtils.getInstance().load(user.getHead_image()).asBitmap().transform(new BlurTransformation(getActivity())).into(iv_personinfo_bg);
    }


    @Override
    protected void onFragmentFirstVisible()
    {
        super.onFragmentFirstVisible();
    }


    @OnClick({R.id.iv_title_setting})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_title_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
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

    @Override
    protected void retryRequestData()
    {

    }

}
