package com.kivi.framework.dubbo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ImportResource;

@Inherited
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE } )
@ImportResource( { "classpath:dubbo.xml" } )
public @interface DubboApplication {

}
