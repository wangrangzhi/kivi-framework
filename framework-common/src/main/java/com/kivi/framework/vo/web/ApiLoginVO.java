package com.kivi.framework.vo.web;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(value = "ApiLoginVO", description = "登录对象")
public class ApiLoginVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@NotEmpty
	@Size(max = 32, message = "登录账号长度不能超过32")
	@ApiModelProperty(value = "登录账号", required = true, dataType = "String", notes = "登录账号长度不能超过32", example = "user001")
	private String principal;

	/**
	 * 密码
	 */
	@NotEmpty
	@Size(max = 32, message = "密码长度不能超过32")
	@ApiModelProperty(value = "密码", required = true, dataType = "String", notes = "密码长度不能超过32", example = "123456")
	private String password;

	@Size(min = 0, max = 4, message = "校验码长度不正确")
	@ApiModelProperty(value = "校验码", required = false, dataType = "String", notes = "登录校验码", example = "1qaz")
	private String kaptcha;

}
