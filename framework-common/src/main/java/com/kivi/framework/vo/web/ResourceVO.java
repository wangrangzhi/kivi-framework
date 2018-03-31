package com.kivi.framework.vo.web;

import com.kivi.framework.util.kit.DateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceVO {
    private Long id;

    private String code;

    private String pcode;

    private String pcodes;

    private String name;

    private String icon;

    private String url;

    private Short num;

    private Short levels;

    private Short isMenu;

    private String tips;

    private Short status;

    private Short isOpen;

    private DateTime createTime;

    private DateTime updateTime;

    private String checked;
}