package com.saiyi.smartkeycabinetclient.user.presenter;

import android.content.Context;

import com.lib.fast.common.http.BaseResponseListener;
import com.lib.fast.common.http.exception.ErrorStatus;
import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.user.model.PersonalCenterModel;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;
import com.saiyi.smartkeycabinetclient.user.ui.PersonalCenterFragment;

/**
 * created by siwei on 2018/11/6
 */
public class PersonalCenterPresenter extends PresenterImpl<PersonalCenterFragment, PersonalCenterModel> {

    public PersonalCenterPresenter(Context context) {
        super(context);
    }

    @Override
    public PersonalCenterModel initModel(Context context) {
        return new PersonalCenterModel(context);
    }

    /**获取用户详细信息*/
    public void getUserDetials(){
        User user = UserHelper.instance().getLoginUser();
        getModel().getUserDetials(user.getUid(), new BaseResponseListener<User>() {
            @Override
            public void onResponse(User data) {
                super.onResponse(data);
                getView().onGetUserDetialsSuccess(data);
            }

            @Override
            public void onFaild(ErrorStatus e) {
                super.onFaild(e);
                getView().onGetUserDetialsSuccess(UserHelper.instance().getLoginUser());
            }
        });
    }
}
