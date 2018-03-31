package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ktf_dict")
public class KtfDict {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 排序
     */
    private Integer num;

    /**
     * 父级字典
     */
    private Long pid;

    /**
     * 名称
     */
    private String name;

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
     * 获取排序
     *
     * @return num - 排序
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置排序
     *
     * @param num 排序
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取父级字典
     *
     * @return pid - 父级字典
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 设置父级字典
     *
     * @param pid 父级字典
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
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
}