package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ktf_operation_log")
public class KtfOperationLog {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日志类型
     */
    @Column(name = "LOG_TYPE")
    private String logType;

    /**
     * 日志名称
     */
    @Column(name = "LOG_NAME")
    private String logName;

    /**
     * 用户id
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 类名称
     */
    @Column(name = "CLASS_NAME")
    private String className;

    /**
     * 方法名称
     */
    @Column(name = "METHOD")
    private String method;

    /**
     * 是否成功
     */
    @Column(name = "SUCCEED")
    private String succeed;

    /**
     * 备注
     */
    @Column(name = "MESSAGE")
    private String message;

    /**
     * 请求IP
     */
    @Column(name = "IP")
    private String ip;

    /**
     * 创建时间
     */
    @Column(name = "gmt_CREATE")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_UPDATE")
    private Date gmtUpdate;

    /**
     * 获取主键ID
     *
     * @return ID - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取日志类型
     *
     * @return LOG_TYPE - 日志类型
     */
    public String getLogType() {
        return logType;
    }

    /**
     * 设置日志类型
     *
     * @param logType 日志类型
     */
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
     * 获取日志名称
     *
     * @return LOG_NAME - 日志名称
     */
    public String getLogName() {
        return logName;
    }

    /**
     * 设置日志名称
     *
     * @param logName 日志名称
     */
    public void setLogName(String logName) {
        this.logName = logName;
    }

    /**
     * 获取用户id
     *
     * @return USER_ID - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取类名称
     *
     * @return CLASS_NAME - 类名称
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置类名称
     *
     * @param className 类名称
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 获取方法名称
     *
     * @return METHOD - 方法名称
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置方法名称
     *
     * @param method 方法名称
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取是否成功
     *
     * @return SUCCEED - 是否成功
     */
    public String getSucceed() {
        return succeed;
    }

    /**
     * 设置是否成功
     *
     * @param succeed 是否成功
     */
    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    /**
     * 获取备注
     *
     * @return MESSAGE - 备注
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置备注
     *
     * @param message 备注
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取请求IP
     *
     * @return IP - 请求IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置请求IP
     *
     * @param ip 请求IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_CREATE - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取更新时间
     *
     * @return gmt_UPDATE - 更新时间
     */
    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * 设置更新时间
     *
     * @param gmtUpdate 更新时间
     */
    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}