/**
 * 
 */
package com.kivi.framework.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 应用服务状态请求
 * 
 * @author Eric
 *
 */
@Setter
@Getter
@ApiModel( value = "SvrStatusReqDTO", description = "应用服务状态请求" )
public class SvrStatusReqDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
