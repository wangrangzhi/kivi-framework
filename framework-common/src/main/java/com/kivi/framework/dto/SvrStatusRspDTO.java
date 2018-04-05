/**
 * 
 */
package com.kivi.framework.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 应用服务状态响应体
 * 
 * @author Eric
 *
 */
@Setter
@Getter
@ApiModel( value = "SvrStatusRspDTO", description = "应用服务状态响应体" )
public class SvrStatusRspDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(
                       value = "应用服务状态",
                       required = false,
                       dataType = "String",
                       notes = "应用服务状态",
                       example = "UP" )
    private String            status;

    @ApiModelProperty(
                       value = "应用服务状态说明",
                       required = false,
                       dataType = "String",
                       notes = "应用服务状态说明",
                       example = "正常" )
    private String            desc;

}
