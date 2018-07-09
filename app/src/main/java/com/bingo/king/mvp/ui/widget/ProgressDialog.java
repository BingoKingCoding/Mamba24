package com.bingo.king.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.config.MDialogConfig;
import com.blankj.utilcode.util.SizeUtils;
import com.zhy.autolayout.AutoRelativeLayout;


/**
 * <请描述这个类是干什么的>
 *
 * @data: 2017/5/10 17:27
 */
public class ProgressDialog extends Dialog {

    private MDialogConfig mDialogConfig;
    //布局
    private AutoRelativeLayout dialog_window_background;
    private AutoRelativeLayout dialog_view_bg;
    private ProgressWheel progress_wheel;
    private TextView tvContent;


    private Context mContext;

    public ProgressDialog(Context context) {
        super(context, R.style.ProgressDialogTheme);
        this.mContext = context;
    }

    public ProgressDialog(Context context, MDialogConfig mDialogConfig) {
        super(context, R.style.ProgressDialogTheme);
        this.mContext = context;
        this.mDialogConfig = mDialogConfig;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        init();
    }

    private void init() {
        //布局相关
        tvContent = findViewById(R.id.tvContent);
        dialog_window_background = findViewById(R.id.dialog_window_background);
        dialog_view_bg = findViewById(R.id.dialog_view_bg);
        progress_wheel = findViewById(R.id.progress_wheel);


        this.setCancelable(false);// 不可以用“返回键”取消
        this.setCanceledOnTouchOutside(false);

        //设置整个Dialog的宽高
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int screenH = dm.heightPixels;

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = screenW;
        layoutParams.height = screenH;
        getWindow().setAttributes(layoutParams);


        //默认相关
        progress_wheel.spin();

        configView();
        //点击事件
        dialog_window_background.setOnClickListener(v -> {
            //取消Dialog
            if (mDialogConfig != null && mDialogConfig.canceledOnTouchOutside) {
                dismiss();
            }
        });

    }


    @SuppressLint("NewApi")
    private void configView() {
        //设置动画
        if (mDialogConfig.animationID != 0 && getWindow() != null) {
            this.getWindow().setWindowAnimations(mDialogConfig.animationID);
        }
        setCanceledOnTouchOutside(mDialogConfig.canceledOnTouchOutside);
        dialog_window_background.setBackgroundColor(mDialogConfig.backgroundWindowColor);

        GradientDrawable myGrad = new GradientDrawable();
        myGrad.setColor(mDialogConfig.backgroundViewColor);
        myGrad.setStroke(SizeUtils.dp2px(mDialogConfig.strokeWidth), mDialogConfig.strokeColor);
        myGrad.setCornerRadius(SizeUtils.dp2px(mDialogConfig.cornerRadius));
        dialog_view_bg.setBackground(myGrad);

        progress_wheel.setBarColor(mDialogConfig.progressColor);
        progress_wheel.setBarWidth(SizeUtils.dp2px(mDialogConfig.progressWidth));
        progress_wheel.setRimColor(mDialogConfig.progressRimColor);
        progress_wheel.setRimWidth(mDialogConfig.progressRimWidth);

        tvContent.setTextColor(mDialogConfig.textColor);
    }



    public void setText(String msg){
        tvContent.setText(msg);
    }


}
