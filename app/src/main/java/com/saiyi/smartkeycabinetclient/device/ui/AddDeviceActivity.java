package com.saiyi.smartkeycabinetclient.device.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.lib.fast.common.helper.VibratorHelper;
import com.lib.fast.common.http.exception.ErrorStatus;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;
import com.saiyi.smartkeycabinetclient.device.presenter.AddDevicePresenter;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;

public class AddDeviceActivity extends BKMVPActivity<AddDevicePresenter> implements QRCodeView.Delegate {

    @BindView(R.id.zxingview)
    QRCodeView mZxingView;

    @BindView(R.id.code_et)
    EditText mCodeEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
    }

    @Override
    public AddDevicePresenter initPresenter(Context context) {
        return new AddDevicePresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().setTitle("扫码添加设备");
        mZxingView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZxingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mZxingView.stopCamera();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        if (!getPresenter().bindDevice(result)) {
            showCustomLoading("正在添加");
            mZxingView.startSpot();
        }
    }

    private void vibrate() {
        VibratorHelper.createVibrator(this, VibratorHelper.TYPE_VVW).vibrator();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        toast("扫码失败,请检查是否授予相机相关权限");
        mZxingView.startSpot();
    }

    @OnClick(R.id.add_device_btn)
    protected void onAddDeviceClick(){
        String deviceCode = mCodeEt.getText().toString().trim();
        if (!getPresenter().bindDevice(deviceCode)) {
            showCustomLoading("正在添加");
            mZxingView.stopCamera();
        }
    }

    /**
     * 显示添加设备成功
     */
    public void showAddDeviceSuccess() {
        dismissProgressDialog();
        toast("设备添加成功");
        finish();
    }

    /**
     * 显示添加设备失败
     */
    public void showAddDeviceFaild(ErrorStatus e) {
        toastErrorMsg(e);
        mZxingView.startSpot();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mZxingView.onDestroy();
    }
}
