package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.mvp.ui.activity.LiveSharingActivity;
import com.phjt.shangxueyuan.utils.Zxingutil;
import com.phsxy.utils.LogUtils;
import com.youth.banner.loader.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Roy
 * date:   2021/4/14
 * company: 普华集团
 * description:
 */
public class LiveGlideImageLoader extends ImageLoader {
    public static List<Bitmap> listBitmap = new ArrayList<>(10);
    int numb = 1;
    private String mTtile = "";

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context)
                .load(path)
                .error(R.drawable.banner_holder)
                //圆角半径
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(45)))
                .into(imageView);
    }

    @Override
    public void displayImage(Context context, Object path, String code, ImageView imageView, int postion) {

    }

    @Override
    public void displayImage(Context context, Object path, String code, String titles, ImageView imageView, int postion) {
        View relativeLayout = null;
//        RelativeLayout rvContainer = null;
        ImageView imageView1 = null;
        ImageView wb_qr_code;
        TextView tvTitle;
        LogUtils.e(path.toString() + "索引————————————————————" + listBitmap.size());
        relativeLayout = View.inflate(context, R.layout.banner_live_codee, null);

//        rvContainer = relativeLayout.findViewById(R.id.bannerContainer);
        imageView1 = relativeLayout.findViewById(R.id.bannerDefaultImage);
        wb_qr_code = relativeLayout.findViewById(R.id.wb_qr_code);
        tvTitle = relativeLayout.findViewById(R.id.tv_text_title);
        wb_qr_code.setVisibility(View.VISIBLE);

        AppImageLoader.loadResUrl(path.toString(), imageView1);
        Bitmap mBitmap = Zxingutil.createQRCodeBitmap(code, 600, 600);
        wb_qr_code.setImageBitmap(mBitmap);

        imageView1.setPadding(60, 0, 60, 0);
        tvTitle.setText(titles);
        ImageView finalImageView = imageView1;
        View finalRelativeLayout = relativeLayout;
        Glide.with(context)
                .load(path)
                .error(R.drawable.banner_holder)
                //圆角半径
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(1)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        finalImageView.setImageDrawable(resource);
                        Bitmap bitmap = createBitmap3(finalRelativeLayout, 480, 710);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        Glide.with(context)
                                .load(bytes)
                                .error(R.drawable.banner_holder)
                                //圆角半径
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(1)))
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        imageView.setImageDrawable(resource);
                                        if (postion == 1) {
                                            listBitmap.remove(postion);
                                            listBitmap.add(postion, bitmap);
                                            LogUtils.e(postion + "索引————————————————————还在加载" + resource);
                                        } else if (postion > 1 && postion <= LiveSharingActivity.imageList.size()) {
                                            listBitmap.remove(postion);
                                            listBitmap.add(postion, bitmap);
                                            LogUtils.e(postion + "索引————————————————————还在加载" + resource);
                                        }
                                    }
                                });
                    }
                });

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    public Drawable creatdra(String urladdr) {
        Drawable drawable = null;
        try {
            //judge if has picture locate or not according to filename
            drawable = Drawable.createFromStream(new URL(urladdr).openStream(), "image.jpg");

        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }
        return drawable;

    }

    public static Bitmap createBitmap3(View v, int width, int height) {
        //测量使得view指定大小
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        v.measure(measuredWidth, measuredHeight);
        //调用layout方法布局后，可以得到view的尺寸大小
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        return bmp;
    }
}
