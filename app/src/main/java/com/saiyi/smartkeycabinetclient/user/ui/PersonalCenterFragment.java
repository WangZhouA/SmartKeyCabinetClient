package com.saiyi.smartkeycabinetclient.user.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lib.fast.common.activity.view.NavBar;
import com.lib.fast.common.event.EventAction;
import com.lib.fast.common.utils.StringUtils;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPFragment;
import com.saiyi.smartkeycabinetclient.core.model.Action;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;
import com.saiyi.smartkeycabinetclient.user.presenter.PersonalCenterPresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * 个人中心
 */
public class PersonalCenterFragment extends BKMVPFragment<PersonalCenterPresenter> {

    @BindView(R.id.title_nb)
    NavBar mNavBar;

    @BindView(R.id.my_approval_rl)
    RelativeLayout mMyApprovalRl;

    @BindView(R.id.car_info_rl)
    RelativeLayout mCarInfoRl;

    @BindView(R.id.avatar_iv)
    ImageView mAvatarIv;

    @BindView(R.id.name_tv)
    TextView mNameTv;

    @BindView(R.id.phone_tv)
    TextView mPhoneTv;

    public PersonalCenterFragment() {
    }

    public static PersonalCenterFragment newInstance() {
        PersonalCenterFragment fragment = new PersonalCenterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public PersonalCenterPresenter initPresenter(Context context) {
        return new PersonalCenterPresenter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_center, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mNavBar.setTitle("我的");
        mNavBar.hiddenLeftIcon();
    }

    @Override
    protected void initData() {
        super.initData();
        getPresenter().getUserDetials();
        registerEventBus();
    }

    @Override
    public void onMessageEvent(EventAction event) {
        super.onMessageEvent(event);
        if (event instanceof Action) {
            Action action = (Action) event;
            if (action == Action.UpdateAvator || action == Action.FullUserInformation) {
                onGetUserDetialsSuccess(UserHelper.instance().getLoginUser());
            }
        }
    }

    /**
     * 获取用户详细信息成功
     */
    public void onGetUserDetialsSuccess(User user) {
        Picasso.with(this.getContext()).load(user.getAvater())
                .error(R.drawable.headsculpture).transform(new CropCircleTransformation()).into(mAvatarIv);
        mNameTv.setText(user.getName());
        mPhoneTv.setText(StringUtils.getOmitTelStr(user.getPhone()));
    }


    @OnClick(R.id.user_info_rl)
    protected void onUserInfoClick() {
        openActivity(UserInfoActivity.class);
    }

    @OnClick({R.id.my_application_rl, R.id.my_approval_rl, R.id.car_info_rl,
            R.id.my_msg_rl, R.id.account_info_rl, R.id.feed_back_rl, R.id.about_us_rl})
    protected void onMenuItemClick(View view) {
        switch (view.getId()) {
            case R.id.my_application_rl:
                openActivity(ApplicationActivity.class);
                break;
            case R.id.my_approval_rl:

                break;
            case R.id.car_info_rl:

                break;
            case R.id.my_msg_rl:

                break;
            case R.id.account_info_rl:

                break;
            case R.id.feed_back_rl:

                break;
            case R.id.about_us_rl:
                openActivity(AboutUsActivity.class);
                break;
        }
    }


}
