package com.saiyi.smartkeycabinetclient.key.model.bean;

import android.support.annotation.IntDef;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by siwei on 2018/11/12
 */
public class Approver implements Serializable {

    /**
     * 不同意
     */
    public static final int DisAgree = 2;
    /**
     * 同意
     */
    public static final int Agree = 3;

    @IntDef({DisAgree, Agree})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ApproverStatus {
    }

    @SerializedName("auditor")
    private long uid;
    private String job;
    private String name;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Approver{" +
                "uid=" + uid +
                ", job='" + job + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
