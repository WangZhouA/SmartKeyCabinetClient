package com.saiyi.smartkeycabinetclient.user.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.lib.fast.common.activity.view.NavBar;
import com.lib.fast.common.adapter.PagerAdapter;
import com.lib.fast.common.widgets.CheckTextView;
import com.lib.fast.common.widgets.ezlistener.OnPageSelectedListener;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class ApplicationActivity extends BKActivity {

    @BindViews({R.id.pending_ct, R.id.approved_ct})
    CheckTextView[] mSelectorCts;

    @BindView(R.id.vp)
    ViewPager mVp;

    SparseArray<Fragment> mFragments;

    private PagerAdapter mPagerAdapter;
    private List<CheckTextView> mCheckTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().setTitle("我申请的");
        getTitleBar().setRightImageResource(R.drawable.nav_sousuo);
        getTitleBar().showRightIcon();
        getTitleBar().setClickListener(mNavBarOnClickListener);
        mVp.addOnPageChangeListener(mOnPageSelectedListener);
    }

    @Override
    protected void initData() {
        super.initData();
        mCheckTextViews = Arrays.asList(mSelectorCts);
        mFragments = new SparseArray<>();
        mFragments.put(1, PendingFragment.newInstance());
        mFragments.put(2, ApprovedFragment.newInstance());
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mFragments);
        mVp.setAdapter(mPagerAdapter);
        mVp.setCurrentItem(0);
        mCheckTextViews.get(0).setChecked(true);
    }

    NavBar.NavBarOnClickListener mNavBarOnClickListener = new NavBar.NavBarOnClickListener() {
        @Override
        public void onLeftIconClick(View view) {
            finish();
        }

        @Override
        public void onLeftSenIconClick(View view) {
        }

        @Override
        public void onRightIconClick(View view) {

        }

        @Override
        public void onRightTxtClick(View view) {
        }
    };

    OnPageSelectedListener mOnPageSelectedListener = new OnPageSelectedListener() {
        @Override
        public void onPageSelected(int position) {
            setSelectedItem(position);
        }
    };

    private void setSelectedItem(int position) {
        for (int i = 0; i < mCheckTextViews.size(); i++) {
            mCheckTextViews.get(i).setChecked(i == position);
        }
    }

    @OnClick({R.id.pending_ct, R.id.approved_ct})
    protected void onItemClick(CheckTextView view) {
        int position = mCheckTextViews.indexOf(view);
        if (position >= 0) {
            mVp.setCurrentItem(position);
        }
    }
}
