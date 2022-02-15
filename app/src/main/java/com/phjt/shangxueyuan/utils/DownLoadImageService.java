package com.phjt.shangxueyuan.utils;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Roy
 * date:   2020/7/1
 * company: 普华集团
 * description:
 */
public class DownLoadImageService implements Runnable {

    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;

    public DownLoadImageService(Context context, String url, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    public void run() {
        File file = null;
        try {
            file = Glide.with(context)
                    .load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    callBack.onDownLoadSuccess(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                callBack.onDownLoadFailed();
            }
        }
    }



    public interface ImageDownLoadCallBack {

        void onDownLoadSuccess(File file) throws IOException;

        void onDownLoadFailed();
    }
}
