package com.phjt.base.base;



import com.phjt.base.di.component.AppComponent;

import androidx.annotation.NonNull;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 10:28
 * description:
 */
public interface App {
  @NonNull
  AppComponent getAppComponent();
}
