package com.nibiru.dagger2demo.Qualifiers_name;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Computer {
}
