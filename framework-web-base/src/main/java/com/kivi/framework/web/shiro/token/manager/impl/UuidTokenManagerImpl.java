package com.kivi.framework.web.shiro.token.manager.impl;

import java.util.UUID;

import com.kivi.framework.web.shiro.token.StatelessToken;
import com.kivi.framework.web.shiro.token.manager.AbstractTokenManager;

/**
 * 默认token管理实现类
 *
 */
public class UuidTokenManagerImpl extends AbstractTokenManager {

	@Override
	public String createStringToken(String userCode) {
		// 创建简易的32为uuid
		return UUID.randomUUID().toString().replace("-", "");
	}

	@Override
	public boolean checkToken(StatelessToken model) {

		return super.checkMemoryToken(model);
	}

}
