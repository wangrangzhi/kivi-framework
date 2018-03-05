package com.kivi.framework.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.kivi.framework.util.kit.DateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String type;

	private String account;

	private String password;

	private String salt;

	private String name;

	private Date birthday;

	private BigDecimal sex;

	private String email;

	private String phone;

	private String roleId;

	private Long deptId;

	private Short status;

	private DateTime createTime;

	private DateTime updateTime;

	private Set<Long> roles;

}
