package com.saiyi.smartkeycabinetclient.account.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lib.fast.common.event.EventAction;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.account.presenter.LoginPresenter;
import com.saiyi.smartkeycabinetclient.account.tool.BindOnClickListeber;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;
import com.saiyi.smartkeycabinetclient.core.model.Action;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.home.ui.HomeActivity;
import com.saiyi.smartkeycabinetclient.user.model.bean.Account;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BKMVPActivity<LoginPresenter> {

    @BindView(R.id.uname_et)
    EditText mUNameEt;

    @BindView(R.id.upwd_et)
    EditText mUPwdEt;

    @BindView(R.id.remember_pwd_ck)
    CheckBox mRememberPwdCk;

    @BindView(R.id.remember_pwd_tv)
    TextView mRememberPwdTv;

    @BindView(R.id.error_tv)
    TextView mErrorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public LoginPresenter initPresenter(Context context) {
        return new LoginPresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().hidden();
        mRememberPwdCk.setOnClickListener(new BindOnClickListeber(mRememberPwdTv, mRememberPwdCk));
    }

    @Override
    protected void initData() {
        super.initData();
        String cachePhone = UserHelper.instance().getCachePhone();
        mUNameEt.setText(cachePhone);
        Account account = UserHelper.instance().getRememberPwd(cachePhone);
        if (account != null) {
            mUPwdEt.setText(account.getPwd());
        }
        registerEventBus();

    }

    @Override
    public void onMessageEvent(EventAction event) {
        super.onMessageEvent(event);
        if (event instanceof Action) {
            Action action = (Action) event;
            if (action == Action.UserAccountChanged) {
                mUNameEt.setText(UserHelper.instance().getCachePhone());
                mUPwdEt.setText(null);
            }
        }
    }

    @OnClick(R.id.login_btn)
    protected void onLoginClick() {
        String phone = mUNameEt.getText().toString().trim();
        String pwd = mUPwdEt.getText().toString().trim();
        if (getPresenter().login(phone, pwd, mRememberPwdCk.isChecked())) {
            showCustomLoading("正在登陆");
        }
    }

    /**
     * 去完善完整信息
     */
    public void goPerfectInfomationActivity() {
        dismissProgressDialog();
        openActivity(PerfectInfomationActivity.class);
        finish();
    }

    /**
     * 登陆成功
     */
    public void goHomeActivity() {
        dismissProgressDialog();
        openActivity(HomeActivity.class);
        finish();
    }

    @OnClick(R.id.forget_pwd_tv)
    protected void onForgetPwdClick() {
        openActivity(ForgetPwdActivity.class);
    }

    @OnClick(R.id.regist_tv)
    protected void onRegisterClick() {
        openActivity(RegisterActivity.class);
    }
}
