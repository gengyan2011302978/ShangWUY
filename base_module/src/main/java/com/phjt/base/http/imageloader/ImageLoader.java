package com.phjt.base.http.imageloader;

import android.content.Context;


import com.phjt.base.utils.Preconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.Nullable;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/4/4 16:52
 * description:
 */
@Singleton
public final class ImageLoader {

  @Inject
  @Nullable
  BaseImageLoaderStrategy mStrategy;

  @Inject
  public ImageLoader() {
  }

  /**
   * 加载图片
   */
  public <T extends ImageConfig> void loadImage(Context context, T config) {
    Preconditions.checkNotNull(mStrategy,
        "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
    this.mStrategy.loadImage(context, config);
  }

  /**
   * 停止加载或清理缓存
   *
   * @param context
   * @param config
   * @param <T>
   */
  public <T extends ImageConfig> void clear(Context context, T config) {
    Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
    this.mStrategy.clear(context, config);
  }

  /**
   * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
   *
   * @param strategy
   */
  public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
    Preconditions.checkNotNull(strategy, "strategy == null");
    this.mStrategy = strategy;
  }


  @Nullable
  public BaseImageLoaderStrategy getLoadImgStrategy() {
    return mStrategy;
  }
}
