package com.saiyi.smartkeycabinetclient.core.model;

import com.lib.fast.common.event.EventAction;

/**
 * created by siwei on 2018/11/5
 */
public enum Action implements EventAction {

    /**
     * 添加新的设备
     */
    AddNewDevice(0X08),
    /**
     * 用户信息改变
     */
    UserInfoChanged(0X07),
    /**
     * 修改头像
     */
    UpdateAvator(0X06),
    /**
     * 填写用户详情
     */
    FullUserInformation(0X05),
    /**
     * 申请人变更
     */
    ApprovalChangeOfApplicationKey(0X04),
    /**
     * 申请钥匙的钥匙位改变
     */
    KeyPositionChangedOfApplicationKey(0X03),
    /**
     * 申请钥匙的设备改变
     */
    ApplicationKeyOfDeviceChange(0X02),
    /**
     * 用户的账户发生改变
     */
    UserAccountChanged(0X01);

    private int actionId;

    @Override
    public int getActionId() {
        return actionId;
    }

    Action(int actionId) {
        this.actionId = actionId;
    }
}
