package com.phjt.base.http.imageloader;

import android.content.Context;

import androidx.annotation.Nullable;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/4/4 16:58
 * description:
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {

  /**
   * 加载图片
   *
   * @param ctx {@link Context}
   * @param config 图片加载配置信息
   */
  void loadImage(@Nullable Context ctx, @Nullable T config);

  /**
   * 停止加载
   *
   * @param ctx {@link Context}
   * @param config 图片加载配置信息
   */
  void clear(@Nullable Context ctx, @Nullable T config);
}
