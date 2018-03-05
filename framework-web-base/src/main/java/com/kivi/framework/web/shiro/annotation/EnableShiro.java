package com.kivi.framework.web.shiro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.kivi.framework.web.shiro.configuration.KtfShiroConfiguration;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Import(KtfShiroConfiguration.class)
public @interface EnableShiro {

}
