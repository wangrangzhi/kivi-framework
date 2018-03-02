package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ktf_error_code")
public class KtfErrorCode {
    /**
     * 自增Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 错误代码
     */
    @Column(name = "err_code")
    private String errCode;

    /**
     * 错误代码英文别名
     */
    @Column(name = "err_alias")
    private String errAlias;

    /**
     * 错误代码中文说明
     */
    @Column(name = "err_desc")
    private String errDesc;

    /**
     * 错误代码友好提示
     */
    @Column(name = "err_tip")
    private String errTip;

    /**
     * 错误代码分组
     */
    @Column(name = "err_group")
    private String errGroup;

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
     * 获取自增Id
     *
     * @return id - 自增Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增Id
     *
     * @param id 自增Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取错误代码
     *
     * @return err_code - 错误代码
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * 设置错误代码
     *
     * @param errCode 错误代码
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * 获取错误代码英文别名
     *
     * @return err_alias - 错误代码英文别名
     */
    public String getErrAlias() {
        return errAlias;
    }

    /**
     * 设置错误代码英文别名
     *
     * @param errAlias 错误代码英文别名
     */
    public void setErrAlias(String errAlias) {
        this.errAlias = errAlias;
    }

    /**
     * 获取错误代码中文说明
     *
     * @return err_desc - 错误代码中文说明
     */
    public String getErrDesc() {
        return errDesc;
    }

    /**
     * 设置错误代码中文说明
     *
     * @param errDesc 错误代码中文说明
     */
    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    /**
     * 获取错误代码友好提示
     *
     * @return err_tip - 错误代码友好提示
     */
    public String getErrTip() {
        return errTip;
    }

    /**
     * 设置错误代码友好提示
     *
     * @param errTip 错误代码友好提示
     */
    public void setErrTip(String errTip) {
        this.errTip = errTip;
    }

    /**
     * 获取错误代码分组
     *
     * @return err_group - 错误代码分组
     */
    public String getErrGroup() {
        return errGroup;
    }

    /**
     * 设置错误代码分组
     *
     * @param errGroup 错误代码分组
     */
    public void setErrGroup(String errGroup) {
        this.errGroup = errGroup;
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