package com.kivi.framework.web.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.kivi.framework.constant.Constant;
import com.kivi.framework.web.shiro.token.StatelessToken;
import com.kivi.framework.web.shiro.token.manager.TokenManager;
import com.kivi.framework.web.util.kit.WafRequestWrapper;

/**
 * 无状态授权过滤器
 *
 */
public class StatelessAuthcFilter extends AccessControlFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TokenManager tokenManager;

	public TokenManager getTokenManager() {
		return tokenManager;
	}

	public void setTokenManager(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestUrl = httpRequest.getRequestURL().toString();
		logger.debug("拦截到的url:{}", requestUrl);
		// 前段token授权信息放在请求头中传入
		WafRequestWrapper wafRequest = new WafRequestWrapper(httpRequest);
		String authorization = wafRequest.getHeader(Constant.HTTP_AUTHORIZATION);
		if (StringUtils.isEmpty(authorization)) {
			onLoginFail(response, "请求头不包含认证信息:" + Constant.HTTP_AUTHORIZATION);
			return false;
		}

		Subject subject = getSubject(request, response);

		// 获取无状态Token
		StatelessToken accessToken = tokenManager.getToken(authorization);

		try {
			// 委托给Realm进行登录
			subject.login(accessToken);

		} catch (Exception e) {
			logger.error("认证失败:" + e.getMessage(), e);
			onLoginFail(response, "认证失败:" + e.getMessage()); // 6、登录失败
			return false;
		}

		// 通过isPermitted 才能调用doGetAuthorizationInfo方法获取权限信息
		requestUrl = httpRequest.getServletPath();
		boolean permitted = subject.isPermitted(requestUrl);
		if (!permitted) {
			logger.error("访问URL{}，没有权限", requestUrl);
			onLoginFail(response, "尝试访问的URL，没有权限"); // 6、登录失败
			return false;
		}

		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}

	// 登录失败时默认返回401状态码
	private void onLoginFail(ServletResponse response, String errorMsg) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
		httpResponse.setContentType("text/html");
		httpResponse.setCharacterEncoding("utf-8");
		httpResponse.getWriter().write(errorMsg);
		httpResponse.getWriter().close();
	}

}
