package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Context;
import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luck.picture.lib.photoview.PhotoView;
import com.phjt.shangxueyuan.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Roy
 * date:   2020/9/17
 * company: 普华集团
 * description:
 */
public class BigPhotoAdapter extends PagerAdapter {
    private List<String> datas = new ArrayList<String>();
    private Context context;
    private RequestManager requestManager;
    private int mType;
    private String mUrlPre;
    Listener mListener;

    public void setDatas(List<String> datas) {
        if (datas != null) {
            this.datas = datas;
        }
    }

    public BigPhotoAdapter(Context context, int mType, String mUrlPre, Listener listener) {
        this.context = context;
        this.mType = mType;
        this.mUrlPre = mUrlPre;
        this.mListener = listener;
        requestManager = Glide.with(context);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo_images, container, false);
        if (view != null) {
            PhotoView imageView = view.findViewById(R.id.image);
            final String imgUrl;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onImageClicked(v);
                }
            });
            if (mType == 1) {
                imgUrl = mUrlPre + datas.get(position);
            } else {
                imgUrl = datas.get(position);
            }

            if (imgUrl.contains("http")) {
                requestManager.load(imgUrl)
                        //缓存多个尺寸
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        //先显示缩略图  缩略图为原图的1/10
                        .thumbnail(0.1f)
                        .into(imageView);
            } else {
                requestManager
                        .load(new File(imgUrl))
                        .into(imageView);
            }
            container.addView(view, 0);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    public interface Listener {
        void onImageClicked(View view);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
