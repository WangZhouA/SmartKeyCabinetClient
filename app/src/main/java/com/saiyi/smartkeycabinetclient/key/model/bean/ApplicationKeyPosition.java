package com.saiyi.smartkeycabinetclient.key.model.bean;

import com.saiyi.smartkeycabinetclient.core.model.Action;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPosition;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

/**
 * created by siwei on 2018/11/9
 */
public class ApplicationKeyPosition implements Serializable {

    private KeyCabinetDevice mKeyCabinetDevice;//选中的设备
    private KeyPosition mKeyPosition;//选中的钥匙位
    private Approver mApprovel;//审批人的信息
    private String reason;//原因
    private long startTime;//开始时间
    private long endTime;//结束时间


    public KeyCabinetDevice getKeyCabinetDevice() {
        return mKeyCabinetDevice;
    }

    public void setKeyCabinetDevice(KeyCabinetDevice keyCabinetDevice) {
        mKeyCabinetDevice = keyCabinetDevice;
    }

    public KeyPosition getKeyPosition() {
        return mKeyPosition;
    }

    public void setKeyPosition(KeyPosition keyPosition) {
        this.mKeyPosition = keyPosition;
        EventBus.getDefault().post(Action.KeyPositionChangedOfApplicationKey);
    }

    public Approver getApprovel() {
        return mApprovel;
    }

    public void setApprovel(Approver approvel) {
        mApprovel = approvel;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ApplicationKeyPosition{" +
                "mKeyCabinetDevice=" + mKeyCabinetDevice +
                ", mKeyPosition=" + mKeyPosition +
                ", mApprovel=" + mApprovel +
                ", reason='" + reason + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
