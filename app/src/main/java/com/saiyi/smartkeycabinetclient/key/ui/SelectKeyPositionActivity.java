package com.saiyi.smartkeycabinetclient.key.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;
import com.saiyi.smartkeycabinetclient.core.model.KeyCabinetHelper;
import com.saiyi.smartkeycabinetclient.key.model.bean.ApplicationKeyPosition;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPosition;
import com.saiyi.smartkeycabinetclient.key.presenter.SelectKeyPositionPresenter;
import com.saiyi.smartkeycabinetclient.key.ui.adapter.KeyPositionAdapter;
import com.saiyi.smartkeycabinetclient.key.ui.adapter.KeyPositionStatusAdapter;
import com.saiyi.smartkeycabinetclient.key.ui.widget.KeyPositionItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectKeyPositionActivity extends BKMVPActivity<SelectKeyPositionPresenter> implements EasyRefreshLayout.EasyEvent {

    @BindView(R.id.key_status_rv)
    RecyclerView mKeyStatusRv;

    @BindView(R.id.key_positions_rv)
    RecyclerView mKeyPositionsRv;

    @BindView(R.id.ez_refresh_view)
    EasyRefreshLayout mEzRefreshView;

    private KeyPositionAdapter mKeyPositionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_key_position);
    }

    @Override
    public SelectKeyPositionPresenter initPresenter(Context context) {
        return new SelectKeyPositionPresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        ApplicationKeyPosition applicationKeyInfo = KeyCabinetHelper.instance().getApplicationKeySelectKeyPosition();
        if (applicationKeyInfo == null || applicationKeyInfo.getKeyCabinetDevice() == null) {
            toast("参数错误");
            finish();
            return;
        }
        getTitleBar().setTitle(applicationKeyInfo.getKeyCabinetDevice().getDeviceName());

        mEzRefreshView.setLoadMoreModel(LoadModel.NONE);
        mEzRefreshView.addEasyEvent(this);

        mKeyStatusRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mKeyStatusRv.setHasFixedSize(true);
        mKeyStatusRv.setAdapter(new KeyPositionStatusAdapter());

        mKeyPositionAdapter = new KeyPositionAdapter(new ArrayList<KeyPosition>());
        mKeyPositionsRv.setLayoutManager(new GridLayoutManager(this, 5));
        mKeyPositionsRv.setHasFixedSize(true);
        mKeyPositionsRv.addItemDecoration(new KeyPositionItemDecoration());
        mKeyPositionsRv.setAdapter(mKeyPositionAdapter);

        mEzRefreshView.autoRefresh();
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onRefreshing() {
        getPresenter().getKeyPositions();
    }

    public void getKeyPositionsSuccess(List<KeyPosition> datas) {
        if (datas == null) datas = new ArrayList<>();
        mKeyPositionAdapter.replaceData(datas);
        mEzRefreshView.refreshComplete();
    }

    @OnClick(R.id.complate_btn)
    public void onComplateClick() {
        List<KeyPosition> keyPositions = mKeyPositionAdapter.getCheckedItems();
        if (keyPositions == null || keyPositions.isEmpty()) {
            toast("请选择钥匙位");
        } else {
            KeyCabinetHelper.instance().setKeyPositionOfApplicationKey(keyPositions.get(0));
            finish();
        }
    }

    @OnClick(R.id.cancel_btn)
    public void onCancelClick() {
        finish();
    }
}
