package com.saiyi.smartkeycabinetclient.device.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.lib.fast.common.http.BaseResponseListener;
import com.lib.fast.common.http.exception.ErrorStatus;
import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.device.model.ManagerDeviceModel;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.device.ui.ManagerDeviceFragment;

import java.util.List;

/**
 * created by siwei on 2018/11/6
 */
public class ManagerDevicePresenter extends PresenterImpl<ManagerDeviceFragment, ManagerDeviceModel> {

    public ManagerDevicePresenter(Context context) {
        super(context);
    }

    @Override
    public ManagerDeviceModel initModel(Context context) {
        return new ManagerDeviceModel(context);
    }

    public void getManagerDevices() {
        getModel().getDeviceList(new BaseResponseListener<List<KeyCabinetDevice>>() {
            @Override
            public void onResponse(List<KeyCabinetDevice> data) {
                super.onResponse(data);
                getView().onGetManagerDeviceSuccess(data);
            }

            @Override
            public void onFaild(ErrorStatus e) {
                super.onFaild(e);
                getView().onGetManagerDeviceFaild(e);
            }
        });
    }

    /**
     * 删除设备
     */
    public boolean deleteDevice(KeyCabinetDevice device) {
        if (device != null) {
            getModel().deleteDevice(device.getDeviceBindId(), new BaseResponseListener<Void>() {
                @Override
                public void onResponse(Void data) {
                    super.onResponse(data);
                    getView().showDeleteDeviceSuccess();
                }

                @Override
                public void onFaild(ErrorStatus e) {
                    super.onFaild(e);
                    getView().toastErrorMsg(e);
                }
            });
            return true;
        } else {
            getView().toast("参数错误");
            return false;
        }
    }

    /**
     * 更新设备名称
     */
    public boolean updateDeviceName(KeyCabinetDevice device, String newName) {
        if (device != null && !TextUtils.isEmpty(newName)) {
            getModel().updateDeviceName(device.getDeviceBindId(), newName, new BaseResponseListener<Void>() {
                @Override
                public void onResponse(Void data) {
                    super.onResponse(data);
                    getView().showEditDeviceSuccess();
                }

                @Override
                public void onFaild(ErrorStatus e) {
                    super.onFaild(e);
                    getView().toastErrorMsg(e);
                }
            });
            return true;
        } else {
            getView().toast("参数错误");
            return false;
        }
    }

}
