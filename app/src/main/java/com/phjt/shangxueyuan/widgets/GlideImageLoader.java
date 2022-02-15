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
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.mvp.ui.activity.ShareActivity;
import com.phjt.shangxueyuan.utils.Zxingutil;
import com.youth.banner.loader.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GlideImageLoader extends ImageLoader {
    public static List<Bitmap> listBitmap = new ArrayList<>(10);
    int numb = 1;

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡456307乱强转！j
         */
        //Glide 加载图片简单用法
        Glide.with(context)
                .load(path)
                .error(R.drawable.banner_holder)
                //圆角半径
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)))
                .into(imageView);
    }

    @Override
    public void displayImage(Context context, Object path, String code, ImageView imageView, int postion) {
        View relativeLayout = null;
        ImageView imageView1 = null;
        ImageView wb_qr_code, wb_qr_code1;
        System.out.println(path.toString()+"索引————————————————————"+listBitmap.size());
        relativeLayout = View.inflate(context, R.layout.bannercodee, null);
        imageView1 = relativeLayout.findViewById(R.id.bannerDefaultImage);
        wb_qr_code = relativeLayout.findViewById(R.id.wb_qr_code);
        wb_qr_code1 = relativeLayout.findViewById(R.id.wb_qr_code1);
        if (postion == 1) {
            wb_qr_code1.setVisibility(View.INVISIBLE);
            wb_qr_code.setVisibility(View.VISIBLE);
        } else if (postion > 1 && postion <= ShareActivity.images.size()) {
            wb_qr_code1.setVisibility(View.VISIBLE);
            wb_qr_code.setVisibility(View.INVISIBLE);
        }

        AppImageLoader.loadResUrl(path.toString(), imageView1);
//        imageView1.setBackground(creatdra(path.toString()));
        Bitmap mBitmap = Zxingutil.createQRCodeBitmap(code, 600, 600);
        wb_qr_code.setImageBitmap(mBitmap);
        wb_qr_code1.setImageBitmap(mBitmap);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(70, 70);
        params.setMargins(380, 595, 0, 0);
        wb_qr_code.setLayoutParams(params);
        RelativeLayout.LayoutParams paramst = new RelativeLayout.LayoutParams(150, 150);
        paramst.setMargins(173, 423, 0, 0);
        wb_qr_code1.setLayoutParams(paramst);
        ImageView finalImageView = imageView1;
        View finalRelativeLayout = relativeLayout;
        Glide.with(context)
                .load(path)
                .error(R.drawable.banner_holder)
                //圆角半径
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        finalImageView.setImageDrawable(resource);
                        Bitmap bitmap = createBitmap3(finalRelativeLayout, 500, 700);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        Glide.with(context)
                                .load(bytes)
                                .error(R.drawable.banner_holder)
                                //圆角半径
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        imageView.setImageDrawable(resource);
                                        if (postion == 1) {
                                            listBitmap.remove(postion);
                                            listBitmap.add(postion,bitmap);
                                            System.out.println(postion+"索引————————————————————还在加载"+resource);
                                        } else if (postion > 1 && postion <= ShareActivity.images.size()) {
                                            listBitmap.remove(postion);
                                            listBitmap.add(postion,bitmap);
                                            System.out.println(postion+"索引————————————————————还在加载"+resource);
                                        }
                                    }
                                });
                    }
                });

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        imageView.setPadding(0,0,0,100);

    }

    @Override
    public void displayImage(Context context, Object path, String code, String titles, ImageView imageView, int index) {

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