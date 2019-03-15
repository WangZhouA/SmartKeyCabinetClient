package com.saiyi.smartkeycabinetclient.user.model.bean;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.lib.fast.common.activity.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by siwei on 2018/11/9
 */
public class User implements Serializable , Cloneable{

    public static final int FEMAN = 1;
    public static final int MAN = 2;

    @IntDef({FEMAN, MAN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
    }

    /**获取性别*/
    public static String getGender(@Gender int gender) {
        String genderStr = "";
        switch (gender) {
            case MAN:
                genderStr = "男";
                break;
            case FEMAN:
                genderStr = "女";
                break;
        }
        return genderStr;
    }

    @SerializedName("head")
    private String avater;
    private String name;
    @SerializedName("sex")
    private int gender;
    private String phone;
    private String role;
    private String token;
    @SerializedName("userId")
    private long uid;
    private String job;
    private String jobno;

    public String getAvater() {
        if (!TextUtils.isEmpty(avater)) {
            if (!avater.toLowerCase().startsWith("http")) {
                String url = BaseApplication.getInstance().getBuildConfig().getHttpBaseUrl();
                if (url.endsWith("/") == avater.startsWith("/")) {
                    if (url.endsWith("/")) {
                        url = url.substring(0, url.length() - 1);
                        url += avater;
                    } else {
                        url += "/" + avater;
                    }
                } else {
                    url += avater;
                }
                avater = url;
            }
        }
        return avater;
    }

    public void setAvater(String avater) {
        if (!TextUtils.isEmpty(avater)) {
            if (!avater.toLowerCase().startsWith("http")) {
                String url = BaseApplication.getInstance().getBuildConfig().getHttpBaseUrl();
                if (url.endsWith("/") == avater.startsWith("/")) {
                    if (url.endsWith("/")) {
                        url = url.substring(0, url.length() - 1);
                        url += avater;
                    } else {
                        url += "/" + avater;
                    }
                } else {
                    url += avater;
                }
                avater = url;
            }
        }
        this.avater = avater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @Gender
    int getGender() {
        return gender;
    }

    public void setGender(@Gender int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getJobno() {
        return jobno;
    }

    public void setJobno(String jobno) {
        this.jobno = jobno;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object t = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            t = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
        return t;
    }

    @Override
    public String toString() {
        return "User{" +
                "avater='" + avater + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", token='" + token + '\'' +
                ", uid=" + uid +
                ", job='" + job + '\'' +
                ", jobno='" + jobno + '\'' +
                '}';
    }
}
