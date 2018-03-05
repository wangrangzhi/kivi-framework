package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ktf_seq_mutex")
public class KtfSeqMutex {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 业务名称
     */
    private String name;

    /**
     * ID值
     */
    private Long count;

    /**
     * 最大值
     */
    @Column(name = "max_count")
    private Long maxCount;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
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
     * 获取业务名称
     *
     * @return name - 业务名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置业务名称
     *
     * @param name 业务名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取ID值
     *
     * @return count - ID值
     */
    public Long getCount() {
        return count;
    }

    /**
     * 设置ID值
     *
     * @param count ID值
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * 获取最大值
     *
     * @return max_count - 最大值
     */
    public Long getMaxCount() {
        return maxCount;
    }

    /**
     * 设置最大值
     *
     * @param maxCount 最大值
     */
    public void setMaxCount(Long maxCount) {
        this.maxCount = maxCount;
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
}