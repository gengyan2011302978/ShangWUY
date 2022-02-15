package com.youth.banner.loader;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;


public interface ImageLoaderInterface<T extends View> extends Serializable {

    void displayImage(Context context, Object path, T imageView);
//    void displayTtles(Context context, String  titles, TextView textView);
    void displayImage(Context context, Object path, String code,  T imageView,int index);
    void displayImage(Context context, Object path, String code,String  titles,  T imageView,int index);

    T createImageView(Context context, Object path);
}
