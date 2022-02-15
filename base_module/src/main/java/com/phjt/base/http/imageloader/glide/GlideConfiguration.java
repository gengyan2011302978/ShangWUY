package com.phjt.base.http.imageloader.glide;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.http.OkHttpUrlLoader;
import com.phjt.base.http.imageloader.BaseImageLoaderStrategy;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.base.utils.DataHelper;

import java.io.File;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/4/4 14:02
 * description:
 */
@GlideModule(glideName = "GlideArchitect")
public class GlideConfiguration extends AppGlideModule {
  /**
   * 图片缓存文件最大值为100Mb
   */
  public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;

  @Override public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
    final AppComponent appComponent = ArchitectUtils.obtainAppComponentFromContext(context);

    builder.setDiskCache(new DiskCache.Factory() {
      @Nullable @Override public DiskCache build() {
        // Careful: the external cache directory doesn't enforce permissions
        return DiskLruCacheWrapper.create(
            DataHelper.makeDirs(new File(appComponent.cacheFile(), "Glide")),
            IMAGE_DISK_CACHE_MAX_SIZE);
      }
    });

    MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
    int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
    int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

    int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
    int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

    builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
    builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

    BaseImageLoaderStrategy loadImgStrategy = appComponent.imageLoader().getLoadImgStrategy();

    Log.e(TAG,"loadImgStrategy"+(loadImgStrategy instanceof GlideAppliesOptions));
    if (loadImgStrategy != null && loadImgStrategy instanceof GlideAppliesOptions) {
      ((GlideAppliesOptions) loadImgStrategy).applyGlideOptions(context, builder);
    }
  }
  private static final String TAG = "GlideConfiguration";

  @Override public void registerComponents(@NonNull Context context, @NonNull Glide glide,
      @NonNull Registry registry) {
//    AppComponent appComponent = ArchitectUtils.obtainAppComponentFromContext(context);
//    OkHttpClient okHttpClient = appComponent.okHttpClient();
//    Log.e(TAG,"loadImgStrategy"+(okHttpClient));
    registry.replace(GlideUrl.class, InputStream.class,
        new OkHttpUrlLoader.Factory(new OkHttpClient()));
  }

  @Override public boolean isManifestParsingEnabled() {
    return false;
  }
}
