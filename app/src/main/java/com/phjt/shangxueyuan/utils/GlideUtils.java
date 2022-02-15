package com.phjt.shangxueyuan.utils;


import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.phjt.shangxueyuan.R;
import com.phsxy.utils.AppContextUtil;



/**
 * @author: Roy
 * date:   2020/3/27
 * company: 普华集团
 * description:
 */
public class GlideUtils {


    private static int failImage = R.drawable.image_placeholder;

    public static void load(@Nullable String url, ImageView imageView) {
        Glide.with(AppContextUtil.getInstance()).load(url).error(failImage).into(imageView);
    }

    public static void load(@RawRes @DrawableRes @Nullable Integer resourceId, ImageView imageView) {
        Glide.with(AppContextUtil.getInstance()).load(resourceId).error(failImage).into(imageView);
    }

    public static void load(String url, ImageView imageView, int failImage) {
        Glide.with(AppContextUtil.getInstance()).load(url).error(failImage).into(imageView);
    }

}