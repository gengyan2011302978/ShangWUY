package com.phjt.base.integration.lifecycle;

import androidx.annotation.NonNull;
import io.reactivex.subjects.Subject;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 11:03
 * description:
 */
public interface Lifecycleable<E> {
  @NonNull
  Subject<E> provideLifecycleSubject();
}
