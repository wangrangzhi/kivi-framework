package com.kivi.framework.persist.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "KTF_ROLE_RESOURCE")
public class KtfRoleResource {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT TO_CHAR(sysdate,yyyymmdd)||SEQ_KTF_COMMON.NEXTVAL AS ID FROM DUAL")
    private String id;

    @Column(name = "ROLE_ID")
    private BigDecimal roleId;

    @Column(name = "RESOURCE_ID")
    private BigDecimal resourceId;

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
     * @return ROLE_ID
     */
    public BigDecimal getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    /**
     * @return RESOURCE_ID
     */
    public BigDecimal getResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId
     */
    public void setResourceId(BigDecimal resourceId) {
        this.resourceId = resourceId;
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