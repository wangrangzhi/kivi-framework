package com.kivi.framework.persist.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "KTF_RESOURCE")
public class KtfResource {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT TO_CHAR(sysdate,'yyyymmdd')||SEQ_KTF_COMMON.NEXTVAL AS ID FROM DUAL")
    private Long id;

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
    private Integer num;

    @Column(name = "LEVELS")
    private Short levels;

    @Column(name = "IS_MENU")
    private Short isMenu;

    @Column(name = "TIPS")
    private String tips;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "IS_OPEN")
    private Short isOpen;

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
     * @return LEVELS
     */
    public Short getLevels() {
        return levels;
    }

    /**
     * @param levels
     */
    public void setLevels(Short levels) {
        this.levels = levels;
    }

    /**
     * @return IS_MENU
     */
    public Short getIsMenu() {
        return isMenu;
    }

    /**
     * @param isMenu
     */
    public void setIsMenu(Short isMenu) {
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
    public Short getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return IS_OPEN
     */
    public Short getIsOpen() {
        return isOpen;
    }

    /**
     * @param isOpen
     */
    public void setIsOpen(Short isOpen) {
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