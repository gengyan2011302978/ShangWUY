package com.phjt.shangxueyuan.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.phjt.base.http.imageloader.ImageLoader;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.imageloader.ImageConfigImpl;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;


/**
 * @author: austenYang
 * date: 2019/7/11 10:59
 * company: 普华集团
 * description: 业务层图片加载封装
 */
public class AppImageLoader {

    private static ImageLoader mImageLoader;

    private static class Holder {
        private static final AppImageLoader INSTANCE = new AppImageLoader();
    }

    /**
     * 获取单例实例
     */
    public static AppImageLoader init(Context context) {
        mImageLoader = ArchitectUtils
                .obtainAppComponentFromContext(context).imageLoader();
        return AppImageLoader.Holder.INSTANCE;
    }

    /**
     * @param resId     resId
     * @param imageView imageView
     */
    public static void loadRes(@RawRes @DrawableRes @Nullable Integer resId, ImageView imageView) {
        mImageLoader.loadImage(imageView.getContext(), ImageConfigImpl.Builder()
                .res(resId)
                .imageView(imageView)
                .build());
    }

    /**
     * @param url       url
     * @param imageView imageView
     */
    public static void loadResUrl(@Nullable String url, ImageView imageView) {
        mImageLoader.loadImage(imageView.getContext(), ImageConfigImpl.Builder()
                .url(url == null ? "" : url)
                .imageView(imageView)
                .build());
    }

    /**
     * @param url
     * @param imageView imageView
     */
    public static void loadResUrl(@Nullable String url, ImageView imageView, @DrawableRes int failImage) {
        mImageLoader.loadImage(imageView.getContext(), ImageConfigImpl.Builder()
                .url(url == null ? "" : url)
                .errorPic(failImage)
                .placeHoder(failImage)
                .imageView(imageView)
                .build());
    }

    /**
     * @param url
     * @param imageView imageView
     */
    public static void loadResUrl(@Nullable String url, ImageView imageView, @DrawableRes int failImage, @DrawableRes int palaceHolder) {
        mImageLoader.loadImage(imageView.getContext(), ImageConfigImpl.Builder()
                .url(url == null ? "" : url)
                .errorPic(failImage)
                .placeHoder(palaceHolder)
                .imageView(imageView)
                .build());
    }


    public static void loadRoundImg(@Nullable String url, ImageView imageView, @DrawableRes int failImage) {
        mImageLoader.loadImage(imageView.getContext(), ImageConfigImpl.Builder()
                .url(url == null ? "" : url)
                .circle(true)
                .errorPic(failImage)
                .placeHoder(failImage)
                .imageView(imageView)
                .build());
    }

    public static void loadBlurImg(@Nullable String url, ImageView imageView, @DrawableRes int failImage) {
        mImageLoader.loadImage(imageView.getContext(), ImageConfigImpl.Builder()
                .url(url == null ? "" : url)
                .errorPic(failImage)
                .blurValue(1)
                .crossFade(true)
                .imageView(imageView)
                .build());
    }

    public static Bitmap rsBlur(Context context, Bitmap source, int radius){

        Bitmap inputBmp = source;
        RenderScript renderScript =  RenderScript.create(context);


        final Allocation input = Allocation.createFromBitmap(renderScript,inputBmp);
        final Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(inputBmp);
        renderScript.destroy();

        return inputBmp;
    }

}
