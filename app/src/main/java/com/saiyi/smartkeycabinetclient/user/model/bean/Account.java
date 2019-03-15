package com.saiyi.smartkeycabinetclient.user.model.bean;

import org.litepal.crud.DataSupport;

/**
 * created by siwei on 2018/12/4
 */
public class Account extends DataSupport{

    private long _id;
    private String phone;
    private String pwd;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Account() {
    }

    public Account(String phone, String pwd) {
        this.phone = phone;
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Account{" +
                "_id=" + _id +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
