package com.saiyi.smartkeycabinetclient.home.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

import com.lib.fast.common.adapter.PagerAdapter;
import com.lib.fast.common.widgets.ScrollViewPager;
import com.lib.fast.common.widgets.ezlistener.OnPageSelectedListener;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKActivity;
import com.saiyi.smartkeycabinetclient.device.ui.ManagerDeviceFragment;
import com.saiyi.smartkeycabinetclient.key.ui.ApplicationKeyFragment;
import com.saiyi.smartkeycabinetclient.user.ui.PersonalCenterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class HomeActivity extends BKActivity {

    @BindView(R.id.viewpager)
    ScrollViewPager mViewpager;

    @BindViews({R.id.tabs_key_ll, R.id.tabs_devices_ll, R.id.tabs_me_ll})
    ViewGroup[] tables;

    private SparseArray<Fragment> mFragments = new SparseArray<>();
    private PagerAdapter mPagerAdapter;
    private List<ViewGroup> mTabViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    protected void initView() {
        super.initView();
        getTitleBar().hidden();

        for (ViewGroup view : tables) {
            mTabViews.add(view);
        }

        mFragments.append(0, ApplicationKeyFragment.newInstance());
        mFragments.append(1, ManagerDeviceFragment.newInstance());
        mFragments.append(2, PersonalCenterFragment.newInstance());
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mFragments);

        mViewpager.setScanScroll(false);
        mViewpager.setOffscreenPageLimit(mFragments.size());
        mViewpager.setAdapter(mPagerAdapter);
        mViewpager.addOnPageChangeListener(mOnPageSelectedListener);

        setSelectedTable(0);
    }

    @OnClick({R.id.tabs_key_ll, R.id.tabs_devices_ll, R.id.tabs_me_ll})
    protected void onTableItemClick(View view) {
        int position = mTabViews.indexOf(view);
        mViewpager.setCurrentItem(position);
        setSelectedTable(position);
    }

    //设置被选中的tab
    private void setSelectedTable(int position) {
        for (int i = 0; i < mTabViews.size(); i++) {
            boolean isChecked = i == position;
            ViewGroup viewGroup = mTabViews.get(i);
            int childCount = viewGroup.getChildCount();
            for (int j = 0; j < childCount; j++) {
                View childView = viewGroup.getChildAt(j);
                if (childView instanceof Checkable) {
                    Checkable checkable = (Checkable) childView;
                    checkable.setChecked(isChecked);
                }
            }
        }
    }

    OnPageSelectedListener mOnPageSelectedListener = new OnPageSelectedListener() {
        @Override
        public void onPageSelected(int position) {
            setSelectedTable(position);
        }
    };

}
