package com.saiyi.smartkeycabinetclient.key.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.lib.fast.common.activity.view.NavBar;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;
import com.saiyi.smartkeycabinetclient.core.model.KeyCabinetHelper;
import com.saiyi.smartkeycabinetclient.key.model.bean.Approver;
import com.saiyi.smartkeycabinetclient.key.presenter.ApproverPresenter;
import com.saiyi.smartkeycabinetclient.key.ui.adapter.ApproverAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ApproverActivity extends BKMVPActivity<ApproverPresenter>
        implements EasyRefreshLayout.EasyEvent, NavBar.NavBarOnClickListener{

    @BindView(R.id.approvel_rv)
    RecyclerView mApprovelRv;

    @BindView(R.id.ez_refresh_view)
    EasyRefreshLayout mEzRefreshView;

    private ApproverAdapter mApprovelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approver);
    }

    @Override
    public ApproverPresenter initPresenter(Context context) {
        return new ApproverPresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();

        getTitleBar().setTitle("指定审批人");
        getTitleBar().showRightText();
        getTitleBar().setRightText("确定");
        getTitleBar().setClickListener(this);

        mEzRefreshView.setLoadMoreModel(LoadModel.NONE);
        mEzRefreshView.addEasyEvent(this);

        mApprovelRv.setLayoutManager(new LinearLayoutManager(this));
        mApprovelRv.setHasFixedSize(true);
        mApprovelAdapter = new ApproverAdapter(new ArrayList<Approver>());
        mApprovelRv.setAdapter(mApprovelAdapter);

        mEzRefreshView.autoRefresh();
    }

    public void onGetApprovals(List<Approver> approvals){
        if(approvals == null)approvals = new ArrayList<>();
        mApprovelAdapter.replaceData(approvals);
        mEzRefreshView.refreshComplete();
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onRefreshing() {
        getPresenter().getAllApprover();
    }

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
        List<Approver> approvals = mApprovelAdapter.getSelectedApprovals();
        if(approvals == null || approvals.isEmpty()){
            toast("请选择审批人");
        }else{
            KeyCabinetHelper.instance().setApprovalOfApplicationKey(approvals.get(0));
            finish();
        }
    }
}
