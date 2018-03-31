package com.kivi.framework.vo.web;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@NotEmpty
	@Size(max = 32, message = "登录账号长度不能超过32")
	private String principal;

	/**
	 * 密码
	 */
	@NotEmpty
	@Size(max = 32, message = "密码长度不能超过32")
	private String password;

	/**
	 * 验证码
	 */
	@NotEmpty
	@Size(min = 0, max = 4, message = "校验码长度不正确")
	private String kaptcha;

}
