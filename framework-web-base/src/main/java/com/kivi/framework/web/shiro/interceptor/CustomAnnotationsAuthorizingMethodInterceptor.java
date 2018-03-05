package com.kivi.framework.web.shiro.interceptor;

import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

import com.kivi.framework.web.shiro.aop.RoleAllowsAnnotationMethodInterceptor;

public class CustomAnnotationsAuthorizingMethodInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {

	public CustomAnnotationsAuthorizingMethodInterceptor() {
		super();
		// 自定义
		getMethodInterceptors().add(new RoleAllowsAnnotationMethodInterceptor());
	}

}
