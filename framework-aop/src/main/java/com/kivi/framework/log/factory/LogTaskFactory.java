package com.kivi.framework.log.factory;


import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.db.dao.IDaoEx;
import com.kivi.framework.log.constant.enums.LogStatus;
import com.kivi.framework.log.constant.enums.LogType;
import com.kivi.framework.util.ToolUtil;
import com.kivi.framework.util.kit.BeanKit;
import com.kivi.framework.vo.log.LoginLogVO;
import com.kivi.framework.vo.log.OperationLogVO;

/**
 * 日志操作任务创建工厂
 *
 */
public class LogTaskFactory {

    private static Logger   logger          = LoggerFactory.getLogger(LogTaskFactory.class);
    @SuppressWarnings("rawtypes")
	private static IDaoEx   loginLogDao     = SpringContextHolder.getBean("loginLogDaoImpl");
    @SuppressWarnings("rawtypes")
	private static IDaoEx 	operationLogDao = SpringContextHolder.getBean("operationLogDaoImpl");

    public static TimerTask loginLog(final String userId, final String ip) {
        return new TimerTask() {
        	@SuppressWarnings("unchecked")
            @Override
            public void run() {
                try {
                	LoginLogVO loginLog = LogFactory.createLoginLog(LogType.LOGIN, userId, null, ip);
                	if(loginLogDao != null)
                		loginLogDao.saveNotNull(BeanKit.beanToMap(loginLog));
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginLog(final String username, final String msg, final String ip) {
        return new TimerTask() {
            @SuppressWarnings("unchecked")
			@Override
            public void run() {
            	LoginLogVO loginLog = LogFactory.createLoginLog(
                        LogType.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip);
                try {
                	if(loginLogDao != null)
                		loginLogDao.saveNotNull(BeanKit.beanToMap(loginLog));
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final String userId, final String ip) {
        return new TimerTask() {
            @SuppressWarnings("unchecked")
			@Override
            public void run() {
            	LoginLogVO loginLog = LogFactory.createLoginLog(LogType.EXIT, userId, null,ip);
                try {
                	if(loginLogDao != null)
                		loginLogDao.saveNotNull(BeanKit.beanToMap(loginLog));
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask bussinessLog(final String userId, final String bussinessName, final String clazzName, final String methodName, final String msg) {
        return new TimerTask() {
            @SuppressWarnings("unchecked")
			@Override
            public void run() {
                OperationLogVO operationLog = LogFactory.createOperationLog(
                        LogType.BUSSINESS, userId, bussinessName, clazzName, methodName, msg, LogStatus.SUCCESS);
                try {
                	if(operationLogDao != null)
                		operationLogDao.saveNotNull(BeanKit.beanToMap(operationLog));
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final String userId, final Exception exception) {
        return new TimerTask() {
            @SuppressWarnings("unchecked")
			@Override
            public void run() {
                String msg = ToolUtil.getExceptionMsg(exception);
                OperationLogVO operationLog = LogFactory.createOperationLog(
                        LogType.EXCEPTION, userId, "", null, null, msg, LogStatus.FAIL);
                try {
                	if(operationLogDao != null)
                		operationLogDao.saveNotNull(BeanKit.beanToMap(operationLog));
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
