package com.kivi.framework.dao;

import com.kivi.framework.constant.enums.KtfDBStatus;

public interface TestDatabaseDao {

    /**
     * 校验数据查询是否正常
     * 
     * @return
     */
    KtfDBStatus validation();
}
