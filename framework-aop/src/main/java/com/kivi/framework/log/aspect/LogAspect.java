package com.kivi.framework.log.aspect;


import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kivi.framework.constant.Constant;
import com.kivi.framework.log.LogManager;
import com.kivi.framework.log.annotation.BizLog;
import com.kivi.framework.log.factory.LogTaskFactory;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.util.kit.StrKit;

/**
 * 日志记录
 *
 */
@Aspect
@Component
public class LogAspect {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    private ThreadLocal<Long> msThreadLocal = new ThreadLocal<Long>(); 

    @Pointcut(value = "@annotation(com.kivi.framework.log.annotation.BizLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

		if (log.isTraceEnabled()) {
			msThreadLocal.set(System.currentTimeMillis());
		}
        //先执行业务
        Object result = point.proceed();
        

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception {

        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();


        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        //获取操作名称
        BizLog annotation = currentMethod.getAnnotation(BizLog.class);
        String bussinessName = annotation.value();
        //String key = annotation.key();
        //String dictClass = annotation.dict();
        boolean isSave = annotation.isSave();

        String msg = StrKit.join(StrKit.AND, params) ;
        if (log.isTraceEnabled()) {
        	log.trace("===={}.{}执行耗时：{}ms，参数：{}",className, methodName, 
        			DateTimeKit.spendMs(msThreadLocal.get()), msg);
        }
        
        if(isSave){
	        // TODO：待修改代码
	        String userId = Constant.SYS_USER;
	
	        LogManager.me().executeLog(LogTaskFactory.bussinessLog(userId, bussinessName, className, methodName, msg));
        }
    }
}