package com.saiyi.smartkeycabinetclient.key.model.bean;

import android.support.annotation.IntDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by siwei on 2018/12/3
 */
public class Applicationer {

    /**
     * 审核中
     */
    public static final int UnderReview = 1;
    /**
     * 不同意
     */
    public static final int Disagree = 2;
    /**
     * 同意
     */
    public static final int Agree = 3;
    /**
     * 正在使用中
     */
    public static final int Useing = 4;
    /**
     * 已归还
     */
    public static final int Refunded = 5;

    @IntDef({UnderReview, Disagree, Agree, Useing, Refunded})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplicationStatus {}

    private long aid;
    private String startTime;
    private String endTime;
    @SerializedName("head")
    private String avater;
    private String job;
    @SerializedName("name")
    private String keyPositionName;
    private String phone;
    @SerializedName("position")
    private String keyPosition;
    private String reason;
    private int state;
    @SerializedName("time")
    private String applicationTime;
    @SerializedName("username")
    private String applicationerName;

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
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

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public @ApplicationStatus
    int getState() {
        return state;
    }

    public void setState(@ApplicationStatus int state) {
        this.state = state;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getApplicationerName() {
        return applicationerName;
    }

    public void setApplicationerName(String applicationerName) {
        this.applicationerName = applicationerName;
    }

    @Override
    public String toString() {
        return "Applicationer{" +
                "aid=" + aid +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", avater='" + avater + '\'' +
                ", job='" + job + '\'' +
                ", keyPositionName='" + keyPositionName + '\'' +
                ", phone='" + phone + '\'' +
                ", keyPosition='" + keyPosition + '\'' +
                ", reason='" + reason + '\'' +
                ", state=" + state +
                ", applicationTime='" + applicationTime + '\'' +
                ", applicationerName='" + applicationerName + '\'' +
                '}';
    }
}
