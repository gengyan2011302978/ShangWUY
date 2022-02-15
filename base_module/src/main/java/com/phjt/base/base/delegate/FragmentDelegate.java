package com.phjt.base.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 10:16
 * description: {@link Fragment} 代理类
 */
public interface FragmentDelegate {
  String FRAGMENT_DELEGATE = "FRAGMENT_DELEGATE";

  void onAttach(@NonNull Context context);

  void onCreate(@Nullable Bundle savedInstanceState);

  void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState);

  void onActivityCreate(@Nullable Bundle savedInstanceState);

  void onStart();

  void onResume();

  void onPause();

  void onStop();

  void onSaveInstanceState(@NonNull Bundle outState);

  void onDestroyView();

  void onDestroy();

  void onDetach();

  /**
   * Return true if the fragment is currently added to its activity.
   */
  boolean isAdded();
}
