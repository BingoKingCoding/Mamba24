package com.bingo.king.mvp.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.app.utils.ViewBinder;
import com.bingo.king.di.component.DaggerWelfareComponent;
import com.bingo.king.di.module.WelfareModule;
import com.bingo.king.mvp.contract.WelfareContract;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.presenter.WelfarePresenter;
import com.bingo.king.mvp.ui.adapter.WelfareAdapter;
import com.bingo.king.mvp.ui.widget.sparkbutton.SparkButton;

import java.util.List;

import butterknife.BindView;
import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;


/**
 * <福利>
 * Created by wwb on 2017/9/20 16:13.
 */

public class WelfareFragment extends BaseFragment<WelfarePresenter> implements WelfareContract.View
{
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.spark_button)
    SparkButton mSparkButton;

    private WelfareAdapter mWelfareAdapter;

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.fragment_welfare;
    }


    public void initData(Bundle savedInstanceState)
    {
        ViewBinder.setTextView(toolbar_title, "福利");
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);
        mWelfareAdapter = new WelfareAdapter(null);
        mRecyclerView.setAdapter(mWelfareAdapter);
        mPresenter.requestData(true);
    }

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    private void animatePhotoLike()
    {
        mSparkButton.setScaleY(0.1f);
        mSparkButton.setScaleX(0.1f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(mSparkButton, "scaleY", 0.1f, 1f);
        imgScaleUpYAnim.setDuration(300);
        imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(mSparkButton, "scaleX", 0.1f, 1f);
        imgScaleUpXAnim.setDuration(300);
        imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(mSparkButton, "scaleY", 1f, 0f);
        imgScaleDownYAnim.setDuration(300);
        imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(mSparkButton, "scaleX", 1f, 0f);
        imgScaleDownXAnim.setDuration(300);
        imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        animatorSet.playTogether(imgScaleUpYAnim, imgScaleUpXAnim);
        animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);

        animatorSet.start();
    }

    private void collectWelfare(Object o)
    {
//        animatePhotoLike();
        GankEntity.ResultsBean entity = (GankEntity.ResultsBean) o;
        mPresenter.addToFavorites(entity);
    }

    @Override
    public void setNewData(List<GankEntity.ResultsBean> mData)
    {
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(mRecyclerView.getAdapter(), mData);
        cardCallback.setOnSwipedListener(new OnSwipeListener()
        {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction)
            {

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction)
            {
                mPresenter.requestData(false);
                if (direction == CardConfig.SWIPED_RIGHT)
                {
                    collectWelfare(o);
                }
            }

            @Override
            public void onSwipedClear()
            {

            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(mRecyclerView, touchHelper);
        mRecyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(mRecyclerView);

        mWelfareAdapter.setNewData(mData);
        if (mWelfareAdapter.getData().size() < 3)
        {
            mPresenter.requestData(false);
        }

    }

    @Override
    public void setAddData(List<GankEntity.ResultsBean> results)
    {
        mWelfareAdapter.addData(results);
    }


    @Override
    public void initComponent()
    {
        DaggerWelfareComponent.builder()
                .appComponent(getAppComponent())
                .welfareModule(new WelfareModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void retryRequestData()
    {
        mPresenter.requestData(true);
    }

}
