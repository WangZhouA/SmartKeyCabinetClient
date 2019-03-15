package com.saiyi.smartkeycabinetclient.key.presenter;

import android.content.Context;

import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.key.model.ApproverModel;
import com.saiyi.smartkeycabinetclient.key.model.bean.Approver;
import com.saiyi.smartkeycabinetclient.key.ui.ApproverActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * created by siwei on 2018/11/12
 */
public class ApproverPresenter extends PresenterImpl<ApproverActivity, ApproverModel> {

    public ApproverPresenter(Context context) {
        super(context);
    }

    @Override
    public ApproverModel initModel(Context context) {
        return new ApproverModel(context);
    }

    /**
     * 获取所有的审批者
     */
    public void getAllApprover() {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Approver> approvals = new ArrayList<>();
                Approver approval1 = new Approver();
                approval1.setJob("局长");
                approval1.setName("张涛");
                approvals.add(approval1);
                Approver approval2 = new Approver();
                approval2.setJob("副局长");
                approval2.setName("李长安");
                approvals.add(approval2);

                getView().onGetApprovals(approvals);
            }
        }, 1500);
    }
}
