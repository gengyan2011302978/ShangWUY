package com.phjt.base.http.imageloader;

import android.widget.ImageView;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/4/4 16:54
 * description:
 */
public class ImageConfig {
  protected Object loadRes;
  protected ImageView imageView;
  protected int placeholder;//占位符
  protected int errorPic;//错误占位符

  public Object getLoadRes() {
    return loadRes;
  }

  public ImageView getImageView() {
    return imageView;
  }

  public int getPlaceholder() {
    return placeholder;
  }

  public int getErrorPic() {
    return errorPic;
  }
}
