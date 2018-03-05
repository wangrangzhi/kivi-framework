package com.kivi.framework.persist.mapper;

import org.apache.ibatis.annotations.Param;

import com.kivi.framework.web.vo.UserVO;

public interface KtfUserMapperEx {

	UserVO selectUserByAccount(@Param("account") String account);
}
