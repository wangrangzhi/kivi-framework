package com.kivi.framework.web.vo.api;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.kivi.framework.component.ErrorKit;
import com.kivi.framework.constant.GlobalErrorConst;
import com.kivi.framework.exception.AppException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回对象Result封装
 * 
 */

@ApiModel( value = "JsonResult", description = "传输JSON对象数据模型" )
@Setter
@Getter
public class JsonResult<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     * 
     */
    @ApiModelProperty( value = "结果状态码", required = true )
    private String            status;
    /**
     * 提示消息
     */
    @ApiModelProperty( value = "结果提示信息", required = true )
    private String            message          = "";

    /**
     * 数据对象
     */
    @ApiModelProperty( value = "传输数据对象", required = true )
    private T                 data;

    /**
     * token信息
     */
    @ApiModelProperty( value = "token", required = true )
    private String            token;

    public JsonResult() {
        this(null, ErrorKit.me().getDesc(GlobalErrorConst.SUCCESS), GlobalErrorConst.SUCCESS);
    }

    public JsonResult( T data, String message ) {
        this(data, message, GlobalErrorConst.SUCCESS);
    }

    public JsonResult( String message, String status ) {
        this(null, message, status);
    }

    public JsonResult( T data, String message, String status ) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public void setResult( String status ) {
        this.status = status;
        this.message = ErrorKit.me().getDesc(status);
    }

    public void setResult( AppException e ) {
        this.status = e.getErrorCode();
        this.message = e.getErrorMessage();
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

}
