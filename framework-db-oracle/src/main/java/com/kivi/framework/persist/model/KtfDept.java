package com.kivi.framework.persist.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "KTF_DEPT")
public class KtfDept {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT TO_CHAR(sysdate,yyyymmdd)||SEQ_KTF_COMMON.NEXTVAL AS ID FROM DUAL")
    private String id;

    @Column(name = "NUM")
    private BigDecimal num;

    @Column(name = "PID")
    private BigDecimal pid;

    @Column(name = "PIDS")
    private String pids;

    @Column(name = "SIMPLENAME")
    private String simplename;

    @Column(name = "FULLNAME")
    private String fullname;

    @Column(name = "TIPS")
    private String tips;

    @Column(name = "GMT_CREATE")
    private Date gmtCreate;

    @Column(name = "GMT_UPDATE")
    private Date gmtUpdate;

    @Column(name = "VERSION")
    private BigDecimal version;

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
     * @return NUM
     */
    public BigDecimal getNum() {
        return num;
    }

    /**
     * @param num
     */
    public void setNum(BigDecimal num) {
        this.num = num;
    }

    /**
     * @return PID
     */
    public BigDecimal getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(BigDecimal pid) {
        this.pid = pid;
    }

    /**
     * @return PIDS
     */
    public String getPids() {
        return pids;
    }

    /**
     * @param pids
     */
    public void setPids(String pids) {
        this.pids = pids;
    }

    /**
     * @return SIMPLENAME
     */
    public String getSimplename() {
        return simplename;
    }

    /**
     * @param simplename
     */
    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }

    /**
     * @return FULLNAME
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return TIPS
     */
    public String getTips() {
        return tips;
    }

    /**
     * @param tips
     */
    public void setTips(String tips) {
        this.tips = tips;
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
     * @return VERSION
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}