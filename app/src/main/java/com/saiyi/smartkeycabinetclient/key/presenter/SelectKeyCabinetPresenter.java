package com.saiyi.smartkeycabinetclient.key.presenter;

import android.content.Context;

import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.key.model.SelectKeyCabinetModel;
import com.saiyi.smartkeycabinetclient.key.ui.SelectKeyCabinetActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * created by siwei on 2018/11/9
 */
public class SelectKeyCabinetPresenter extends PresenterImpl<SelectKeyCabinetActivity, SelectKeyCabinetModel> {

    public SelectKeyCabinetPresenter(Context context) {
        super(context);
    }

    @Override
    public SelectKeyCabinetModel initModel(Context context) {
        return new SelectKeyCabinetModel(context);
    }

    public void getKeyCabinetDevices(){
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<KeyCabinetDevice> devices = new ArrayList<>();
                for(int i = 0; i < 10; i ++){
                    KeyCabinetDevice device = new KeyCabinetDevice();
                    device.setDeviceName((i+1)+"号钥匙柜");
                    device.setMac("ASOU-PKGF-KHT"+i);
                    devices.add(device);
                }
                getView().onGetKeyCabinetDevicesSuccess(devices);
            }
        }, 1000);
    }
}
