package com.saiyi.smartkeycabinetclient.key.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lib.fast.common.utils.UiUtil;

/**
 * created by siwei on 2018/11/10
 */
public class KeyPositionItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top += UiUtil.dip2px(8);
        outRect.bottom += UiUtil.dip2px(8);
        outRect.left += UiUtil.dip2px(10);
        outRect.right += UiUtil.dip2px(10);
        //super.getItemOffsets(outRect, view, parent, state);
    }
}
