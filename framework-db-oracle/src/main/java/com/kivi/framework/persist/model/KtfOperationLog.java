package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "KTF_OPERATION_LOG")
public class KtfOperationLog {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT TO_CHAR(sysdate,yyyymmddhh24miss)||SEQ_ktf_operation_log.NEXTVAL AS ID FROM DUAL")
    private String id;

    @Column(name = "LOG_TYPE")
    private String logType;

    @Column(name = "LOG_NAME")
    private String logName;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CLASS_NAME")
    private String className;

    @Column(name = "METHOD")
    private String method;

    @Column(name = "SUCCEED")
    private String succeed;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "IP")
    private String ip;

    @Column(name = "GMT_CREATE")
    private Date gmtCreate;

    @Column(name = "GMT_UPDATE")
    private Date gmtUpdate;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return LOG_TYPE
     */
    public String getLogType() {
        return logType;
    }

    /**
     * @param logType
     */
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
     * @return LOG_NAME
     */
    public String getLogName() {
        return logName;
    }

    /**
     * @param logName
     */
    public void setLogName(String logName) {
        this.logName = logName;
    }

    /**
     * @return USER_ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return CLASS_NAME
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return METHOD
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return SUCCEED
     */
    public String getSucceed() {
        return succeed;
    }

    /**
     * @param succeed
     */
    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    /**
     * @return MESSAGE
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return GMT_CREATE
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return GMT_UPDATE
     */
    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * @param gmtUpdate
     */
    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}