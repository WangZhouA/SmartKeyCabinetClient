package com.saiyi.smartkeycabinetclient.key.model.bean;

import android.support.annotation.IntDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by siwei on 2018/12/3
 */
public class ApplicationDetails {

    /**
     * 刷卡
     */
    public static final int AuthCard = 1;
    /**
     * 指纹
     */
    public static final int AuthFingerprint = 2;
    /**
     * 验证码
     */
    public static final int AuthVerifyCode = 3;
    /**
     * 人脸识别
     */
    public static final int AuthFace = 4;

    @IntDef({AuthCard, AuthFingerprint, AuthVerifyCode, AuthFace})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthType{}

    private String approvername;
    private String backTime;
    private String startTime;
    @SerializedName("time")
    private String applicationTime;
    private String endTime;
    private String disagreement;
    private String job;
    @SerializedName("method")
    private int authType;
    @SerializedName("name")
    private String keyPositionName;
    @SerializedName("position")
    private String keyPosition;
    private String reason;
    private int state;
    @SerializedName("username")
    private String applicationerName;

    public String getApprovername() {
        return approvername;
    }

    public void setApprovername(String approvername) {
        this.approvername = approvername;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDisagreement() {
        return disagreement;
    }

    public void setDisagreement(String disagreement) {
        this.disagreement = disagreement;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public @AuthType int getAuthType() {
        return authType;
    }

    public void setAuthType(@AuthType int authType) {
        this.authType = authType;
    }

    public String getKeyPositionName() {
        return keyPositionName;
    }

    public void setKeyPositionName(String keyPositionName) {
        this.keyPositionName = keyPositionName;
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

    public @Applicationer.ApplicationStatus int getState() {
        return state;
    }

    public void setState(@Applicationer.ApplicationStatus int state) {
        this.state = state;
    }

    public String getApplicationerName() {
        return applicationerName;
    }

    public void setApplicationerName(String applicationerName) {
        this.applicationerName = applicationerName;
    }

    @Override
    public String toString() {
        return "ApplicationDetails{" +
                "approvername='" + approvername + '\'' +
                ", backTime='" + backTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", applicationTime='" + applicationTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", disagreement='" + disagreement + '\'' +
                ", job='" + job + '\'' +
                ", authType=" + authType +
                ", keyPositionName='" + keyPositionName + '\'' +
                ", keyPosition='" + keyPosition + '\'' +
                ", reason='" + reason + '\'' +
                ", state=" + state +
                ", applicationerName='" + applicationerName + '\'' +
                '}';
    }
}
