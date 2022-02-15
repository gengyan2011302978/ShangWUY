package com.phjt.shangxueyuan.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: gengyan
 * date:    2020/4/3 14:35
 * company: 普华集团
 * description: 用户Vip状态判断
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface VipStateCheck {
}
