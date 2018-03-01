package com.kivi.framework.service;

import java.util.Map;

public interface ErrorCodeService {
    /**
     * 从数据库加载系统错误码
     * 
     * @return
     */
    Map<String, String[]> loadErrorCode();

    /**
     * 保存错误码
     * 
     * @param map
     */
    void saveErrorCode( Map<String, String[]> map );
}
