package com.saiyi.smartkeycabinetclient.account.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.lib.fast.common.widgets.text.CountDownView;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.account.presenter.ForgetPwdPresenter;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPwdActivity extends BKMVPActivity<ForgetPwdPresenter> implements CountDownView.CountDownCallBack {

    private static final int VERIFY_CODE_COUNT_DOWN_TIME = 60;//获取验证码倒计时时长60秒

    @BindView(R.id.uname_et)
    EditText mUNameEt;

    @BindView(R.id.ucode_et)
    EditText mUCodeEt;

    @BindView(R.id.upwd_et)
    EditText mUPwdEt;

    @BindView(R.id.urepwd_et)
    EditText mURepwdEt;

    @BindView(R.id.count_view)
    CountDownView mCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
    }

    @Override
    public ForgetPwdPresenter initPresenter(Context context) {
        return new ForgetPwdPresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().setTitle("找回密码");
    }

    @OnClick(R.id.count_view)
    protected void onGetSmsVerifyCodeClick() {
        if (getPresenter().getSmsVerfiyCode(mUNameEt.getText().toString().trim())) {
            mCountView.setText("正在获取...");
            mCountView.setEnabled(false);
        }
    }

    @OnClick(R.id.register_btn)
    protected void onRegisterClick() {
        String phone = mUNameEt.getText().toString().trim();
        String verifyCode = mUCodeEt.getText().toString().trim();
        String pwd = mUPwdEt.getText().toString().trim();
        String rePwd = mURepwdEt.getText().toString().trim();
        if (getPresenter().registerAccount(phone, verifyCode, pwd, rePwd)) {
            showCustomLoading("正在注册");
        }
    }

    /**
     * 获取短信验证码成功
     */
    public void onGetSmsVerifyCodeSuccess() {
        mCountView.startCountDown(VERIFY_CODE_COUNT_DOWN_TIME, this);
    }

    /**
     * 找回成功
     */
    public void onRetrieveSuccess() {
        dismissProgressDialog();
        toast("找回成功!");
        finish();
    }

    @Override
    public void onCountDown(int second) {
        mCountView.setText(String.format("剩余%s秒", second));
    }

    @Override
    public void onCountComplate() {
        mCountView.setText("发送验证码");
        mCountView.setEnabled(true);
    }

    @Override
    public void onCountCancled() {
        mCountView.setText("发送验证码");
        mCountView.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountView.stopContDown();
        mCountView.release();
    }
}
