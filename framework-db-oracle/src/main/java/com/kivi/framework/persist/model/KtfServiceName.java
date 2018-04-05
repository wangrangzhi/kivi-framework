package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "KTF_SERVICE_NAME")
public class KtfServiceName {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT TO_CHAR(sysdate,'yyyymmdd')||SEQ_KTF_COMMON.NEXTVAL AS ID FROM DUAL")
    private Long id;

    @Column(name = "SID")
    private String sid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HOST")
    private String host;

    @Column(name = "PORT")
    private Integer port;

    @Column(name = "URI")
    private String uri;

    @Column(name = "BIZ_TYPE")
    private String bizType;

    @Column(name = "SLOT_ID")
    private Short slotId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "GMT_CREATE")
    private Date gmtCreate;

    @Column(name = "GMT_UPDATE")
    private Date gmtUpdate;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return SID
     */
    public String getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return HOST
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return PORT
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return URI
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return BIZ_TYPE
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * @param bizType
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * @return SLOT_ID
     */
    public Short getSlotId() {
        return slotId;
    }

    /**
     * @param slotId
     */
    public void setSlotId(Short slotId) {
        this.slotId = slotId;
    }

    /**
     * @return STATUS
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
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