package com.bingo.king.mvp.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bingo.king.R;
import com.blankj.utilcode.util.SizeUtils;

/**
 * 提示Dialog
 */

public class MStatusDialog {

    private Handler mHandler = new Handler();
    private Context mContext;
    private Dialog mDialog;

    private MDialogConfig mDialogConfig;

    private RelativeLayout dialog_window_background;
    private RelativeLayout dialog_view_bg;
    private ImageView imageStatus;
    private TextView tvShow;

    public MStatusDialog(Context context) {
        this(context, new MDialogConfig.Builder().build());
    }

    public MStatusDialog(Context context, MDialogConfig mDialogConfig) {
        mContext = context;
        this.mDialogConfig = mDialogConfig;
        //初始化
        initDialog();
    }

    private void initDialog() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mProgressDialogView = inflater.inflate(R.layout.mn_status_dialog_layout, null);// 得到加载view
        mDialog = new Dialog(mContext, R.style.MNCustomDialog);// 创建自定义样式dialog
        mDialog.setCancelable(false);// 不可以用“返回键”取消
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(mProgressDialogView);// 设置布局

        //设置整个Dialog的宽高
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int screenH = dm.heightPixels;

        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.width = screenW;
        layoutParams.height = screenH;
        mDialog.getWindow().setAttributes(layoutParams);

        //获取布局
        dialog_window_background = mProgressDialogView.findViewById(R.id.dialog_window_background);
        dialog_view_bg = mProgressDialogView.findViewById(R.id.dialog_view_bg);
        imageStatus = mProgressDialogView.findViewById(R.id.imageStatus);
        tvShow = mProgressDialogView.findViewById(R.id.tvShow);

        //默认配置
        configView();

    }

    private void configView() {
        dialog_window_background.setBackgroundColor(mDialogConfig.backgroundWindowColor);
        tvShow.setTextColor(mDialogConfig.textColor);

        GradientDrawable myGrad = new GradientDrawable();
        myGrad.setColor(mDialogConfig.backgroundViewColor);
        myGrad.setStroke(SizeUtils.dp2px(mDialogConfig.strokeWidth), mDialogConfig.strokeColor);
        myGrad.setCornerRadius(SizeUtils.dp2px(mDialogConfig.cornerRadius));
        dialog_view_bg.setBackground(myGrad);

        //设置动画
        if (mDialogConfig.animationID != 0) {
            mDialog.getWindow().setWindowAnimations(mDialogConfig.animationID);
        }
    }


    public void show(String msg) {
        show(msg, mContext.getResources().getDrawable(R.drawable.mn_icon_dialog_ok));
    }

    public void show(String msg, Drawable drawable) {
        show(msg, drawable, 2000);
    }

    public void show(String msg, Drawable drawable, long delayMillis) {
        imageStatus.setImageDrawable(drawable);
        tvShow.setText(msg);
        mDialog.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                mHandler.removeCallbacksAndMessages(null);
                if (mDialogConfig != null && mDialogConfig.onDialogDismissListener != null) {
                    mDialogConfig.onDialogDismissListener.onDismiss();
                }
            }
        }, delayMillis);
    }
}
