package com.kivi.framework.web.constant.dictmap.factory;

import java.lang.reflect.Method;

import com.kivi.framework.constant.GlobalErrorConst;
import com.kivi.framework.exception.AppException;
import com.kivi.framework.web.constant.factory.ConstantFactory;
import com.kivi.framework.web.constant.factory.IConstantFactory;

/**
 * 字段的包装创建工厂
 *
 */
public class DictFieldWarpperFactory {

    public static Object createFieldWarpper( Object field, String methodName ) {
        IConstantFactory me = ConstantFactory.me();
        try {
            Method method = IConstantFactory.class.getMethod(methodName, field.getClass());
            Object result = method.invoke(me, field);
            return result;
        }
        catch (Exception e) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, Integer.class);
                Object result = method.invoke(me, Integer.parseInt(field.toString()));
                return result;
            }
            catch (Exception e1) {
                throw new AppException(GlobalErrorConst.E_OBJECT_CONVERT);
            }
        }
    }

}
