package com.saiyi.smartkeycabinetclient.device.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lib.fast.common.activity.view.NavBar;
import com.lib.fast.common.dialog.BaseDialog;
import com.lib.fast.common.dialog.InputPwdDialog;
import com.lib.fast.common.dialog.RemindMsgDialog;
import com.lib.fast.common.event.EventAction;
import com.lib.fast.common.http.exception.ErrorStatus;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPFragment;
import com.saiyi.smartkeycabinetclient.core.http.error.ResponseError;
import com.saiyi.smartkeycabinetclient.core.model.Action;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.device.presenter.ManagerDevicePresenter;
import com.saiyi.smartkeycabinetclient.device.ui.adapter.DeviceAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 */
public class ManagerDeviceFragment extends BKMVPFragment<ManagerDevicePresenter>
        implements EasyRefreshLayout.EasyEvent, DeviceAdapter.OnItemClickListener {

    @BindView(R.id.title_nb)
    NavBar mNavBar;

    @BindView(R.id.device_rv)
    RecyclerView mDeviceRv;

    @BindView(R.id.ez_refresh_view)
    EasyRefreshLayout mEzRefreshView;

    DeviceAdapter mDeviceAdapter;

    InputPwdDialog mInputPwdDialog;
    RemindMsgDialog mRemindMsgDialog;
    KeyCabinetDevice mOperationDevice;

    public ManagerDeviceFragment() {
    }

    public static ManagerDeviceFragment newInstance() {
        ManagerDeviceFragment fragment = new ManagerDeviceFragment();
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
    public ManagerDevicePresenter initPresenter(Context context) {
        return new ManagerDevicePresenter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager_device, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mNavBar.setTitle("我的设备");
        mNavBar.hiddenLeftIcon();
        mNavBar.showRightIcon();
        mNavBar.setRightImageResource(R.drawable.nav_add);
        mNavBar.setClickListener(mNavBarOnClickListener);

        mEzRefreshView.setLoadMoreModel(LoadModel.NONE);
        mEzRefreshView.addEasyEvent(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mDeviceAdapter = new DeviceAdapter(new ArrayList<KeyCabinetDevice>());
        mDeviceRv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mDeviceRv.setHasFixedSize(true);
        View emptyView = View.inflate(getContext(), R.layout.layout_none_devices, null);
        mDeviceAdapter.setEmptyView(emptyView);
        mDeviceAdapter.setOnItemClickListener(this);
        mDeviceRv.setAdapter(mDeviceAdapter);

        mEzRefreshView.autoRefresh();

        registerEventBus();
    }

    @Override
    public void onMessageEvent(EventAction event) {
        super.onMessageEvent(event);
        if (event instanceof Action) {
            Action action = (Action) event;
            if (action == Action.AddNewDevice) {
                mEzRefreshView.autoRefresh();
            }
        }
    }

    NavBar.NavBarOnClickListener mNavBarOnClickListener = new NavBar.NavBarOnClickListener() {
        @Override
        public void onLeftIconClick(View view) {
        }

        @Override
        public void onLeftSenIconClick(View view) {
        }

        @Override
        public void onRightIconClick(View view) {
            openActivity(AddDeviceActivity.class);
        }

        @Override
        public void onRightTxtClick(View view) {
        }
    };


    @Override
    public void onLoadMore() {
    }

    @Override
    public void onRefreshing() {
        getPresenter().getManagerDevices();
    }

    public void onGetManagerDeviceSuccess(List<KeyCabinetDevice> devices) {
        if (devices == null) devices = new ArrayList<>();
        mDeviceAdapter.replaceData(devices);
        mEzRefreshView.refreshComplete();
    }

    public void onGetManagerDeviceFaild(ErrorStatus e) {
        mEzRefreshView.refreshComplete();
        if (e.code != ResponseError.Null.getCode()) {
            toastErrorMsg(e);
        }
    }

    @Override
    public void onItemClick(int position, BaseQuickAdapter adapter) {

    }

    @Override
    public void onUpdateClick(int position, BaseQuickAdapter adapter) {
        showEditDeviceDialog((KeyCabinetDevice) adapter.getData().get(position));
    }

    @Override
    public void onDeleteClick(int position, BaseQuickAdapter adapter) {
        showDeleteDeviceDialog((KeyCabinetDevice) adapter.getData().get(position));
    }

    @Override
    protected void dismissProgressDialog() {
        super.dismissProgressDialog();
        if (mInputPwdDialog != null && mInputPwdDialog.isShowing()) {
            mInputPwdDialog.dismiss();
            mInputPwdDialog = null;
        }
        if (mRemindMsgDialog != null && mRemindMsgDialog.isShowing()) {
            mRemindMsgDialog.dismiss();
            mRemindMsgDialog = null;
        }
    }

    //显示编辑设备弹窗
    private void showEditDeviceDialog(KeyCabinetDevice keyCabinetDevice) {
        dismissProgressDialog();
        if (keyCabinetDevice != null) {
            mOperationDevice = keyCabinetDevice;
            mInputPwdDialog = new InputPwdDialog(this.getContext());
            mInputPwdDialog.hidenTitle()
                    .setMsgText("请输入设备名称")
                    .setInputText(keyCabinetDevice.getDeviceName())
                    .setInputHintText("设备名称")
                    .setInputType(InputType.TYPE_CLASS_TEXT)
                    .setClick(new BaseDialog.OnDialogClickListener() {
                        @Override
                        public void onDialogClick(int whichOne) {
                            switch (whichOne) {
                                case InputPwdDialog.WHICH_COMPLATE:
                                    editDeviceName(mInputPwdDialog.getInputText().toString().trim());
                                    break;
                            }
                        }
                    });
            mInputPwdDialog.show();
        }
    }

    //编辑设备名称
    private void editDeviceName(String newName){
        if (mOperationDevice != null) {
            getPresenter().updateDeviceName(mOperationDevice, newName);
            mOperationDevice = null;
        }
    }

    /**显示设备删除成功*/
    public void showEditDeviceSuccess(){
        mEzRefreshView.autoRefresh();
    }

    //显示删除设备弹窗
    private void showDeleteDeviceDialog(KeyCabinetDevice keyCabinetDevice) {
        dismissProgressDialog();
        if (keyCabinetDevice != null) {
            mOperationDevice = keyCabinetDevice;
            mRemindMsgDialog = new RemindMsgDialog(getContext());
            mRemindMsgDialog.hidenTitle()
                    .setMsgText(String.format("确定删除%s?", keyCabinetDevice.getDeviceName()))
                    .showMsg()
                    .setClick(new BaseDialog.OnDialogClickListener() {
                        @Override
                        public void onDialogClick(int whichOne) {
                            switch (whichOne) {
                                case RemindMsgDialog.WHICH_COMPLATE:
                                    deleteDevice();
                                    break;
                            }
                        }
                    });
            mRemindMsgDialog.show();
        }
    }

    //删除设备
    private void deleteDevice(){
        if (mOperationDevice != null) {
            getPresenter().deleteDevice(mOperationDevice);
            mOperationDevice = null;
        }
    }

    /**显示设备删除成功*/
    public void showDeleteDeviceSuccess(){
        mEzRefreshView.autoRefresh();
    }

}
