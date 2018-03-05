package com.kivi.framework.web.vo;

import java.util.Set;

import com.kivi.framework.util.kit.DateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleVO {
	private Long id;

	private Short num;

	private Long pid;

	private String name;

	private Long deptId;

	private String tips;

	private DateTime createTime;

	private DateTime updateTime;

	private String checked;

	private Set<String> permissions;
}