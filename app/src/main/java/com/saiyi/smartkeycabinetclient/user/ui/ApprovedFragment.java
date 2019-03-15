package com.saiyi.smartkeycabinetclient.user.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPFragment;
import com.saiyi.smartkeycabinetclient.user.presenter.ApprovedPresenter;

/**
 */
public class ApprovedFragment extends BKMVPFragment<ApprovedPresenter> {

    public ApprovedFragment() {
        // Required empty public constructor
    }
    public static ApprovedFragment newInstance() {
        ApprovedFragment fragment = new ApprovedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public ApprovedPresenter initPresenter(Context context) {
        return new ApprovedPresenter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approved, container, false);
        return view;
    }


}
