package com.kivi.framework.persist.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "KTF_NOTICE")
public class KtfNotice {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT TO_CHAR(sysdate,yyyymmdd)||SEQ_KTF_COMMON.NEXTVAL AS ID FROM DUAL")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TYPE")
    private BigDecimal type;

    @Column(name = "GMT_CREATE")
    private Date gmtCreate;

    @Column(name = "GMT_UPDATE")
    private Date gmtUpdate;

    @Column(name = "CREATER")
    private BigDecimal creater;

    @Column(name = "CONTENT")
    private String content;

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
     * @return TITLE
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return TYPE
     */
    public BigDecimal getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(BigDecimal type) {
        this.type = type;
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

    /**
     * @return CREATER
     */
    public BigDecimal getCreater() {
        return creater;
    }

    /**
     * @param creater
     */
    public void setCreater(BigDecimal creater) {
        this.creater = creater;
    }

    /**
     * @return CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}