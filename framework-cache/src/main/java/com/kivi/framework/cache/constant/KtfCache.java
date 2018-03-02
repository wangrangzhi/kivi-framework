package com.kivi.framework.cache.constant;

public interface KtfCache {
	/**
	 * 常量缓存
	 */
	public final static String CONSTANT = "KTFCACHE-CONSTANT";

	// 用户登录次数计数 CACHE
	public final static String SHIRO_LOGIN_COUNT_CACHE = "shiro_login_count_cache";

	// 用户登录是否被锁定 CACHE
	public final static String SHIRO_IS_LOCK_CACHE = "shiro_is_lock_cache";

	// 用户登录标识keyCACHE
	public final static String SHIRO_LOGIN_USER_CACHE = "shiro_login_user_cache";

	// 登录用户shiro用户对象信息
	public final static String SHIRO_PRINCIPAL_CACHE = "shiro_principal_cache";

	// 登录用户token对象信息缓存名称
	public final static String SHIRO_LOGIN_TOKEN_CACHE = "shiro_login_token_cache";

	// 密码重试缓存
	public final static String SHIRO_PASSWORD_RETRY_CACHE = "shiro_password_retry_cache";
}
