package com.kivi.framework.web.kaptcha.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.kivi.framework.web.kaptcha.configuration.KaptchConfiguration;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Import(KaptchConfiguration.class)
public @interface EnableKaptcha {

}
