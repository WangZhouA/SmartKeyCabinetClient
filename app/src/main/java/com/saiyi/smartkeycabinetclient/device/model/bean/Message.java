package com.saiyi.smartkeycabinetclient.device.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * created by siwei on 2018/12/3
 */
public class Message {

    private String code;
    private String codeTime;
    @SerializedName("name")
    private String deviceName;
    @SerializedName("position")
    private String keyPosition;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeTime() {
        return codeTime;
    }

    public void setCodeTime(String codeTime) {
        this.codeTime = codeTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getKeyPosition() {
        return keyPosition;
    }

    public void setKeyPosition(String keyPosition) {
        this.keyPosition = keyPosition;
    }

    @Override
    public String toString() {
        return "Message{" +
                "code='" + code + '\'' +
                ", codeTime='" + codeTime + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", keyPosition='" + keyPosition + '\'' +
                '}';
    }
}
