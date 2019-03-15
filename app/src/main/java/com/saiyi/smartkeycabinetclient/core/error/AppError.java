package com.saiyi.smartkeycabinetclient.core.error;

import com.lib.fast.common.error.CustomError;

/**
 * created by siwei on 2018/12/4
 * 1400 - 1500;
 */
public enum  AppError implements CustomError {

    NotFullUserInfomation(1400, "用户资料尚未完善");

    private int code;
    private String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    AppError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
