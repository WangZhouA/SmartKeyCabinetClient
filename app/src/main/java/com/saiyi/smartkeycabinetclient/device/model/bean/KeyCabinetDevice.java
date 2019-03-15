package com.saiyi.smartkeycabinetclient.device.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * created by siwei on 2018/12/3
 */
public class KeyCabinetDevice implements Serializable{

    @SerializedName("deviceinfoId")
    private long deviceBindId;
    private long did;
    private String mac;
    @SerializedName("name")
    private String deviceName;

    public long getDeviceBindId() {
        return deviceBindId;
    }

    public void setDeviceBindId(long deviceBindId) {
        this.deviceBindId = deviceBindId;
    }

    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "KeyCabinetDevice{" +
                "deviceBindId=" + deviceBindId +
                ", did=" + did +
                ", mac='" + mac + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
