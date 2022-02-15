package com.phjt.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.phjt.base.http.imageloader.BaseImageLoaderStrategy;
import com.phjt.base.http.imageloader.glide.GlideAppliesOptions;
import com.phjt.base.http.imageloader.glide.GlideArchitect;
import com.phjt.base.http.imageloader.glide.GlideRequest;
import com.phjt.base.http.imageloader.glide.GlideRequests;
import com.phjt.base.utils.Preconditions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/4/8 09:20
 * description:
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<ImageConfigImpl>,
        GlideAppliesOptions {

    @Override
    public void loadImage(@Nullable Context ctx, @Nullable ImageConfigImpl config) {
        Preconditions.checkNotNull(ctx, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");
        Preconditions.checkNotNull(config.getImageView(), "ImageView is required");

        GlideRequests requests;

        requests = GlideArchitect.with(ctx);

        GlideRequest<Drawable> glideRequest = null;
        Object loadRes = config.getLoadRes();
        if (loadRes instanceof String) {
            String load = (String) loadRes;
            glideRequest = requests.load(load);
        } else if (loadRes instanceof Integer) {
            Integer load = (Integer) loadRes;
            glideRequest = requests.load(load);
        } else {
            throw new IllegalArgumentException("This type of resource is not supported");
        }


        switch (config.getCacheStrategy()) {//缓存策略
            case 0:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }

        if (config.isCrossFade()) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        if (config.isCenterCrop()) {
            glideRequest.centerCrop();
        }

        if (config.isCircle()) {
            glideRequest.circleCrop();
        }

        if (config.isImageRadius()) {
            glideRequest.transform(new RoundedCorners(config.getImageRadius()));
        }

        if (config.isBlurImage() && config.isCenterCrop()) {
            glideRequest.transform(new BlurTransformation(config.getBlurValue()), new CenterCrop());
        }

        if (config.isBlurImage()) {
            glideRequest.transform(new BlurTransformation(config.getBlurValue()));
        }
        if (config.getPlaceholder() != 0)//设置占位符
        {
            glideRequest.placeholder(config.getPlaceholder());
        }

        if (config.getErrorPic() != 0)//设置错误的图片
        {
            glideRequest.error(config.getErrorPic());
        }

        glideRequest
                .into(config.getImageView());
    }

    @Override
    public void clear(@Nullable final Context ctx, @Nullable ImageConfigImpl config) {
        Preconditions.checkNotNull(ctx, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");

        if (config.getImageView() != null) {
            GlideArchitect.get(ctx).getRequestManagerRetriever().get(ctx).clear(config.getImageView());
        }

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                GlideArchitect.get(ctx).getRequestManagerRetriever().get(ctx).clear(imageView);
            }
        }

        if (config.isClearDiskCache()) {//清除本地缓存
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    Glide.get(ctx).clearDiskCache();
                }
            }).subscribeOn(Schedulers.io()).subscribe();
        }

        if (config.isClearMemory()) {//清除内存缓存
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    Glide.get(ctx).clearMemory();
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
        }
    }

    @Override
    public void applyGlideOptions(@NonNull Context context, @NonNull GlideBuilder builder) {

    }
}
