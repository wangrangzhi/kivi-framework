package com.kivi.framwork.actuator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kivi.framwork.actuator.annotation.enums.MetircType;

/**
 * 标记需要收集访问数据的方法
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TranMetric {

    /**
     * 交易代码
     */
    String value() default "";
    
    /**
     * 度量类型
     */
    MetircType[] type();
    
}
