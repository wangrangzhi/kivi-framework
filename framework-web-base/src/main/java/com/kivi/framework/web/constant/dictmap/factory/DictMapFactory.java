package com.kivi.framework.web.constant.dictmap.factory;

import com.kivi.framework.constant.GlobalErrorConst;
import com.kivi.framework.exception.AppException;
import com.kivi.framework.web.constant.dictmap.base.AbstractDictMap;
import com.kivi.framework.web.constant.dictmap.base.SystemDict;

/**
 * 字典的创建工厂
 *
 */
public class DictMapFactory {

    private static final String basePath = "com.framework.web.constant.dictmap.";

    /**
     * 通过类名创建具体的字典类
     */
    @SuppressWarnings( "unchecked" )
    public static AbstractDictMap createDictMap( String className ) {
        if ("SystemDict".equals(className)) {
            return new SystemDict();
        }
        else {
            try {
                Class<AbstractDictMap> clazz = (Class<AbstractDictMap>) Class.forName(basePath + className);
                return clazz.newInstance();
            }
            catch (Exception e) {
                throw new AppException(GlobalErrorConst.E_OBJECT_INSTANCE, e);
            }
        }
    }
}
