package com.phjt.shangxueyuan.bean;

import android.graphics.drawable.Drawable;

/**
 * @author: Roy
 * date:   2020/8/4
 * company: 普华集团
 * description:
 */
public class MineBean {
    private String name;
    private Drawable drawable;

    public MineBean(String name, Drawable drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
