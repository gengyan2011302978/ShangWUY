package com.phjt.shangxueyuan.widgets.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ListBannerBean;

/**
 * @author: gengyan
 * date:    2020/3/5 17:07
 * company: 普华集团
 * description: 描述
 */
public class ImageHolderCreator implements HolderCreator {

    private OnMainBannerClick mainBannerClick;

    @Override
    public View createView(final Context context, final int index, Object o) {
        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);

        String url = "";
        if (o instanceof ListBannerBean) {
            url = ((ListBannerBean) o).getCoverUrl();
        }

        Glide.with(context)
                .load(url)
                .error(R.drawable.banner_holder)
                //圆角半径
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(iv);

        //内部实现点击事件
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainBannerClick != null && o instanceof ListBannerBean) {
                    mainBannerClick.onClick((ListBannerBean) o);
                }
            }
        });
        return iv;
    }

    public interface OnMainBannerClick {
        void onClick(ListBannerBean bean);
    }

    public void setOnMainBannerClick(OnMainBannerClick click) {
        this.mainBannerClick = click;
    }
}
