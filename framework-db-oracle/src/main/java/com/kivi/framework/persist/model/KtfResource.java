package com.kivi.framework.persist.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "KTF_RESOURCE")
public class KtfResource {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT TO_CHAR(sysdate,yyyymmdd)||SEQ_KTF_COMMON.NEXTVAL AS ID FROM DUAL")
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "PCODE")
    private String pcode;

    @Column(name = "PCODES")
    private String pcodes;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "URL")
    private String url;

    @Column(name = "NUM")
    private BigDecimal num;

    @Column(name = "LEVELS")
    private BigDecimal levels;

    @Column(name = "IS_MENU")
    private BigDecimal isMenu;

    @Column(name = "TIPS")
    private String tips;

    @Column(name = "STATUS")
    private BigDecimal status;

    @Column(name = "IS_OPEN")
    private BigDecimal isOpen;

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
     * @return CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return PCODE
     */
    public String getPcode() {
        return pcode;
    }

    /**
     * @param pcode
     */
    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    /**
     * @return PCODES
     */
    public String getPcodes() {
        return pcodes;
    }

    /**
     * @param pcodes
     */
    public void setPcodes(String pcodes) {
        this.pcodes = pcodes;
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
     * @return ICON
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
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
     * @return LEVELS
     */
    public BigDecimal getLevels() {
        return levels;
    }

    /**
     * @param levels
     */
    public void setLevels(BigDecimal levels) {
        this.levels = levels;
    }

    /**
     * @return IS_MENU
     */
    public BigDecimal getIsMenu() {
        return isMenu;
    }

    /**
     * @param isMenu
     */
    public void setIsMenu(BigDecimal isMenu) {
        this.isMenu = isMenu;
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
     * @return STATUS
     */
    public BigDecimal getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    /**
     * @return IS_OPEN
     */
    public BigDecimal getIsOpen() {
        return isOpen;
    }

    /**
     * @param isOpen
     */
    public void setIsOpen(BigDecimal isOpen) {
        this.isOpen = isOpen;
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