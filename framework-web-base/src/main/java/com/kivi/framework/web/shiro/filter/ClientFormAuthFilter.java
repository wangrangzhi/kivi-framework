package com.kivi.framework.web.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.kivi.framework.web.constant.enums.LoginType;

/**
 */
public class ClientFormAuthFilter extends FormAuthenticationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() != null) {
			Session session = subject.getSession();
			if (session.getAttribute(LoginType.getName()).equals(LoginType.CLIENT.toString())) {
				return super.isAccessAllowed(request, response, mappedValue);
			} else {
				return false;
			}
		} else {
			return super.isAccessAllowed(request, response, mappedValue);
		}
	}

}
