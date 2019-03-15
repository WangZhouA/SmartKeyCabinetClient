package com.lib.fast.common.picaso;

import android.graphics.drawable.Drawable;

import com.squareup.picasso.Target;

/**
 * 项目：智能控制     SmartLock
 */
public abstract class BitmapTarget implements Target {

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
