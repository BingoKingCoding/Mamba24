package com.bingo.king.mvp.ui.widget.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragmentDialog;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/7/4:23:07.
 * Email:634051075@qq.com
 */

public class ADFragmentDialog extends BaseFragmentDialog {


    ImageView iv_close;

    /**
     * 布局初始化
     *
     * @param inflater
     * @return
     */

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_test, null);

        iv_close = view.findViewById(R.id.iv_close);

        iv_close.setOnClickListener(v -> {
            //关闭
            dismiss();
        });

        return view;
    }

    @Override
    protected int initAnimations() {
        return R.style.animate_dialog;
    }

    /**
     * Dialog初始化相关
     */
    @Override
    public void initDialog() {
        //点击外部不可取消,默认false
        getDialog().setCanceledOnTouchOutside(true);
    }

    /**
     * 背景透明度
     *
     * @return
     */
    @Override
    public float initBackgroundAlpha() {
        //默认0.8f
        return 0.8f;
    }
}
