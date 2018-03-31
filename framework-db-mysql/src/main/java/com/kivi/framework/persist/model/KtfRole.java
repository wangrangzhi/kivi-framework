package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ktf_role")
public class KtfRole {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 序号
     */
    private Integer num;

    /**
     * 父角色id
     */
    private Integer pid;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 部门名称
     */
    @Column(name = "dept_id")
    private Integer deptId;

    /**
     * 提示
     */
    private String tips;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_update")
    private Date gmtUpdate;

    /**
     * 保留字段(暂时没用）
     */
    private Integer version;

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
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取序号
     *
     * @return num - 序号
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置序号
     *
     * @param num 序号
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取父角色id
     *
     * @return pid - 父角色id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父角色id
     *
     * @param pid 父角色id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取部门名称
     *
     * @return dept_id - 部门名称
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * 设置部门名称
     *
     * @param deptId 部门名称
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取提示
     *
     * @return tips - 提示
     */
    public String getTips() {
        return tips;
    }

    /**
     * 设置提示
     *
     * @param tips 提示
     */
    public void setTips(String tips) {
        this.tips = tips;
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
     * @return gmt_update - 更新时间
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

    /**
     * 获取保留字段(暂时没用）
     *
     * @return version - 保留字段(暂时没用）
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置保留字段(暂时没用）
     *
     * @param version 保留字段(暂时没用）
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}