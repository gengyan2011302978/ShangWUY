package com.phjt.shangxueyuan.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: gengyan
 * date:    2019/11/22
 * company: 普华集团
 * description: 描述
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface SingleClick {
    long diffTime() default 1000;
}
