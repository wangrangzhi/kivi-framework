package com.kivi.framework.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kivi.framework.constant.enums.KtfDBStatus;
import com.kivi.framework.dao.TestDatabaseDao;
import com.kivi.framework.persist.mapper.KtfTestDBMapper;
import com.kivi.framework.util.kit.StrKit;

@Repository
public class TestDatabaseDaoImpl implements TestDatabaseDao {

    private static final Logger log = LoggerFactory.getLogger(TestDatabaseDaoImpl.class);

    @Autowired
    private KtfTestDBMapper     ktfTestDBMapper;

    @Override
    public KtfDBStatus validation() {
        KtfDBStatus result = KtfDBStatus.DOWN;
        try {
            String val = ktfTestDBMapper.validationQuery();
            if (StrKit.isNotEmpty(val) && "1".equals(val))
                result = KtfDBStatus.UP;
        }
        catch (Exception e) {
            log.error("数据库异常", e);
        }
        return result;
    }

}
