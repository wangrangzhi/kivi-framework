package com.kivi.framework.web.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatelessToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;

	public StatelessToken(String username, String password, boolean rememberMe) {
		super(username, password, rememberMe);
	}

	public StatelessToken(String username, String password, String token) {
		super(username, password, false);
		this.token = token;
	}

	public StatelessToken(String username, String token) {
		super(username, token, false);
		this.token = token;
	}

}
