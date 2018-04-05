package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "KTF_ROLE")
public class KtfRole {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT TO_CHAR(sysdate,'yyyymmdd')||SEQ_KTF_COMMON.NEXTVAL AS ID FROM DUAL")
    private Long id;

    @Column(name = "NUM")
    private Integer num;

    @Column(name = "PID")
    private Long pid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DEPT_ID")
    private Long deptId;

    @Column(name = "TIPS")
    private String tips;

    @Column(name = "GMT_CREATE")
    private Date gmtCreate;

    @Column(name = "GMT_UPDATE")
    private Date gmtUpdate;

    @Column(name = "VERSION")
    private Integer version;

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
     * @return NUM
     */
    public Integer getNum() {
        return num;
    }

    /**
     * @param num
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * @return PID
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Long pid) {
        this.pid = pid;
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
     * @return DEPT_ID
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}