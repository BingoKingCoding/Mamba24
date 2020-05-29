package com.bingo.king.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseActivity;
import com.bingo.king.mvp.model.entity.HomeEntity;
import com.bingo.king.mvp.ui.adapter.HomeAdapter;
import com.bingo.king.mvp.ui.widget.FRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {


    @BindView(R.id.rv)
    FRecyclerView mRecyclerView;

    @Override
    public int onCreateContentView(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    public void setupActivityComponent() {
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mRecyclerView.addDividerHorizontal(ContextCompat.getDrawable(this, R.drawable.shape_divider_1));
        HomeAdapter adapter = new HomeAdapter(getHomeEntities());
        adapter.bindToRecyclerView(mRecyclerView);
    }


    private List<HomeEntity> getHomeEntities() {
        List<HomeEntity> list = new ArrayList<>();
        list.add(new HomeEntity("Main", MainActivity.class));
        list.add(new HomeEntity("Main", MainActivity.class));
        return list;
    }


}
