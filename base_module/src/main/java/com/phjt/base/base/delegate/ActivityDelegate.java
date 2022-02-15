package com.phjt.base.base.delegate;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 10:14
 * description: {@link Activity} 代理类,
 */
public interface ActivityDelegate {

  String ACTIVITY_DELEGATE = "ACTIVITY_DELEGATE";
  void onCreate(@Nullable Bundle savedInstanceState);

  void onStart();

  void onResume();

  void onPause();

  void onStop();

  void onSaveInstanceState(@NonNull Bundle outState);

  void onDestroy();
}
