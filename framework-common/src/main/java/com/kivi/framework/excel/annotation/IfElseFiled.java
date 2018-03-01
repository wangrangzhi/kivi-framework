package com.kivi.framework.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IfElseFiled {
	  public abstract String name();
	  public abstract String ifKey();
	  public abstract String ifValue();
	  public abstract String elseValue();
}
