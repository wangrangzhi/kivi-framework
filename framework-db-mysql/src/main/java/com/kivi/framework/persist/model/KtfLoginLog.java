package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ktf_login_log")
public class KtfLoginLog {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日志名称
     */
    @Column(name = "LOG_NAME")
    private String logName;

    /**
     * 登录用户ID
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 是否执行成功
     */
    @Column(name = "SUCCEED")
    private String succeed;

    /**
     * 描述消息
     */
    @Column(name = "MESSAGE")
    private String message;

    /**
     * 登录ip
     */
    @Column(name = "IP")
    private String ip;

    /**
     * 登录地区信息，数据格式：country|city
     */
    @Column(name = "REGION_INFO")
    private String regionInfo;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
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
     * 获取登录用户ID
     *
     * @return USER_ID - 登录用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置登录用户ID
     *
     * @param userId 登录用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取是否执行成功
     *
     * @return SUCCEED - 是否执行成功
     */
    public String getSucceed() {
        return succeed;
    }

    /**
     * 设置是否执行成功
     *
     * @param succeed 是否执行成功
     */
    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    /**
     * 获取描述消息
     *
     * @return MESSAGE - 描述消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置描述消息
     *
     * @param message 描述消息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取登录ip
     *
     * @return IP - 登录ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置登录ip
     *
     * @param ip 登录ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取登录地区信息，数据格式：country|city
     *
     * @return REGION_INFO - 登录地区信息，数据格式：country|city
     */
    public String getRegionInfo() {
        return regionInfo;
    }

    /**
     * 设置登录地区信息，数据格式：country|city
     *
     * @param regionInfo 登录地区信息，数据格式：country|city
     */
    public void setRegionInfo(String regionInfo) {
        this.regionInfo = regionInfo;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
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