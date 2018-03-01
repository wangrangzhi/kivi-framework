/**
 * 
 */
package com.kivi.framework.constant.enums.shiro;

/**
 * 基于TOKEN验证的认证方式
 * 
 * @author Eric
 *
 */
public enum TokenAuthType {
	/** 简单UUID方式 */
	UUID,
	/** JWT方式 */
	JWT,
	/** 防重放攻击的JWT方式 */
	JWT_RA
}
