package com.saiyi.smartkeycabinetclient.user.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPFragment;
import com.saiyi.smartkeycabinetclient.user.presenter.PendingPresenter;

/**
 */
public class PendingFragment extends BKMVPFragment<PendingPresenter> {

    public PendingFragment() {
    }

    public static PendingFragment newInstance() {
        PendingFragment fragment = new PendingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public PendingPresenter initPresenter(Context context) {
        return new PendingPresenter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false);
    }

}
