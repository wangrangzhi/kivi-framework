package com.kivi.framework.log.factory;


import com.kivi.framework.log.constant.enums.LogStatus;
import com.kivi.framework.log.constant.enums.LogType;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.vo.log.LoginLogVO;
import com.kivi.framework.vo.log.OperationLogVO;

/**
 * 日志对象创建工厂
 *
 */
public class LogFactory {

    /**
     * 创建操作日志
     *
     */
    public static OperationLogVO createOperationLog(LogType logType, String userId, String bussinessName, String clazzName, String methodName, String msg, LogStatus succeed) {
    	OperationLogVO operationLog = new OperationLogVO();
        operationLog.setLogType(logType.getMessage());
        operationLog.setLogName(bussinessName);
        operationLog.setUserId(userId);
        operationLog.setClassName(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setCreateTime(DateTimeKit.date());
        operationLog.setUpdateTime(operationLog.getCreateTime());
        operationLog.setSucceed(succeed.getMessage());
        operationLog.setMessage(msg);
        return operationLog;
    }

    /**
     * 创建登录日志
     *
     */
    public static LoginLogVO createLoginLog(LogType logType, String userId, String msg,String ip) {
    	LoginLogVO loginLog = new LoginLogVO();
        loginLog.setLogName(logType.getMessage());
        loginLog.setUserId(userId);
        loginLog.setCreateTime(DateTimeKit.date());
        loginLog.setUpdateTime(loginLog.getCreateTime());
        loginLog.setSucceed(LogStatus.SUCCESS.getMessage());
        loginLog.setIp(ip);
        loginLog.setMessage(msg);
        return loginLog;
    }
}
