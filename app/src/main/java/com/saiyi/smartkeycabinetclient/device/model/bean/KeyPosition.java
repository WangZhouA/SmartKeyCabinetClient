package com.saiyi.smartkeycabinetclient.device.model.bean;

import android.support.annotation.IntDef;

import com.google.gson.annotations.SerializedName;
import com.saiyi.smartkeycabinetclient.R;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by siwei on 2018/11/9
 */
public class KeyPosition implements Serializable {

    /**
     * 在位
     */
    public static final int StatusNormal = 1;
    /**
     * 使用中
     */
    public static final int StatusUseing = 2;

    @IntDef({StatusNormal, StatusUseing})
    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyPositionStatus {
    }

    private long kid;
    private String plateno;
    @SerializedName("name")
    private String positionName;
    private String position;
    private int status;

    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public @KeyPositionStatus int getStatus() {
        return status;
    }

    public void setStatus(@KeyPositionStatus int status) {
        this.status = status;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "KeyPosition{" +
                "kid=" + kid +
                ", plateno='" + plateno + '\'' +
                ", positionName='" + positionName + '\'' +
                ", position='" + position + '\'' +
                ", status=" + status +
                '}';
    }

    public static String getKeyPositionStatus(@KeyPositionStatus int status) {
        String statusStr;
        switch (status) {
            case StatusNormal:
                statusStr = "在位";
                break;
            case StatusUseing:
                statusStr = "不在位";
                break;
            default:
                statusStr = "未知状态";
                break;
        }
        return statusStr;
    }

    public static int getKeyPositionBgRes(@KeyPositionStatus int status) {
        int bgRes = 0;
        switch (status) {
            case StatusNormal:
                bgRes = R.drawable.shape_key_status_normal;
                break;
            case StatusUseing:
                bgRes = R.drawable.shape_key_status_used;
                break;
            default:
                break;
        }
        return bgRes;
    }
}
