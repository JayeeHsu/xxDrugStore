package com.xiaoxiang.xxdrugstore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)  //在运行时也生效
public @interface LoginRequired {
    boolean loginSuccess() default true;
}


