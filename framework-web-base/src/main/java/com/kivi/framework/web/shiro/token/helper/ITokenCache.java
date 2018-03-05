package com.kivi.framework.web.shiro.token.helper;

/**
 * Token缓存接口
 *
 */
public interface ITokenCache {

	// 锁定标识
	public final static String LOCKED = "LOCKED";

	// 用户在线
	public final static String ONLINE = "ONLINE";

	// 用户下线
	public final static String OFFLINE = "OFFLINE";

	/**
	 * 获取并设置登录标识
	 * 
	 * @param key
	 * @return
	 */
	public String getAndSetLoginFlag(String key);

	/**
	 * 设置登录标识过期时间
	 * 
	 * @param key
	 * @param seconds
	 */
	public void setLoginFlagExpiration(String key, int seconds);

	/**
	 * 增加并返回登录计数
	 * 
	 * @param key
	 * @return
	 */
	public int getAndIncrementLoginCount(String key);

	/**
	 * 设置登录锁定标识
	 * 
	 * @param key
	 * @param seconds
	 */
	public void setLoginLockFlag(String key, int seconds);

	/**
	 * 获取锁定标识
	 * 
	 * @param key
	 */
	public boolean getLoginLockFlag(String key);

	/**
	 * 根据用户编码获取令牌
	 * 
	 * @param userCode
	 * @return
	 */
	public String getUserToken(String userCode);

	/**
	 * 更新令牌， 每次获取令牌成功时更新令牌失效时间
	 * 
	 * @param userCode
	 * @param token
	 * @param seconds
	 */
	public void updateUserToken(String userCode, String token, long seconds);

	/**
	 * 删除令牌
	 * 
	 * @param userCode
	 */
	public void deleteUserToken(String userCode);

}
