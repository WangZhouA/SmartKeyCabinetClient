package com.saiyi.smartkeycabinetclient.device.model.bean;

import com.google.gson.annotations.SerializedName;
import com.saiyi.smartkeycabinetclient.key.model.bean.ApplicationDetails;

import java.util.List;

/**
 * created by siwei on 2018/12/3
 */
public class KeyPositionDetails {

    private String startTime;
    private String endTime;
    @SerializedName("keyimgs")
    private List<String> carImgs;
    @SerializedName("img")
    private String carImg;
    @SerializedName("method")
    private int authType;
    @SerializedName("name")
    private String userName;
    private String plateno;
    @SerializedName("position")
    private String keyPosition;
    private String reason;
    private int state;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getCarImgs() {
        return carImgs;
    }

    public void setCarImgs(List<String> carImgs) {
        this.carImgs = carImgs;
    }

    public String getCarImg() {
        return carImg;
    }

    public void setCarImg(String carImg) {
        this.carImg = carImg;
    }

    public @ApplicationDetails.AuthType int getAuthType() {
        return authType;
    }

    public void setAuthType(@ApplicationDetails.AuthType int authType) {
        this.authType = authType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getKeyPosition() {
        return keyPosition;
    }

    public void setKeyPosition(String keyPosition) {
        this.keyPosition = keyPosition;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public @KeyPosition.KeyPositionStatus int getState() {
        return state;
    }

    public void setState(@KeyPosition.KeyPositionStatus int state) {
        this.state = state;
    }
}
