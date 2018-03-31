package com.kivi.framework.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口请求基础DTO
 */
@ApiModel( value = "BaseReqDTO", description = "接口请求基础DTO" )
@Setter
@Getter
public class BaseReqDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** 请求流水号 */
    @ApiModelProperty(
                       value = "请求流水号",
                       required = false,
                       dataType = "String",
                       notes = "请求流水号",
                       example = "" )
    private String            tranSeqId;

    /** 交易代码 */
    @ApiModelProperty(
                       value = "交易代码",
                       required = false,
                       dataType = "String",
                       notes = "交易代码",
                       example = "" )
    private String            tranCode;

    /** 交易时间 */
    @ApiModelProperty(
                       value = "交易时间，格式:yyyymmddHHMMSS",
                       required = false,
                       dataType = "String",
                       notes = "交易时间，格式:yyyymmddHHMMSS",
                       example = "20180306165501" )
    private String            trantime;

    /** 接口版本 */
    @ApiModelProperty(
                       value = "接口版本 ",
                       required = false,
                       dataType = "String",
                       notes = "接口版本 ",
                       example = "1.0.0" )
    private String            version;

    /** 签名 */
    @ApiModelProperty(
                       value = "接口请求签名",
                       required = false,
                       dataType = "String",
                       example = "" )
    private String            sign;

}
