package com.kivi.framework.web.shiro.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShiroUserVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String SHIRO_USER_SESSION_KEY = "shiro_user_session_key";

	private Object user;
	private String principal;
	private boolean isLogin;
	private String[] roles;
	private String[] permissions;
	private String objectName;

	public ShiroUserVO(String principal) {

		this(true, principal, null);
	}

	public ShiroUserVO(boolean isLogin, String principal, Object user) {
		this.principal = principal;
		this.user = user;
		this.isLogin = isLogin;
	}

	public boolean isLogin() {
		return this.isLogin;
	}

	public boolean getIsLogin() {
		return isLogin();
	}

}
