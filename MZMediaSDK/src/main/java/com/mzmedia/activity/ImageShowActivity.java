package com.mzmedia.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mengzhu.sdk.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import androidx.appcompat.app.AppCompatActivity;

public class ImageShowActivity extends AppCompatActivity {

    public static final String URL_IMAGE = "url_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_show);

        ImageView ivBig = findViewById(R.id.iv_big);

        String urlImage = getIntent().getStringExtra(URL_IMAGE);
        if (!TextUtils.isEmpty(urlImage)) {
            ImageLoader.getInstance().displayImage(urlImage, ivBig, new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .build());
        }


        ivBig.setOnClickListener(v -> finish());
    }
}