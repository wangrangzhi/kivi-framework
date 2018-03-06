package com.kivi.framework.dto;

import java.io.Serializable;

import com.kivi.framework.component.ErrorKit;
import com.kivi.framework.exception.AppException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 服务方法返回基础bean
 */
@ApiModel( value = "BaseResBean", description = "服务方法返回基础bean" )
@Setter
@Getter
public class BaseResDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** 响应码 */
    @ApiModelProperty(
                       value = "响应码",
                       required = false,
                       dataType = "String",
                       notes = "响应码",
                       example = "S0000000" )
    private String            respCode;

    /** 响应描述 */
    @ApiModelProperty(
                       value = "响应描述",
                       required = false,
                       dataType = "String",
                       notes = "响应描述",
                       example = "成功" )
    private String            respMsg;

    /** 错误提示 */
    @ApiModelProperty(
                       value = "错误提示",
                       required = false,
                       dataType = "String",
                       notes = "错误提示",
                       example = "交易成功" )
    private String            errorTip;

    /** 数据信息 */
    @ApiModelProperty(
                       value = "数据对象",
                       required = false,
                       dataType = "String",
                       notes = "数据对象，用于传输附加信息",
                       example = "成功" )
    private Object            data;

    /** 返回流水号 */
    @ApiModelProperty(
                       value = "返回流水号",
                       required = false,
                       dataType = "String",
                       notes = "返回流水号",
                       example = "" )
    private String            resTranSeqId;

    /** 返回接口版本 */
    @ApiModelProperty(
                       value = "返回接口版本 ",
                       required = false,
                       dataType = "String",
                       notes = "返回接口版本 ",
                       example = "" )
    private String            version;

    /**
     * 设置错误信息
     * 
     * @param e
     */
    public void setException( AppException e ) {
        this.setRespCode(e.getErrorCode());
        this.setRespMsg(e.getErrorMessage());
        this.setErrorTip(e.getErrorTip());
    }

    public void setRespCode( String code ) {
        this.respCode = code;
        this.setRespMsg(ErrorKit.me().getDesc(code));
        this.setErrorTip(ErrorKit.me().getTip(code));
    }

    @Override
    public String toString() {
        return "BaseResBean [respCode=" + respCode + ", respMsg=" + respMsg + ", data=" + data + ", resTranSeqId=" +
                resTranSeqId + ", version=" + version + "]";
    }

}
