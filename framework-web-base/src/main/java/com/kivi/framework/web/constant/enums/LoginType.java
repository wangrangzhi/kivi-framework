package com.kivi.framework.web.constant.enums;

/**
 */
public enum LoginType {

	CLIENT("1"), ADMIN("2");

	private String type;

	private LoginType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type.toString();
	}

	public static String getName() {
		return "LoginType";
	}
}
