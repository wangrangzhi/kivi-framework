package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "KTF_ERROR_CODE")
public class KtfErrorCode {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT TO_CHAR(sysdate,'yyyymmdd')||SEQ_KTF_COMMON.NEXTVAL AS ID FROM DUAL")
    private Long id;

    @Column(name = "ERR_CODE")
    private String errCode;

    @Column(name = "ERR_ALIAS")
    private String errAlias;

    @Column(name = "ERR_DESC")
    private String errDesc;

    @Column(name = "ERR_TIP")
    private String errTip;

    @Column(name = "ERR_GROUP")
    private String errGroup;

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
     * @return ERR_CODE
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @param errCode
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * @return ERR_ALIAS
     */
    public String getErrAlias() {
        return errAlias;
    }

    /**
     * @param errAlias
     */
    public void setErrAlias(String errAlias) {
        this.errAlias = errAlias;
    }

    /**
     * @return ERR_DESC
     */
    public String getErrDesc() {
        return errDesc;
    }

    /**
     * @param errDesc
     */
    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    /**
     * @return ERR_TIP
     */
    public String getErrTip() {
        return errTip;
    }

    /**
     * @param errTip
     */
    public void setErrTip(String errTip) {
        this.errTip = errTip;
    }

    /**
     * @return ERR_GROUP
     */
    public String getErrGroup() {
        return errGroup;
    }

    /**
     * @param errGroup
     */
    public void setErrGroup(String errGroup) {
        this.errGroup = errGroup;
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