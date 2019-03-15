package com.saiyi.smartkeycabinetclient.account.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.lib.fast.common.http.BaseResponseListener;
import com.lib.fast.common.http.exception.ErrorStatus;
import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.account.model.PerfectInfomationModel;
import com.saiyi.smartkeycabinetclient.account.ui.PerfectInfomationActivity;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

/**
 * created by siwei on 2018/11/5
 */
public class PerfectInfomationPresenter extends PresenterImpl<PerfectInfomationActivity, PerfectInfomationModel> {

    public PerfectInfomationPresenter(Context context) {
        super(context);
    }

    @Override
    public PerfectInfomationModel initModel(Context context) {
        return new PerfectInfomationModel(context);
    }

    /**
     * 提交用户详细信息
     */
    public boolean submitPerfectInfomation(final String uname, final boolean gender, final String office, final String jobNum) {
        if (TextUtils.isEmpty(uname) || TextUtils.isEmpty(office) || TextUtils.isEmpty(jobNum)) {
            getView().toast("请输入完整信息");
        } else {
            final User user = UserHelper.instance().getLoginUser();
            if (user != null) {
                user.setName(uname);
                user.setGender(gender ? User.MAN : User.FEMAN);
                user.setJob(office);
                user.setJobno(jobNum);
                getModel().fullUserInformation(user, new BaseResponseListener<Void>() {
                    @Override
                    public void onResponse(Void data) {
                        super.onResponse(data);
                        getView().onSubmitPerfectInfomationSuccess();
                    }

                    @Override
                    public void onFaild(ErrorStatus e) {
                        super.onFaild(e);
                        getView().toastErrorMsg(e);
                    }
                });
            }
            return true;
        }
        return false;
    }
}
