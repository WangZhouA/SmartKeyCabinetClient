package com.saiyi.smartkeycabinetclient.key.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * created by siwei on 2018/12/3
 */
public class Application {

    private long aid;
    private String approvername;
    private String startTime;
    private String endTime;
    @SerializedName("time")
    private String applicationTime;
    private String job;
    @SerializedName("name")
    private String keyPositionName;
    @SerializedName("position")
    private String keyPosition;
    @SerializedName("reason")
    private String reason;
    private int state;
    @SerializedName("username")
    private String applicationerName;

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getApprovername() {
        return approvername;
    }

    public void setApprovername(String approvername) {
        this.approvername = approvername;
    }

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

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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
        return "Application{" +
                "aid=" + aid +
                ", approvername='" + approvername + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", applicationTime='" + applicationTime + '\'' +
                ", job='" + job + '\'' +
                ", keyPositionName='" + keyPositionName + '\'' +
                ", keyPosition='" + keyPosition + '\'' +
                ", reason='" + reason + '\'' +
                ", state=" + state +
                ", applicationerName='" + applicationerName + '\'' +
                '}';
    }
}
