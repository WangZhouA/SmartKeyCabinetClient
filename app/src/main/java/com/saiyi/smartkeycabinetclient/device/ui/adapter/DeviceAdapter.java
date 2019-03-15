package com.saiyi.smartkeycabinetclient.device.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;

import java.util.List;

/**
 * created by siwei on 2018/11/8
 */
public class DeviceAdapter extends BaseQuickAdapter<KeyCabinetDevice, DeviceAdapter.DeviceHolder> {

    private OnItemClickListener mOnItemClickListener;


    public DeviceAdapter(@Nullable List<KeyCabinetDevice> data) {
        super(R.layout.layout_device_item, data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    protected void convert(final DeviceHolder helper, KeyCabinetDevice item) {
        final EasySwipeMenuLayout mSwipLayout = helper.getView(R.id.swip_layout);
        helper.setText(R.id.device_name_tv, item.getDeviceName());
        helper.setText(R.id.devie_num_tv, item.getMac());
        View content = helper.getView(R.id.content);
        if(content.getTag() == null){
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    dispatchOnItemClick(position);
                }
            });
        }
        content.setTag(helper.getLayoutPosition());

        View update = helper.getView(R.id.menu_update);
        if(update.getTag() == null){
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSwipLayout.resetStatus();
                    int position = (int) v.getTag();
                    dispatchOnUpdateClick(position);
                }
            });
        }
        update.setTag(helper.getLayoutPosition());

        View delete = helper.getView(R.id.menu_delete);
        if(delete.getTag() == null){
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSwipLayout.resetStatus();
                    int position = (int) v.getTag();
                    dispatchOnDeleteClick(position);
                }
            });
        }
        delete.setTag(helper.getLayoutPosition());
    }

    private void dispatchOnItemClick(int position){
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(position, this);
        }
    }

    private void dispatchOnUpdateClick(int position){
        if(mOnItemClickListener != null){
            mOnItemClickListener.onUpdateClick(position, this);
        }
    }

    private void dispatchOnDeleteClick(int position){
        if(mOnItemClickListener != null){
            mOnItemClickListener.onDeleteClick(position, this);
        }
    }

    protected class DeviceHolder extends BaseViewHolder{

        public DeviceHolder(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener{

        void onItemClick(int position, BaseQuickAdapter adapter);

        void onUpdateClick(int position, BaseQuickAdapter adapter);

        void onDeleteClick(int position, BaseQuickAdapter adapter);
    }
}
