package com.phjt.base.http.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;

import androidx.annotation.NonNull;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/4/4 13:47
 * description:
 */
public interface GlideAppliesOptions {

  void applyGlideOptions(@NonNull Context context, @NonNull GlideBuilder builder);
}
