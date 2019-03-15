package com.saiyi.smartkeycabinetclient.core.http.error;

import com.lib.fast.common.error.CustomError;

/**
 * created by siwei on 2018/12/3
 */
public enum ResponseError implements CustomError {

    DeviceNotActivated(3003, "设备未激活"),

    Repeat(3002, "数据重复"),

    Null(3001, "暂无数据"),

    VerificationError(2005, "验证码错误或过期"),

    PwdInconsistent(2004, "两次密码不一致"),

    AccountOrPwdError(2002, "帐号或密码错误"),

    NotLogin(2001, "您尚未登录,请先登录"),

    ServiceFaild(1002, "操作失败"),

    ServiceError(1001, "服务错误"),

    ServiceSuccess(1000, "操作成功"),

    TokenTimeout(101, "Token不合法或已过期,请重新登录"),

    UnKnow(0, "未知错误");

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

    ResponseError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseError handleError(int code){
        ResponseError[] errors = ResponseError.values();
        for(ResponseError customError : errors){
            if(code == customError.code){
                return customError;
            }
        }
        return UnKnow;
    }
}
