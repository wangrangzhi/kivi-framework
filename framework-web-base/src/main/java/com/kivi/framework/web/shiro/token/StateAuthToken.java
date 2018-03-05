package com.kivi.framework.web.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 */
public class StateAuthToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 验证码
	 */
	private String captcha;
	/**
	 * 用来区分前后台登录的标记
	 */
	private String loginType;
	/**
	 * 用来区分登录用户的渠道
	 */
	private String loginForm;

	public StateAuthToken(String username, String password, boolean rememberMe) {
		super(username, password, rememberMe);
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(String loginForm) {
		this.loginForm = loginForm;
	}
}
