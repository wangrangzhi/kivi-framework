package com.kivi.framework.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kivi.framework.db.dao.BaseDao;
import com.kivi.framework.persist.mapper.KtfErrorCodeMapperEx;
import com.kivi.framework.persist.model.KtfErrorCode;
import com.kivi.framework.service.ErrorCodeService;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.DateTime;
import com.kivi.framework.util.kit.DateTimeKit;

@Service( "errorCodeService" )
public class ErrorCodeServiceImpl extends BaseDao<KtfErrorCode> implements ErrorCodeService {
    private static final Logger  log = LoggerFactory.getLogger(ErrorCodeServiceImpl.class);

    @Autowired
    private KtfErrorCodeMapperEx ktfErrorCodeMapperEx;

    @Override
    public Map<String, String[]> loadErrorCode() {
        Map<String, String[]> result = new HashMap<String, String[]>();

        List<KtfErrorCode> list = super.queryAll();
        Iterator<KtfErrorCode> it = list.iterator();
        while (it.hasNext()) {
            KtfErrorCode jerr = it.next();
            result.put(jerr.getErrCode(),
                    CollectionKit.wrap(jerr.getErrAlias(), jerr.getErrDesc(), jerr.getErrTip(), jerr.getErrGroup()));

        }
        return result;
    }

    @Override
    public void saveErrorCode( Map<String, String[]> map ) {
        if (map == null || map.isEmpty()) {
            log.warn("需要保存的错误码信息为空，直接返回");
            return;
        }

        DateTime now = DateTimeKit.date();
        List<KtfErrorCode> list = CollectionKit.newArrayList();

        TreeSet<String> keys = new TreeSet<>(map.keySet());
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String[] values = map.get(key);
            KtfErrorCode jerr = new KtfErrorCode();
            jerr.setErrCode(key);
            jerr.setErrAlias(values[0]);
            jerr.setErrDesc(values[1]);
            jerr.setErrTip(values[2]);
            jerr.setErrGroup(values[3]);
            jerr.setGmtCreate(now);
            jerr.setGmtUpdate(now);
            list.add(jerr);
        }

        ktfErrorCodeMapperEx.insertBatch(list);
    }

}
