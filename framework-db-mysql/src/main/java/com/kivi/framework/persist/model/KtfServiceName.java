package com.kivi.framework.persist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table( name = "ktf_service_name" )
public class KtfServiceName {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long    id;

    /**
     * service唯一标签
     */
    private String  sid;

    /**
     * service名字
     */
    private String  name;

    /**
     * 主机IP
     */
    private String  host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 服务地址
     */
    private String  uri;

    /**
     * 业务类型
     */
    @Column( name = "biz_type" )
    private String  bizType;

    /**
     * 服务序号
     */
    @Column( name = "slot_id" )
    private Short   slotId;

    /**
     * 状态，00：离线 01：在线
     */
    private String  status;

    @Column( name = "gmt_create" )
    private Date    gmtCreate;

    @Column( name = "gmt_update" )
    private Date    gmtUpdate;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id
     *            主键id
     */
    public void setId( Long id ) {
        this.id = id;
    }

    /**
     * 获取service唯一标签
     *
     * @return sid - service唯一标签
     */
    public String getSid() {
        return sid;
    }

    /**
     * 设置service唯一标签
     *
     * @param sid
     *            service唯一标签
     */
    public void setSid( String sid ) {
        this.sid = sid;
    }

    /**
     * 获取service名字
     *
     * @return name - service名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置service名字
     *
     * @param name
     *            service名字
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * 获取主机IP
     *
     * @return host - 主机IP
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置主机IP
     *
     * @param host
     *            主机IP
     */
    public void setHost( String host ) {
        this.host = host;
    }

    /**
     * 获取端口
     *
     * @return port - 端口
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 设置端口
     *
     * @param port
     *            端口
     */
    public void setPort( Integer port ) {
        this.port = port;
    }

    /**
     * 获取服务地址
     *
     * @return uri - 服务地址
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置服务地址
     *
     * @param uri
     *            服务地址
     */
    public void setUri( String uri ) {
        this.uri = uri;
    }

    /**
     * 获取业务类型
     *
     * @return biz_type - 业务类型
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 设置业务类型
     *
     * @param bizType
     *            业务类型
     */
    public void setBizType( String bizType ) {
        this.bizType = bizType;
    }

    /**
     * 获取服务序号
     *
     * @return index - 服务序号
     */
    public Short getSlotId() {
        return slotId;
    }

    /**
     * 设置服务序号
     *
     * @param index
     *            服务序号
     */
    public void setSlotId( Short slotId ) {
        this.slotId = slotId;
    }

    /**
     * 获取状态，00：离线 01：在线
     *
     * @return status - 状态，00：离线 01：在线
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态，00：离线 01：在线
     *
     * @param status
     *            状态，00：离线 01：在线
     */
    public void setStatus( String status ) {
        this.status = status;
    }

    /**
     * @return gmt_create
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     */
    public void setGmtCreate( Date gmtCreate ) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return gmt_update
     */
    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * @param gmtUpdate
     */
    public void setGmtUpdate( Date gmtUpdate ) {
        this.gmtUpdate = gmtUpdate;
    }
}
