package com.saiyi.smartkeycabinetclient.key.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lib.fast.common.activity.view.NavBar;
import com.lib.fast.common.event.EventAction;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPFragment;
import com.saiyi.smartkeycabinetclient.core.model.Action;
import com.saiyi.smartkeycabinetclient.core.model.KeyCabinetHelper;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPosition;
import com.saiyi.smartkeycabinetclient.key.model.bean.ApplicationKeyPosition;
import com.saiyi.smartkeycabinetclient.key.model.bean.Approver;
import com.saiyi.smartkeycabinetclient.key.presenter.ApplicationKeyPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请钥匙
 */
public class ApplicationKeyFragment extends BKMVPFragment<ApplicationKeyPresenter> {

    @BindView(R.id.title_nb)
    NavBar mNavBar;

    @BindView(R.id.feed_back_et)
    EditText mFeedBackEt;

    @BindView(R.id.key_cabinet_tv)
    TextView mKeyCabinetTv;

    @BindView(R.id.approvel_tv)
    TextView mApprovelTv;

    public ApplicationKeyFragment() {
    }

    public static ApplicationKeyFragment newInstance() {
        ApplicationKeyFragment fragment = new ApplicationKeyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public ApplicationKeyPresenter initPresenter(Context context) {
        return new ApplicationKeyPresenter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_application_key, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mNavBar.setTitle("申请钥匙");
        mNavBar.hiddenLeftIcon();
    }

    @Override
    protected void initData() {
        super.initData();
        KeyCabinetHelper.instance().clearSelectKeyCabinetOfApplicationKey();
        registerEventBus();
    }

    @Override
    public void onMessageEvent(EventAction event) {
        super.onMessageEvent(event);
        if (event instanceof Action) {
            Action action = (Action) event;
            switch (action) {
                case KeyPositionChangedOfApplicationKey:
                    onSelectDeviceChanged();
                    break;
                case ApprovalChangeOfApplicationKey:
                    onSelectApprovelChanged();
                    break;
            }
        }
    }

    //选择的设备变更
    private void onSelectDeviceChanged() {
        ApplicationKeyPosition applicationKeyInfo = KeyCabinetHelper.instance().getApplicationKeySelectKeyPosition();
        if (applicationKeyInfo != null) {
            KeyCabinetDevice device = applicationKeyInfo.getKeyCabinetDevice();
            KeyPosition position = applicationKeyInfo.getKeyPosition();
            if (device != null && position != null) {
                mKeyCabinetTv.setText(String.format("%s %s号钥匙", device.getDeviceName(), position.getPosition()));
            }
        }
    }

    //选择的审批人变更
    private void onSelectApprovelChanged(){
        ApplicationKeyPosition applicationKeyInfo = KeyCabinetHelper.instance().getApplicationKeySelectKeyPosition();
        if (applicationKeyInfo != null) {
            Approver approval = applicationKeyInfo.getApprovel();
            if (approval != null) {
                mApprovelTv.setText(String.format("%s %s", approval.getJob(), approval.getName()));
            }
        }
    }

    @OnClick(R.id.select_key_rl)
    protected void onSelectKeyClick()  {
        openActivity(SelectKeyCabinetActivity.class);
    }

    @OnClick(R.id.start_time_rl)
    protected void onStartTimeClick() {

    }

    @OnClick(R.id.end_time_rl)
    protected void onEndTimeClick() {

    }

    @OnClick(R.id.select_approval_rl)
    protected void onSelectApprovalClick() {
        openActivity(ApproverActivity.class);
    }

    @OnClick(R.id.submit_btn)
    protected void onSubmitClick() {

    }

}
