package com.saiyi.smartkeycabinetclient.key.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.lib.fast.common.event.EventAction;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;
import com.saiyi.smartkeycabinetclient.core.model.Action;
import com.saiyi.smartkeycabinetclient.core.model.KeyCabinetHelper;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.key.presenter.SelectKeyCabinetPresenter;
import com.saiyi.smartkeycabinetclient.key.ui.adapter.SelectKeyCabinetAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectKeyCabinetActivity extends BKMVPActivity<SelectKeyCabinetPresenter> implements EasyRefreshLayout.EasyEvent {

    @BindView(R.id.ez_refresh_view)
    EasyRefreshLayout mEzRefreshView;

    @BindView(R.id.key_cabinet_rv)
    RecyclerView mKeyCabinetRv;

    SelectKeyCabinetAdapter mSelectKeyCabinetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_key_cabinet);
    }

    @Override
    public SelectKeyCabinetPresenter initPresenter(Context context) {
        return new SelectKeyCabinetPresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().setTitle("选择钥匙柜");

        mEzRefreshView.setLoadMoreModel(LoadModel.NONE);
        mEzRefreshView.addEasyEvent(this);

        mSelectKeyCabinetAdapter = new SelectKeyCabinetAdapter(new ArrayList<KeyCabinetDevice>());
        mKeyCabinetRv.setLayoutManager(new LinearLayoutManager(this));
        mKeyCabinetRv.setHasFixedSize(true);
        mKeyCabinetRv.setAdapter(mSelectKeyCabinetAdapter);
        mEzRefreshView.autoRefresh();
    }

    @Override
    protected void initData() {
        super.initData();
        registerEventBus();
    }

    @Override
    public void onMessageEvent(EventAction event) {
        super.onMessageEvent(event);
        if (event instanceof Action) {
            Action action = (Action) event;
            switch (action) {
                case KeyPositionChangedOfApplicationKey:
                    finish();
                    break;
            }
        }
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onRefreshing() {
        getPresenter().getKeyCabinetDevices();
    }

    public void onGetKeyCabinetDevicesSuccess(List<KeyCabinetDevice> devices) {
        if(devices == null) devices = new ArrayList<>();
        mSelectKeyCabinetAdapter.replaceData(devices);
        mEzRefreshView.refreshComplete();
    }

    @OnClick(R.id.submit_btn)
    protected void onNextClick() {
        KeyCabinetDevice device = mSelectKeyCabinetAdapter.getCheckedItem();
        if (device == null) {
            toast("请选择钥匙柜");
        } else {
            KeyCabinetHelper.instance().setSelectKeyCabinetOfApplicationKey(device);
            openActivity(SelectKeyPositionActivity.class);
        }
    }

}
